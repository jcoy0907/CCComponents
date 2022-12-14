package com.jcoy0907.cccomponents.blocks;

import com.jcoy0907.cccomponents.CCComponents;
import com.jcoy0907.cccomponents.creativetab.CreativeTabPPP;
import com.jcoy0907.cccomponents.reference.Reference;
import com.jcoy0907.cccomponents.tiles.TileEntityInteractiveSorter;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockInteractiveSorter extends BlockContainerPPP {
	
	public BlockInteractiveSorter() {
		super(Material.rock);
		this.setBlockName("interactiveSorter");
		this.setCreativeTab(CreativeTabPPP.PPP_TAB);
		this.setHardness(4f);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityInteractiveSorter();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ){
		TileEntity te = world.getTileEntity(x, y, z);
		if (!world.isRemote) {
			if (te != null)
				player.openGui(CCComponents.instance, Reference.GUIs.INTERACTIVE_SORTER.ordinal(), world, x, y, z);
		}
		return true;
	}
}
