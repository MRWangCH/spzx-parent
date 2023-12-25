package com.atguigu.spzx.order.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.feign.cart.CartFeignClient;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.feign.user.UserFeignClient;
import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.order.OrderLog;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.mapper.OrderInfoMapper;
import com.atguigu.spzx.order.mapper.OrderItemMapper;
import com.atguigu.spzx.order.mapper.OrderLogMapper;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    //确认下单
    @Override
    public TradeVo getTread() {
        //远程调用获取购物车中的选中商品列表
        List<CartInfo> cartInfoList = cartFeignClient.getAllChecked();
        //创建订单项集合用于封装订单项
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartInfo cartInfo : cartInfoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        }

        //获取订单支付总金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }

        TradeVo tradeVo = new TradeVo();
        tradeVo.setOrderItemList(orderItemList);
        tradeVo.setTotalAmount(totalAmount);
        return tradeVo;
    }


    //提交订单
    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        //1 orderInfoDto获取所有订单项list List<OrderItem>
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        //2 判断List<OrderItem>为空抛出异常
        if (CollectionUtils.isEmpty(orderItemList)){
            throw  new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        //3 校验商品的库存是否充足
        //遍历List<OrderItem>，得到每个orderItem
        for (OrderItem orderItem : orderItemList) {
            //根据商品skuid获取sku信息，远程调用获取商品sku信息，包含（库存量）
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            //校验每个orderItem的库存是否充足
            if (productSku == null){
                throw new GuiguException(ResultCodeEnum.DATA_ERROR);
            }
            if (orderItem.getSkuNum().intValue() > productSku.getStockNum().intValue()){
                throw new GuiguException(ResultCodeEnum.STOCK_LESS);
            }

        }

        //4 添加数据到order_info表
        //封装数据到order_info对象

        OrderInfo orderInfo = new OrderInfo();
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        //订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());

        //封装收获地址信息
        Long userAddressId = orderInfoDto.getUserAddressId();
        //远程调用 获取用户的收货信息
        UserAddress userAddress = userFeignClient.getUserAddress(userAddressId);

        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);

        orderInfoMapper.save(orderInfo);


        //5 添加数据到order_item表
        //添加list<OrderItem>里面数据，把每个集合orderitem添加表
        for (OrderItem orderItem : orderItemList){
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        }
        //6 添加数据到order_log表
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);

        // 7 远程调用 把生成订单的商品从购物车删除
        cartFeignClient.deleteChecked();
        //8 返回订单id
        return orderInfo.getId();
    }


    //获取订单信息
    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return orderInfoMapper.getById(orderId);
    }
}
