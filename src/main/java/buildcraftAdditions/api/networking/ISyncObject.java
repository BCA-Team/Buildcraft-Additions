package buildcraftAdditions.api.networking;

import io.netty.buffer.ByteBuf;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public interface ISyncObject {

	void writeToByteBuff(ByteBuf buf);

	void readFromByteBuff(ByteBuf buf);
}
