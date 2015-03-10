package buildcraftAdditions.api.item.dust;

import java.util.List;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface IDustManager {

	void addDust(int meta, String name, int colorMultiplier, IDustType dustType);

	void removeDust(int meta);

	void removeDust(String name);

	IDust getDust(int meta);

	IDust getDust(String name);

	List<? extends IDust> getDusts();

}
