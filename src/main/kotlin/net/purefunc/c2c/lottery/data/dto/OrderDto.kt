package net.purefunc.c2c.lottery.data.dto

import net.purefunc.c2c.lottery.data.enu.OrderType
import net.purefunc.c2c.lottery.data.repository.OrderRepository

data class OrderDto(

    val orderType: OrderType,

    val multiple: Int,

    val betItemUuids: List<String>,
) {

    companion object {
        suspend fun queryByUuid(orderRepository: OrderRepository, uuid: String) = orderRepository.findByUuid(uuid)

        suspend fun queryAll(orderRepository: OrderRepository, page: Int, size: Int) =
            orderRepository.findAll(page, size)
    }

    suspend fun add(orderRepository: OrderRepository, email: String) = orderRepository.save(this, email)
}
