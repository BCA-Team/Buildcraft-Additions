package buildcraftAdditions.reference;

import buildcraftAdditions.multiBlocks.MultiBlockPatern;
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

	//GUI ID's
	public static final int GUI_FLUIDIC_COMPRESSOR = 70;
	public static final int GUI_CHARGING_STATION = 71;
	public static final int GUI_KINETIC_TOOL = 72;
	public static final int GUI_HEATED_FURNACE = 73;
	public static final int GUI_BASIC_COIL = 74;
	public static final int GUI_KEB = 75;
	public static final int GUI_MACHINE_CONFIGURATOR = 76;


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
	}

	//multiblock paterns
	public static final class Paterns {
		public static final MultiBlockPatern KEBT2 = new MultiBlockPaternKEBT2();
		public static final MultiBlockPatern KEBT3 = new MultiBlockPaternKEBT3();
		public static final MultiBlockPatern REFINERY = new MultiBlockPaternRefinery();
	}

}
