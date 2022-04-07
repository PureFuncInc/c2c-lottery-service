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
        suspend fun modify(gameRepository: GameRepository, betItemUuids: List<String>, email: String) =
            catch {
                gameRepository.updateBetItemsResult(betItemUuids, email)

//                thread {
//                    gameRepository.updateGameResult(betItemUuids[0])
//                }.start()
            }
    }

    suspend fun modifyGame(gameRepository: GameRepository, email: String) =
        catch {
            gameRepository.updateGameStatus(gameUuid, email, status)
        }

    suspend fun modifyBetItems(gameRepository: GameRepository, email: String) =
        catch {
            gameRepository.updateBetItemsStatus(betItemUuids, email, status)
        }
}