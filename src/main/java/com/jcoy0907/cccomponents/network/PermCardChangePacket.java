package com.jcoy0907.cccomponents.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PermCardChangePacket implements IMessage
{
    private boolean canGetStacks, canWithdraw, canDeposit;

    public PermCardChangePacket() {}

    public PermCardChangePacket(boolean canGetStacks, boolean canWithdraw, boolean canDeposit) {
        this.canGetStacks = canGetStacks;
        this.canWithdraw = canWithdraw;
        this.canDeposit = canDeposit;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound tag = ByteBufUtils.readTag(buf);
        canGetStacks = tag.getBoolean("getStacks");
        canWithdraw = tag.getBoolean("withdraw");
        canDeposit = tag.getBoolean("deposit");
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("getStacks", canGetStacks);
        tag.setBoolean("withdraw", canWithdraw);
        tag.setBoolean("deposit", canDeposit);
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class PermCardChangePacketHandler implements IMessageHandler<PermCardChangePacket, IMessage> {
        @Override
        public IMessage onMessage(PermCardChangePacket message, MessageContext ctx)
        {
            ItemStack permCard = ctx.getServerHandler().playerEntity.getCurrentEquippedItem();
            if (permCard != null) {
                NBTTagCompound permCardTag = permCard.getTagCompound();
                if (permCardTag != null) {
                    permCardTag.setBoolean("getStacks", message.canGetStacks);
                    permCardTag.setBoolean("withdraw", message.canWithdraw);
                    permCardTag.setBoolean("deposit", message.canDeposit);
                }
            }
            return null;
        }
    }
}
