package com.github.theredbrain.foodoverhaul.gui.screen.ingame;

import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import com.github.theredbrain.foodoverhaul.network.packet.UpdateFoodDisplayBlockPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

@Environment(value = EnvType.CLIENT)
public class FoodDisplayBlockScreen extends Screen {

	private static final Text USE_PREVENTING_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT = Text.translatable("gui.food_display_block.use_preventing_status_effect_identifier_label");
	private static final Text ENABLES_MODIFICATION_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT = Text.translatable("gui.food_display_block.enables_modification_status_effect_identifier_label");
	private static final Text VIABLE_ITEMS_TAG_IDENTIFIER_LABEL_TEXT = Text.translatable("gui.food_display_block.viable_items_tag_identifier_label");
	private static final Text SINGLE_ITEM_MODE_TRUE_LABEL_TEXT = Text.translatable("gui.food_display_block.single_item_mode_true_label");
	private static final Text SINGLE_ITEM_MODE_FALSE_LABEL_TEXT = Text.translatable("gui.food_display_block.single_item_mode_false_label");
	private static final Text SINGLE_ITEM_MODE_LABEL_TEXT = Text.translatable("gui.food_display_block.single_item_mode_label");
	private static final Text ITEM_ROTATION_1_LABEL_TEXT = Text.translatable("gui.food_display_block.item_rotation_1_label");
	private static final Text ITEM_ROTATION_2_LABEL_TEXT = Text.translatable("gui.food_display_block.item_rotation_2_label");
	private static final Text ITEM_ROTATION_3_LABEL_TEXT = Text.translatable("gui.food_display_block.item_rotation_3_label");
	private static final Text ITEM_ROTATION_4_LABEL_TEXT = Text.translatable("gui.food_display_block.item_rotation_4_label");
	private static final Text INFINITE_USE_TRUE_LABEL_TEXT = Text.translatable("gui.food_display_block.infinite_use_true_label");
	private static final Text INFINITE_USE_FALSE_LABEL_TEXT = Text.translatable("gui.food_display_block.infinite_use_false_label");
	private static final Text INFINITE_USES_LABEL_TEXT = Text.translatable("gui.food_display_block.infinite_uses_label");
	private final FoodDisplayBlockEntity foodDisplayBlockEntity;
	private final FoodDisplayBlockEntity.FoodDisplayBlockData foodDisplayBlockData;

	private TextFieldWidget usePreventingStatusEffectIdentifierField;
	private TextFieldWidget enablesModificationStatusEffectIdentifierField;
	private TextFieldWidget viableItemsTagIdentifierField;

	private boolean singleItemMode;
	private ButtonWidget setSingleItemModeTrueButton;
	private ButtonWidget setSingleItemModeFalseButton;

	private TextFieldWidget rotation1Field;
	private TextFieldWidget rotation2Field;
	private TextFieldWidget rotation3Field;
	private TextFieldWidget rotation4Field;

	private boolean infiniteUses;
	private ButtonWidget setInfiniteUsesTrueButton;
	private ButtonWidget setInfiniteUsesFalseButton;

	public FoodDisplayBlockScreen(FoodDisplayBlockEntity foodDisplayBlockEntity) {
		super(NarratorManager.EMPTY);
		this.foodDisplayBlockEntity = foodDisplayBlockEntity;
		this.foodDisplayBlockData = this.foodDisplayBlockEntity.getFoodDisplayBlockData();
	}

	private void done() {
		if (this.updateFoodDisplayBlock()) {
			this.close();
		}
	}

	private void cancel() {
		this.close();
	}

	private void setSingleItemModeToTrue() {
		this.singleItemMode = true;
		this.updateWidgets();
	}

	private void setSingleItemModeToFalse() {
		this.singleItemMode = false;
		this.updateWidgets();
	}

	private void setInfiniteUsesToTrue() {
		this.infiniteUses = true;
		this.updateWidgets();
	}

	private void setInfiniteUsesToFalse() {
		this.infiniteUses = false;
		this.updateWidgets();
	}

