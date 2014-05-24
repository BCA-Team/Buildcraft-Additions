package buildcraftAdditions.items;

import buildcraftAdditions.core.BuildcraftAdditions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class ItemIronStick extends Item {
    IIcon icon;

    public ItemIronStick(){
        super();
        this.setCreativeTab(BuildcraftAdditions.bcadditions);
        this.setUnlocalizedName("ironStick");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        icon = par1IconRegister.registerIcon("bcadditions:ironStick");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return icon;
    }
}
