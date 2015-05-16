package buildcraftAdditions.armour;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import buildcraftAdditions.reference.ItemsAndBlocks;
import buildcraftAdditions.utils.IHUD;
import buildcraftAdditions.utils.Utils;

/**
 * Created by AEnterprise
 */
public class ItemHoverBoots extends ItemPoweredArmor implements IHUD {

	public ItemHoverBoots() {
		super("hoverBoots", 3);
	}

	private void tagTest(ItemStack stack) {
		if (stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		if (!stack.stackTagCompound.hasKey("enabled"))
			stack.stackTagCompound.setBoolean("enabled", true);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		tagTest(itemStack);
		if (itemStack.stackTagCompound.getBoolean("enabled")) {
			player.fallDistance = 0;
			if (player.motionY < -0 && !player.onGround) {
				ItemStack stack = player.getCurrentArmor(2);
				if (stack != null && stack.getItem() == ItemsAndBlocks.kineticBackpack) {
					ItemKineticBackpack backpack = (ItemKineticBackpack) stack.getItem();
					if (backpack.extractEnergy(stack, 30, true) == 30) {
						if (player.isSneaking()) {
							player.motionY /= 1.1;
						} else {
							player.motionY = 0;
							backpack.extractEnergy(stack, 30, false);
						}
					}
				}
			}
		}
	}

	@Override
	public String getInfo(ItemStack stack) {
		return EnumChatFormatting.GOLD + Utils.localize("hud.boots") + " " + (stack.stackTagCompound.getBoolean("enabled") ? EnumChatFormatting.GREEN + Utils.localize("hud.enabled") : EnumChatFormatting.DARK_RED + Utils.localize("hud.dissabled"));
	}
}
