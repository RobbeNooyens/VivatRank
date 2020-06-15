package net.vivatcreative.rank.commands;

import net.vivatcreative.core.exceptions.InvalidTitleException;
import net.vivatcreative.core.messages.CoreMessage;
import net.vivatcreative.core.messages.Message;
import net.vivatcreative.core.players.Users;
import net.vivatcreative.core.ranks.Title;
import net.vivatcreative.core.ranks.TitleManager;
import net.vivatcreative.core.utils.Logger;
import net.vivatcreative.core.utils.PlayerUtil;
import net.vivatcreative.rank.api.RankMessage;
import net.vivatcreative.rank.api.RankPermission;
import net.vivatcreative.rank.guis.TitleGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TitleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player)) return Message.send(sender, CoreMessage.SHOULD_BE_PLAYER);
		if (!command.getName().equalsIgnoreCase("title")) return true;
		Player player = (Player) sender;
		Player target;

		if (args.length == 0)
			return TitleGui.show(player);
		
		if (args.length == 1) {
			target = PlayerUtil.getOnlinePlayer(args[0]);
			if (target == null) return Message.send(sender, CoreMessage.TARGET_NOT_FOUND); // Target should be online for permission check in title gui
			if (RankPermission.OPEN_OTHERS_TITLE_GUI.hasAndWarn(player)) return TitleGui.show(player, target);
		}

		if (args.length < 3 || !args[0].equalsIgnoreCase("set"))
			return Message.send(player, CoreMessage.COMMAND_USAGE, "%usage%", "/title set <player> <title>");

		target = PlayerUtil.getOnlinePlayer(args[1]);
		if (target == null) return Message.send(sender, CoreMessage.TARGET_NOT_FOUND);
		Title title;
		try {
			title = TitleManager.fromString(args[2]);
		} catch (InvalidTitleException e) {
			return Message.send(sender, RankMessage.INVALID_TITLE);
		}
		if (!RankPermission.SET_TITLE.hasAndWarn(player, title.getKey().toLowerCase()))
			return true;
		try {
			Users.get(target).setTitle(title.getKey());
		} catch (InvalidTitleException e) {
			Logger.exception(e);
		}
		return Message.send((CommandSender) target, RankMessage.TITLE_SET, "%title%", title.getName(true));
	}
}
