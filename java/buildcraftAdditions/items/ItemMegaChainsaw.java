package buildcraftAdditions.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import buildcraftAdditions.core.BuildcraftAdditions;
import buildcraftAdditions.core.Variables;

public class ItemMegaChainsaw extends ItemPoweredBase {
	
	public ItemMegaChainsaw(int maxEnergy){
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		setUnlocalizedName("chainsaw");
		this.setMaxDamage(maxEnergy);
		this.setHarvestLevel("axe", 3);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta){
		if (getEnergy(stack) >= block.getBlockHardness(world, x, y, z))
			if(block.getHarvestTool(0) == "axe" || block.getMaterial() == Material.leaves || block.getMaterial() == Material.wood || block.getMaterial() == Material.vine)
				return 30;
		return 1;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		if (player.isSneaking() && !world.isRemote)
			player.openGui(BuildcraftAdditions.instance, Variables.GuiChainsaw, world, x, y, z);
		return stack;
	}

}
