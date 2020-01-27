package com.example.examplemod.packets;

import com.example.examplemod.ExampleMod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandlerTest {
	
	private static final String PROTOCOL_VERSION = "1";
	public static SimpleChannel INSTANCE; 
	
	public static void register() {
	INSTANCE = NetworkRegistry.newSimpleChannel(
				    new ResourceLocation(ExampleMod.MODID, "main"),
				    () -> PROTOCOL_VERSION,
				    PROTOCOL_VERSION::equals,
				    PROTOCOL_VERSION::equals
				);
	int id = 0;
	
	INSTANCE.registerMessage(id++, PacketTest.class, PacketTest::encode, PacketTest::decode, PacketTest::handle);
	}
}