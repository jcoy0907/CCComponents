package com.jcoy0907.peripheralsplusplus.tiles;

import com.jcoy0907.peripheralsplusplus.mount.DynamicMount;
import com.jcoy0907.peripheralsplusplus.utils.IPlusPlusPeripheral;
import com.jcoy0907.peripheralsplusplus.utils.IPlusPlusPeripheral;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.tileentity.TileEntity;

public abstract class MountedTileEntity extends TileEntity implements IPlusPlusPeripheral {

	public MountedTileEntity() {
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
