package buildcraftAdditions.items;

import net.minecraft.item.ItemStack;
import buildcraftAdditions.core.BuildcraftAdditions;

public class ItemPowerCapsuleTier2 extends BatteryBase {
	
	public ItemPowerCapsuleTier2(){
		this.maxStackSize = 1;
		setCreativeTab(BuildcraftAdditions.bcadditions);
		this.setUnlocalizedName("PowerCapsuleTier2");
		this.setMaxDamage(2000);
	}
	
	@Override
	public int getCapacity(){
		return 2000;
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack){
		return (int) (getCapacity() - getEnergy(stack));
	}

}
