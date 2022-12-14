package com.jcoy0907.peripheralsplusplus.client.gui;

import com.jcoy0907.peripheralsplusplus.reference.Reference;
import com.jcoy0907.peripheralsplusplus.tiles.containers.ContainerAnalyzer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiAnalyzer extends GuiContainer {

	private int x, y, z;
	private EntityPlayer player;
	private World world;
	private int sizeX, sizeY;
	private ResourceLocation backgroundimage = new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + "textures/gui/guiAnalyzer.png");

	public GuiAnalyzer(EntityPlayer player, World world, int x, int y, int z) {
		super(new ContainerAnalyzer(player, (IInventory)world.getTileEntity(x,y,z), 176, 166));
		this.x = x;
		this.y = y;
		this.z = z;
		this.player = player;
		this.world = world;
		sizeX = 176;
		sizeY = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(backgroundimage);
		int x = (width - sizeX) / 2;
		int y = (height - sizeY) / 2;
		drawTexturedModalRect(x, y, 0, 0, sizeX, sizeY);
		fontRendererObj.drawString(StatCollector.translateToLocal("peripheralsplusplus.inv.analyzer"), x+65, y+3, 0x313131);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
