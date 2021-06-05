package org.rhett.mysecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Secured({"ROLE_ADMIN","ROLE_ORDER"})
    @RequestMapping("/info")
    public String info() {
        return "Order Controller ...";
    }
}
