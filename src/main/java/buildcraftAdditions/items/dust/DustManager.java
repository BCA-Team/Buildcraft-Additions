package buildcraftAdditions.items.dust;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.util.StringUtils;


import buildcraftAdditions.api.item.dust.IDust;
import buildcraftAdditions.api.item.dust.IDustManager;
import buildcraftAdditions.api.item.dust.IDustType;
import buildcraftAdditions.config.ConfigurationHandler;
import buildcraftAdditions.core.Logger;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class DustManager implements IDustManager {

    private final IDust[] dusts = new IDust[Short.MAX_VALUE];

    @Override
    public void addDust(int meta, String name, int colorMultiplier, IDustType dustType) {
        if (meta < 0 || meta >= dusts.length || StringUtils.isNullOrEmpty(name) || Character.isLowerCase(name.charAt(0)) || dustType == null || !ConfigurationHandler.shouldRegisterDusts) {
            Logger.error("Tried to register an invalid dust! Skipping.");
            return;
        }
        IDust dust = getDust(meta);
        if (dust != null) {
            Logger.error("A dust with the meta '" + meta + "' is already registered! Skipping.");
            Logger.error("Was trying to add: Meta: " + meta + " Name: " + name + " Color multiplier: " + colorMultiplier + " Dust type: " + dustType.getName());
            Logger.error("Found: Meta: " + meta + " Name: " + dust.getName() + " Color multiplier: " + dust.getColorMultiplier() + " Dust type: " + dust.getDustType().getName());
            return;
        }
        dust = getDust(name);
        if (dust != null) {
            Logger.error("A dust with the name '" + name + "' is already registered! Skipping.");
            Logger.error("Was trying to add: Meta: " + meta + " Name: " + name + " Color multiplier: " + colorMultiplier + " Dust type: " + dustType.getName());
            Logger.error("Found: Meta: " + meta + " Name: " + dust.getName() + " Color multiplier: " + dust.getColorMultiplier() + " Dust type: " + dust.getDustType().getName());
            return;
        }
        dust = new Dust(meta, name, colorMultiplier, dustType);
        if (dustType.isValid(meta, name, dust.getDustStack())) {
            dusts[meta] = dust;
        }
    }

    @Override
    public void removeDust(int meta) {
        if (meta >= 0 && meta < dusts.length) {
            dusts[meta] = null;
        }
    }

    @Override
    public void removeDust(String name) {
        if (!StringUtils.isNullOrEmpty(name)) {
            for (int i = 0; i < dusts.length; i++) {
                if (dusts[i] != null && dusts[i].getName().equalsIgnoreCase(name)) {
                    dusts[i] = null;
                    return;
                }
            }
        }
    }

    @Override
    public IDust getDust(int meta) {
        if (meta >= 0 && meta < dusts.length) {
            return dusts[meta];
        }
        return null;
    }

    @Override
    public IDust getDust(String name) {
        if (!StringUtils.isNullOrEmpty(name)) {
            for (IDust dust : dusts) {
                if (dust != null && dust.getName().equalsIgnoreCase(name)) {
                    return dust;
                }
            }
        }
        return null;
    }

    @Override
    public List<? extends IDust> getDusts() {
        return Collections.unmodifiableList(Arrays.asList(dusts));
    }
}
