package buildcraftAdditions.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import buildcraftAdditions.reference.ItemsAndBlocks;

/**
 * Created by AEnterprise
 */
public class HUDRenderer {
	private static final Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event) {
		if (event.phase == TickEvent.Phase.END && mc.currentScreen == null) {
			ItemStack stack = mc.thePlayer.getCurrentArmor(0);
			if (stack != null && stack.getItem() == ItemsAndBlocks.hoverBoots) {
				GL11.glPushMatrix();
				mc.entityRenderer.setupOverlayRendering();
				mc.fontRenderer.drawString("Hover boots status: " + (stack.stackTagCompound.getBoolean("enabled") ? "enabled" : "dissabled"), 0, 0, 0x1D1CFF);
				GL11.glPopMatrix();
			}
		}
	}
}
