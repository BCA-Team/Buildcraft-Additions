package buildcraftAdditions.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

import net.minecraftforge.fluids.Fluid;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.entities.EntityLaserShot;
import buildcraftAdditions.inventories.InventoryKineticMultiTool;
import buildcraftAdditions.inventories.InventoryPortableLaser;
import buildcraftAdditions.inventories.containers.ContainerBasicCoil;
import buildcraftAdditions.inventories.containers.ContainerChargingStation;
import buildcraftAdditions.inventories.containers.ContainerCoolingTower;
import buildcraftAdditions.inventories.containers.ContainerFluidicCompressor;
import buildcraftAdditions.inventories.containers.ContainerHeatedFurnace;
import buildcraftAdditions.inventories.containers.ContainerItemSorter;
import buildcraftAdditions.inventories.containers.ContainerKEB;
import buildcraftAdditions.inventories.containers.ContainerKineticMultiTool;
import buildcraftAdditions.inventories.containers.ContainerMachineConfigurator;
import buildcraftAdditions.inventories.containers.ContainerPipeColoringTool;
import buildcraftAdditions.inventories.containers.ContainerPortableLaser;
import buildcraftAdditions.inventories.containers.ContainerRefinery;
import buildcraftAdditions.items.Tools.ItemKineticMultiTool;
import buildcraftAdditions.items.Tools.ItemPipeColoringTool;
import buildcraftAdditions.items.Tools.ItemPortableLaser;
import buildcraftAdditions.reference.Variables;
import buildcraftAdditions.tileEntities.Bases.TileKineticEnergyBufferBase;
import buildcraftAdditions.tileEntities.TileBasicCoil;
import buildcraftAdditions.tileEntities.TileChargingStation;
import buildcraftAdditions.tileEntities.TileCoolingTower;
import buildcraftAdditions.tileEntities.TileFluidicCompressor;
import buildcraftAdditions.tileEntities.TileHeatedFurnace;
import buildcraftAdditions.tileEntities.TileItemSorter;
import buildcraftAdditions.tileEntities.TileRefinery;
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

public class CommonProxy implements IGuiHandler {
	public static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/villagers/Engineer.png");

	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityLaserShot.class, "bcaLaserShot", EntityRegistry.findGlobalUniqueEntityId(), BuildcraftAdditions.instance, 64, 10, true);
	}

	public void registerRenderers() {
	}

	public void registerBucketRenderer() {
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

	public void cloneFluidTextures(Fluid source, Fluid target) {
	}

	public EntityPlayer getClientPlayer() {
		return null;
	}

	public World getClientWorld() {
		return null;
	}

	public int addArmor(String name) {
		return 0;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (Variables.Gui.values()[ID]) {
			case FLUIDIC_COMPRESSOR:
				if (tile instanceof TileFluidicCompressor)
					return new ContainerFluidicCompressor(player.inventory, (TileFluidicCompressor) tile);
			case CHARGING_STATION:
				if (tile instanceof TileChargingStation)
					return new ContainerChargingStation(player.inventory, (TileChargingStation) tile);
			case KINETIC_MULTI_TOOL:
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemKineticMultiTool)
					return new ContainerKineticMultiTool(player.inventory, new InventoryKineticMultiTool(player.getHeldItem()));
			case PORTABLE_LASER:
				if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPortableLaser)
					return new ContainerPortableLaser(player.inventory, new InventoryPortableLaser(player.getHeldItem()));
			case HEATED_FURNACE:
				if (tile instanceof TileHeatedFurnace)
					return new ContainerHeatedFurnace(player.inventory, (TileHeatedFurnace) tile);
			case BASIC_COIL:
				if (tile instanceof TileBasicCoil)
					return new ContainerBasicCoil(player.inventory, (TileBasicCoil) tile);
			case KEB:
				if (tile instanceof TileKineticEnergyBufferBase)
					return new ContainerKEB(player, (TileKineticEnergyBufferBase) tile);
			case MACHINE_CONFIGURATOR:
				return new ContainerMachineConfigurator(player.inventory, tile);
			case REFINERY:
				return new ContainerRefinery(player.inventory, (TileRefinery) tile);
			case COOLING_TOWER:
				return new ContainerCoolingTower(player.inventory, (TileCoolingTower) tile);
			case ITEM_SORTER:
				return new ContainerItemSorter(player.inventory, (TileItemSorter) tile);
			case PIPE_COLORING_TOOL:
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemPipeColoringTool)
					return new ContainerPipeColoringTool();
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
