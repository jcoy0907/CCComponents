package com.jcoy0907.cccomponents.blocks;

import com.jcoy0907.cccomponents.reference.Config;
import com.jcoy0907.cccomponents.tiles.TileEntityPlayerSensor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlayerSensor extends BlockPPP implements ITileEntityProvider {

	public BlockPlayerSensor() {
		super();
		this.setBlockName("playerSensor");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityPlayerSensor();
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		if (Config.enablePlayerSensor)
			((TileEntityPlayerSensor)world.getTileEntity(x,y,z)).blockActivated(player.getDisplayName());
		return true;
	}
}
