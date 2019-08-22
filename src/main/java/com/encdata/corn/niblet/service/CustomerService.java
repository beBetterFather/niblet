package com.encdata.corn.niblet.service;

import com.encdata.corn.niblet.domain.Order;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: jinsi
 * @Date: 2019/7/17 09:43
 * @Description:
 */
@Service
public class CustomerService {

//    @Autowired
//    @Qualifier("orderJdbcTemplate")
//    private JdbcTemplate orderJdbcTemplate;
//
//    @Autowired
//    @Qualifier("userJdbcTemplate")
//    private JdbcTemplate userJdbcTemplate;
//
//    private final String SQL_UPDATE_DEPOSIT = "UPDATE customer SET deposit = deposit - ? where id = ?";
//
//    private final String SQL_CREATE_ORDER = "INSERT INTO customer_order (customer_id, title, amount) VALUES (?, ?, ?)";
//
//    @Transactional
//    public void createOrder(Order order){
//
//        userJdbcTemplate.update(SQL_UPDATE_DEPOSIT, order.getAmount(), order.getCustomerId());
//        if(order.getTitle().contains("error1")){
//            throw new RuntimeException("error1");
//        }
//
//        orderJdbcTemplate.update(SQL_CREATE_ORDER, order.getCustomerId(), order.getTitle(), order.getAmount());
//        if(order.getTitle().contains("error2")){
//            throw new RuntimeException("error2");
//        }
//
//    }
//
//    public Map userInfo(long customerId){
//        Map customer = userJdbcTemplate.queryForMap("SELECT * FROM customer where id = "+customerId);
//        List orders = orderJdbcTemplate.queryForList("SELECT *FROM customer_order where customer_id = "+customerId);
//
//        Map result = Maps.newHashMap();
//        result.put("customer", customer);
//        result.put("orders", orders);
//        return result;
//    }


}
