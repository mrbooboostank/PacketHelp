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
	
	@Override
	public void inventoryTick(ItemStack item, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		CompoundNBT nbt;
		if (item.hasTag()) {
			nbt = item.getTag();
		} else {
			nbt = new CompoundNBT();
			// setting up pages, character limit for pages is 255 characters
			ListNBT nbtList = new ListNBT();
			nbtList.add((INBT) (StringNBT.func_229705_a_(
				"{\"text\":\"" + new StringTextComponent("Lorem Ipsum").getString() + "\"}")));
			// setup strings for author, title, and pages.
			nbt.putString("title", "My house");
			nbt.putString("author", "The construction company");
			nbt.put("pages", nbtList);
		}
		item.setTag(nbt);
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
			int randChoice = rand.nextInt(6);
			switch (randChoice) {
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
			
			playerIn.openBook(item, handIn);
			
			// right here randChoice, generated serverside, should be sent to the client via this packet handler
			
			PacketHandlerTest.INSTANCE.send(PacketDistributor.PLAYER.with((Supplier<ServerPlayerEntity>) playerIn), new PacketTest(randChoice));
			
			// include this to update the Stats
			playerIn.addStat(Stats.ITEM_USED.get(this));
		}
		else {
			showPage(item, playerIn, handIn);
		}

		// minecraft day is 20 min, or 24000 ticks, or 1200 seconds.
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, item);
	}

	// DistExecutor wouldn't work, but this does
	@OnlyIn(Dist.CLIENT)
	private void showPage(ItemStack item, PlayerEntity playerIn, Hand handIn) {
		String title = "asdf";
		Minecraft.getInstance().displayGuiScreen(new TestScreen(playerIn, item, false, handIn, 1, title ));
		// read randChoice packet and use it in a message
		playerIn.sendStatusMessage(new StringTextComponent("randChoice should be here"), true);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	}
}