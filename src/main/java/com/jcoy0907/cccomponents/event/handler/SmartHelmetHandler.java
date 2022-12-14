package com.jcoy0907.cccomponents.event.handler;

import com.austinv11.collectiveframework.minecraft.utils.NBTHelper;
import com.jcoy0907.cccomponents.CCComponents;
import com.jcoy0907.cccomponents.client.gui.GuiHelmet;
import com.jcoy0907.cccomponents.items.ItemSmartHelmet;
import com.jcoy0907.cccomponents.network.InputEventPacket;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.UUID;

@SideOnly(Side.CLIENT)
public class SmartHelmetHandler {

	@SubscribeEvent
	public void onClick(InputEvent.MouseInputEvent event) {
		if (checkSmartHelmetStatus()) {
			CCComponents.NETWORK.sendToServer(new InputEventPacket(UUID.fromString(Minecraft.getMinecraft().thePlayer.getCurrentArmor(3).getTagCompound().getString("identifier")), Mouse.getEventButton(), Mouse.getEventButtonState(), "mouseInput", Minecraft.getMinecraft().thePlayer.getDisplayName()));
		}
	}

	@SubscribeEvent
	public void onKeyPressed(InputEvent.KeyInputEvent event) {
		if (checkSmartHelmetStatus()) {
			CCComponents.NETWORK.sendToServer(new InputEventPacket(UUID.fromString(Minecraft.getMinecraft().thePlayer.getCurrentArmor(3).getTagCompound().getString("identifier")), Keyboard.getEventKey(), Keyboard.getEventKeyState(), "keyInput", Minecraft.getMinecraft().thePlayer.getDisplayName()));
		}
	}

	@SubscribeEvent
	public void onButtonClick(GuiScreenEvent.ActionPerformedEvent.Post event) {
		if (event.gui instanceof GuiHelmet)
			if (checkSmartHelmetStatus())
				CCComponents.NETWORK.sendToServer(new InputEventPacket(UUID.fromString(Minecraft.getMinecraft().thePlayer.getCurrentArmor(3).getTagCompound().getString("identifier")), event.button.id, true, "buttonClicked", Minecraft.getMinecraft().thePlayer.getDisplayName()));
	}

	private boolean checkSmartHelmetStatus() {
		ItemStack helmet = Minecraft.getMinecraft().thePlayer.getCurrentArmor(3);
		if (helmet != null)
			if (helmet.getItem() instanceof ItemSmartHelmet)
				if (NBTHelper.hasTag(helmet, "identifier"))
					return true;
		return false;
	}
}
