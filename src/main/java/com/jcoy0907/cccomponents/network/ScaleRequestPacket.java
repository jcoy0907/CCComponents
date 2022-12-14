package com.jcoy0907.cccomponents.network;

import com.jcoy0907.cccomponents.CCComponents;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class ScaleRequestPacket implements IMessage {

	public ChunkCoordinates coords;
	public int id, dim;

	public ScaleRequestPacket() {}

	public ScaleRequestPacket(TileEntity te, int id, int dim) {
		this.coords = new ChunkCoordinates(te.xCoord, te.yCoord, te.zCoord);
		this.id = id;
		this.dim = dim;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		coords = new ChunkCoordinates(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
		id = tag.getInteger("id");
		dim = tag.getInteger("dim");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", coords.posX);
		tag.setInteger("y", coords.posY);
		tag.setInteger("z", coords.posZ);
		tag.setInteger("id", id);
		tag.setInteger("dim", dim);
		ByteBufUtils.writeTag(buf, tag);
	}

	public static class ScaleRequestPacketHandler implements IMessageHandler<ScaleRequestPacket, IMessage> {

		@Override
		public IMessage onMessage(ScaleRequestPacket message, MessageContext ctx) {
			ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
			CCComponents.NETWORK.sendToServer(new ScaleRequestResponsePacket(message.coords, message.id, resolution.getScaledWidth(), resolution.getScaledHeight(), message.dim));
			return null;
		}
	}
}
