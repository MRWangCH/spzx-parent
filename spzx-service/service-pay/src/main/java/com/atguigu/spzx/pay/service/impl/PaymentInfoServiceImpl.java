package com.atguigu.spzx.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.feign.order.OrderFeignClient;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.pay.mapper.PaymentInfoMapper;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private OrderFeignClient orderFeignClient;

    //保存支付记录
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        //1 查询支付信息数据，如果已经已经存在了就不用进行保存(一个订单支付失败以后可以继续支付)
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(orderNo);
        //2 判断支付记录是否存在
        if (paymentInfo == null){
            //TODO 远程调用获取订单信息
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
            //Orderinfo获得的数据封装到paymentinfo中去
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);

            //添加
            paymentInfoMapper.save(paymentInfo);
        }
        return paymentInfo;
    }


    //支付完成后更新状态
    @Override
    public void updatePaymentStatus(Map<String, String> map) {
        //1 根据订单号查新支付信息
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(map.get("out_trade_no"));
        //2 判断支付记录已经完成了支付，
        if (paymentInfo.getPaymentStatus() == 1){
            return;
        }
        //3 没有完成才更新
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(map));
        paymentInfoMapper.updatePaymentInfo(paymentInfo);

        //TODO 更新订单状态

        //TODO 更新销量
    }
}
