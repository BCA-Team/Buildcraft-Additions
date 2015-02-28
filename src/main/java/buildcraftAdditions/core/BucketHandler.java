package buildcraftAdditions.core;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;

import buildcraftAdditions.client.render.items.BucketItemRenderer;
import buildcraftAdditions.items.ItemBucketBCA;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public final class BucketHandler {

	private static final BucketHandler INSTANCE = new BucketHandler();

	private final HashMap<Block, ItemStack> bucketMap = new HashMap<Block, ItemStack>();

	private BucketHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void registerBucket(Fluid fluid, ItemStack bucket) {
		if (fluid != null && fluid.getBlock() != null && bucket != null && bucket.getItem() != null && bucket.stackSize > 0) {
			FluidContainerRegistry.registerFluidContainer(fluid, bucket.copy(), FluidContainerRegistry.EMPTY_BUCKET);
			INSTANCE.bucketMap.put(fluid.getBlock(), bucket.copy());
		}
	}

	public static void registerRenderers() {
		for (ItemStack stack : INSTANCE.bucketMap.values())
			if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBucketBCA)
				MinecraftForgeClient.registerItemRenderer(stack.getItem(), BucketItemRenderer.INSTANCE);
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event) {
		ItemStack bucket = bucketMap.get(event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ));

		if (bucket != null && event.world.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ) == 0) {
			event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
			event.result = bucket.copy();
			event.setResult(Event.Result.ALLOW);
		}
	}

}
