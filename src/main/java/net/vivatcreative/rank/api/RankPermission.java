package net.vivatcreative.rank.api;

import net.vivatcreative.core.permissions.VivatPermission;

public enum RankPermission implements VivatPermission {
    /**
     * Players with this permission can set people's staffrank.
     */
    SET_STAFFRANK("rank.set.staff.%s"),
    /**
     * Players with this permission can set people's buildrank.
     */
    SET_BUILDRANK("rank.set.build.%s"),
    /**
     * Players with this permission can set people's title.
     */
    SET_TITLE("rank.set.title.%s"),
    /**
     * Players with this permission can open and set other people's title.
     */
    OPEN_OTHERS_TITLE_GUI("title.gui.others");

    private final String node;

    RankPermission(String node) {
        this.node = node;
    }

    @Override
    public String getNode() {
        return node;
    }
}
