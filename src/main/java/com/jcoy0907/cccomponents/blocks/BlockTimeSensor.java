package com.jcoy0907.cccomponents.blocks;

import com.jcoy0907.cccomponents.tiles.TileEntityTimeSensor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTimeSensor extends BlockPPP implements ITileEntityProvider {

	public BlockTimeSensor() {
		super();
		this.setBlockName("timeSensor");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTimeSensor();
	}
}
