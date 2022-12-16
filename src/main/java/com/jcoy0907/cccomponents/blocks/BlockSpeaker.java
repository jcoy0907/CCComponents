package com.jcoy0907.cccomponents.blocks;

import com.jcoy0907.cccomponents.init.ModBlocks;
import com.jcoy0907.cccomponents.reference.Reference;
import com.jcoy0907.cccomponents.tiles.TileEntitySpeaker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSpeaker extends BlockPPP implements ITileEntityProvider {

	public IIcon frontIcon;

	public BlockSpeaker() {
		super();
		this.setBlockName("speaker");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntitySpeaker();
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		int direction = BlockPistonBase.determineOrientation(world, x, y, z, entity);
		ForgeDirection dir = ForgeDirection.getOrientation(direction);
		direction = dir == ForgeDirection.DOWN || dir == ForgeDirection.UP ? 3 : direction;
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		return (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP ) && (side != world.getBlockMetadata(x, y, z)) ? this.blockIcon : this.frontIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		return (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP) && (side != meta) ? this.blockIcon : this.frontIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister){//Registers the block icon(s)
		blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(ModBlocks.chatBox.getUnlocalizedName())));
		frontIcon = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase()+":speaker");
	}
}
