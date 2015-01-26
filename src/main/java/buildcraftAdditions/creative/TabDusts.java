package buildcraftAdditions.creative;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import buildcraftAdditions.api.item.BCAItemManager;
import buildcraftAdditions.api.item.dust.IDust;


public class TabDusts extends CreativeTabs {

	public TabDusts() {
		super("bcaDusts");
		setBackgroundImageName("item_search.png");
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}

	@Override
	public ItemStack getIconItemStack() {
		IDust dust = BCAItemManager.dusts.getDust(3);
		if (dust != null) {
			ItemStack stack = dust.getDustStack();
			if (stack != null) {
				return stack;
			}
		}
		return new ItemStack(Items.diamond);
	}

	@Override
	public boolean hasSearchBar() {
		return true;
	}
}