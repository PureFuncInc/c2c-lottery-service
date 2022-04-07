package net.purefunc.c2c.lottery.data.dto

import io.swagger.v3.oas.annotations.media.Schema
import net.purefunc.c2c.lottery.data.repository.OrderRepository

data class OrderDto(

    @Schema(description = "串關類型")
    val combination: List<String>,

    @Schema(description = "倍數", example = "10")
    val multiple: Int,

    @Schema(description = "投注項目 UUID")
    val betItemUuids: List<String>,
) {

    companion object {
        suspend fun queryByUuid(
            orderRepository: OrderRepository,
            uuid: String,
        ) = orderRepository.findByUuid(uuid)

        suspend fun queryAll(orderRepository: OrderRepository, page: Int, size: Int) =
            orderRepository.findAll(page, size)

        suspend fun removeByUuid(
            orderRepository: OrderRepository,
            uuid: String,
            email: String,
        ) = orderRepository.deleteByUuid(uuid, email)
    }

    suspend fun add(orderRepository: OrderRepository, email: String) = orderRepository.save(this, email)
}
