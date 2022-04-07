package net.purefunc.c2c.lottery.schedule

import net.purefunc.core.ext.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class OrderTasks {

    // TODO
//    @Scheduled(cron = "*/10 * * * * *")
//    fun update() =
//        runBlocking {
//            contentImageRepository.refreshAllMap(
//                contentImageDao.findAll()
//                    .toList()
//                    .associateBy(
//                        { it.id!! },
//                        { ContentImageDto(it.id!!, it.uri) },
//                    )
//            )
//        }
}