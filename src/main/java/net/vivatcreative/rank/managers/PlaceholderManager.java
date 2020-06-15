package net.vivatcreative.rank.managers;

import net.vivatcreative.core.exceptions.InvalidTitleException;
import net.vivatcreative.core.players.Users;
import net.vivatcreative.core.players.VivatPlayer;
import net.vivatcreative.core.ranks.BuildRank;
import net.vivatcreative.core.ranks.StaffRank;
import net.vivatcreative.core.utils.Logger;
import net.vivatcreative.core.utils.TextUtil;
import net.vivatcreative.rank.VivatRank;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderManager extends PlaceholderExpansion {

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "rank";
    }

    @Override
    public String getAuthor() {
        return VivatRank.get().getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return VivatRank.get().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){
    	VivatPlayer p = Users.get(player);
    	
    	BuildRank buildRank = p.getBuildRank();
    	StaffRank staffRank = p.getStaffRank();
		String title = null;
		try {
			title = p.getTitle().getName(true);
		} catch (InvalidTitleException e) {
			Logger.exception(e);
		}
		String build = buildRank.getName(true);
    	String staff = staffRank.getName(true);
    	String rankPrefix = (staffRank == StaffRank.NONE ? build : staff); // Staff? -> staffRank | Player? -> buildRank
    	
    	switch(identifier.toLowerCase()) {
    	case "title":
    		return title;
    	case "build":
    		return build;
    	case "staff":
    		return staff;
    	case "rank":
    		return rankPrefix;
    	case "prefix":
    		return TextUtil.toColor("&7[" + title + " &7- " + rankPrefix + "&7]");
    	case "colour":
    		return buildRank.getChatColor();
    	}

    	return TextUtil.toColor("&4Error"); // Invalid placeholder (f.e. %rt_placeholder3%)
    }
}
