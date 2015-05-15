package buildcraftAdditions.reference;

import buildcraftAdditions.multiBlocks.MultiBlockPatern;
import buildcraftAdditions.multiBlocks.MultiBlockPaternCoolingTower;
import buildcraftAdditions.multiBlocks.MultiBlockPaternKEBT2;
import buildcraftAdditions.multiBlocks.MultiBlockPaternKEBT3;
import buildcraftAdditions.multiBlocks.MultiBlockPaternRefinery;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

public final class Variables {

	public static final int NETWORK_RANGE = 64;

	//GUI ID's
	public enum Gui {
		FLUIDIC_COMPRESSOR,
		CHARGING_STATION,
		KINETIC_MULTI_TOOL,
		PORTABLE_LASER,
		HEATED_FURNACE,
		BASIC_COIL,
		KEB,
		MACHINE_CONFIGURATOR,
		REFINERY,
		COOLING_TOWER,
		ITEM_SORTER,
		PIPE_COLORING_TOOL
	}

	public enum SyncIDs {
		BACKPACK_STAND,
		BASIC_COIL,
		BASIC_DUSTER,
		CHARGING_STATION,
		COOLING_TOWER,
		FLUIDIC_COMPRESSOR,
		HEATED_FURNACE,
		ITEM_SORTER,
		KEBT1,
		KEBT2,
		KEBT3,
		KINETIC_COIL,
		KINETIC_DUSTER,
		LAVA_COIL,
		MECHANIC_DUSTER,
		REFINERY,
		SEMI_AUTOMATIC_DUSTER
	}

	//Render ID's
	public static final class RenderIDs {
		public static int SIDED_TEXTURES_RENDER_ID;
		public static int SORTER;
	}

	//EUREKA NBT names
	public static final class Eureka {
		public static final String DustT0Key = "dusterTier0";
		public static final String DustT1Key = "dusterTier1";
		public static final String DustT2Key1 = "dusterTier2-1";
		public static final String DustT2Key2 = "dusterTier2-2";
		public static final String KineticToolKey = "kineticTool";
		public static final String HEATED_FURNACE = "heatedFurnace";
		public static final String BASIC_COIL = "basicCoil";
		public static final String LAVA_COIL = "lavaCoil";
		public static final String KINETIC_COIL = "kineticCoil";
		public static final String KEBT1 = "KEBT1";
		public static final String KEBT2 = "KEBT2";
		public static final String KEBT3 = "KEBT3";
		public static final String PORTABLE_LASER = "PortableLaser";
	}

	public static final class MOD {
		public static final String ID = "bcadditions";
		public static final String NAME = "BuildCraft Additions";
	}

	public static final class UUIDs {
		public static final String CORJAANTJE = "209f3364-0042-4d2a-b539-8640e6bbd6c1";
	}

	//Multiblock identifiers
	public static final class Identifiers {
		public static final char REFINERY_WALLS = 'R';
		public static final char REFINERY_VALVE = 'V';
		public static final char KEBT3_PLATING = 'W';
		public static final char KEBT3_CORE = 'C';
		public static final char KEBT2 = 'K';
		public static final char COOLING_TOWER_WALLS = 'T';
		public static final char COOLING_TOWER_VALVE = 'D';
	}

	//multiblock paterns
	public static final class Paterns {
		public static final MultiBlockPatern KEBT2 = new MultiBlockPaternKEBT2().setRotatable(false);
		public static final MultiBlockPatern KEBT3 = new MultiBlockPaternKEBT3();
		public static final MultiBlockPatern REFINERY = new MultiBlockPaternRefinery();
		public static final MultiBlockPatern COOLING_TOWER = new MultiBlockPaternCoolingTower();
	}

}
