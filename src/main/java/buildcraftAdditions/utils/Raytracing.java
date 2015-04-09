package buildcraftAdditions.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Vec3;
/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */

//credit goes to chickenbones for the raytracing code
public class Raytracing {

	public static Vec3 getEndVector(EntityPlayer player) {

		Vec3 headVec = getCorrectedHeadVec(player);
		Vec3 lookVec = player.getLook(1.0F);
		double reach = player.capabilities.isCreativeMode ? 5 : 4.5;

		return headVec.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
	}

	public static Vec3 getCorrectedHeadVec(EntityPlayer player) {

		Vec3 v = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
		if (player.worldObj.isRemote) {
			v.yCoord += player.getEyeHeight() - player.getDefaultEyeHeight();// compatibility with eye height changing mods
		} else {
			v.yCoord += player.getEyeHeight();
			if (player instanceof EntityPlayerMP && player.isSneaking())
				v.yCoord -= 0.08;
		}
		return v;
	}
}
