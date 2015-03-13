package buildcraftAdditions.compat.eureka;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.entity.player.AchievementEvent;

import buildcraftAdditions.reference.Variables;

import eureka.api.EurekaAPI;

public class BCAEurekaEvents {

	@SubscribeEvent
	public void onGettingAchievement(AchievementEvent event) {
		EurekaAPI.API.makeProgress(Variables.Eureka.DustT0Key, event.entityPlayer);
	}
}
