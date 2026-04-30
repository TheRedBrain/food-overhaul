package com.github.theredbrain.foodoverhaul.gui.screen.ingame;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.network.packet.UpdateFoodBlockPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.util.StringRepresentable;
import java.util.Arrays;
import java.util.Optional;

@Environment(value = EnvType.CLIENT)
public class FoodBlockScreen extends Screen {
	private static final Component STATUS_EFFECT_IDENTIFIER_LABEL_TEXT = Component.translatable("gui.food_block.status_effect_identifier_label");
	private static final Component STATUS_EFFECT_DURATION_LABEL_TEXT = Component.translatable("gui.food_block.status_effect_duration_label");
	private static final Component STATUS_EFFECT_AMPLIFIER_LABEL_TEXT = Component.translatable("gui.food_block.status_effect_amplifier_label");
	private static final Component AMBIENT_TRUE_LABEL_TEXT = Component.translatable("gui.food_block.ambient_true_label");
	private static final Component AMBIENT_FALSE_LABEL_TEXT = Component.translatable("gui.food_block.ambient_false_label");
	private static final Component HIDE_PARTICLES_LABEL_TEXT = Component.translatable("gui.food_block.hide_particles_label");
	private static final Component SHOW_PARTICLES_LABEL_TEXT = Component.translatable("gui.food_block.show_particles_label");
	private static final Component HIDE_ICON_LABEL_TEXT = Component.translatable("gui.food_block.hide_icon_label");
	private static final Component SHOW_ICON_LABEL_TEXT = Component.translatable("gui.food_block.show_icon_label");

	private static final Component INTERACTION_RESULT_ITEM_IDENTIFIER_LABEL_TEXT = Component.translatable("gui.food_block.interaction_result_item_identifier_label");
	private static final Component INTERACTION_TOOL_ITEM_IDENTIFIER_LABEL_TEXT = Component.translatable("gui.food_block.interaction_tool_item_identifier_label");
	private static final Component USE_PREVENTING_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT = Component.translatable("gui.food_block.use_preventing_status_effect_identifier_label");
	private static final Component REQUIRED_ADVANCEMENT_IDENTIFIER_LABEL_TEXT = Component.translatable("gui.food_block.required_advancement_identifier_label");
	private static final Component RECOVERY_TIMER_THRESHOLD_LABEL_TEXT = Component.translatable("gui.food_block.recovery_timer_threshold_label");
	private static final Component INFINITE_USE_TRUE_LABEL_TEXT = Component.translatable("gui.food_block.infinite_use_true_label");
	private static final Component INFINITE_USE_FALSE_LABEL_TEXT = Component.translatable("gui.food_block.infinite_use_false_label");
	private final FoodBlockEntity foodBlockEntity;
	private final FoodBlockEntity.FoodBlockData foodBlockData;
	private ScreenPage screenPage;

	private EditBox appliedStatusEffectIdentifierField;
	private EditBox appliedStatusEffectDurationField;
	private EditBox appliedStatusEffectAmplifierField;
	private CycleButton<Boolean> toggleAppliedStatusEffectAmbientButton;
	private CycleButton<Boolean> toggleAppliedStatusEffectShowParticlesButton;
	private CycleButton<Boolean> toggleAppliedStatusEffectShowIconButton;
	private boolean appliedStatusEffectAmbient;
	private boolean appliedStatusEffectShowParticles;
	private boolean appliedStatusEffectShowIcon;

	private EditBox interactionResultItemIdentifierField;
	private EditBox interactionToolItemIdentifierField;
	private EditBox usePreventingStatusEffectIdentifierField;
	private EditBox requiredAdvancementIdentifierField;
	private EditBox recoveryTimerThresholdField;
	private boolean infiniteUses;
	private CycleButton<Boolean> toggleInfiniteUsesButton;

	public FoodBlockScreen(FoodBlockEntity foodBlockEntity) {
		super(GameNarrator.NO_TITLE);
		this.foodBlockEntity = foodBlockEntity;
		this.foodBlockData = this.foodBlockEntity.getFoodBlockData();
		this.screenPage = ScreenPage.APPLIED_EFFECT;
	}

	private void done() {
		if (this.updateFoodBlock()) {
			this.onClose();
		}
	}

