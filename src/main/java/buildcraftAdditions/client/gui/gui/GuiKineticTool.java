package buildcraftAdditions.client.gui.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraftAdditions.client.gui.containers.ContainerKineticTool;
import buildcraftAdditions.items.Tools.ItemKineticTool;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
@SideOnly(Side.CLIENT)
public class GuiKineticTool extends GuiBase {

	private static final ResourceLocation texture = new ResourceLocation("bcadditions", "textures/gui/guiKineticTool.png");
	private final ItemKineticTool tool;
	private final ItemStack stack;
	private final EntityPlayer player;

	public GuiKineticTool(InventoryPlayer inventoryplayer, ItemKineticTool Tool, IInventory inventory, ItemStack stack, EntityPlayer player) {
		super(new ContainerKineticTool(inventoryplayer, Tool, inventory, stack, player));
		this.tool = Tool;
		this.stack = stack;
		this.player = player;
		tool.setPlayer(player);
	}

	@Override
	public void onGuiClosed() {
		tool.readBateries(stack);
		tool.writeUpgrades(stack);
	}

	@Override
	public ResourceLocation texture() {
		return texture;
	}

	@Override
	public int getXSize() {
		return 176;
	}

	@Override
	public int getYSize() {
		return 53;
	}

	@Override
	public String getInventoryName() {
		return "kineticTool";
	}

	@Override
	public void initialize() {
	}
}
