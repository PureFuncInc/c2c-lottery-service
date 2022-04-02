package net.purefunc.c2c.lottery.web.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1.0/orders")
class OrderController {

    @GetMapping
    fun getOrders(
        principal: Principal,
    ) = ""

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    fun postOrders(
        principal: Principal,
    ) = ""
}