	private void cancel() {
		this.onClose();
	}

	@Override
	protected void init() {

		this.addRenderableWidget(CycleButton.builder(ScreenPage::asText, this.screenPage).withValues((ScreenPage[]) ScreenPage.values()).displayOnlyValue().create(this.width / 2 - 154, 30, 300, 20, Component.empty(), (button, screenPage) -> {
			this.screenPage = screenPage;
			this.updateWidgets();
		}));

		this.appliedStatusEffectIdentifierField = new EditBox(this.font, this.width / 2 - 154, 65, 300, 20, Component.empty());
		this.appliedStatusEffectIdentifierField.setMaxLength(128);
		this.appliedStatusEffectIdentifierField.setValue(this.foodBlockData.applied_status_effect_identifier());
		this.addWidget(this.appliedStatusEffectIdentifierField);

		this.appliedStatusEffectDurationField = new EditBox(this.font, this.width / 2 - 154, 100, 75, 20, Component.empty());
		this.appliedStatusEffectDurationField.setValue(Integer.toString(this.foodBlockData.applied_status_effect_duration()));
		this.addWidget(this.appliedStatusEffectDurationField);

		this.appliedStatusEffectAmplifierField = new EditBox(this.font, this.width / 2 - 75, 100, 75, 20, Component.empty());
		this.appliedStatusEffectAmplifierField.setValue(Integer.toString(this.foodBlockData.applied_status_effect_amplifier()));
		this.addWidget(this.appliedStatusEffectAmplifierField);

		this.appliedStatusEffectAmbient = this.foodBlockData.applied_status_effect_ambient();
		this.toggleAppliedStatusEffectAmbientButton = this.addRenderableWidget(CycleButton.booleanBuilder(AMBIENT_TRUE_LABEL_TEXT, AMBIENT_FALSE_LABEL_TEXT, this.appliedStatusEffectAmbient).displayOnlyValue().create(this.width / 2 + 4, 100, 150, 20, Component.empty(), (button, appliedStatusEffectAmbient) -> {
			this.appliedStatusEffectAmbient = appliedStatusEffectAmbient;
		}));

		this.appliedStatusEffectShowParticles = this.foodBlockData.applied_status_effect_show_particles();
		this.toggleAppliedStatusEffectShowParticlesButton = this.addRenderableWidget(CycleButton.booleanBuilder(SHOW_PARTICLES_LABEL_TEXT, HIDE_PARTICLES_LABEL_TEXT, this.appliedStatusEffectShowParticles).displayOnlyValue().create(this.width / 2 - 154, 124, 150, 20, Component.empty(), (button, appliedStatusEffectShowParticles) -> {
			this.appliedStatusEffectShowParticles = appliedStatusEffectShowParticles;
		}));

		this.appliedStatusEffectShowIcon = this.foodBlockData.applied_status_effect_show_icon();
		this.toggleAppliedStatusEffectShowIconButton = this.addRenderableWidget(CycleButton.booleanBuilder(SHOW_ICON_LABEL_TEXT, HIDE_ICON_LABEL_TEXT, this.appliedStatusEffectShowIcon).displayOnlyValue().create(this.width / 2 + 4, 124, 150, 20, Component.empty(), (button, appliedStatusEffectShowIcon) -> {
			this.appliedStatusEffectShowIcon = appliedStatusEffectShowIcon;
		}));


		this.interactionResultItemIdentifierField = new EditBox(this.font, this.width / 2 - 154, 65, 300, 20, Component.empty());
		this.interactionResultItemIdentifierField.setMaxLength(128);
		this.interactionResultItemIdentifierField.setValue(this.foodBlockData.interaction_result_item_identifier());
		this.addWidget(this.interactionResultItemIdentifierField);

		this.interactionToolItemIdentifierField = new EditBox(this.font, this.width / 2 - 154, 100, 300, 20, Component.empty());
		this.interactionToolItemIdentifierField.setMaxLength(128);
		this.interactionToolItemIdentifierField.setValue(this.foodBlockData.interaction_tool_item_identifier());
		this.addWidget(this.interactionToolItemIdentifierField);

		this.usePreventingStatusEffectIdentifierField = new EditBox(this.font, this.width / 2 - 154, 135, 300, 20, Component.empty());
		this.usePreventingStatusEffectIdentifierField.setMaxLength(128);
		this.usePreventingStatusEffectIdentifierField.setValue(this.foodBlockData.use_preventing_status_effect_identifier());
		this.addWidget(this.usePreventingStatusEffectIdentifierField);

		this.requiredAdvancementIdentifierField = new EditBox(this.font, this.width / 2 - 154, 170, 300, 20, Component.empty());
		this.requiredAdvancementIdentifierField.setMaxLength(128);
		this.requiredAdvancementIdentifierField.setValue(this.foodBlockData.required_advancement_identifier());
		this.addWidget(this.requiredAdvancementIdentifierField);

		this.recoveryTimerThresholdField = new EditBox(this.font, this.width / 2 - 154, 205, 150, 20, Component.empty());
		this.recoveryTimerThresholdField.setMaxLength(128);
		this.recoveryTimerThresholdField.setValue(Integer.toString(this.foodBlockData.recovery_timer_threshold()));
		this.addWidget(this.recoveryTimerThresholdField);

		this.infiniteUses = this.foodBlockData.infinite_uses();
		this.toggleInfiniteUsesButton = this.addRenderableWidget(CycleButton.booleanBuilder(INFINITE_USE_TRUE_LABEL_TEXT, INFINITE_USE_FALSE_LABEL_TEXT, this.infiniteUses).displayOnlyValue().create(this.width / 2 + 4, 205, 150, 20, Component.empty(), (button, infiniteUses) -> {
			this.infiniteUses = infiniteUses;
		}));

		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.done()).bounds(this.width / 2 - 4 - 150, 229, 150, 20).build());
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.cancel()).bounds(this.width / 2 + 4, 229, 150, 20).build());
		this.updateWidgets();
	}

	private void updateWidgets() {

		this.appliedStatusEffectIdentifierField.setVisible(false);
		this.appliedStatusEffectDurationField.setVisible(false);
		this.appliedStatusEffectAmplifierField.setVisible(false);
		this.toggleAppliedStatusEffectAmbientButton.visible = false;
		this.toggleAppliedStatusEffectShowParticlesButton.visible = false;
		this.toggleAppliedStatusEffectShowIconButton.visible = false;

		this.interactionResultItemIdentifierField.setVisible(false);
		this.interactionToolItemIdentifierField.setVisible(false);
		this.usePreventingStatusEffectIdentifierField.setVisible(false);
		this.requiredAdvancementIdentifierField.setVisible(false);
		this.recoveryTimerThresholdField.setVisible(false);
		this.toggleInfiniteUsesButton.visible = false;

		if (this.screenPage == ScreenPage.APPLIED_EFFECT) {

			this.appliedStatusEffectIdentifierField.setVisible(true);
			this.appliedStatusEffectDurationField.setVisible(true);
			this.appliedStatusEffectAmplifierField.setVisible(true);

			this.toggleAppliedStatusEffectAmbientButton.visible = true;
			this.toggleAppliedStatusEffectShowParticlesButton.visible = true;
			this.toggleAppliedStatusEffectShowIconButton.visible = true;

		} else if (this.screenPage == ScreenPage.TRIGGER_SETTINGS) {

			this.interactionResultItemIdentifierField.setVisible(true);
			this.interactionToolItemIdentifierField.setVisible(true);
			this.usePreventingStatusEffectIdentifierField.setVisible(true);
			this.requiredAdvancementIdentifierField.setVisible(true);
			this.recoveryTimerThresholdField.setVisible(true);
			this.toggleInfiniteUsesButton.visible = true;

		}
	}

	@Override
	public void resize(int width, int height) {
		ScreenPage var = this.screenPage;
		boolean bool = this.appliedStatusEffectAmbient;
		boolean bool1 = this.appliedStatusEffectShowParticles;
		boolean bool2 = this.appliedStatusEffectShowIcon;
		boolean bool3 = this.infiniteUses;
		String string = this.appliedStatusEffectIdentifierField.getValue();
		String string1 = this.appliedStatusEffectDurationField.getValue();
		String string2 = this.appliedStatusEffectAmplifierField.getValue();
		String string3 = this.interactionResultItemIdentifierField.getValue();
		String string4 = this.interactionToolItemIdentifierField.getValue();
		String string5 = this.usePreventingStatusEffectIdentifierField.getValue();
		String string6 = this.requiredAdvancementIdentifierField.getValue();
		String string7 = this.recoveryTimerThresholdField.getValue();
		this.init(width, height);
		this.screenPage = var;
		this.appliedStatusEffectAmbient = bool;
		this.appliedStatusEffectShowParticles = bool1;
		this.appliedStatusEffectShowIcon = bool2;
		this.infiniteUses = bool3;
		this.appliedStatusEffectIdentifierField.setValue(string);
		this.appliedStatusEffectDurationField.setValue(string1);
		this.appliedStatusEffectAmplifierField.setValue(string2);
		this.interactionResultItemIdentifierField.setValue(string3);
		this.interactionToolItemIdentifierField.setValue(string4);
		this.usePreventingStatusEffectIdentifierField.setValue(string5);
		this.requiredAdvancementIdentifierField.setValue(string6);
		this.recoveryTimerThresholdField.setValue(string7);
	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {

		super.extractRenderState(graphics, mouseX, mouseY, a);

		if (this.screenPage == ScreenPage.APPLIED_EFFECT) {
			graphics.text(this.font, STATUS_EFFECT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 55, CommonColors.LIGHT_GRAY);
			this.appliedStatusEffectIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, STATUS_EFFECT_DURATION_LABEL_TEXT, this.width / 2 - 153, 90, CommonColors.LIGHT_GRAY);
			this.appliedStatusEffectDurationField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, STATUS_EFFECT_AMPLIFIER_LABEL_TEXT, this.width / 2 - 74, 90, CommonColors.LIGHT_GRAY);
			this.appliedStatusEffectAmplifierField.extractRenderState(graphics, mouseX, mouseY, a);
		} else if (this.screenPage == ScreenPage.TRIGGER_SETTINGS) {
			graphics.text(this.font, INTERACTION_RESULT_ITEM_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 55, CommonColors.LIGHT_GRAY);
			this.interactionResultItemIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, INTERACTION_TOOL_ITEM_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 90, CommonColors.LIGHT_GRAY);
			this.interactionToolItemIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, USE_PREVENTING_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 125, CommonColors.LIGHT_GRAY);
			this.usePreventingStatusEffectIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, REQUIRED_ADVANCEMENT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 160, CommonColors.LIGHT_GRAY);
			this.requiredAdvancementIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, RECOVERY_TIMER_THRESHOLD_LABEL_TEXT, this.width / 2 - 153, 140, CommonColors.LIGHT_GRAY);
			this.recoveryTimerThresholdField.extractRenderState(graphics, mouseX, mouseY, a);
		}

	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	private boolean updateFoodBlock() {
		ClientPlayNetworking.send(new UpdateFoodBlockPacket(
				this.foodBlockEntity.getBlockPos(),
				new FoodBlockEntity.FoodBlockData(
						this.appliedStatusEffectIdentifierField.getValue(),
						parseInt(this.appliedStatusEffectDurationField.getValue()),
						parseInt(this.appliedStatusEffectAmplifierField.getValue()),
						appliedStatusEffectAmbient,
						appliedStatusEffectShowParticles,
						appliedStatusEffectShowIcon,
						this.interactionResultItemIdentifierField.getValue(),
						this.interactionToolItemIdentifierField.getValue(),
						this.usePreventingStatusEffectIdentifierField.getValue(),
						this.requiredAdvancementIdentifierField.getValue(),
						parseInt(this.recoveryTimerThresholdField.getValue()),
						infiniteUses
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

	public static enum ScreenPage implements StringRepresentable {
		APPLIED_EFFECT("applied_effect"),
		TRIGGER_SETTINGS("interaction_settings");

		private final String name;

		private ScreenPage(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}

		public static Optional<ScreenPage> byName(String name) {
			return Arrays.stream(ScreenPage.values()).filter(screenPage -> screenPage.getSerializedName().equals(name)).findFirst();
		}

		public Component asText() {
			return Component.translatable("gui.food_block.screenPage." + this.name);
		}
	}
}
