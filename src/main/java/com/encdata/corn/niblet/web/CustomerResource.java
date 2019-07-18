package com.encdata.corn.niblet.web;

import com.encdata.corn.niblet.domain.Order;
import com.encdata.corn.niblet.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: jinsi
 * @Date: 2019/7/17 09:40
 * @Description:
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/order")
    public void create(@RequestBody Order order){
        customerService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Map userInfo(@PathVariable Long id){
        return customerService.userInfo(id);
    }
}
