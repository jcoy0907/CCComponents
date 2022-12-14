package com.jcoy0907.cccomponents.turtles;

import com.austinv11.collectiveframework.minecraft.reference.ModIds;
import com.jcoy0907.cccomponents.reference.Config;
import com.jcoy0907.cccomponents.reference.Reference;
import com.jcoy0907.cccomponents.turtles.peripherals.PeripheralBarrel;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class TurtleBarrel implements ITurtleUpgrade {

	@Override
	public int getUpgradeID() {
		return Reference.BARREL_UPGRADE;
	}

	@Override
	public String getUnlocalisedAdjective() {
		return "cccomponents.turtleUpgrade.barrel";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		if (!Config.enableBarrelTurtle)
			return null;
		if (Loader.isModLoaded(ModIds.JABBA))
			return new ItemStack(GameRegistry.findBlock(ModIds.JABBA, "barrel"));
		if (Loader.isModLoaded(ModIds.Factorization))
			return new ItemStack(GameRegistry.findBlock(ModIds.Factorization, "dayBarrel"));
		return null;
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new PeripheralBarrel(turtle, side);
	}

	@Override
	public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
		return Blocks.log.getIcon(2, 0);
	}

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
		IPeripheral peripheral = turtle.getPeripheral(side);
		if (peripheral instanceof PeripheralBarrel) {
			PeripheralBarrel barrel = (PeripheralBarrel) peripheral;
			if (barrel.changed)
				barrel.update();
		}
	}
}
