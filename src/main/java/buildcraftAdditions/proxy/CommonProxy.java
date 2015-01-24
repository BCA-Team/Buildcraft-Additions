package buildcraftAdditions.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;

import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.server.FMLServerHandler;

import net.minecraftforge.fluids.Fluid;

import buildcraftAdditions.villager.ComponentPowerPlant;
import buildcraftAdditions.villager.PowerPlantCreationHandeler;
import buildcraftAdditions.villager.VillagerTradeHandler;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

public class CommonProxy {
	public static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/villagers/Engineer.png");

	public void registerRenderers() {
	}

	public void addPowerplant() {
		VillagerRegistry.instance().registerVillagerId(457);
		VillagerRegistry.instance().registerVillageTradeHandler(457, new VillagerTradeHandler());
		VillagerRegistry.instance().registerVillageCreationHandler(new PowerPlantCreationHandeler());
		try {
			MapGenStructureIO.func_143031_a(ComponentPowerPlant.class, "bcadditions:powerplant");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public EntityPlayer getPlayer(String name) {
		return FMLServerHandler.instance().getServer().getEntityWorld().getPlayerEntityByName(name);
	}

	public EntityPlayer getPlayer() {
		return null;
	}

	public void cloneFluidTextures(Fluid source, Fluid target) {

	}

}
