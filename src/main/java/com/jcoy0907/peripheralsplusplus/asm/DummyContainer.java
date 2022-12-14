package com.jcoy0907.peripheralsplusplus.asm;

import com.jcoy0907.peripheralsplusplus.reference.Reference;
import com.google.common.eventbus.EventBus;
import com.jcoy0907.peripheralsplusplus.reference.Reference;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class DummyContainer extends DummyModContainer {
	
	public DummyContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = Reference.MOD_ID+"Core";
		meta.name = Reference.MOD_NAME+" Core";
		meta.version = Reference.VERSION;
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		return true;
	}
}
