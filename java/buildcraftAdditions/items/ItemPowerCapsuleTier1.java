package buildcraftAdditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import buildcraftAdditions.core.BuildcraftAdditions;
import net.minecraft.util.IIcon;

public class ItemPowerCapsuleTier1 extends BatteryBase {
    IIcon icon;
	
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        icon = par1IconRegister.registerIcon("bcadditions:T1_battery");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return icon;
    }

}
