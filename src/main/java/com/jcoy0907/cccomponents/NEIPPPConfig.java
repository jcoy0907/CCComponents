package com.jcoy0907.cccomponents;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.jcoy0907.cccomponents.creativetab.CreativeTabPPP;
import com.jcoy0907.cccomponents.nei.TurtleRecipeHandler;
import com.jcoy0907.cccomponents.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class NEIPPPConfig implements IConfigureNEI{

	@Override
	public void loadConfig() {
		List<ITurtleUpgrade> list = CreativeTabPPP.upgrades;
		ItemStack base = GameRegistry.findItemStack("ComputerCraft", "CC-TurtleExpanded", 1);
		if (base != null) {
			for (ITurtleUpgrade upgrade : list) {
				ItemStack upg1 = base.copy();
				upg1.stackTagCompound = new NBTTagCompound();
				upg1.stackTagCompound.setShort("leftUpgrade", (short) upgrade.getUpgradeID());
				API.addItemListEntry(upg1);
			}
		}
		ItemStack base2 = GameRegistry.findItemStack("ComputerCraft", "CC-TurtleAdvanced", 1);
		if (base2 == null)
			return;
		API.addItemListEntry(base2);
		for (ITurtleUpgrade upgrade : list) {
			ItemStack upg1 = base2.copy();
			upg1.stackTagCompound = new NBTTagCompound();
			upg1.stackTagCompound.setShort("leftUpgrade", (short) upgrade.getUpgradeID());
			API.addItemListEntry(upg1);
		}
		
		API.registerRecipeHandler(new TurtleRecipeHandler());
		API.registerUsageHandler(new TurtleRecipeHandler());
	}

	@Override
	public String getName() {
		return Reference.MOD_NAME+" NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "2.0";
	}
}
