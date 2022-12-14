package com.jcoy0907.peripheralsplusplus.turtles.peripherals;

import com.jcoy0907.peripheralsplusplus.mount.DynamicMount;
import com.jcoy0907.peripheralsplusplus.mount.DynamicMount;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

public abstract class MountedPeripheral implements IPeripheral {
	
	public MountedPeripheral() {
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
