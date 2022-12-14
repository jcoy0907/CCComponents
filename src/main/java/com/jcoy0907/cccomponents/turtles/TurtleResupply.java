package com.jcoy0907.cccomponents.turtles;

import com.jcoy0907.cccomponents.init.ModBlocks;
import com.jcoy0907.cccomponents.init.ModItems;
import com.jcoy0907.cccomponents.reference.Reference;
import com.jcoy0907.cccomponents.turtles.peripherals.PeripheralResupply;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class TurtleResupply implements ITurtleUpgrade {
	
	@Override
	public int getUpgradeID() {
		return Reference.RESUPPLY_UPGRADE;
	}
	
	@Override
	public String getUnlocalisedAdjective() {
		return "cccomponents.turtleUpgrade.resupply";
	}
	
	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}
	
	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(ModItems.resupplyUpgrade);
	}
	
	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new PeripheralResupply(turtle);
	}
	
	@Override
	public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
		return null;
	}
	
	@Override
	public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
		return ModBlocks.resupplyStation.getIcon(1, 1);
	}
	
	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
		
	}
}
