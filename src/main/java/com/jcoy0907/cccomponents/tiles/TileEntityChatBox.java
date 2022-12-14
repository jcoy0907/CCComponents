package com.jcoy0907.cccomponents.tiles;

import com.jcoy0907.cccomponents.reference.Config;
import com.jcoy0907.cccomponents.utils.ChatUtil;
import com.jcoy0907.cccomponents.utils.Util;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.HashMap;

public class TileEntityChatBox extends MountedTileEntity {

	public static String publicName = "chatBox";
	private  String name = "tileEntityChatBox";
	private HashMap<IComputerAccess,Boolean> computers = new HashMap<IComputerAccess,Boolean>();
	private static final int TICKER_INTERVAL = 20;
	private int ticker = 0;
	private int subticker = 0;
	private ITurtleAccess turtle;

	public TileEntityChatBox() {
		super();
	}

	public TileEntityChatBox(ITurtleAccess turtle) {
		this.xCoord = turtle.getPosition().posX;
		this.yCoord = turtle.getPosition().posY;
		this.zCoord = turtle.getPosition().posZ;
		this.setWorldObj(turtle.getWorld());
		this.turtle = turtle;
	}

	public String getName() {
		return name;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
	}

	@Override
	public void updateEntity() {
		if (subticker > 0)
			subticker--;
		if (subticker == 0 && ticker != 0)
			ticker = 0;
	}

	public void updateEntity(boolean turtle) {
		if (turtle) {
			this.xCoord = this.turtle.getPosition().posX;
			this.yCoord = this.turtle.getPosition().posY;
			this.zCoord = this.turtle.getPosition().posZ;
		}
		updateEntity();
	}

	public void onChat(EntityPlayer player, String message) {
		for (IComputerAccess computer : computers.keySet())
			computer.queueEvent("chat", new Object[]{player.getDisplayName(), message});
	}

	public void onDeath(EntityPlayer player, DamageSource source) {
		String killer = null;
		if (source instanceof EntityDamageSource) {
			Entity ent = source.getEntity();
			if (ent != null)
				killer = ent.getCommandSenderName();
		}
		for (IComputerAccess computer : computers.keySet())
			computer.queueEvent("death", new Object[] {player.getDisplayName(), killer, source.damageType});
	}
	
	public void onCommand(EntityPlayerMP player, String message) {
		for (IComputerAccess computer : computers.keySet())
			computer.queueEvent("command", new Object[] {player.getDisplayName(), Util.arrayToMap(message.split(" "))});
	}

	@Override
	public String getType() {
		return publicName;
	}

