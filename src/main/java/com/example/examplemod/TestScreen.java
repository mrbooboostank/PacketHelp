package com.example.examplemod;

import java.util.List;

import net.minecraft.client.gui.screen.ReadBookScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TestScreen extends ReadBookScreen {
	// copying over some GuiScreenBook variables
	//private static final ResourceLocation asdf = location("asdf");
	private int currPage;
	private ListNBT bookPages;
	private List<ITextComponent> cachedComponents;
	private ItemStack item;
	private String title;

	// constructor
	public TestScreen(PlayerEntity playerIn, ItemStack item, boolean bookSign, Hand handIn, int currPage,
			String title) {
		// super(playerIn, item, bookSign, handIn);
		this.currPage = currPage;
		this.item = item;
		this.title = title;
	}

	// override to remove page buttons from spawning
	@Override
	protected void init() {
		// function that spawns "done" button from vanilla book
		func_214162_b();
	}

	// override to customize the page's appearance
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		// required for the buttons to render, I.E. buttonDone
		super.render(mouseX, mouseY, partialTicks);

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(field_214167_b);
		int i = (this.width - 192) / 2;
		this.blit(i, 2, 0, 0, 192, 192);

		// custom code for determining the page to display, and grabbing the nbt data
		// and cleaning it
		//String s5 = item.getTag().getList("pages", 8).getString(currPage);
		//s5 = s5.substring(9, s5.length() - 2);

		// grabs a string from the instantiation
		String s4 = I18n.format("\u00A7l" + title);

		String s6 = "error";

		switch (currPage) {
		case 0:
			s6 = "26";
			break;
		case 1:
			s6 = "33";
			break;
		case 2:
			s6 = "78";
			break;
		case 3:
			s6 = "31";
			break;
		case 4:
			s6 = "16";
			break;
		case 5:
			s6 = "52";
			break;
		}

		// backup code / redundancy code
		if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.size()) {
			//s5 = this.bookPages.getString(this.currPage);
		}

		// rendering code for page text
		int j1 = this.minecraft.fontRenderer.getStringWidth(s6);
		this.minecraft.fontRenderer.drawString(s4, (float) (i + 36), 20.0F, 0);
		if (this.cachedComponents == null) {
			//this.minecraft.fontRenderer.drawSplitString(s5, i + 36, 34, 116, 0);
		}
		this.minecraft.fontRenderer.drawString(s6, (float) (i - j1 + 192 - 44), 158.0F, 0);
	}
}