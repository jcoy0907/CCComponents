package com.jcoy0907.cccomponents.init;

import com.austinv11.collectiveframework.minecraft.reference.ModIds;
import com.jcoy0907.cccomponents.event.handler.PocketComputerCraftingHandler;
import com.jcoy0907.cccomponents.recipe.ContainerRecipe;
import com.jcoy0907.cccomponents.reference.Config;
import com.jcoy0907.cccomponents.reference.Reference;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class Recipes {

	public static void init(){
		//Hehehe inside jokes ftw!
//		ItemStack cDust = new ItemStack(Items.redstone);
//		cDust.setStackDisplayName(StatCollector.translateToLocal("item.cccomponents:dustRedstone.name"));
//		ItemStack tIngot = new ItemStack(Items.iron_ingot);
//		tIngot.setStackDisplayName(StatCollector.translateToLocal("item.cccomponents:ingotIron.name"));
//		GameRegistry.addShapelessRecipe(cDust, new ItemStack(Items.redstone));
//		GameRegistry.addShapelessRecipe(tIngot, new ItemStack(Items.iron_ingot));
		if (Config.enableChatBox)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.chatBox), "gng", "ndn", "gng", 'g', "ingotGold", 'n', new ItemStack(Blocks.noteblock), 'd', "gemDiamond"));
		if (Config.enablePlayerSensor)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.playerSensor), "grg", "ede", "grg", 'g', "ingotGold", 'r', "dustRedstone", 'e', new ItemStack(Items.ender_eye), 'd', "gemDiamond"));
		if (Config.enableRFCharger)
			if (Loader.isModLoaded("ThermalExpansion"))
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.rfCharger), "rlr", "lcl", "rer", 'r', "dustRedstone", 'l', "ingotLead", 'c', new ItemStack(GameRegistry.findItem("ThermalExpansion", "capacitor"), 1, 2), 'e', new ItemStack(GameRegistry.findItem("ThermalExpansion", "material"), 1, 3)));
			else if (Loader.isModLoaded("BuildCraft|Core"))
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.rfCharger), "rlr", "lcl", "rer", 'r', "dustRedstone", 'l', "gearIron", 'c', "gearGold", 'e', new ItemStack(GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowergold"))));
		if (Config.enableOreDictionary)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.oreDictionary), "isi", "sbs", "isi", 'i', "ingotIron", 's', "stone", 'b', new ItemStack(Items.book)));
		if (Config.enableAnalyzers && Loader.isModLoaded("Forestry")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.beeAnalyzer), "grg", "rar", "grg", 'g', "ingotGold", 'r', "dustRedstone", 'a', new ItemStack(GameRegistry.findItem("Forestry", "beealyzer"))));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.butterflyAnalyzer), "grg", "rar", "grg", 'g', "ingotGold", 'r', "dustRedstone", 'a', new ItemStack(GameRegistry.findItem("Forestry", "flutterlyzer"))));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.treeAnalyzer), "grg", "rar", "grg", 'g', "ingotGold", 'r', "dustRedstone", 'a', new ItemStack(GameRegistry.findItem("Forestry", "treealyzer"))));
		}
		if (Config.enableTurtleTeleporter) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.teleporter), "pep", "pop", "pep", 'p', new ItemStack(Items.ender_pearl), 'e', new ItemStack(Items.ender_eye), 'o', new ItemStack(Blocks.obsidian)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.teleporterT2), "ere", "EtE", "ere", 'r', "dustRedstone", 'e', new ItemStack(Items.ender_eye), 't', new ItemStack(ModBlocks.teleporter), 'E', "gemEmerald"));
		}
		if (Config.enableEnvironmentScanner)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.environmentScanner), "iei", "rmr", "iri", 'i', "ingotIron", 'e', new ItemStack(Items.ender_eye), 'r', "dustRedstone", 'm', Items.map));
		if (Config.enableFeederTurtle)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.feederUpgrade), "iwi", "wew", "iwi", 'i', "ingotIron", 'w', new ItemStack(Items.wheat), 'e', new ItemStack(Items.ender_eye)));
		if (Config.enableSpeaker)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.speaker), "gng", "nrn", "gng", 'g', "ingotGold", 'n', Blocks.noteblock, 'r', "blockRedstone"));
		if (Config.enablePeripheralContainer) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.peripheralContainer), "iii", "ici", "imi", 'i', "ingotIron", 'c', Blocks.chest, 'm', new ItemStack(GameRegistry.findItem("ComputerCraft", "CC-Cable"), 1, 1)));
			RecipeSorter.register(Reference.MOD_ID.toLowerCase()+":containerRecipe", ContainerRecipe.class,
					RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
			GameRegistry.addRecipe(new ContainerRecipe());
		}
		if (Config.enableMEBridge && Loader.isModLoaded("appliedenergistics2"))
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.meBridge), new ItemStack(GameRegistry.findItem("appliedenergistics2", "tile.BlockInterface")), new ItemStack(GameRegistry.findItem("ComputerCraft", "CC-Cable"), 1, 1)));
		if (Config.enableTankTurtle)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tank), "ggg", "gmg", "ggg", 'g', "blockGlass", 'm', new ItemStack(GameRegistry.findItem("ComputerCraft", "CC-Cable"), 1, 1)));
		if (Config.enableSmartHelmet) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.smartHelmet), new ItemStack(GameRegistry.findItem("ComputerCraft", "CC-Peripheral"), 1, 1), Items.iron_helmet));
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.smartHelmet), new ItemStack(ModItems.smartHelmet));
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.antenna), "sms", " i ", "ppp", 's', "stone", 'm', new ItemStack(GameRegistry.findItem("ComputerCraft", "CC-Peripheral"), 1, 1), 'i', "ingotIron", 'p', new ItemStack(Blocks.heavy_weighted_pressure_plate)));
		if (Config.enableNoteBlock) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.noteBlock), "igi", "rnr", "igi", 'i', "ingotIron", 'g', "ingotGold", 'r', "dustRedstone", 'n', Blocks.noteblock));
		}
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.turtle), GameRegistry.findBlock("ComputerCraft", "CC-TurtleAdvanced"));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.turtle), GameRegistry.findBlock("ComputerCraft", "CC-TurtleExpanded"));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.turtle), GameRegistry.findBlock("ComputerCraft", "CC-Turtle"));
		if (Config.enableChunkyTurtle) {
			List<Object> chunkLoaders = new ArrayList<Object>();
			if (Loader.isModLoaded(ModIds.ChickenChunks)) {
				chunkLoaders.add(new ItemStack(GameRegistry.findBlock(ModIds.ChickenChunks, "chickenChunkLoader")));
				if (Config.chunkLoadingRadius == 0)
					chunkLoaders.add(new ItemStack(GameRegistry.findBlock(ModIds.ChickenChunks, "chickenChunkLoader"), 1, 1));
			}
			if (Loader.isModLoaded(ModIds.DimensionalAnchors))
				chunkLoaders.add(new ItemStack(GameRegistry.findBlock(ModIds.DimensionalAnchors, "chunkloader")));
			if (Loader.isModLoaded(ModIds.MineFactoryReloaded))
				chunkLoaders.add(new ItemStack(GameRegistry.findBlock(ModIds.MineFactoryReloaded, "machine.2"), 1, 10));
			if (Loader.isModLoaded(ModIds.Railcraft))
				chunkLoaders.add(GameRegistry.findBlock(ModIds.Railcraft, "machine.alpha"));
			for (Object o : chunkLoaders)
				if (o != null)
					GameRegistry.addShapelessRecipe(new ItemStack(ModItems.chunkLoaderUpgrade), o, new ItemStack(GameRegistry.findItem("ComputerCraft", "CC-Peripheral"), 1, 1));
		}
		if (Config.enableInteractiveSorter)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.interactiveSorter), "dpd", "pep", "dpd", 'd', "gemDiamond", 'p', Blocks.piston, 'e', Items.ender_eye));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.permCard), new ItemStack(ModItems.permCard));
		if (Config.enableResupplyStation) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.resupplyUpgrade), "grg", "rer", "grg", 'g', "ingotGold", 'r', "dustRedstone", 'e', Blocks.ender_chest));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.resupplyStation), "iri", "cuc", "iri", 'i', "ingotIron", 'r', "dustRedstone", 'c', Blocks.chest, 'u', ModItems.resupplyUpgrade));
		}
        if (Config.enablePlayerInterface)
            GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.playerInterface), "ipi", "ini", "imi", 'i', new ItemStack(Items.iron_ingot), 'p', new ItemStack(ModItems.permCard), 'n', new ItemStack(Items.nether_star), 'm', new ItemStack(GameRegistry.findBlock("ComputerCraft", "CC-Peripheral"), 1, 1));
        if (Config.enablePlayerInterface)
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.permCard), new ItemStack(Items.emerald), new ItemStack(Items.iron_ingot), new ItemStack(Items.redstone));
		if (Config.enableMotionDetector)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.motionDetector), "srs", "rer", "srs", 's', "stone", 'r', "dustRedstone", 'e', Items.ender_pearl));
		RecipeSorter.register(Reference.MOD_ID.toLowerCase()+":pocketComputerUpgradeRecipe", 
				PocketComputerCraftingHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		GameRegistry.addRecipe(new PocketComputerCraftingHandler());
        if (Config.enableAIChatBox)
            GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.aiChatBox), " r ", "cbc", " s ", 'r', new ItemStack(Items.redstone), 'c', new ItemStack(Items.comparator), 'b', new ItemStack(ModBlocks.chatBox), 's', new ItemStack(Items.slime_ball));
	}
}
