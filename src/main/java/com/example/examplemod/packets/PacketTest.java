package com.example.examplemod.packets;

import java.util.function.Supplier;

import com.example.examplemod.TestScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketTest {

	private final int data;
	
	public PacketTest(int data) {
		this.data = data;
	}
	
	public static void encode(PacketTest msg, PacketBuffer buf) {
		buf.writeInt(msg.data);
	}
	
	public static PacketTest decode(PacketBuffer buf) {
		return new PacketTest(buf.readInt());
	}
	
	public static void handle(PacketTest msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
	        // Work that needs to be threadsafe (most work)
	        // ServerPlayerEntity playerIn = context.get().getSender(); // the client that sent this packet
	        clientWork(msg);
	    });
		context.get().setPacketHandled(true);
	}
	
	// why is OnlyIn working but DistExecutor isn't?
	@OnlyIn(Dist.CLIENT)
	private static void clientWork(PacketTest msg) {
        int rand_choice = msg.data;
        System.out.println("HELLO FROM CLIENT");
        System.out.println(rand_choice);
		ClientPlayerEntity playerIn = Minecraft.getInstance().player;
        Minecraft.getInstance().displayGuiScreen(new TestScreen(playerIn, false, 1, "CLIENT TIME?" ));
	}
    
}