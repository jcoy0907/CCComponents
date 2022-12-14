package com.jcoy0907.cccomponents.tiles;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import com.austinv11.collectiveframework.minecraft.tiles.NetworkedTileEntity;
import com.jcoy0907.cccomponents.reference.Config;
import com.jcoy0907.cccomponents.utils.ReflectionHelper;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class TileEntityRFCharger extends NetworkedTileEntity implements IEnergyReceiver {
	private EnergyStorage storage = new EnergyStorage(80000);//Leadstone Capacitor

	public static String publicName = "rfCharger";
	private String name = "tileEntityrfCharger";

	public TileEntityRFCharger() {
		super();
	}

	public String getName() {
		return name;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		storage.readFromNBT(nbttagcompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		storage.writeToNBT(nbttagcompound);
	}

	@Override
	public void updateEntity() {
		if (!getWorldObj().isRemote) {
			List<ITurtleAccess> turtles = new ArrayList<ITurtleAccess>(6);
			ForgeDirection[] dirs = {ForgeDirection.DOWN, ForgeDirection.EAST, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.UP, ForgeDirection.WEST};
			for (int i = 0; i < 6; i++) {
				int x = this.xCoord+dirs[i].offsetX;
				int y = this.yCoord+dirs[i].offsetY;
				int z = this.zCoord+dirs[i].offsetZ;
				if (!getWorldObj().blockExists(x, y, z))
					continue;
				TileEntity te = getWorldObj().getTileEntity(x, y, z);
				if (te != null) {
					try {
						ITurtleAccess turtle = ReflectionHelper.getTurtle(te);
						if (turtle != null) {
							turtles.add(turtle);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			int rate = ((int) Math.floor((float) 6/(float) turtles.size()));
			for (ITurtleAccess turtle : turtles) {
				if (storage.getEnergyStored() >= rate)
					storage.extractEnergy(addFuel(turtle, rate)* Config.fuelRF, false);
			}
		}
	}

	private int addFuel(ITurtleAccess turtle, int rate) {
		if (turtle.getFuelLimit() > turtle.getFuelLevel()) {
			turtle.setFuelLevel(rate+turtle.getFuelLevel());//Bad fix for a crash with turtle.addFuel()
			return rate;
		}
		return 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}
}
