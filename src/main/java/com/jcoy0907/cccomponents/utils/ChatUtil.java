package com.jcoy0907.cccomponents.utils;

import com.jcoy0907.cccomponents.CCComponents;
import com.jcoy0907.cccomponents.network.ChatPacket;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class ChatUtil {

	public static void sendMessage(TileEntity te, String text, double range, boolean unlimitedY) {
		if (range == Double.MAX_VALUE) {
			for (EntityPlayer player : (Iterable<EntityPlayer>)MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				CCComponents.NETWORK.sendTo(new ChatPacket(text), (EntityPlayerMP) player);
			}
		} else if (unlimitedY) {
			for (EntityPlayer player : (Iterable<EntityPlayer>) te.getWorldObj().playerEntities) {
				Vec3 playerPos = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
				playerPos.yCoord = te.yCoord;
				if (playerPos.distanceTo(Vec3.createVectorHelper(te.xCoord, te.yCoord, te.zCoord)) > range)
					continue;
				CCComponents.NETWORK.sendTo(new ChatPacket(text), (EntityPlayerMP) player);
			}
		} else {
			CCComponents.NETWORK.sendToAllAround(new ChatPacket(text), new NetworkRegistry.TargetPoint(te.getWorldObj().provider.dimensionId, te.xCoord, te.yCoord, te.zCoord, range));
		}
	}

	public static boolean sendMessage(String ign, TileEntity te, String text, double range, boolean unlimitedY) {
		EntityPlayer player = getPlayer(ign, range == Double.MAX_VALUE ? null : te.getWorldObj());
		if (player != null) {
			Vec3 playerPos = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
			if (unlimitedY)
				playerPos.yCoord = te.yCoord;
			if (playerPos.distanceTo(Vec3.createVectorHelper(te.xCoord,te.yCoord,te.zCoord)) > range)
				return false;
			CCComponents.NETWORK.sendTo(new ChatPacket(text), (EntityPlayerMP) player);
			return true;
		}
		return false;
	}

	private static EntityPlayer getPlayer(String ign, World w) {
		List<EntityPlayer> players = w == null ? MinecraftServer.getServer().getConfigurationManager().playerEntityList : w.playerEntities;
		for (EntityPlayer p : players) {
			if (p.getDisplayName().equalsIgnoreCase(ign))
				return p;
		}
		return null;
	}

	public static String getCoordsPrefix(TileEntity te) {
		return "[#" + te.xCoord + "," + te.yCoord + "," + te.zCoord + "] ";
	}

	public static String getCoordsPrefix(Entity ent) {
		return "[#" + ent.posX + "," + ent.posY + "," + ent.posZ + "] ";
	}
}
