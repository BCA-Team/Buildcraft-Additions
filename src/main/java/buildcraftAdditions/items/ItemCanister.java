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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

public class ItemCanister extends ItemFluidContainer {

	public IIcon overlay;
	public String name;

	public ItemCanister(String name, int canisterCapacity) {
		super(0);
		setMaxStackSize(4);
		setCreativeTab(BuildcraftAdditions.bcaCannisters);
		setUnlocalizedName(name);
		setCapacity(canisterCapacity);
		setTextureName("bcadditions:" + name);
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
		if (fStack == null)
			list.add(String.format(Utils.localize("tooltip.FluidCapacity"), capacity / 1000));
		else
			list.add(String.format(Utils.localize("tooltip.storedFluid"), fStack.amount, fStack.getFluid().getLocalizedName(fStack)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		super.registerIcons(register);
		overlay = RenderUtils.registerIcon(register, "fluidOverlay");
	}

	public ItemStack getFilledItemStack(FluidStack fluidStack) {
		ItemStack itemStack = new ItemStack(this);
		itemStack.stackTagCompound = new NBTTagCompound();
		NBTTagCompound fluidTag = fluidStack.writeToNBT(new NBTTagCompound());

		if (fluidStack.amount > capacity)
			fluidTag.setInteger("Amount", capacity);

		itemStack.getTagCompound().setTag("Fluid", fluidTag);

		return itemStack;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		super.getSubItems(item, tab, list);
		for (Fluid fluid : FluidRegistry.getRegisteredFluids().values())
			if (fluid != null)
				list.add(getFilledItemStack(new FluidStack(fluid, getCapacity())));
	}
}
