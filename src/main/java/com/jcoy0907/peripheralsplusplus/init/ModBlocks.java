package com.jcoy0907.peripheralsplusplus.init;

import com.jcoy0907.peripheralsplusplus.PeripheralsPlusPlus;
import com.jcoy0907.peripheralsplusplus.blocks.*;
import com.jcoy0907.peripheralsplusplus.items.ItemBlockPeripheralContainer;
import com.jcoy0907.peripheralsplusplus.items.ItemBlockTurtle;
import com.jcoy0907.peripheralsplusplus.reference.Reference;
import com.jcoy0907.peripheralsplusplus.blocks.*;
import com.jcoy0907.peripheralsplusplus.items.ItemBlockPeripheralContainer;
import com.jcoy0907.peripheralsplusplus.items.ItemBlockTurtle;
import com.jcoy0907.peripheralsplusplus.reference.Reference;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
	public static final BlockPPP chatBox = new BlockChatBox();
	public static final BlockPPP aiChatBox = new BlockAIChatBox();
	public static final BlockPPP playerSensor = new BlockPlayerSensor();
	public static BlockPPP rfCharger;
	public static final BlockPPP oreDictionary = new BlockOreDictionary();
	public static BlockContainer beeAnalyzer;
	public static BlockContainer treeAnalyzer;
	public static BlockContainer butterflyAnalyzer;
	public static final BlockPPP teleporter = new BlockTeleporter();
	public static final BlockPPP teleporterT2 = new BlockTeleporterT2();
	public static final BlockPPP environmentScanner = new BlockEnvironmentScanner();
	public static final BlockPPP speaker = new BlockSpeaker();
	public static final BlockPPP antenna = new BlockAntenna();
	public static final BlockPPP peripheralContainer = new BlockPeripheralContainer();
	public static final BlockPPP meBridge = new BlockMEBridge();
	public static final BlockPPP dummyBlock = new BlockDummyBlock();
	public static final BlockPPP noteBlock = new BlockNote();
	public static final BlockPPP turtle = new BlockTurtle();
	public static final BlockPPP timeSensor = new BlockTimeSensor();
	public static final BlockContainer interactiveSorter = new BlockInteractiveSorter();
    public static final BlockContainer playerInterface = new BlockPlayerInterface();
	public static final BlockContainer resupplyStation = new BlockResupplyStation();

	public static void init(){
		GameRegistry.registerBlock(chatBox, "chatBox");
		GameRegistry.registerBlock(aiChatBox, "aiChatBox");
		GameRegistry.registerBlock(playerSensor, "playerSensor");
		if (Loader.isModLoaded("ThermalExpansion")) {
			rfCharger = new BlockRFCharger();
			GameRegistry.registerBlock(rfCharger, "rfCharger");
		}
		GameRegistry.registerBlock(oreDictionary, "oreDictionary");
		if (Loader.isModLoaded("Forestry")) {
			PeripheralsPlusPlus.LOGGER.info("Forestry is loaded! Registering analyzer peripherals...");
			beeAnalyzer = new BlockAnalyzerBee();
			GameRegistry.registerBlock(beeAnalyzer, "beeAnalyzer");
			treeAnalyzer = new BlockAnalyzerTree();
			GameRegistry.registerBlock(treeAnalyzer, "treeAnalyzer");
			butterflyAnalyzer = new BlockAnalyzerButterfly();
			GameRegistry.registerBlock(butterflyAnalyzer, "butterflyAnalyzer");
		} else
			PeripheralsPlusPlus.LOGGER.info("Forestry not found, skipping analyzer peripherals");
		GameRegistry.registerBlock(teleporter, "teleporter");
		GameRegistry.registerBlock(teleporterT2, "teleporterT2");
		GameRegistry.registerBlock(environmentScanner, "environmentScanner");
		GameRegistry.registerBlock(speaker, "speaker");
		GameRegistry.registerBlock(antenna, "antenna");
		GameRegistry.registerBlock(peripheralContainer, ItemBlockPeripheralContainer.class, "peripheralContainer");
		if (Loader.isModLoaded("appliedenergistics2")) {
			PeripheralsPlusPlus.LOGGER.info("Applied Energistics is loaded! Registering the ME Bridge...");
			GameRegistry.registerBlock(meBridge, "meBridge");
		} else
			PeripheralsPlusPlus.LOGGER.info("Applied Energistics not found, skipping the ME Bridge");
		GameRegistry.registerBlock(dummyBlock, "dummyBlock");
		GameRegistry.registerBlock(noteBlock, "noteBlock");
		GameRegistry.registerBlock(turtle, ItemBlockTurtle.class, "turtle");
		GameRegistry.registerBlock(timeSensor, "timeSensor");
		GameRegistry.registerBlock(interactiveSorter, "interactiveSorter");
        GameRegistry.registerBlock(playerInterface, "playerInterface");
		GameRegistry.registerBlock(resupplyStation, "resupplyStation");
    }
}
