package net.purefunc.c2c.lottery.data.enu

enum class BetType {

    // 主隊贏
    WIN,

    // 客隊贏
    LOST,

    // 平手
    DRAW,

    // 主隊讓分贏
    HANDICAP_WIN,

    // 客隊讓分贏
    HANDICAP_LOST,

    // 主隊平或贏
    HOST_DRAW_OR_WIN,

    // 客隊平或贏
    GUEST_DRAW_OR_WIN,

    // 主隊或客隊贏
    HOST_OR_GUEST_WIN,

    // 半全場
    HALF_ALL,

    // 總分開大
    SCORE_SUM_BIG,

    // 總分開小
    SCORE_SUM_SMALL,

    // 正確比分
    SCORE,

    // 半全場正確比分
    SCORE_HALF_ALL,
}
