package buildcraftAdditions.compat;

/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public @interface CompatModule {

	public String id();

	public String requiredMods() default "";

	public static @interface Handler {

	}
}
