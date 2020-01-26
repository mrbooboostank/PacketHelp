package com.example.examplemod.packets;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
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
	        ServerPlayerEntity sender = context.get().getSender(); // the client that sent this packet
	        // do stuff
	    });
		context.get().setPacketHandled(true);
	}
    
}