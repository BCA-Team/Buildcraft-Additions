package buildcraftAdditions.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import buildcraftAdditions.utils.IHUD;

/**
 * Created by AEnterprise
 */
public class HUDRenderer {
	private static final Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event) {
		List<String> info = new ArrayList<String>();
		if (event.phase == TickEvent.Phase.END && mc.currentScreen == null) {
			for (int i = 0; i < 4; i++) {
				ItemStack stack = mc.thePlayer.getCurrentArmor(i);
				if (stack != null && stack.getItem() instanceof IHUD) {
					IHUD h = (IHUD) stack.getItem();
					info.add(h.getInfo(stack));
				}
			}
			int line = 0;
			for (String s : info) {
				mc.fontRenderer.drawString(s, 0, line++ * 9, 0x000000, true);
			}
		}
	}
}
