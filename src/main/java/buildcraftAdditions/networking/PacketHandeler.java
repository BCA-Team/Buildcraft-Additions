package buildcraftAdditions.networking;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class PacketHandeler {

	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel("bcadditions");

	public static void init() {
		instance.registerMessage(MessageSemiAutomaticDuster.class, MessageSemiAutomaticDuster.class, 0, Side.CLIENT);
		instance.registerMessage(MessageMechanicDuster.class, MessageMechanicDuster.class, 1, Side.CLIENT);
		instance.registerMessage(MessageDusterKinetic.class, MessageDusterKinetic.class, 2, Side.CLIENT);
		instance.registerMessage(MessageCoilStatus.class, MessageCoilStatus.class, 3, Side.CLIENT);
		instance.registerMessage(MessageHeatedFurnaceProgress.class, MessageHeatedFurnaceProgress.class, 4, Side.CLIENT);
		instance.registerMessage(MessageFluidicCompressorA.class, MessageFluidicCompressorA.class, 5, Side.SERVER);
		instance.registerMessage(MessageFluidicCompressorC.class, MessageFluidicCompressorC.class, 6, Side.CLIENT);
		instance.registerMessage(MessageKEBT2.class, MessageKEBT2.class, 7, Side.CLIENT);
		instance.registerMessage(MessageKEBT1.class, MessageKEBT1.class, 8, Side.CLIENT);
		instance.registerMessage(MessageConfiguration.class, MessageConfiguration.class, 9, Side.SERVER);
		instance.registerMessage(MessageSelfDestruct.class, MessageSelfDestruct.class, 10, Side.SERVER);
		instance.registerMessage(MessageKEBT3.class, MessageKEBT3.class, 11, Side.CLIENT);
		instance.registerMessage(MessageRefinery.class, MessageRefinery.class, 12, Side.CLIENT);
		instance.registerMessage(MessageMultiBlockData.class, MessageMultiBlockData.class, 13, Side.CLIENT);
		instance.registerMessage(MessageTank.class, MessageTank.class, 14, Side.CLIENT);
	}
}
