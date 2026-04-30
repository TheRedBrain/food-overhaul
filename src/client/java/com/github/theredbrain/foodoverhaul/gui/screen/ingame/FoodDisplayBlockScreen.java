package com.github.theredbrain.foodoverhaul.gui.screen.ingame;

import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import com.github.theredbrain.foodoverhaul.network.packet.UpdateFoodDisplayBlockPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;

@Environment(value = EnvType.CLIENT)
public class FoodDisplayBlockScreen extends Screen {

	private static final Component USE_PREVENTING_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT = Component.translatable("gui.food_display_block.use_preventing_status_effect_identifier_label");
	private static final Component ENABLES_MODIFICATION_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT = Component.translatable("gui.food_display_block.enables_modification_status_effect_identifier_label");
	private static final Component VIABLE_ITEMS_TAG_IDENTIFIER_LABEL_TEXT = Component.translatable("gui.food_display_block.viable_items_tag_identifier_label");
	private static final Component SINGLE_ITEM_MODE_TRUE_LABEL_TEXT = Component.translatable("gui.food_display_block.single_item_mode_true_label");
	private static final Component SINGLE_ITEM_MODE_FALSE_LABEL_TEXT = Component.translatable("gui.food_display_block.single_item_mode_false_label");
	private static final Component SINGLE_ITEM_MODE_LABEL_TEXT = Component.translatable("gui.food_display_block.single_item_mode_label");
	private static final Component ITEM_ROTATION_1_LABEL_TEXT = Component.translatable("gui.food_display_block.item_rotation_1_label");
	private static final Component ITEM_ROTATION_2_LABEL_TEXT = Component.translatable("gui.food_display_block.item_rotation_2_label");
	private static final Component ITEM_ROTATION_3_LABEL_TEXT = Component.translatable("gui.food_display_block.item_rotation_3_label");
	private static final Component ITEM_ROTATION_4_LABEL_TEXT = Component.translatable("gui.food_display_block.item_rotation_4_label");
	private static final Component INFINITE_USE_TRUE_LABEL_TEXT = Component.translatable("gui.food_display_block.infinite_use_true_label");
	private static final Component INFINITE_USE_FALSE_LABEL_TEXT = Component.translatable("gui.food_display_block.infinite_use_false_label");
	private static final Component INFINITE_USES_LABEL_TEXT = Component.translatable("gui.food_display_block.infinite_uses_label");
	private final FoodDisplayBlockEntity foodDisplayBlockEntity;
	private final FoodDisplayBlockEntity.FoodDisplayBlockData foodDisplayBlockData;

	private EditBox usePreventingStatusEffectIdentifierField;
	private EditBox enablesModificationStatusEffectIdentifierField;
	private EditBox viableItemsTagIdentifierField;

	private boolean singleItemMode;
	private Button setSingleItemModeTrueButton;
	private Button setSingleItemModeFalseButton;

	private EditBox rotation1Field;
	private EditBox rotation2Field;
	private EditBox rotation3Field;
	private EditBox rotation4Field;

	private boolean infiniteUses;
	private Button setInfiniteUsesTrueButton;
	private Button setInfiniteUsesFalseButton;

	public FoodDisplayBlockScreen(FoodDisplayBlockEntity foodDisplayBlockEntity) {
		super(GameNarrator.NO_TITLE);
		this.foodDisplayBlockEntity = foodDisplayBlockEntity;
		this.foodDisplayBlockData = this.foodDisplayBlockEntity.getFoodDisplayBlockData();
	}

	private void done() {
		if (this.updateFoodDisplayBlock()) {
			this.onClose();
		}
	}

