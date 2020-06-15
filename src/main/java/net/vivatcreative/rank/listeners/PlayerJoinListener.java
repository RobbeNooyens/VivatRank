package net.vivatcreative.rank.listeners;


import net.vivatcreative.core.permissions.CorePermission;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private static final int DEDICATED_ONLINE_TIME = (20 * 60 * 60 * 100); // 100h

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if (e.getPlayer().getStatistic(Statistic.PLAY_ONE_TICK) > DEDICATED_ONLINE_TIME)
            CorePermission.EQUIP_TITLE.give(e.getPlayer(), "dedicated");
    }
}
