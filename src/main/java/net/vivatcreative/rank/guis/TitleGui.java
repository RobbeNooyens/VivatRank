package net.vivatcreative.rank.guis;

import net.vivatcreative.core.exceptions.InvalidTitleException;
import net.vivatcreative.core.gui.BlueprintGui;
import net.vivatcreative.core.gui.GuiItem;
import net.vivatcreative.core.messages.Message;
import net.vivatcreative.core.players.Users;
import net.vivatcreative.core.players.VivatPlayer;
import net.vivatcreative.core.ranks.StaffRank;
import net.vivatcreative.core.ranks.Title;
import net.vivatcreative.core.ranks.TitleManager;
import net.vivatcreative.core.utils.Logger;
import net.vivatcreative.core.utils.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class TitleGui extends BlueprintGui {

	private final Player t;
	private String rankPrefix;

	public TitleGui(Player p, Player t) {
		super(p);
		this.t = t;
		this.setTitle("&8Choose a title!");
		this.setItems(getItems().toArray(new GuiItem[0]));
	}

	public static boolean show(Player p) {
		return show(p, p);
	}

	public static boolean show(Player p, Player target) {
		Objects.requireNonNull(p);
		Objects.requireNonNull(target);
		new TitleGui(p, target).open();
		return true;
	}

	private List<GuiItem> getItems(){
		VivatPlayer target = Users.get(t);
		rankPrefix = target.getStaffRank() == StaffRank.NONE ? target.getBuildRank().getName(true) : target.getStaffRank().getName(true);
		List<GuiItem> list = new ArrayList<>();
		TitleManager.getTitles().forEach(title -> list.add(getTitleItem(title)));
		list.sort(new SortByMaterial());
		return list;
	}

	private GuiItem getTitleItem(Title title) {
		final String titlePrefix = title.getName(true);
		int matData = 0;
		boolean hasPerm = title.canEquip(t);

		Material material;
		if (hasPerm) {
			material = Material.CONCRETE;
			matData = title.getBlockData();
		} else {
			material = Material.STONE;
		}
		return new GuiItem.Builder().name("&7Title " + titlePrefix + " &7Information:")
				.lore(getItemLore(title)).click((p) -> {
					if (hasPerm) {
						try {
							Users.get(t).setTitle(title.toString());
						} catch (InvalidTitleException e) {
							Logger.exception(e);
						}
						if (t.getUniqueId().equals(getPlayer().getUniqueId())) {
							Message.send(p, "title_set", "%title%", titlePrefix);
						} else {
							Message.send(t, "title_set", "%title%", titlePrefix);
							Message.send(p, "title_set_other", "%title%", titlePrefix, "%player%", t.getName());
						}
					} else {
						Message.send(p, "no_access_title");
					}
				}).material(material).data(matData).build();
	}

	private List<String> getItemLore(Title title) {
		String isAchieved = !title.canEquip(t) ? "&cNo" : "&aYes";
		List<String> lore = new ArrayList<>();
		lore.add(" ");
		lore.add("&7Unlocked: " + isAchieved);
		for (String s : title.getDescription())
			lore.add("&7" + s);
		lore.add(" ");
		lore.add("&7[" + title.getName(true) + " &7- " + rankPrefix + "&7] &f" + t.getDisplayName());
		for(int i = 0; i < lore.size(); i++)
			lore.set(i, TextUtil.toColor(lore.get(i)));
		return lore;
	}

	@Override
	public void updateGui() {
	}

}
