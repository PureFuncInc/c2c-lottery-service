package net.purefunc.c2c.lottery.data.enu

enum class BetType {

    // 主隊贏
    WIN,

    // 客隊贏
    LOST,

    // 主隊讓分贏
    HANDICAP_WIN,

    // 客隊讓分贏
    HANDICAP_LOST,

    // 總分開大
    SCORE_SUM_BIG,

    // 總分開小
    SCORE_SUM_SMALL,
}
