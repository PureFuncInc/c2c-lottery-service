package net.purefunc.c2c.lottery.data.dto.request

import arrow.core.Either.Companion.catch
import net.purefunc.c2c.lottery.data.enu.BetItemStatus
import net.purefunc.c2c.lottery.data.repository.GameRepository

data class GameModifyStatusReq(

    val gameUuid: String,

    val betItemUuids: List<String>,

    val status: BetItemStatus,
) {

    companion object {
        fun modify(gameRepository: GameRepository, betItemUuids: List<String>, email: String) =
            catch {

            }
    }

    fun modify(gameRepository: GameRepository, email: String) =
        catch {

        }
}