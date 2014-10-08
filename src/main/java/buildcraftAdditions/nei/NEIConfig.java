package buildcraftAdditions.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class NEIConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {

        DustingRecipeHandler dustingRecipeHandler = new DustingRecipeHandler();

        API.registerRecipeHandler(dustingRecipeHandler);
        API.registerUsageHandler(dustingRecipeHandler);

    }

    @Override
    public String getName() {
        return "Buildcraft Additions";
    }

    @Override
    public String getVersion() {
        return "@MODVERSION@";
    }
}
