package com.jcoy0907.cccomponents.network;

import com.jcoy0907.cccomponents.tiles.TileEntityAntenna;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class InputEventPacket implements IMessage {

	public UUID uuid;
	public int key;
	public String event, player;
	public boolean state;

	public InputEventPacket() {}

	public InputEventPacket(UUID uuid, int key, boolean state, String event, String player) {
		this.uuid = uuid;
		this.key = key;
		this.state = state;
		this.event = event;
		this.player = player;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		uuid = UUID.fromString(tag.getString("uuid"));
		key = tag.getInteger("key");
		event = tag.getString("event");
		player = tag.getString("player");
		state = tag.getBoolean("text");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("uuid", uuid.toString());
		tag.setInteger("key", key);
		tag.setString("event", event);
		tag.setString("player", player);
		tag.setBoolean("text", state);
		ByteBufUtils.writeTag(buf, tag);
	}

	public static class InputEventPacketHandler implements IMessageHandler<InputEventPacket, IMessage> {

		@Override
		public IMessage onMessage(InputEventPacket message, MessageContext ctx) {
			TileEntityAntenna antenna = TileEntityAntenna.antenna_registry.get(message.uuid);
			if (antenna != null) {
				for (IComputerAccess computer : antenna.computers.keySet())
					computer.queueEvent(message.event, new Object[]{message.player, message.key, message.state});
			}
			return null;
		}
	}
}
