package com.jcoy0907.cccomponents.blocks;

import com.jcoy0907.cccomponents.tiles.TileEntityChatBox;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockChatBox extends BlockPPP implements ITileEntityProvider {

	public BlockChatBox() {
		super();
		this.setBlockName("chatBox");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityChatBox();
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

}
