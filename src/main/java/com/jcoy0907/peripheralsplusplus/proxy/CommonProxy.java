package com.jcoy0907.peripheralsplusplus.proxy;

import com.austinv11.collectiveframework.minecraft.reference.ModIds;
import com.jcoy0907.peripheralsplusplus.PeripheralsPlusPlus;
import com.jcoy0907.peripheralsplusplus.event.handler.PeripheralContainerHandler;
import com.jcoy0907.peripheralsplusplus.event.handler.PropertiesHandler;
import com.jcoy0907.peripheralsplusplus.hooks.ComputerCraftHooks;
import com.jcoy0907.peripheralsplusplus.tiles.*;
import com.jcoy0907.peripheralsplusplus.villagers.TradeHandler;
import com.jcoy0907.peripheralsplusplus.event.handler.PeripheralContainerHandler;
import com.jcoy0907.peripheralsplusplus.event.handler.PropertiesHandler;
import com.jcoy0907.peripheralsplusplus.tiles.*;
import com.jcoy0907.peripheralsplusplus.villagers.TradeHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public void setupVillagers() {
		while (VillagerRegistry.getRegisteredVillagers().contains(PeripheralsPlusPlus.VILLAGER_ID)) //Dynamic villager ids ftw?
			PeripheralsPlusPlus.VILLAGER_ID++;
		VillagerRegistry.instance().registerVillagerId(PeripheralsPlusPlus.VILLAGER_ID);
		VillagerRegistry.instance().registerVillageTradeHandler(PeripheralsPlusPlus.VILLAGER_ID, new TradeHandler());
	}

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityChatBox.class, TileEntityChatBox.publicName);
		GameRegistry.registerTileEntity(TileEntityAIChatBox.class, TileEntityAIChatBox.publicName);
		GameRegistry.registerTileEntity(TileEntityPlayerSensor.class, TileEntityPlayerSensor.publicName);
		if (Loader.isModLoaded(ModIds.ThermalExpansion) || Loader.isModLoaded(ModIds.BuildCraft_Core))
			GameRegistry.registerTileEntity(TileEntityRFCharger.class, TileEntityRFCharger.publicName);
		GameRegistry.registerTileEntity(TileEntityOreDictionary.class, TileEntityOreDictionary.publicName);
		if (Loader.isModLoaded(ModIds.Forestry)) {
			GameRegistry.registerTileEntity(TileEntityAnalyzerBee.class, TileEntityAnalyzerBee.publicName);
			GameRegistry.registerTileEntity(TileEntityAnalyzerButterfly.class, TileEntityAnalyzerButterfly.publicName);
			GameRegistry.registerTileEntity(TileEntityAnalyzerTree.class, TileEntityAnalyzerTree.publicName);
		}
		GameRegistry.registerTileEntity(TileEntityTeleporter.class, TileEntityTeleporter.publicName);
		GameRegistry.registerTileEntity(TileEntityTeleporterT2.class, TileEntityTeleporterT2.publicName+"T2");
		GameRegistry.registerTileEntity(TileEntityEnvironmentScanner.class, TileEntityEnvironmentScanner.publicName);
		GameRegistry.registerTileEntity(TileEntitySpeaker.class, TileEntitySpeaker.publicName);
		GameRegistry.registerTileEntity(TileEntityAntenna.class, TileEntityAntenna.publicName);
		GameRegistry.registerTileEntity(TileEntityPeripheralContainer.class, TileEntityPeripheralContainer.publicName);
		if (Loader.isModLoaded(ModIds.AppliedEnergistics2))
			GameRegistry.registerTileEntity(TileEntityMEBridge.class, TileEntityMEBridge.publicName);
		GameRegistry.registerTileEntity(TileEntityNoteBlock.class, TileEntityNoteBlock.publicName);
		GameRegistry.registerTileEntity(TileEntityTurtle.class, TileEntityTurtle.publicName);
		GameRegistry.registerTileEntity(TileEntityTimeSensor.class, TileEntityTimeSensor.publicName);
		GameRegistry.registerTileEntity(TileEntityInteractiveSorter.class, TileEntityInteractiveSorter.publicName);
        GameRegistry.registerTileEntity(TileEntityPlayerInterface.class, TileEntityPlayerInterface.publicName);
		GameRegistry.registerTileEntity(TileEntityResupplyStation.class, TileEntityResupplyStation.publicName);
    }

	public void iconManagerInit() {}

	public void registerRenderers() {}

	public void prepareGuis() {}

	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new TileEntityChatBox.ChatListener());
		MinecraftForge.EVENT_BUS.register(new PeripheralContainerHandler());
		MinecraftForge.EVENT_BUS.register(new TileEntityAntenna());
		MinecraftForge.EVENT_BUS.register(new PropertiesHandler());
		MinecraftForge.EVENT_BUS.register(new ComputerCraftHooks());
	}
}
