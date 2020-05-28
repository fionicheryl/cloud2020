package com.fion.springcloud.payment.dao;

import com.fion.springcloud.entity.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaymentDao {

    @Insert({
            "insert into payment(serial) values(#{serial})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Payment payment);

    @Select({
            "select id, serial from payment where id = #{id}"
    })
    Payment selectById(@Param("id") Long id);

    @Select({
            "select id, serial from payment"
    })
    List<Payment> selectAll();
}
