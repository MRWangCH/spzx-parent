package com.atguigu.spzx.pay.mapper;

import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInfoMapper {
    //查询支付信息数据
    PaymentInfo getByOrderNo(String orderNo);
    //添加
    void save(PaymentInfo paymentInfo);
    //更新订单状态
    void updatePaymentInfo(PaymentInfo paymentInfo);
}
