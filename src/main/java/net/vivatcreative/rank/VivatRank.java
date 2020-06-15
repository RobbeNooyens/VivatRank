package net.vivatcreative.rank;

import net.vivatcreative.core.connections.ConnectionManager;
import net.vivatcreative.core.files.FileManager;

import net.vivatcreative.core.messages.MessageHelper;
import net.vivatcreative.core.ranks.TitleManager;
import net.vivatcreative.core.utils.Logger;
import net.vivatcreative.rank.api.RankConnection;
import net.vivatcreative.rank.commands.RankCommand;
import net.vivatcreative.rank.commands.TitleCommand;
import net.vivatcreative.rank.listeners.PlayerJoinListener;
import net.vivatcreative.rank.managers.PlaceholderManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class VivatRank extends JavaPlugin {

    @Override
    public void onEnable() {
        // Third party API's
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            (new PlaceholderManager()).register();

        // Events
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // Vivat Core
        MessageHelper.register(FileManager.getFile(this, "messages.yml", false));
        RankConnection rankConnection = new RankConnection();
        rankConnection.registerCommand("rank", new RankCommand());
        rankConnection.registerCommand("title", new TitleCommand());
        ConnectionManager.register(rankConnection);

        TitleManager.loadFromDatabase();

        Logger.info("VivatRank >> Titles loaded from file.");
    }

    public static VivatRank get() {
        return JavaPlugin.getPlugin(VivatRank.class);
    }

    @Override
    public void onDisable() {
        TitleManager.clearEntries();
        FileManager.removeFile(this, "titles.yml", null);
    }
}
