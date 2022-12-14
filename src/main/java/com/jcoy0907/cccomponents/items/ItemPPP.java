package com.jcoy0907.cccomponents.items;

import com.austinv11.collectiveframework.minecraft.items.ItemBase;
import com.jcoy0907.cccomponents.creativetab.CreativeTabPPP;
import com.jcoy0907.cccomponents.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;

public class ItemPPP extends ItemBase {
	
	@Override
	public CreativeTabs getTab() {
		return CreativeTabPPP.PPP_TAB;
	}
	
	@Override
	public String getModId() {
		return Reference.MOD_ID;
	}
}
