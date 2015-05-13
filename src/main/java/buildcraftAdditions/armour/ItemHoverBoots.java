package buildcraftAdditions.armour;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created by AEnterprise
 */
public class ItemHoverBoots extends ItemPoweredArmor {

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
				if (player.isSneaking()) {
					player.motionY /= 1.1;
				} else {
					player.motionY = 0;
				}
			}
		}
	}
}
