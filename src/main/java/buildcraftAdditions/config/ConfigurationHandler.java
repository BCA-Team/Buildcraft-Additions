package buildcraftAdditions.config;

import java.io.File;
import java.util.HashSet;

import net.minecraftforge.common.config.Configuration;

import buildcraftAdditions.core.VersionCheck;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ConfigurationHandler {

	public static final int[] powerDifficultyModifiers = new int[4];
	private static final HashSet<String> enabledFeatures = new HashSet<String>();
	public static Configuration configFile;

	public static boolean
			shouldPrintChangelog,
			shouldRegisterDusts,
			powerloss,
			eurekaIntegration,
			dusterParticles,
			forceEnableBCRefinery;

	public static int
			basePowerModifier,
			entityHitModifier,
			hoeCost,
			toolHarvestLevel,
			KEB1powerloss, KEB2powerloss,
			KEB3powerloss,
			heatedFurnaceHeatRequired,
			basicCoilHeat,
			lavaCoilHeat,
			kineticCoilHeatModifier,
			portableLaserPowerUse,
			portableLaserLaserPower,
			portableLaserEntityBurnTime,
			capacityPipeColoringTool,
			maxTransferColoringTool,
			energyUseColoringTool,
			capacityPowerCapsuleTier1,
			maxTransferPowerCapsuleTier1,
			capacityPowerCapsuleTier2,
			maxTransferPowerCapsuleTier2,
			capacityPowerCapsuleTier3,
			maxTransferPowerCapsuleTier3,
			capacityKEBTier1,
			maxTransferKEBTier1,
			capacityKEBTier2,
			maxTransferKEBTier2,
			capacityKEBTier3,
			maxTransferKEBTier3,
			capacityChargingStation,
			maxTransferChargingStation,
			capacityFluidicCompressor,
			maxTransferFluidicCompressor,
			capacityMechanicalDuster,
			maxTransferMechanicalDuster,
			energyUseMechanicalDuster,
			capacityRefinery,
			maxTransferRefinery,
			energyUseRefineryMultiplier,
			refineryAutoExportMaxTransfer,
			refineryAutoImportMaxTransfer,
			refinerySpeed1SpeedModifier,
			refinerySpeed2SpeedModifier,
			refinerySpeed3SpeedModifier,
			coolingTowerAutoExportMaxTransfer,
			coolingTowerAutoImportMaxTransfer,
			coolingTowerSpeed1SpeedModifier,
			coolingTowerSpeed2SpeedModifier,
			coolingTowerSpeed3SpeedModifier,
			particleCount;

	public static float
			portableLaserEntityDamage,
			toolEfficiencyPickaxe,
			toolEfficiencyShovel,
			toolEfficiencyAxe,
			toolEfficiencyAreaMultiplier,
			entityDamage,
			coolingTowerEfficiency1CoolingModifier,
			coolingTowerEfficiency2CoolingModifier,
			coolingTowerEfficiency3CoolingModifier;

	public static double
			refineryEfficiency1EnergyCostModifier,
			refineryEfficiency2EnergyCostModifier,
			refineryEfficiency3EnergyCostModifier,
			refinerySpeed1EnergyCostModifier,
			refinerySpeed2EnergyCostModifier,
			refinerySpeed3EnergyCostModifier;

	public static void init(File file) {
		configFile = new Configuration(file);
		readConfig();
	}

	public static boolean enabled(String name) {
		return enabledFeatures.contains(name);
	}

	private static void registerFeature(String name) {
		if (configFile.get("Feature", name, true).setRequiresMcRestart(true).getBoolean()) {
			enabledFeatures.add(name);
		}
	}

	public static void readConfig() {
		configFile.addCustomCategoryComment("Updates", "Section about updates");
		if (configFile.get("Updates", "shouldCheckForUpdates", true).getBoolean())
			VersionCheck.start();
		shouldPrintChangelog = configFile.get("Updates", "shouldPrintOutChangelog", false).getBoolean();

		configFile.addCustomCategoryComment("Power Usage", "Configuration stuff for your Kinetic Multi-Tool");
		powerDifficultyModifiers[0] = configFile.get("Power Usage", "PeacefulDifficultyModifier", 1).setMinValue(0).getInt();
		powerDifficultyModifiers[1] = configFile.get("Power Usage", "EasyDifficultyModifier", 2).setMinValue(0).getInt();
		powerDifficultyModifiers[2] = configFile.get("Power Usage", "NormalDifficultyModifier", 3).setMinValue(0).getInt();
		powerDifficultyModifiers[3] = configFile.get("Power Usage", "HardDifficultyModifier", 4).setMinValue(0).getInt();
		basePowerModifier = configFile.get("Power Usage", "BaseModifier", 10).setMinValue(0).getInt();
		entityHitModifier = configFile.get("Power Usage", "EntityHitModifier", 10).setMinValue(0).getInt();
		entityDamage = (float) configFile.get("Power Usage", "EntityDamage", 7D).setMinValue(0).setMaxValue(Float.MAX_VALUE).getDouble();
		hoeCost = configFile.get("Power Usage", "HoeCost", 5).setMinValue(0).getInt();
		toolEfficiencyPickaxe = (float) configFile.get("Power Usage", "ToolEfficiencyPickaxe", 40D).setMinValue(1.1).setMaxValue(Float.MAX_VALUE).getDouble();
		toolEfficiencyShovel = (float) configFile.get("Power Usage", "ToolEfficiencyShovel", 10D).setMinValue(1.1).setMaxValue(Float.MAX_VALUE).getDouble();
		toolEfficiencyAxe = (float) configFile.get("Power Usage", "ToolEfficiencyAxe", 30D).setMinValue(1.1).setMaxValue(Float.MAX_VALUE).getDouble();
		toolHarvestLevel = configFile.get("Power Usage", "ToolHarvestLevel", Integer.MAX_VALUE).setMinValue(0).getInt();
		toolEfficiencyAreaMultiplier = (float) configFile.get("Power Usage", "ToolEfficiencyAreaMultiplier", 0.25).setMinValue(0).setMaxValue(Float.MAX_VALUE).getDouble();

		configFile.addCustomCategoryComment("KEB", "Configuration stuff for your Kinetic Energy Buffers");
		powerloss = configFile.get("KEB", "powerloss", false).getBoolean();
		KEB1powerloss = configFile.get("KEB", "KEB1powerloss", 10).setMinValue(0).getInt();
		KEB2powerloss = configFile.get("KEB", "KEBT2powerloss", 5).setMinValue(0).getInt();
		KEB3powerloss = configFile.get("KEB", "KEBT3powerloss", 3).setMinValue(0).getInt();
		capacityKEBTier1 = configFile.get("KEB", "KEBTier1Capacity", 3000000).setMinValue(0).getInt();
		capacityKEBTier2 = configFile.get("KEB", "KEBTier2Capacity", 25000000).setMinValue(0).getInt();
		capacityKEBTier3 = configFile.get("KEB", "KEBTier3Capacity", 100000000).setMinValue(0).getInt();
		maxTransferKEBTier1 = configFile.get("KEB", "KEBTier1MaxTransfer", 32768).setMinValue(0).getInt();
		maxTransferKEBTier2 = configFile.get("KEB", "KEBTier2MaxTransfer", 65536).setMinValue(0).getInt();
		maxTransferKEBTier3 = configFile.get("KEB", "KEBTier3MaxTransfer", 131072).setMinValue(0).getInt();

		configFile.addCustomCategoryComment("Heated Furnace", "Configuration stuff for your Heated Furnace");
		heatedFurnaceHeatRequired = configFile.get("Heated Furnace", "heatedFurnaceHeatRequired", 6500, "How much heat the furnace needs to receive to process one item").setMinValue(0).getInt();
		basicCoilHeat = configFile.get("Heated Furnace", "basicCoilHeat", 16, "Amount of heat a basic coil can generate each tick").setMinValue(0).getInt();
		lavaCoilHeat = configFile.get("Heated Furnace", "lavaCoilHeat", 32, "Amount of heat generated by a lava coil each tick").setMinValue(0).getInt();
		kineticCoilHeatModifier = configFile.get("Heated Furnace", "kineticCoilHeatModifier", 15, "The amount of heat a kinetic coil generates is the amount of RF it received during the last tick multiplied with this number").setMinValue(0).getInt();

		configFile.addCustomCategoryComment("Charging Station", "Configuration stuff for your Charging Station");
		capacityChargingStation = configFile.get("Charging Station", "ChargingStationCapacity", 10000).setMinValue(0).getInt();
		maxTransferChargingStation = configFile.get("Charging Station", "ChargingStationMaxTransfer", 8192).setMinValue(0).getInt();

		configFile.addCustomCategoryComment("Fluidic Compressor", "Configuration stuff for your Fluidic Compressor");
		capacityFluidicCompressor = configFile.get("Fluidic Compressor", "FluidicCompressorCapacity", 1000).setMinValue(0).getInt();
		maxTransferFluidicCompressor = configFile.get("Fluidic Compressor", "FluidicCompressorMaxTransfer", 128).setMinValue(0).getInt();

		configFile.addCustomCategoryComment("Mechanical Duster", "Configuration stuff for your Mechanical Duster");
		capacityMechanicalDuster = configFile.get("Mechanical Duster", "MechanicalDusterCapacity", 2000).setMinValue(0).getInt();
		maxTransferMechanicalDuster = configFile.get("Mechanical Duster", "MechanicalDusterMaxTransfer", 1024).setMinValue(0).getInt();
		energyUseMechanicalDuster = configFile.get("Mechanical Duster", "MechanicalDusterEnergyUse", 10).setMinValue(0).getInt();

		configFile.addCustomCategoryComment("Refinery", "Configuration stuff for your Refinery");
		capacityRefinery = configFile.get("Refinery", "RefineryCapacity", 50000).setMinValue(0).getInt();
		maxTransferRefinery = configFile.get("Refinery", "RefineryMaxTransfer", 1024).setMinValue(0).getInt();
		energyUseRefineryMultiplier = configFile.get("Refinery", "RefineryEnergyUseMultiplier", 1).setMinValue(0).getInt();

		configFile.addCustomCategoryComment("Portable Laser", "Configuration stuff for your Portable Lasers");
		portableLaserPowerUse = configFile.get("Portable Laser", "PortableLaserPowerUse", 8000).setMinValue(0).getInt();
		portableLaserLaserPower = configFile.get("Portable Laser", "PortableLaserLaserPower", 8000).setMinValue(0).getInt();
		portableLaserEntityDamage = (float) configFile.get("Portable Laser", "PortableLaserEntityDamage", 10D).setMinValue(0).setMaxValue(Float.MAX_VALUE).getDouble();
		portableLaserEntityBurnTime = configFile.get("Portable Laser", "PortableLaserEntityBurnTime", 8).setMinValue(0).getInt();

		configFile.addCustomCategoryComment("Coloring Tool", "Configuration stuff for your Portable Lasers");
		capacityPipeColoringTool = configFile.get("Coloring Tool", "ColoringToolCapacity", 100000).setMinValue(0).getInt();
		maxTransferColoringTool = configFile.get("Coloring Tool", "ColoringToolMaxTransfer", 1024).setMinValue(0).getInt();
		energyUseColoringTool = configFile.get("Coloring Tool", "ColoringToolEnergyUse", 16).setMinValue(0).getInt();

		configFile.addCustomCategoryComment("Power Capsule", "Configuration stuff for your Power Capsules");
		capacityPowerCapsuleTier1 = configFile.get("Power Capsule", "PowerCapsuleTier1Capacity", 100000).setMinValue(0).getInt();
		capacityPowerCapsuleTier2 = configFile.get("Power Capsule", "PowerCapsuleTier2Capacity", 300000).setMinValue(0).getInt();
		capacityPowerCapsuleTier3 = configFile.get("Power Capsule", "PowerCapsuleTier3Capacity", 1000000).setMinValue(0).getInt();
		maxTransferPowerCapsuleTier1 = configFile.get("Power Capsule", "PowerCapsuleTier1MaxTransfer", 1024).setMinValue(0).getInt();
		maxTransferPowerCapsuleTier2 = configFile.get("Power Capsule", "PowerCapsuleTier2MaxTransfer", 4096).setMinValue(0).getInt();
		maxTransferPowerCapsuleTier3 = configFile.get("Power Capsule", "PowerCapsuleTier3MaxTransfer", 16384).setMinValue(0).getInt();

		configFile.addCustomCategoryComment("Upgrades.Refinery", "Configuration stuff for your refinery upgrades");
		refineryAutoExportMaxTransfer = configFile.get("Upgrades.Refinery", "UpgradesRefineryAutoExportMaxTransfer", 100).setMinValue(0).getInt();
		refineryAutoImportMaxTransfer = configFile.get("Upgrades.Refinery", "UpgradesRefineryAutoImportMaxTransfer", 100).setMinValue(0).getInt();
		refineryEfficiency1EnergyCostModifier = configFile.get("Upgrades.Refinery", "UpgradesRefineryEfficiency1EnergyCostModifier", -0.1).setMaxValue(0).getDouble();
		refineryEfficiency2EnergyCostModifier = configFile.get("Upgrades.Refinery", "UpgradesRefineryEfficiency2EnergyCostModifier", -0.15).setMaxValue(0).getDouble();
		refineryEfficiency3EnergyCostModifier = configFile.get("Upgrades.Refinery", "UpgradesRefineryEfficiency3EnergyCostModifier", -0.25).setMaxValue(0).getDouble();
		refinerySpeed1EnergyCostModifier = configFile.get("Upgrades.Refinery", "UpgradesRefinerySpeed1EnergyCostModifier", 0.2).setMinValue(0).getDouble();
		refinerySpeed2EnergyCostModifier = configFile.get("Upgrades.Refinery", "UpgradesRefinerySpeed2EnergyCostModifier", 0.3).setMinValue(0).getDouble();
		refinerySpeed3EnergyCostModifier = configFile.get("Upgrades.Refinery", "UpgradesRefinerySpeed3EnergyCostModifier", 0.5).setMinValue(0).getDouble();
		refinerySpeed1SpeedModifier = configFile.get("Upgrades.Refinery", "UpgradesRefinerySpeed1SpeedModifier", 1).setMinValue(0).getInt();
		refinerySpeed2SpeedModifier = configFile.get("Upgrades.Refinery", "UpgradesRefinerySpeed2SpeedModifier", 2).setMinValue(0).getInt();
		refinerySpeed3SpeedModifier = configFile.get("Upgrades.Refinery", "UpgradesRefinerySpeed3SpeedModifier", 3).setMinValue(0).getInt();

		configFile.addCustomCategoryComment("Upgrades.Cooling Tower", "Configuration stuff for your cooling tower upgrades");
		coolingTowerAutoExportMaxTransfer = configFile.get("Upgrades.Cooling Tower", "UpgradesCoolingTowerAutoExportMaxTransfer", 100).setMinValue(0).getInt();
		coolingTowerAutoImportMaxTransfer = configFile.get("Upgrades.Cooling Tower", "UpgradesCoolingTowerAutoImportMaxTransfer", 100).setMinValue(0).getInt();
		coolingTowerSpeed1SpeedModifier = configFile.get("Upgrades.Cooling Tower", "UpgradesCoolingTowerSpeed1SpeedModifier", 1).setMinValue(0).getInt();
		coolingTowerSpeed2SpeedModifier = configFile.get("Upgrades.Cooling Tower", "UpgradesCoolingTowerSpeed2SpeedModifier", 2).setMinValue(0).getInt();
		coolingTowerSpeed3SpeedModifier = configFile.get("Upgrades.Cooling Tower", "UpgradesCoolingTowerSpeed3SpeedModifier", 3).setMinValue(0).getInt();
		coolingTowerEfficiency1CoolingModifier = (float) configFile.get("Upgrades.Cooling Tower", "UpgradesCoolingTowerEfficiency1CoolingModifier", 0.25).setMinValue(0).setMaxValue(Float.MAX_VALUE).getDouble();
		coolingTowerEfficiency2CoolingModifier = (float) configFile.get("Upgrades.Cooling Tower", "UpgradesCoolingTowerEfficiency2CoolingModifier", 0.5).setMinValue(0).setMaxValue(Float.MAX_VALUE).getDouble();
		coolingTowerEfficiency3CoolingModifier = (float) configFile.get("Upgrades.Cooling Tower", "UpgradesCoolingTowerEfficiency3CoolingModifier", 1D).setMinValue(0).setMaxValue(Float.MAX_VALUE).getDouble();

		configFile.addCustomCategoryComment("Misc", "Stuff that didn't fit in any other category");
		shouldRegisterDusts = configFile.get("Misc", "shouldRegisterDusts", true).setRequiresMcRestart(true).getBoolean();
		eurekaIntegration = configFile.get("Misc", "eurekaIntegration", true).setRequiresMcRestart(true).getBoolean();
		dusterParticles = configFile.get("Misc", "dusterParticles", true).getBoolean();
		particleCount = configFile.get("Misc", "particleCount", 100).setMinValue(0).getInt();
		forceEnableBCRefinery = !enabled("MultiBlockRefining") || configFile.get("Misc", "forceEnableBCRefinery", false).getBoolean();

		registerFeature("ChargingStation");
		registerFeature("ColorSorter");
		registerFeature("ColoringTool");
		registerFeature("Duster");
		registerFeature("Coils");
		registerFeature("HeatedFurnace");
		registerFeature("FluidCanisters");
		registerFeature("KineticBackpack");
		registerFeature("KineticEnergyBuffer");
		registerFeature("MachineUpgrades");
		registerFeature("MultiBlockRefining");
		registerFeature("MultiTools");
		registerFeature("MultiToolsArea");
		registerFeature("MultiToolsFortune");
		registerFeature("MultiToolsSilky");
		registerFeature("PortableLaser");
		registerFeature("PowerCapsules");

		if (configFile.hasChanged())
			configFile.save();
	}
}
