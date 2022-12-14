package com.jcoy0907.cccomponents.pocket;

import com.austinv11.collectiveframework.minecraft.utils.NBTHelper;
import com.austinv11.collectiveframework.utils.LogicUtils;
import com.jcoy0907.cccomponents.hooks.ComputerCraftHooks;
import com.jcoy0907.cccomponents.hooks.ComputerCraftRegistry;
import com.jcoy0907.cccomponents.hooks.IPocketComputerUpgrade;
import com.jcoy0907.cccomponents.init.ModBlocks;
import com.jcoy0907.cccomponents.pocket.peripherals.PeripheralPeripheralContainer;
import com.jcoy0907.cccomponents.reference.Reference;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PocketPeripheralContainer implements IPocketComputerUpgrade {
	
	@Override
	public int getUpgradeID() {
		return Reference.PERIPHERAL_CONTAINER;
	}
	
	@Override
	public String getUnlocalisedAdjective() {
		return "cccomponents.pocketUpgrade.peripheralContainer";
	}
	
	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(ModBlocks.peripheralContainer);
	}
	
	@Override
	public IPeripheral createPeripheral(Entity entity, ItemStack stack) {
		return new PeripheralPeripheralContainer(stack);
	}
	
	@Override
	public void update(Entity entity, ItemStack stack, IPeripheral peripheral) {
		int id = NBTHelper.getInt(stack, "computerID");
		if (NBTHelper.hasTag(stack, "upgrades")) {
			List<IPocketComputerUpgrade> upgrades = getUpgrades(stack);
			HashMap<Integer, IPeripheral> peripherals = ComputerCraftHooks.cachedExtraPeripherals.get(id);
			for (IPocketComputerUpgrade upgrade : upgrades) {
				if (peripherals.containsKey(upgrade.getUpgradeID()))
					upgrade.update(entity, stack, peripherals.get(upgrade.getUpgradeID()));
			}
		}
	}
	
	@Override
	public boolean onRightClick(World world, EntityPlayer player, ItemStack stack, IPeripheral peripheral) {
		List<Boolean> results = new ArrayList<Boolean>();
		List<IPocketComputerUpgrade> upgrades = getUpgrades(stack);
		for (IPocketComputerUpgrade upgrade : upgrades)
			results.add(upgrade.onRightClick(world, player, stack, peripheral));
		boolean[] boolArray = new boolean[results.size()];
		for (int i = 0; i < results.size(); i++)
			boolArray[i] = results.get(i);
		return LogicUtils.or(boolArray);
	}
	
	public List<IPocketComputerUpgrade> getUpgrades(ItemStack item) {
		ArrayList<IPocketComputerUpgrade> upgrades = new ArrayList<IPocketComputerUpgrade>();
		if (NBTHelper.hasTag(item, "upgrades")) {
			NBTTagList list = NBTHelper.getList(item, "upgrades", Constants.NBT.TAG_FLOAT);
			for (int i = 0; i < list.tagCount(); i++) {
				int id = (int)list.func_150308_e(i);
				upgrades.add(ComputerCraftRegistry.pocketUpgrades.get(id));
			}
		}
		return upgrades;
	}
}
