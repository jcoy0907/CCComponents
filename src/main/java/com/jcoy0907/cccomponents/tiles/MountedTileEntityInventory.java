package com.jcoy0907.cccomponents.tiles;

import com.austinv11.collectiveframework.minecraft.tiles.TileEntityInventory;
import com.jcoy0907.cccomponents.mount.DynamicMount;
import com.jcoy0907.cccomponents.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.peripheral.IComputerAccess;

public abstract class MountedTileEntityInventory extends TileEntityInventory implements IPlusPlusPeripheral {

	public MountedTileEntityInventory() {
		super();
	}

	@Override
	public void attach(IComputerAccess computer) {
		computer.mount(DynamicMount.DIRECTORY, new DynamicMount(this));
	}

	@Override
	public void detach(IComputerAccess computer) {
		computer.mount(DynamicMount.DIRECTORY, new DynamicMount(this));
		computer.unmount(DynamicMount.DIRECTORY);
	}
}
