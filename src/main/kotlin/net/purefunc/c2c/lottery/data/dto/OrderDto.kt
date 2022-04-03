package net.purefunc.c2c.lottery.data.dto

import net.purefunc.c2c.lottery.data.enu.OrderType
import net.purefunc.c2c.lottery.data.repository.OrderRepository

data class OrderDto(

    val orderType: OrderType,

    val multiple: Int,

    val betItemUuids: List<String>,
) {

    suspend fun add(orderRepository: OrderRepository, email: String) = orderRepository.save(this, email)
}
