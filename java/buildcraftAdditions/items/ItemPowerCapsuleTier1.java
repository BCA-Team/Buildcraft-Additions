package buildcraftAdditions.items;

import net.minecraft.item.ItemStack;
import buildcraftAdditions.core.BuildcraftAdditions;

public class ItemPowerCapsuleTier1 extends BatteryBase {
	
	public ItemPowerCapsuleTier1(){
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		this.setUnlocalizedName("PowerCapsuleTier1");
		this.setMaxDamage(1000);
	}
	
	@Override
	public int getCapacity(){
		return 1000;
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack){
		return (int) (getCapacity() - getEnergy(stack));
	}

}
