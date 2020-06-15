package net.vivatcreative.rank.guis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.vivatcreative.core.gui.BlueprintGui;
import net.vivatcreative.core.gui.GuiItem;
import net.vivatcreative.core.players.Users;
import net.vivatcreative.core.ranks.BuildRank;
import net.vivatcreative.core.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RankGui extends BlueprintGui {

	private final BuildRank rank;

	public RankGui(Player p, OfflinePlayer target) {
		super(p);
		this.setTitle("&8Ranks");
		this.rank = Users.get(target).getBuildRank();
		this.setItem(10, getRankItem(BuildRank.DEFAULT));
		this.setItem(11, getRankItem(BuildRank.BRONZE));
		this.setItem(12, getRankItem(BuildRank.SILVER));
		this.setItem(13, getRankItem(BuildRank.GOLD));
		this.setItem(14, getRankItem(BuildRank.DIAMOND));
		this.setItem(15, getRankItem(BuildRank.EMERALD));
		this.setItem(16, getRankItem(BuildRank.MASTER));
	}

	public static boolean show(Player p) {
		return show(p, p);
	}

	public static boolean show(Player p, OfflinePlayer t) {
		Objects.requireNonNull(p);
		Objects.requireNonNull(t);
		new RankGui(p, t).open();
		return true;
	}

	private GuiItem getRankItem(BuildRank r){
		GuiItem item = new GuiItem.Builder().name(r.getName(true)).material(Material.CONCRETE).data(r.getBlockData()).hideFlags()
				.lore(getLore(r)).click((p) -> {
					if(r != BuildRank.DEFAULT)
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvtp " + p.getName() + " " + r.toString().toLowerCase());
				}).build();
		item.setGlowing(rank == r);
		return item;
	}

	public List<String> getLore(BuildRank r) {
		List<String> lore = new ArrayList<>();
		if(r == BuildRank.DEFAULT) return lore;
		lore.add(" ");
		lore.add(TextUtil.toColor("&e> Click to teleport"));
		return lore;
	}

	@Override
	public void updateGui() {
	}

}
