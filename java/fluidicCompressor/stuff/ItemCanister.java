package fluidicCompressor.stuff;

import buildcraft.core.CreativeTabBuildCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

import java.util.List;

import fluidicCompressor.core.Utils;

public class ItemCanister extends ItemFluidContainer {

	public IIcon overlay;
	public String name;

	public ItemCanister(String name, int canisterCapacity) {
		super(0);
		this.setMaxStackSize(4);
		this.setCreativeTab(CreativeTabBuildCraft.TIER_3.get());
		this.setUnlocalizedName(name);
		this.setCapacity(canisterCapacity);
		this.name = name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean visible) {
		FluidStack fStack = Utils.getFluidStackFromItemStack(itemStack);
		int buckets = this.capacity / 1000;
		if (buckets < 1)
			return;
		if (fStack == null && buckets == 1)
			list.add("Can hold " + buckets + " bucket of fluid");
		else if (fStack == null && buckets > 1)
			list.add("Can hold " + buckets + " buckets of fluid");
		else
			list.add("Currently stores " + Integer.toString(fStack.amount)+ " mB of " + fStack.getFluid().getLocalizedName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon("fluidicCompressor:" + name);
		this.overlay = iconRegister.registerIcon("fluidicCompressor:fluidOverlay");
		System.out.println(itemIcon);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		return itemIcon;
	}
}
