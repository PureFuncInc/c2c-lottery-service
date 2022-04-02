package net.purefunc.c2c.lottery.data.vo

import io.swagger.v3.oas.annotations.media.Schema
import net.purefunc.c2c.lottery.data.enu.BetType
import java.math.BigDecimal

data class BetItemVo(

    @Schema(description = "投注項目類型", example = "SCORE_SUM")
    val type: BetType,

    @Schema(description = "投注項目數值", example = "240")
    val value: String,

    @Schema(description = "投注項目賠率", example = "2.5")
    val odds: BigDecimal,
)