	@Override
	public String[] getMethodNames() {
		return new String[] {"say", "tell"};
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
		//try {
		if (Config.enableChatBox) {
			if (method == 0) {
				if (arguments.length < 1)
					throw new LuaException("Too few arguments");
				if (!(arguments[0] instanceof String))
					throw new LuaException("Bad argument #1 (expected string)");
				if (arguments.length > 1 && !(arguments[1] instanceof Double))
					throw new LuaException("Bad argument #2 (expected number)");
				if (arguments.length > 2 && !(arguments[2] instanceof Boolean))
					throw new LuaException("Bad argument #3 (expected boolean)");
				if (arguments.length > 3) {
					if(!(arguments[3] instanceof String)) {
						throw new LuaException("Bad argument #4 (expected string)");
					}
				}
				if (ticker == Config.sayRate) {
					throw new LuaException("Please try again later, you are sending messages too often");
				}
				String message;
				if (Config.logCoords) {
					message = ChatUtil.getCoordsPrefix(this) + arguments[0];
				}else if (!Config.logCoords && arguments.length > 3) {
					message = "[" + arguments[3] + "] " + arguments[0];
				}else {
					message ="[@] " + arguments[0];
				}

				double range = Config.sayRange;
				if (Config.sayRange < 0) {
					range = Double.MAX_VALUE;
				} else if (arguments.length > 1) {
					if ((Double) arguments[1] < 0) {
						if (Config.sayRange < 0) {
							range = Double.MAX_VALUE;
						}
					} else if ((Double) arguments[1] < Config.sayRange) {
						range = (Double) arguments[1];
					}
				}

				synchronized (this) {
					ChatUtil.sendMessage(this, message, range, (arguments.length > 2 && (Boolean) arguments[2] && Config.allowUnlimitedVertical));
					subticker = TICKER_INTERVAL;
					ticker++;
				}
				return new Object[]{true};
			} else if (method == 1) {
				if (arguments.length < 2)
					throw new LuaException("Too few arguments");
				if (!(arguments[0] instanceof String))
					throw new LuaException("Bad argument #1 (expected string)");
				if (!(arguments[1] instanceof String))
					throw new LuaException("Bad argument #2 (expected string)");
				else if (arguments.length > 2 && !(arguments[2] instanceof Double))
					throw new LuaException("Bad argument #3 (expected number)");
				else if (arguments.length > 3 && !(arguments[3] instanceof Boolean))
					throw new LuaException("Bad argument #4 (expected boolean)");
				if (arguments.length > 4) {
					if (Config.logCoords) {
						throw new LuaException("Coordinate logging is enabled, disable this to enable naming");
					} else if (!(arguments[4] instanceof String)) {
						throw new LuaException("Bad argument #5 (expected string)");
					}
				}
				if (ticker == Config.sayRate) {
					throw new LuaException("Please try again later, you are sending messages too often");
				}
				String message;
				if (Config.logCoords) {
					message = ChatUtil.getCoordsPrefix(this) + arguments[1];
				} else if (!Config.logCoords && arguments.length > 4) {
					message = "[" + arguments[4] + "] " + arguments[1];
				} else {
					message = "[@] " + arguments[1];
				}
				double range = Config.sayRange < 0 ? Double.MAX_VALUE : Config.sayRange;
				if (arguments.length > 2)
					range = (Double) arguments[2];

				synchronized (this) {
					subticker = TICKER_INTERVAL;
					ticker++;
					ChatUtil.sendMessage((String) arguments[0], this, message, range, (arguments.length > 3 && (Boolean) arguments[3] && Config.allowUnlimitedVertical));
				}
				return new Object[]{true};
			}
		}else {
			throw new LuaException("Chat boxes have been disabled");
		}
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		return new Object[0];
	}

	@Override
	public void attach(IComputerAccess computer) {
		if (computers.size() == 0)
			ChatListener.chatBoxMap.put(this, true);
		computers.put(computer, true);
		super.attach(computer);
	}

	@Override
	public void detach(IComputerAccess computer) {
		computers.remove(computer);
		if (computers.size() == 0)
			ChatListener.chatBoxMap.remove(this);
		super.detach(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return (other == this);
	}

	public static class ChatListener {
		private static HashMap<TileEntityChatBox,Boolean> chatBoxMap = new HashMap<TileEntityChatBox, Boolean>();

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onChat(ServerChatEvent event) {
			if (!event.isCanceled()) {
				if (Config.enableChatBox) {
					String commandPrefix = Config.chatboxCommandPrefix.trim();
					if (!commandPrefix.equals("") && !commandPrefix.equals(" ") && event.message.startsWith(commandPrefix)) {
						event.setCanceled(true);

						for (TileEntityChatBox box : chatBoxMap.keySet()) {
							if (Config.readRange < 0 || Vec3.createVectorHelper(box.xCoord, box.yCoord, box.zCoord).distanceTo(event.player.getPosition(1.0f)) <= Config.readRange)
								box.onCommand(event.player, event.message.replace(commandPrefix, ""));
						}
					} else {
						for (TileEntityChatBox box : chatBoxMap.keySet()) {
							if (Config.readRange < 0 || Vec3.createVectorHelper(box.xCoord, box.yCoord, box.zCoord).distanceTo(event.player.getPosition(1.0f)) <= Config.readRange) {
								box.onChat(event.player, event.message);
							}
						}
					}
				}
			}
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onDeath(LivingDeathEvent event) {
			if (!event.isCanceled()) {
				if (Config.enableChatBox) {
					if (event.entity instanceof EntityPlayer) {
						for (TileEntityChatBox box : chatBoxMap.keySet()) {
							if (Config.readRange < 0 || Vec3.createVectorHelper(box.xCoord, box.yCoord, box.zCoord).distanceTo(((EntityPlayer) event.entity).getPosition(1.0f)) <= Config.readRange)
								box.onDeath((EntityPlayer) event.entity, event.source);
						}
					}
				}
			}
		}
	}
}
