package com.jcoy0907.cccomponents.turtles;

import com.jcoy0907.cccomponents.init.ModBlocks;
import com.jcoy0907.cccomponents.init.ModItems;
import com.jcoy0907.cccomponents.reference.Reference;
import com.jcoy0907.cccomponents.turtles.peripherals.PeripheralTank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class TurtleTank implements ITurtleUpgrade {
	
	@Override
	public int getUpgradeID() {
		return Reference.TANK_UPGRADE;
	}

	@Override
	public String getUnlocalisedAdjective() {
		return "cccomponents.turtleUpgrade.tank";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(ModItems.tank);
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new PeripheralTank(turtle, side);
	}

	@Override
	public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
		return ModBlocks.dummyBlock.getIcon(0, 1);
	}

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
		IPeripheral peripheral = turtle.getPeripheral(side);
		if (peripheral instanceof PeripheralTank)
			((PeripheralTank) peripheral).update();
	}
}
