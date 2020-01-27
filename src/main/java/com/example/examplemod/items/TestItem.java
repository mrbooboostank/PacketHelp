package com.example.examplemod.items;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.example.examplemod.TestScreen;
import com.example.examplemod.packets.PacketHandlerTest;
import com.example.examplemod.packets.PacketTest;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

public class TestItem extends WrittenBookItem {

	//private String title;

	public TestItem(Item.Properties properties) {
		super(properties);
	}

	// disables enchantment glint
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return false;
	}

	// Overridden so the item's display name is the one set from the main java file
	// instead of the nbt title
	@Override
	public ITextComponent getDisplayName(ItemStack stack) {
		return new StringTextComponent("Hello");
	}

	// actual code for right-click interactions
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack item = playerIn.getHeldItem(handIn);

		// creating a random effect
		// only ran on the server so the client doesn't get confused
		if (!worldIn.isRemote) {
			Random rand = new Random();
			int rand_choice = rand.nextInt(6);
			switch (rand_choice) {
			case 0:
					// server things here
				break;
			case 1:
				// server things here
				break;
			case 2:
				// server things here
				break;
			case 3:
				// server things here
				break;
			case 4:
				// server things here
				break;
			case 5:
				// server things here
				break;
			}
			
			System.out.println("serverside rand_choice is");
			System.out.println(rand_choice);
			
			// right here rand_choice, generated serverside, should be sent to the client via this packet handler
			
			PacketHandlerTest.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerIn), new PacketTest(rand_choice));
			
			// include this to update the Stats
			playerIn.addStat(Stats.ITEM_USED.get(this));
		}
		else {
			//System.out.println("CLIENTSIDE rand_choice is");
			//System.out.println(item.getTag());
			//playerIn.sendStatusMessage(new StringTextComponent(item.getTag().toString()), true);
			//showPage(item, playerIn, handIn);
		}

		// minecraft day is 20 min, or 24000 ticks, or 1200 seconds.
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, item);
	}

	// DistExecutor wouldn't work, but this does
	@OnlyIn(Dist.CLIENT)
	private void showPage(ItemStack item, PlayerEntity playerIn, Hand handIn) {
		String title = "asdf";
		Minecraft.getInstance().displayGuiScreen(new TestScreen(playerIn, false, 1, title ));
		// read rand_choice packet and use it in a message
		//int temp_num = item.getTag().getInt("rand_choice");
		//int temp_num = 1;
		//playerIn.sendStatusMessage(new StringTextComponent(Integer.toString(temp_num)), true);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	}
}