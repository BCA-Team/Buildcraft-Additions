package buildcraftAdditions.client.models;

import buildcraftAdditions.reference.Variables;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class ModelLoader {
	public static final IModelCustom HOVER_BOOTS = AdvancedModelLoader.loadModel(new ResourceLocation(Variables.MOD.ID, "models/jetboots.obj"));
}
