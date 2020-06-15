package net.vivatcreative.rank.api;

import net.vivatcreative.core.connections.VivatConnection;
import net.vivatcreative.rank.VivatRank;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class RankConnection implements VivatConnection  {

    private final Set<String> commands = new HashSet<>();

    public Object get(String s, Object... objects) {
        return null;
    }

    public void set(String s, Object... objects) {}

    public JavaPlugin getPlugin() {
        return VivatRank.get();
    }

    public void onReload() {}

    public void addCommand(String s) {commands.add(s);}

    public Set<String> getRegisteredCommands() {
        return commands;
    }
}
