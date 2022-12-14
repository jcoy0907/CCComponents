package com.jcoy0907.peripheralsplusplus.tiles;

import com.austinv11.collectiveframework.minecraft.utils.NBTHelper;
import com.jcoy0907.peripheralsplusplus.PeripheralsPlusPlus;
import com.jcoy0907.peripheralsplusplus.items.ItemSmartHelmet;
import com.jcoy0907.peripheralsplusplus.lua.LuaObjectHUD;
import com.jcoy0907.peripheralsplusplus.network.ScaleRequestPacket;
import com.jcoy0907.peripheralsplusplus.reference.Config;
import com.jcoy0907.peripheralsplusplus.utils.Util;
import com.jcoy0907.peripheralsplusplus.network.ScaleRequestPacket;
import com.jcoy0907.peripheralsplusplus.reference.Config;
import com.jcoy0907.peripheralsplusplus.utils.Util;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TileEntityAntenna extends MountedTileEntity {

	public static String publicName = "antenna";
	private  String name = "tileEntityAntenna";
	private int world = 0;
	public HashMap<IComputerAccess, Boolean> computers = new HashMap<IComputerAccess,Boolean>();
	private HashMap<Integer, LuaObjectHUD> huds = new HashMap<Integer,LuaObjectHUD>();
	public static HashMap<UUID, TileEntityAntenna> antenna_registry = new HashMap<UUID,TileEntityAntenna>();
	public UUID identifier;
	public String label;
	public volatile List<Entity> associatedEntities = new ArrayList<Entity>();

	public TileEntityAntenna() {
		super();
		identifier = UUID.randomUUID();
		while (antenna_registry.containsKey(identifier)) {
			if (antenna_registry.get(identifier).equals(this))
				break;
			identifier = UUID.randomUUID();
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		if (nbttagcompound.hasKey("identifier"))
			identifier = UUID.fromString(nbttagcompound.getString("identifier"));
		if (nbttagcompound.hasKey("label"))
			label = nbttagcompound.getString("label");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setString("identifier", identifier.toString());
		if (label != null)
			nbttagcompound.setString("label", label);
	}

	@Override
	public String getType() {
		return publicName;
	}

	@Override
	public String[] getMethodNames() {
		return new String[]{"getPlayers",/*Lists players wearing smart helmets linked to this antenna*/
				"getHUD",/*Returns a hud handle for the given player*/
				"setLabel",/*Sets the label of the antenna*/
				"getLabel",/*Gets the current label of the antenna*/
				//==Smart Helmet APIs end===
				"getInfectedEntities", /*Lists the entity ids for all entities currently infected by a nanobot swarm*/
				"getInfectedEntity"/*Gets the handle for a specified entity*/};
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
		if (!Config.enableSmartHelmet && method < 5)
			throw new LuaException("Smart Helmets have been disabled");
//		try {
		switch (method) {
			case 0://getPlayers
				synchronized (this) {
					List<String> players = new ArrayList<String>();
					for (Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
						if (player instanceof EntityPlayer)
							if (((EntityPlayer) player).getCurrentArmor(3) != null && ((EntityPlayer) player).getCurrentArmor(3).getItem() instanceof ItemSmartHelmet && NBTHelper.hasTag(((EntityPlayer) player).getCurrentArmor(3), "identifier"))
								if (identifier.equals(UUID.fromString(NBTHelper.getString(((EntityPlayer) player).getCurrentArmor(3), "identifier"))))
									players.add(((EntityPlayer) player).getCommandSenderName());
					return new Object[]{Util.arrayToMap(players.toArray())};
				}
			case 1:
				synchronized (this) {
					if (arguments.length < 1)
						throw new LuaException("Not enough arguments");
					if (!(arguments[0] instanceof String))
						throw new LuaException("Bad argument #1 (expected string)");
					if (Util.getPlayer((String) arguments[0]) == null)
						return new Object[]{null};
					LuaObjectHUD obj = new LuaObjectHUD((String) arguments[0], identifier);
					huds.put(computer.getID(), obj);
					PeripheralsPlusPlus.NETWORK.sendTo(new ScaleRequestPacket(this, computer.getID(), world), (EntityPlayerMP) Util.getPlayer((String)arguments[0]));
					context.pullEvent("resolution");
					return new Object[]{obj};
				}
			case 2:
				synchronized (this){
					if (arguments.length != 1)
						throw new LuaException("Incorrect Arguments!");
					this.setLabel(arguments[0].toString());
				}
			case 3:
				synchronized (this){
					return new Object[]{this.getLabel()};
				}
			case 4:
				HashMap<Integer, Integer> entities = new HashMap<Integer, Integer>();
				for (int i = 0; i < associatedEntities.size(); i++) {
					entities.put(i + 1, associatedEntities.get(i).getEntityId());
				}
				return new Object[]{entities};
		}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		return new Object[0];
	}
	
	private Entity entityFromId(int id) {
		for (Entity entity : associatedEntities)
			if (entity.getEntityId() == id)
				return entity;
		return null;
	}

	@Override
	public void updateEntity() {
		if (worldObj != null) {
			world = worldObj.provider.dimensionId;
			if (!antenna_registry.containsKey(identifier))
				antenna_registry.put(identifier, this);
		}
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		antenna_registry.remove(identifier);
	}
	
	@Override
	public void validate() {
		super.validate();
		antenna_registry.put(identifier, this);
	}

	@Override
	public void attach(IComputerAccess computer) {
		computers.put(computer, true);
		super.attach(computer);
	}

	@Override
	public void detach(IComputerAccess computer) {
		computers.remove(computer);
		super.detach(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return (this == other);
	}

	public void onResponse(int id, int width, int height) {
		if (huds.containsKey(id)) {
			huds.get(id).height = height;
			huds.get(id).width = width;
			for (IComputerAccess comp : computers.keySet())
				if (comp.getID() == id)
					comp.queueEvent("resolution", new Object[]{height, width});
		}
	}

	public void setLabel(String newLabel)
	{
		this.label = newLabel;
	}

	public String getLabel()
	{
		return this.label;
	}
}
