package net.purefunc.c2c.lottery.data.dto

import io.swagger.v3.oas.annotations.media.Schema
import net.purefunc.c2c.lottery.data.enu.OrderType
import net.purefunc.c2c.lottery.data.repository.OrderRepository

data class OrderDto(

    @Schema(description = "串關類型", example = "C_1_1")
    val orderType: OrderType,

    @Schema(description = "倍數", example = "10")
    val multiple: Int,

    @Schema(description = "投注項目 UUID", example = "c83fc1f8-cb41-40f0-8b74-7df30a046885")
    val betItemUuids: List<String>,
) {

    companion object {
        suspend fun queryByUuid(orderRepository: OrderRepository, uuid: String) = orderRepository.findByUuid(uuid)

        suspend fun queryAll(orderRepository: OrderRepository, page: Int, size: Int) =
            orderRepository.findAll(page, size)
    }

    suspend fun add(orderRepository: OrderRepository, email: String) = orderRepository.save(this, email)
}
