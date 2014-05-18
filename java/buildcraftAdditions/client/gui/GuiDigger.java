package buildcraftAdditions.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import buildcraft.core.gui.BuildCraftContainer;
import buildcraft.core.gui.GuiBuildCraft;
import buildcraft.core.gui.widgets.Widget;
import buildcraftAdditions.core.Utils;
import buildcraftAdditions.items.ItemMegaDigger;

public class GuiDigger extends GuiContainer{
	public static ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/DiggerGUI.png");
	ItemMegaDigger digger;
	ItemStack stack;
	EntityPlayer player;

	public GuiDigger(InventoryPlayer inventoryplayer, ItemMegaDigger digger, IInventory inventory, ItemStack stack, EntityPlayer player) {
		super(new ContainerDigger(inventoryplayer, digger, inventory, stack, player));
		this.digger = digger;
		this.stack = stack;
		this.player = player;
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String title = Utils.localize("item.drill.name");
        fontRendererObj.drawString(Utils.localize(title), (xSize - fontRendererObj.getStringWidth(title)) / 2, 6, 0x404040);
        fontRendererObj.drawString(Utils.localize("gui.inventory"), 8, (ySize-110) + 2, 0x404040);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int mX = mouseX - guiLeft;
		int mY = mouseY - guiTop;

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	}
	
	@Override
	public void onGuiClosed(){
		digger.readBateries(stack, player);
	}
	
	 

}
