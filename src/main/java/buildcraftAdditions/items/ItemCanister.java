package buildcraftAdditions.items;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.utils.Utils;

public class ItemCanister extends ItemFluidContainer {

	public IIcon overlay;
	public String name;

	public ItemCanister(String name, int canisterCapacity) {
		super(0);
		this.setMaxStackSize(4);
		this.setCreativeTab(BuildcraftAdditions.bcadditions);
		this.setUnlocalizedName(name);
		this.setCapacity(canisterCapacity);
		this.name = name;
	}

	public void setFluid(ItemStack stack, FluidStack fStack) {
		if (stack == null)
			return;
		if (!(stack.getItem() instanceof ItemCanister))
			return;
		ItemCanister canister = (ItemCanister) stack.getItem();
		canister.drain(stack, canister.getFluid(stack).amount, true);
		canister.fill(stack, fStack, true);
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
			list.add("Currently stores " + Integer.toString(fStack.amount) + " mB of " + fStack.getFluid().getLocalizedName(fStack));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon("bcadditions:" + name);
		this.overlay = iconRegister.registerIcon("bcadditions:fluidOverlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		return itemIcon;
	}
}
