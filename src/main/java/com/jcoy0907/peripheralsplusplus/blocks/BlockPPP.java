package com.jcoy0907.peripheralsplusplus.blocks;

import com.austinv11.collectiveframework.minecraft.blocks.BlockBase;
import com.jcoy0907.peripheralsplusplus.creativetab.CreativeTabPPP;
import com.jcoy0907.peripheralsplusplus.reference.Reference;
import com.jcoy0907.peripheralsplusplus.creativetab.CreativeTabPPP;
import com.jcoy0907.peripheralsplusplus.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPPP extends BlockBase {
	
	@Override
	public CreativeTabs getTab() {
		return CreativeTabPPP.PPP_TAB;
	}
	
	@Override
	public String getModId() {
		return Reference.MOD_ID;
	}
}
