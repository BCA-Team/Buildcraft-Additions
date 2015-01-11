package buildcraftAdditions.creative;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;


public class TabDusts extends CreativeTabs {

	public TabDusts() {
		super("bcaDusts");
	}

	@Override
	public Item getTabIconItem() {
		return GameRegistry.findItem("bcadditions", "dustDiamond");
	}
}
