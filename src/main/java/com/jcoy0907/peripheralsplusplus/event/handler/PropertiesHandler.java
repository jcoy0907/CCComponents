package com.jcoy0907.peripheralsplusplus.event.handler;

import com.jcoy0907.peripheralsplusplus.entities.NanoProperties;
import com.jcoy0907.peripheralsplusplus.reference.Config;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.EntityEvent;

public class PropertiesHandler {
	@SubscribeEvent
	public void onEntityConstruct(EntityEvent.EntityConstructing event) {
		if (Config.enableNanoBots) {
			if (event.entity.getExtendedProperties(NanoProperties.IDENTIFIER) == null) {
				event.entity.registerExtendedProperties(NanoProperties.IDENTIFIER, new NanoProperties());
			}
		}
	}
}
