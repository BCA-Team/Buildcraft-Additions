package buildcraftAdditions.networking;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftAdditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://buildcraftAdditions.wordpress.com/wiki/licensing-stuff/
 */
public class PacketHandeler {

    public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel("bcadditions");

    public static void init(){
        instance.registerMessage(MessageSemiAutomaticDuster.class, MessageSemiAutomaticDuster.class, 0, Side.CLIENT);
        instance.registerMessage(MessageMechanicDuster.class, MessageMechanicDuster.class, 1, Side.CLIENT);
	    instance.registerMessage(MessageDusterKinetic.class, MessageDusterKinetic.class, 2, Side.CLIENT);
    }
}