	@Override
	protected void init() {

		this.usePreventingStatusEffectIdentifierField = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 30, 300, 20, Text.empty());
		this.usePreventingStatusEffectIdentifierField.setMaxLength(128);
		this.usePreventingStatusEffectIdentifierField.setText(this.foodDisplayBlockData.use_preventing_status_effect_identifier());
		this.addSelectableChild(this.usePreventingStatusEffectIdentifierField);

		this.enablesModificationStatusEffectIdentifierField = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 65, 300, 20, Text.empty());
		this.enablesModificationStatusEffectIdentifierField.setMaxLength(128);
		this.enablesModificationStatusEffectIdentifierField.setText(this.foodDisplayBlockData.enables_modification_status_effect_identifier());
		this.addSelectableChild(this.enablesModificationStatusEffectIdentifierField);

		this.viableItemsTagIdentifierField = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 100, 300, 20, Text.empty());
		this.viableItemsTagIdentifierField.setMaxLength(128);
		this.viableItemsTagIdentifierField.setText(this.foodDisplayBlockData.viable_items_tag_identifier());
		this.addSelectableChild(this.viableItemsTagIdentifierField);

		this.singleItemMode = this.foodDisplayBlockData.single_item_mode();
		this.setSingleItemModeTrueButton = this.addDrawableChild(ButtonWidget.builder(SINGLE_ITEM_MODE_TRUE_LABEL_TEXT, button -> this.setSingleItemModeToTrue()).dimensions(this.width / 2 + 4, 124, 73, 20).build());
		this.setSingleItemModeFalseButton = this.addDrawableChild(ButtonWidget.builder(SINGLE_ITEM_MODE_FALSE_LABEL_TEXT, button -> this.setSingleItemModeToFalse()).dimensions(this.width / 2 + 81, 124, 73, 20).build());

		this.rotation1Field = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 159, 73, 20, Text.empty());
		this.rotation1Field.setText(Integer.toString(this.foodDisplayBlockData.rotation_1()));
		this.addSelectableChild(this.rotation1Field);

		this.rotation2Field = new TextFieldWidget(this.textRenderer, this.width / 2 - 77, 159, 73, 20, Text.empty());
		this.rotation2Field.setText(Integer.toString(this.foodDisplayBlockData.rotation_2()));
		this.addSelectableChild(this.rotation2Field);

		this.rotation3Field = new TextFieldWidget(this.textRenderer, this.width / 2 + 4, 159, 73, 20, Text.empty());
		this.rotation3Field.setText(Integer.toString(this.foodDisplayBlockData.rotation_3()));
		this.addSelectableChild(this.rotation3Field);

		this.rotation4Field = new TextFieldWidget(this.textRenderer, this.width / 2 + 81, 159, 73, 20, Text.empty());
		this.rotation4Field.setText(Integer.toString(this.foodDisplayBlockData.rotation_4()));
		this.addSelectableChild(this.rotation4Field);

		this.infiniteUses = this.foodDisplayBlockData.infinite_uses();
		this.setInfiniteUsesTrueButton = this.addDrawableChild(ButtonWidget.builder(INFINITE_USE_TRUE_LABEL_TEXT, button -> this.setInfiniteUsesToTrue()).dimensions(this.width / 2 + 4, 183, 73, 20).build());
		this.setInfiniteUsesFalseButton = this.addDrawableChild(ButtonWidget.builder(INFINITE_USE_FALSE_LABEL_TEXT, button -> this.setInfiniteUsesToFalse()).dimensions(this.width / 2 + 81, 183, 73, 20).build());

		this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.done()).dimensions(this.width / 2 - 4 - 150, 207, 150, 20).build());
		this.addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.cancel()).dimensions(this.width / 2 + 4, 207, 150, 20).build());
		this.updateWidgets();
	}

	private void updateWidgets() {

		this.setSingleItemModeTrueButton.active = !this.singleItemMode;
		this.setSingleItemModeFalseButton.active = this.singleItemMode;
		this.setInfiniteUsesTrueButton.active = !this.infiniteUses;
		this.setInfiniteUsesFalseButton.active = this.infiniteUses;

	}

	@Override
	public void resize(MinecraftClient client, int width, int height) {
		boolean bool = this.singleItemMode;
		boolean bool1 = this.infiniteUses;
		String string = this.usePreventingStatusEffectIdentifierField.getText();
		String string1 = this.enablesModificationStatusEffectIdentifierField.getText();
		String string2 = this.viableItemsTagIdentifierField.getText();
		String string3 = this.rotation1Field.getText();
		String string4 = this.rotation2Field.getText();
		String string5 = this.rotation3Field.getText();
		String string6 = this.rotation4Field.getText();
		this.init(client, width, height);
		this.singleItemMode = bool;
		this.infiniteUses = bool1;
		this.usePreventingStatusEffectIdentifierField.setText(string);
		this.enablesModificationStatusEffectIdentifierField.setText(string1);
		this.viableItemsTagIdentifierField.setText(string2);
		this.rotation1Field.setText(string3);
		this.rotation2Field.setText(string4);
		this.rotation3Field.setText(string5);
		this.rotation4Field.setText(string6);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {

		super.render(context, mouseX, mouseY, delta);

		context.drawTextWithShadow(this.textRenderer, USE_PREVENTING_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 20, Colors.LIGHT_GRAY);
		this.usePreventingStatusEffectIdentifierField.render(context, mouseX, mouseY, delta);
		context.drawTextWithShadow(this.textRenderer, ENABLES_MODIFICATION_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 55, Colors.LIGHT_GRAY);
		this.enablesModificationStatusEffectIdentifierField.render(context, mouseX, mouseY, delta);
		context.drawTextWithShadow(this.textRenderer, VIABLE_ITEMS_TAG_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 90, Colors.LIGHT_GRAY);
		this.viableItemsTagIdentifierField.render(context, mouseX, mouseY, delta);
		context.drawTextWithShadow(this.textRenderer, SINGLE_ITEM_MODE_LABEL_TEXT, this.width / 2 - 153, 130, Colors.LIGHT_GRAY);
		context.drawTextWithShadow(this.textRenderer, ITEM_ROTATION_1_LABEL_TEXT, this.width / 2 - 153, 149, Colors.LIGHT_GRAY);
		this.rotation1Field.render(context, mouseX, mouseY, delta);
		context.drawTextWithShadow(this.textRenderer, ITEM_ROTATION_2_LABEL_TEXT, this.width / 2 - 76, 149, Colors.LIGHT_GRAY);
		this.rotation2Field.render(context, mouseX, mouseY, delta);
		context.drawTextWithShadow(this.textRenderer, ITEM_ROTATION_3_LABEL_TEXT, this.width / 2 + 5, 149, Colors.LIGHT_GRAY);
		this.rotation3Field.render(context, mouseX, mouseY, delta);
		context.drawTextWithShadow(this.textRenderer, ITEM_ROTATION_4_LABEL_TEXT, this.width / 2 + 82, 149, Colors.LIGHT_GRAY);
		this.rotation4Field.render(context, mouseX, mouseY, delta);
		context.drawTextWithShadow(this.textRenderer, INFINITE_USES_LABEL_TEXT, this.width / 2 - 153, 189, Colors.LIGHT_GRAY);

	}

	@Override
	public boolean shouldPause() {
		return false;
	}

	private boolean updateFoodDisplayBlock() {
		ClientPlayNetworking.send(new UpdateFoodDisplayBlockPacket(
				this.foodDisplayBlockEntity.getPos(),
				new FoodDisplayBlockEntity.FoodDisplayBlockData(
						this.usePreventingStatusEffectIdentifierField.getText(),
						this.enablesModificationStatusEffectIdentifierField.getText(),
						this.viableItemsTagIdentifierField.getText(),
						this.singleItemMode,
						parseInt(this.rotation1Field.getText()),
						parseInt(this.rotation2Field.getText()),
						parseInt(this.rotation3Field.getText()),
						parseInt(this.rotation4Field.getText()),
						this.infiniteUses
				)
		));
		return true;
	}

	public static int parseInt(String string) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException numberFormatException) {
			return 0;
		}
	}

	public boolean deferSubtitles() {
		return true;
	}

}
