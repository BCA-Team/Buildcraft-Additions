package buildcraftAdditions.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.utils.RenderUtils;
import buildcraftAdditions.utils.Utils;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ItemBucketBCA extends ItemBucket {

	private final FluidStack fluid;
	@SideOnly(Side.CLIENT)
	private IIcon overlay;

	public ItemBucketBCA(Fluid fluid) {
		super(fluid.getBlock());
		this.fluid = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
		setContainerItem(Items.bucket);
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("bcaBucket." + fluid.getName());
		GameRegistry.registerItem(this, fluid.getName() + "Bucket");
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return Utils.localizeFormatted("item.bcaBucket.name", fluid.getLocalizedName());
	}

	public FluidStack getFluid() {
		return fluid;
	}

	public IIcon getOverlay() {
		return overlay;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		overlay = RenderUtils.registerIcon(register, "bucketOverlay");
	}
}
