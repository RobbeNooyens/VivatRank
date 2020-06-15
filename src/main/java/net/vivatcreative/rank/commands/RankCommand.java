package net.vivatcreative.rank.commands;

import net.vivatcreative.core.messages.CoreMessage;
import net.vivatcreative.core.messages.Message;
import net.vivatcreative.core.players.Users;
import net.vivatcreative.core.ranks.BuildRank;
import net.vivatcreative.core.ranks.StaffRank;
import net.vivatcreative.core.utils.PlayerUtil;
import net.vivatcreative.rank.api.RankMessage;
import net.vivatcreative.rank.api.RankPermission;
import net.vivatcreative.rank.guis.RankGui;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return Message.send(sender, CoreMessage.SHOULD_BE_PLAYER);
		Player p = (Player) sender;

		if (args.length == 0) return RankGui.show(p);
		if (args.length == 1) {
			OfflinePlayer t = PlayerUtil.getOfflinePlayer(args[0]);
			if (t == null) return Message.send(p, CoreMessage.TARGET_NOT_FOUND);
			return RankGui.show(p, t);
		}

		if (!args[0].equalsIgnoreCase("set") || args.length < 4)
			return Message.send(p, CoreMessage.COMMAND_USAGE, "%usage%", "/rank set <build/staff> <player> <rankname>");

		String rankName = args[3].toUpperCase();
		OfflinePlayer target = PlayerUtil.getOfflinePlayer(args[2]);
		if (target == null) return Message.send(p, CoreMessage.TARGET_NOT_FOUND);

		if (args[1].equalsIgnoreCase("build")) {
			BuildRank rank;
			try {
				rank = BuildRank.valueOf(rankName);
			} catch (IllegalArgumentException e) {
				return Message.send(p, RankMessage.INVALID_RANK);
			}
			if (!RankPermission.SET_BUILDRANK.hasAndWarn(p, rank.toString().toLowerCase())) return true;
			Users.get(target).setBuildRank(rank);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("lp user %s parent settrack build %s",
					target.getName(), rank.toString().toLowerCase()));
			if(target.isOnline()) Message.send((CommandSender) target, RankMessage.BUILDRANK_SET, "%rank%", rank.getName(true));
			if (target.getUniqueId().equals(p.getUniqueId())) return true;
			return Message.send(p, RankMessage.RANK_SET_OTHER, "%rank%", rank.getName(true), "%player%", target.getName());

		} else if (args[1].equalsIgnoreCase("staff")) {
			StaffRank rank = StaffRank.fromString(rankName);
			if (RankPermission.SET_STAFFRANK.hasAndWarn(p, rank.toString().toLowerCase())) {
				Users.get(target).setStaffRank(rank);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("lp user %s parent settrack staff %s",
						target.getName(), rank.toString().toLowerCase()));
				Message.send((CommandSender) target, RankMessage.STAFFRANK_SET, "%rank%", rank.getName(true));
				if (target.getUniqueId().equals(p.getUniqueId())) return true;
				return Message.send(p, RankMessage.RANK_SET_OTHER, "%rank%", rank.getName(true), "%player%", target.getName());
			}
		}

		return Message.send(p, CoreMessage.COMMAND_USAGE, "%usage%", "/rank set <build/staff> [player] <rankname>");
	}

}
