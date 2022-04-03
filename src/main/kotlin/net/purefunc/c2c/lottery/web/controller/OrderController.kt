package net.purefunc.c2c.lottery.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import net.purefunc.c2c.lottery.data.dto.OrderDto
import net.purefunc.c2c.lottery.data.repository.OrderRepository
import net.purefunc.c2c.lottery.ext.return200
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1.0/orders")
@SecurityRequirement(name = "BearerAuth")
class OrderController(
    private val orderRepository: OrderRepository,
) {

    @Operation(summary = "根據 uuid 查詢投注單")
    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('USER')")
    suspend fun getOrdersByUuid(
        @PathVariable uuid: String,
        principal: Principal,
    ) = OrderDto.queryByUuid(orderRepository, uuid).return200()

    @Operation(summary = "查詢所有投注單")
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    suspend fun getOrders(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        principal: Principal,
    ) = OrderDto.queryAll(orderRepository, page, size).return200()

    @Operation(summary = "提交投注")
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    suspend fun postOrders(
        @RequestBody orderDto: OrderDto,
        principal: Principal,
    ) = orderDto.add(orderRepository, principal.name).return200()
}