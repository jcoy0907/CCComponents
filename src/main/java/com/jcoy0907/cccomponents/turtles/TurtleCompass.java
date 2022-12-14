package com.jcoy0907.cccomponents.turtles;

import com.jcoy0907.cccomponents.reference.Config;
import com.jcoy0907.cccomponents.reference.Reference;
import com.jcoy0907.cccomponents.turtles.peripherals.PeripheralCompass;
import com.austinv11.collectiveframework.minecraft.utils.IconManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class TurtleCompass implements ITurtleUpgrade, IconManager.IIconNeeded{

	private static IIcon icon;

	@Override
	public int getUpgradeID() {
		return Reference.COMPASS_UPGRADE;
	}

	@Override
	public String getUnlocalisedAdjective() {
		return "cccomponents.turtleUpgrade.compass";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		if (Config.enableNavigationTurtle)
			return new ItemStack(Items.compass);
		return null;
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new PeripheralCompass(turtle);
	}

	@Override
	public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
		return null;
	}

	@Override
	public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
		return icon;
	}

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register) {
		icon = register.registerIcon(Reference.MOD_ID+":upgradeCompass");
	}
}
