package buildcraftAdditions.utils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.common.util.Constants;

import buildcraftAdditions.BuildcraftAdditions;
import buildcraftAdditions.tileEntities.interfaces.IOwnableMachine;

/**
 * Copyright (c) 2014-2015, AEnterprise
 * http://buildcraftadditions.wordpress.com/
 * Buildcraft Additions is distributed under the terms of GNU GPL v3.0
 * Please check the contents of the license located in
 * http://buildcraftadditions.wordpress.com/wiki/licensing-stuff/
 */
public class PlayerUtils {

	private static final String hex = "[0-9a-fA-F]";
	private static final Pattern uuidPattern = Pattern.compile("^" + hex + "{8}-" + hex + "{4}-" + hex + "{4}-" + hex + "{4}-" + hex + "{12}$");

	public static UUID getPlayerUUID(EntityPlayer player) {
		return player != null && player.getGameProfile() != null ? getPlayerUUID(player.getGameProfile().getName()) : null;
	}

	public static String getPlayerUUIDString(EntityPlayer player) {
		return player != null && player.getGameProfile() != null ? getUUIDString(getPlayerUUID(player.getGameProfile().getName())) : null;
	}

	public static UUID getPlayerUUID(String name) {
		if (name != null && !name.isEmpty()) {
			for (Map.Entry<UUID, String> entry : UsernameCache.getMap().entrySet())
				if (entry != null && entry.getValue() != null && entry.getValue().equalsIgnoreCase(name))
					return entry.getKey();
		}
		return null;
	}

	public static String getPlayerName(UUID uuid) {
		String name = null;
		if (uuid != null)
			name = UsernameCache.getLastKnownUsername(uuid);
		return name != null ? name : "";
	}

	public static String getUUIDString(UUID uuid) {
		return uuid != null ? uuid.toString() : "";
	}

	public static UUID getUUID(String uuid) {
		return isUUIDString(uuid) ? UUID.fromString(uuid) : null;
	}

	public static boolean playerMatches(UUID uuid, String name) {
		UUID playerUUID = getPlayerUUID(name);
		return playerUUID != null ? playerUUID.equals(uuid) : false;
	}

	public static boolean playerMatches(IOwnableMachine tile, String name) {
		UUID playerUUID = getPlayerUUID(name);
		return playerUUID != null ? playerUUID.equals(tile.getOwner()) : false;
	}

	public static boolean playerMatches(UUID uuid, EntityPlayer player) {
		UUID playerUUID = getPlayerUUID(player);
		return playerUUID != null ? playerUUID.equals(uuid) : false;
	}

	public static boolean playerMatches(IOwnableMachine tile, EntityPlayer player) {
		UUID playerUUID = getPlayerUUID(player);
		return playerUUID != null ? playerUUID.equals(tile.getOwner()) : false;
	}

	public static boolean playerMatches(String uuid, String name) {
		UUID playerUUID = getPlayerUUID(name);
		return playerUUID != null ? playerUUID.equals(getUUID(uuid)) : false;
	}

	public static boolean playerMatches(String uuid, EntityPlayer player) {
		UUID playerUUID = getPlayerUUID(player);
		return playerUUID != null ? playerUUID.equals(getUUID(uuid)) : false;
	}

	public static EntityPlayer getPlayer(String name) {
		return getPlayer(getPlayerUUID(name));
	}

	public static EntityPlayer getPlayer(UUID uuid) {
		if (uuid != null) {
			if (playerMatches(uuid, BuildcraftAdditions.proxy.getClientPlayer()))
				return BuildcraftAdditions.proxy.getClientPlayer();
			List list = (FMLCommonHandler.instance().getMinecraftServerInstance() != null && FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager() != null && FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList != null) ? FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList : ((BuildcraftAdditions.proxy.getClientWorld() != null && BuildcraftAdditions.proxy.getClientWorld().getLoadedEntityList() != null) ? BuildcraftAdditions.proxy.getClientWorld().getLoadedEntityList() : null);
			if (list != null) {
				for (Object object : list) {
					if (object != null && object instanceof EntityPlayer) {
						EntityPlayer player = (EntityPlayer) object;
						if (playerMatches(uuid, player))
							return player;
					}
				}
			}
		}
		return null;
	}


	public static boolean isUUIDString(String uuid) {
		return uuid != null ? uuidPattern.matcher(uuid).matches() : false;
	}

	public static void writeToNBT(NBTTagCompound nbt, UUID uuid) {
		nbt.setString("owner", getUUIDString(uuid));
	}

	public static UUID readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("owner", Constants.NBT.TAG_STRING)) {
			String owner = nbt.getString("owner");
			if (isUUIDString(owner))
				return getUUID(owner);
			return getPlayerUUID(owner);
		}
		return null;
	}

	public static void writeToByteBuff(ByteBuf buf, UUID uuid) {
		String s = getUUIDString(uuid);
		buf.writeInt(s.length());
		for (int i = 0; i < s.length(); i++)
			buf.writeChar(s.charAt(i));
	}

	public static UUID readFromByteBuff(ByteBuf buf) {
		String s = "";
		int length = buf.readInt();
		for (int i = 0; i < length; i++)
			s += buf.readChar();
		return getUUID(s);
	}

}
