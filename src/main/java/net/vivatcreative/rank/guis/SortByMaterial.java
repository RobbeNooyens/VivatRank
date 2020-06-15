package net.vivatcreative.rank.guis;

import net.vivatcreative.core.gui.GuiItem;

import java.util.Comparator;


public class SortByMaterial implements Comparator<GuiItem> {

	@Override
	public int compare(GuiItem item1, GuiItem item2) {
		return -(item1.getMaterial().compareTo(item2.getMaterial()));
	}

}
