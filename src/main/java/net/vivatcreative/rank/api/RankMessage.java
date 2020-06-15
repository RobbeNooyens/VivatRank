package net.vivatcreative.rank.api;

import net.vivatcreative.core.messages.VivatMessage;

public enum RankMessage implements VivatMessage {
    INVALID_RANK,
    INVALID_TITLE,
    BUILDRANK_SET,
    STAFFRANK_SET,
    TITLE_SET,
    TITLE_SET_OTHER,
    RANK_SET_OTHER,
    NO_ACCESS_TITLE,
    TITLE_UNLOCKED;
}
