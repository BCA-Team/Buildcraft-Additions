package buildcraftAdditions.ModIntegration.Framez;

import com.amadornes.framez.api.FramezApi;
/**
 * Copyright (c) 2014, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class FramezIntegration {

	public static void Framez() {
		FramezApi.inst().getMovementApi().registerMovementHandler(new MovementHandeler());
	}

}