	private void cancel() {
		this.onClose();
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

		this.usePreventingStatusEffectIdentifierField = new EditBox(this.font, this.width / 2 - 154, 30, 300, 20, Component.empty());
		this.usePreventingStatusEffectIdentifierField.setMaxLength(128);
		this.usePreventingStatusEffectIdentifierField.setValue(this.foodDisplayBlockData.use_preventing_status_effect_identifier());
		this.addWidget(this.usePreventingStatusEffectIdentifierField);

		this.enablesModificationStatusEffectIdentifierField = new EditBox(this.font, this.width / 2 - 154, 65, 300, 20, Component.empty());
		this.enablesModificationStatusEffectIdentifierField.setMaxLength(128);
		this.enablesModificationStatusEffectIdentifierField.setValue(this.foodDisplayBlockData.enables_modification_status_effect_identifier());
		this.addWidget(this.enablesModificationStatusEffectIdentifierField);

		this.viableItemsTagIdentifierField = new EditBox(this.font, this.width / 2 - 154, 100, 300, 20, Component.empty());
		this.viableItemsTagIdentifierField.setMaxLength(128);
		this.viableItemsTagIdentifierField.setValue(this.foodDisplayBlockData.viable_items_tag_identifier());
		this.addWidget(this.viableItemsTagIdentifierField);

		this.singleItemMode = this.foodDisplayBlockData.single_item_mode();
		this.setSingleItemModeTrueButton = this.addRenderableWidget(Button.builder(SINGLE_ITEM_MODE_TRUE_LABEL_TEXT, button -> this.setSingleItemModeToTrue()).bounds(this.width / 2 + 4, 124, 73, 20).build());
		this.setSingleItemModeFalseButton = this.addRenderableWidget(Button.builder(SINGLE_ITEM_MODE_FALSE_LABEL_TEXT, button -> this.setSingleItemModeToFalse()).bounds(this.width / 2 + 81, 124, 73, 20).build());

		this.rotation1Field = new EditBox(this.font, this.width / 2 - 154, 159, 73, 20, Component.empty());
		this.rotation1Field.setValue(Integer.toString(this.foodDisplayBlockData.rotation_1()));
		this.addWidget(this.rotation1Field);

		this.rotation2Field = new EditBox(this.font, this.width / 2 - 77, 159, 73, 20, Component.empty());
		this.rotation2Field.setValue(Integer.toString(this.foodDisplayBlockData.rotation_2()));
		this.addWidget(this.rotation2Field);

		this.rotation3Field = new EditBox(this.font, this.width / 2 + 4, 159, 73, 20, Component.empty());
		this.rotation3Field.setValue(Integer.toString(this.foodDisplayBlockData.rotation_3()));
		this.addWidget(this.rotation3Field);

		this.rotation4Field = new EditBox(this.font, this.width / 2 + 81, 159, 73, 20, Component.empty());
		this.rotation4Field.setValue(Integer.toString(this.foodDisplayBlockData.rotation_4()));
		this.addWidget(this.rotation4Field);

		this.infiniteUses = this.foodDisplayBlockData.infinite_uses();
		this.setInfiniteUsesTrueButton = this.addRenderableWidget(Button.builder(INFINITE_USE_TRUE_LABEL_TEXT, button -> this.setInfiniteUsesToTrue()).bounds(this.width / 2 + 4, 183, 73, 20).build());
		this.setInfiniteUsesFalseButton = this.addRenderableWidget(Button.builder(INFINITE_USE_FALSE_LABEL_TEXT, button -> this.setInfiniteUsesToFalse()).bounds(this.width / 2 + 81, 183, 73, 20).build());

		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.done()).bounds(this.width / 2 - 4 - 150, 207, 150, 20).build());
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.cancel()).bounds(this.width / 2 + 4, 207, 150, 20).build());
		this.updateWidgets();
	}

	private void updateWidgets() {

		this.setSingleItemModeTrueButton.active = !this.singleItemMode;
		this.setSingleItemModeFalseButton.active = this.singleItemMode;
		this.setInfiniteUsesTrueButton.active = !this.infiniteUses;
		this.setInfiniteUsesFalseButton.active = this.infiniteUses;

	}

	@Override
	public void resize(int width, int height) {
		boolean bool = this.singleItemMode;
		boolean bool1 = this.infiniteUses;
		String string = this.usePreventingStatusEffectIdentifierField.getValue();
		String string1 = this.enablesModificationStatusEffectIdentifierField.getValue();
		String string2 = this.viableItemsTagIdentifierField.getValue();
		String string3 = this.rotation1Field.getValue();
		String string4 = this.rotation2Field.getValue();
		String string5 = this.rotation3Field.getValue();
		String string6 = this.rotation4Field.getValue();
		this.init(width, height);
		this.singleItemMode = bool;
		this.infiniteUses = bool1;
		this.usePreventingStatusEffectIdentifierField.setValue(string);
		this.enablesModificationStatusEffectIdentifierField.setValue(string1);
		this.viableItemsTagIdentifierField.setValue(string2);
		this.rotation1Field.setValue(string3);
		this.rotation2Field.setValue(string4);
		this.rotation3Field.setValue(string5);
		this.rotation4Field.setValue(string6);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

		super.extractRenderState(graphics, mouseX, mouseY, a);

		graphics.text(this.font, USE_PREVENTING_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 20, CommonColors.LIGHT_GRAY);
		this.usePreventingStatusEffectIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
		graphics.text(this.font, ENABLES_MODIFICATION_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 55, CommonColors.LIGHT_GRAY);
		this.enablesModificationStatusEffectIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
		graphics.text(this.font, VIABLE_ITEMS_TAG_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 90, CommonColors.LIGHT_GRAY);
		this.viableItemsTagIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
		graphics.text(this.font, SINGLE_ITEM_MODE_LABEL_TEXT, this.width / 2 - 153, 130, CommonColors.LIGHT_GRAY);
		graphics.text(this.font, ITEM_ROTATION_1_LABEL_TEXT, this.width / 2 - 153, 149, CommonColors.LIGHT_GRAY);
		this.rotation1Field.extractRenderState(graphics, mouseX, mouseY, a);
		graphics.text(this.font, ITEM_ROTATION_2_LABEL_TEXT, this.width / 2 - 76, 149, CommonColors.LIGHT_GRAY);
		this.rotation2Field.extractRenderState(graphics, mouseX, mouseY, a);
		graphics.text(this.font, ITEM_ROTATION_3_LABEL_TEXT, this.width / 2 + 5, 149, CommonColors.LIGHT_GRAY);
		this.rotation3Field.extractRenderState(graphics, mouseX, mouseY, a);
		graphics.text(this.font, ITEM_ROTATION_4_LABEL_TEXT, this.width / 2 + 82, 149, CommonColors.LIGHT_GRAY);
		this.rotation4Field.extractRenderState(graphics, mouseX, mouseY, a);
		graphics.text(this.font, INFINITE_USES_LABEL_TEXT, this.width / 2 - 153, 189, CommonColors.LIGHT_GRAY);

	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	private boolean updateFoodDisplayBlock() {
		ClientPlayNetworking.send(new UpdateFoodDisplayBlockPacket(
				this.foodDisplayBlockEntity.getBlockPos(),
				new FoodDisplayBlockEntity.FoodDisplayBlockData(
						this.usePreventingStatusEffectIdentifierField.getValue(),
						this.enablesModificationStatusEffectIdentifierField.getValue(),
						this.viableItemsTagIdentifierField.getValue(),
						this.singleItemMode,
						parseInt(this.rotation1Field.getValue()),
						parseInt(this.rotation2Field.getValue()),
						parseInt(this.rotation3Field.getValue()),
						parseInt(this.rotation4Field.getValue()),
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

	public boolean isInGameUi() {
		return true;
	}

}
