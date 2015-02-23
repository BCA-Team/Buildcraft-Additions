package buildcraftAdditions.reference;

import buildcraftAdditions.multiBlocks.MultiBlockPatern;
import buildcraftAdditions.multiBlocks.MultiBlockPaternCoolingTower;
import buildcraftAdditions.multiBlocks.MultiBlockPaternKEBT2;
import buildcraftAdditions.multiBlocks.MultiBlockPaternKEBT3;
import buildcraftAdditions.multiBlocks.MultiBlockPaternRefinery;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

public final class Variables {

	public static final class MOD {
		public static final String ID = "bcadditions";
		public static final String NAME = "Buildcraft Additions";
	}

	//GUI ID's
	public static final class Gui {
		public static final int FLUIDIC_COMPRESSOR = 70;
		public static final int CHARGING_STATION = 71;
		public static final int KINETIC_TOOL = 72;
		public static final int HEATED_FURNACE = 73;
		public static final int BASIC_COIL = 74;
		public static final int KEB = 75;
		public static final int MACHINE_CONFIGURATOR = 76;
		public static final int REFINERY = 77;
		public static final int COOLING_TOWER = 88;
		public static final int ITEM_SORTER = 89;
		public static final int PIPE_COLORING_TOOL = 90;
	}

	//EUREKA NBT names
	public static final String DustT0Key = "dusterTier0";
	public static final String DustT1Key = "dusterTier1";
	public static final String DustT2Key1 = "dusterTier2-1";
	public static final String DustT2Key2 = "dusterTier2-2";
	public static final String KineticToolKey = "kineticTool";

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
