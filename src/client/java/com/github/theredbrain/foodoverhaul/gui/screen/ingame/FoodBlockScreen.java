package com.github.theredbrain.foodoverhaul.gui.screen.ingame;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.network.packet.UpdateFoodBlockPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.StringIdentifiable;

import java.util.Arrays;
import java.util.Optional;

@Environment(value = EnvType.CLIENT)
public class FoodBlockScreen extends Screen {
	private static final Text STATUS_EFFECT_IDENTIFIER_LABEL_TEXT = Text.translatable("gui.food_block.status_effect_identifier_label");
	private static final Text STATUS_EFFECT_DURATION_LABEL_TEXT = Text.translatable("gui.food_block.status_effect_duration_label");
	private static final Text STATUS_EFFECT_AMPLIFIER_LABEL_TEXT = Text.translatable("gui.food_block.status_effect_amplifier_label");
	private static final Text AMBIENT_TRUE_LABEL_TEXT = Text.translatable("gui.food_block.ambient_true_label");
	private static final Text AMBIENT_FALSE_LABEL_TEXT = Text.translatable("gui.food_block.ambient_false_label");
	private static final Text HIDE_PARTICLES_LABEL_TEXT = Text.translatable("gui.food_block.hide_particles_label");
	private static final Text SHOW_PARTICLES_LABEL_TEXT = Text.translatable("gui.food_block.show_particles_label");
	private static final Text HIDE_ICON_LABEL_TEXT = Text.translatable("gui.food_block.hide_icon_label");
	private static final Text SHOW_ICON_LABEL_TEXT = Text.translatable("gui.food_block.show_icon_label");

	private static final Text INTERACTION_RESULT_ITEM_IDENTIFIER_LABEL_TEXT = Text.translatable("gui.food_block.interaction_result_item_identifier_label");
	private static final Text INTERACTION_TOOL_ITEM_IDENTIFIER_LABEL_TEXT = Text.translatable("gui.food_block.interaction_tool_item_identifier_label");
	private static final Text USE_PREVENTING_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT = Text.translatable("gui.food_block.use_preventing_status_effect_identifier_label");
	private static final Text REQUIRED_ADVANCEMENT_IDENTIFIER_LABEL_TEXT = Text.translatable("gui.food_block.required_advancement_identifier_label");
	private static final Text RECOVERY_TIMER_THRESHOLD_LABEL_TEXT = Text.translatable("gui.food_block.recovery_timer_threshold_label");
	private static final Text INFINITE_USE_TRUE_LABEL_TEXT = Text.translatable("gui.food_block.infinite_use_true_label");
	private static final Text INFINITE_USE_FALSE_LABEL_TEXT = Text.translatable("gui.food_block.infinite_use_false_label");
	private final FoodBlockEntity foodBlockEntity;
	private final FoodBlockEntity.FoodBlockData foodBlockData;
	private ScreenPage screenPage;

	private TextFieldWidget appliedStatusEffectIdentifierField;
	private TextFieldWidget appliedStatusEffectDurationField;
	private TextFieldWidget appliedStatusEffectAmplifierField;
	private CyclingButtonWidget<Boolean> toggleAppliedStatusEffectAmbientButton;
	private CyclingButtonWidget<Boolean> toggleAppliedStatusEffectShowParticlesButton;
	private CyclingButtonWidget<Boolean> toggleAppliedStatusEffectShowIconButton;
	private boolean appliedStatusEffectAmbient;
	private boolean appliedStatusEffectShowParticles;
	private boolean appliedStatusEffectShowIcon;

	private TextFieldWidget interactionResultItemIdentifierField;
	private TextFieldWidget interactionToolItemIdentifierField;
	private TextFieldWidget usePreventingStatusEffectIdentifierField;
	private TextFieldWidget requiredAdvancementIdentifierField;
	private TextFieldWidget recoveryTimerThresholdField;
	private boolean infiniteUses;
	private CyclingButtonWidget<Boolean> toggleInfiniteUsesButton;

	public FoodBlockScreen(FoodBlockEntity foodBlockEntity) {
		super(NarratorManager.EMPTY);
		this.foodBlockEntity = foodBlockEntity;
		this.foodBlockData = this.foodBlockEntity.getFoodBlockData();
		this.screenPage = ScreenPage.APPLIED_EFFECT;
	}

	private void done() {
		if (this.updateFoodBlock()) {
			this.close();
		}
	}

	private void cancel() {
		this.close();
	}

	@Override
	protected void init() {

		this.addDrawableChild(CyclingButtonWidget.builder(ScreenPage::asText).values((ScreenPage[]) ScreenPage.values()).initially(this.screenPage).omitKeyText().build(this.width / 2 - 154, 30, 300, 20, Text.empty(), (button, screenPage) -> {
			this.screenPage = screenPage;
			this.updateWidgets();
		}));

		this.appliedStatusEffectIdentifierField = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 65, 300, 20, Text.empty());
		this.appliedStatusEffectIdentifierField.setMaxLength(128);
		this.appliedStatusEffectIdentifierField.setText(this.foodBlockData.applied_status_effect_identifier());
		this.addSelectableChild(this.appliedStatusEffectIdentifierField);

		this.appliedStatusEffectDurationField = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 100, 75, 20, Text.empty());
		this.appliedStatusEffectDurationField.setText(Integer.toString(this.foodBlockData.applied_status_effect_duration()));
		this.addSelectableChild(this.appliedStatusEffectDurationField);

		this.appliedStatusEffectAmplifierField = new TextFieldWidget(this.textRenderer, this.width / 2 - 75, 100, 75, 20, Text.empty());
		this.appliedStatusEffectAmplifierField.setText(Integer.toString(this.foodBlockData.applied_status_effect_amplifier()));
		this.addSelectableChild(this.appliedStatusEffectAmplifierField);

		this.appliedStatusEffectAmbient = this.foodBlockData.applied_status_effect_ambient();
		this.toggleAppliedStatusEffectAmbientButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(AMBIENT_TRUE_LABEL_TEXT, AMBIENT_FALSE_LABEL_TEXT).initially(this.appliedStatusEffectAmbient).omitKeyText().build(this.width / 2 + 4, 100, 150, 20, Text.empty(), (button, appliedStatusEffectAmbient) -> {
			this.appliedStatusEffectAmbient = appliedStatusEffectAmbient;
		}));

		this.appliedStatusEffectShowParticles = this.foodBlockData.applied_status_effect_show_particles();
		this.toggleAppliedStatusEffectShowParticlesButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(SHOW_PARTICLES_LABEL_TEXT, HIDE_PARTICLES_LABEL_TEXT).initially(this.appliedStatusEffectShowParticles).omitKeyText().build(this.width / 2 - 154, 124, 150, 20, Text.empty(), (button, appliedStatusEffectShowParticles) -> {
			this.appliedStatusEffectShowParticles = appliedStatusEffectShowParticles;
		}));

		this.appliedStatusEffectShowIcon = this.foodBlockData.applied_status_effect_show_icon();
		this.toggleAppliedStatusEffectShowIconButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(SHOW_ICON_LABEL_TEXT, HIDE_ICON_LABEL_TEXT).initially(this.appliedStatusEffectShowIcon).omitKeyText().build(this.width / 2 + 4, 124, 150, 20, Text.empty(), (button, appliedStatusEffectShowIcon) -> {
			this.appliedStatusEffectShowIcon = appliedStatusEffectShowIcon;
		}));


		this.interactionResultItemIdentifierField = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 65, 300, 20, Text.empty());
		this.interactionResultItemIdentifierField.setMaxLength(128);
		this.interactionResultItemIdentifierField.setText(this.foodBlockData.interaction_result_item_identifier());
		this.addSelectableChild(this.interactionResultItemIdentifierField);

		this.interactionToolItemIdentifierField = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 100, 300, 20, Text.empty());
		this.interactionToolItemIdentifierField.setMaxLength(128);
		this.interactionToolItemIdentifierField.setText(this.foodBlockData.interaction_tool_item_identifier());
		this.addSelectableChild(this.interactionToolItemIdentifierField);

		this.usePreventingStatusEffectIdentifierField = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 135, 300, 20, Text.empty());
		this.usePreventingStatusEffectIdentifierField.setMaxLength(128);
		this.usePreventingStatusEffectIdentifierField.setText(this.foodBlockData.use_preventing_status_effect_identifier());
		this.addSelectableChild(this.usePreventingStatusEffectIdentifierField);

		this.requiredAdvancementIdentifierField = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 170, 300, 20, Text.empty());
		this.requiredAdvancementIdentifierField.setMaxLength(128);
		this.requiredAdvancementIdentifierField.setText(this.foodBlockData.required_advancement_identifier());
		this.addSelectableChild(this.requiredAdvancementIdentifierField);

		this.recoveryTimerThresholdField = new TextFieldWidget(this.textRenderer, this.width / 2 - 154, 205, 150, 20, Text.empty());
		this.recoveryTimerThresholdField.setMaxLength(128);
		this.recoveryTimerThresholdField.setText(Integer.toString(this.foodBlockData.recovery_timer_threshold()));
		this.addSelectableChild(this.recoveryTimerThresholdField);

		this.infiniteUses = this.foodBlockData.infinite_uses();
		this.toggleInfiniteUsesButton = this.addDrawableChild(CyclingButtonWidget.onOffBuilder(INFINITE_USE_TRUE_LABEL_TEXT, INFINITE_USE_FALSE_LABEL_TEXT).initially(this.infiniteUses).omitKeyText().build(this.width / 2 + 4, 205, 150, 20, Text.empty(), (button, infiniteUses) -> {
			this.infiniteUses = infiniteUses;
		}));

		this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.done()).dimensions(this.width / 2 - 4 - 150, 229, 150, 20).build());
		this.addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.cancel()).dimensions(this.width / 2 + 4, 229, 150, 20).build());
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
	public void resize(MinecraftClient client, int width, int height) {
		ScreenPage var = this.screenPage;
		boolean bool = this.appliedStatusEffectAmbient;
		boolean bool1 = this.appliedStatusEffectShowParticles;
		boolean bool2 = this.appliedStatusEffectShowIcon;
		boolean bool3 = this.infiniteUses;
		String string = this.appliedStatusEffectIdentifierField.getText();
		String string1 = this.appliedStatusEffectDurationField.getText();
		String string2 = this.appliedStatusEffectAmplifierField.getText();
		String string3 = this.interactionResultItemIdentifierField.getText();
		String string4 = this.interactionToolItemIdentifierField.getText();
		String string5 = this.usePreventingStatusEffectIdentifierField.getText();
		String string6 = this.requiredAdvancementIdentifierField.getText();
		String string7 = this.recoveryTimerThresholdField.getText();
		this.init(client, width, height);
		this.screenPage = var;
		this.appliedStatusEffectAmbient = bool;
		this.appliedStatusEffectShowParticles = bool1;
		this.appliedStatusEffectShowIcon = bool2;
		this.infiniteUses = bool3;
		this.appliedStatusEffectIdentifierField.setText(string);
		this.appliedStatusEffectDurationField.setText(string1);
		this.appliedStatusEffectAmplifierField.setText(string2);
		this.interactionResultItemIdentifierField.setText(string3);
		this.interactionToolItemIdentifierField.setText(string4);
		this.usePreventingStatusEffectIdentifierField.setText(string5);
		this.requiredAdvancementIdentifierField.setText(string6);
		this.recoveryTimerThresholdField.setText(string7);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {

		super.render(context, mouseX, mouseY, delta);

		if (this.screenPage == ScreenPage.APPLIED_EFFECT) {
			context.drawTextWithShadow(this.textRenderer, STATUS_EFFECT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 55, Colors.LIGHT_GRAY);
			this.appliedStatusEffectIdentifierField.render(context, mouseX, mouseY, delta);
			context.drawTextWithShadow(this.textRenderer, STATUS_EFFECT_DURATION_LABEL_TEXT, this.width / 2 - 153, 90, Colors.LIGHT_GRAY);
			this.appliedStatusEffectDurationField.render(context, mouseX, mouseY, delta);
			context.drawTextWithShadow(this.textRenderer, STATUS_EFFECT_AMPLIFIER_LABEL_TEXT, this.width / 2 - 74, 90, Colors.LIGHT_GRAY);
			this.appliedStatusEffectAmplifierField.render(context, mouseX, mouseY, delta);
		} else if (this.screenPage == ScreenPage.TRIGGER_SETTINGS) {
			context.drawTextWithShadow(this.textRenderer, INTERACTION_RESULT_ITEM_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 55, Colors.LIGHT_GRAY);
			this.interactionResultItemIdentifierField.render(context, mouseX, mouseY, delta);
			context.drawTextWithShadow(this.textRenderer, INTERACTION_TOOL_ITEM_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 90, Colors.LIGHT_GRAY);
			this.interactionToolItemIdentifierField.render(context, mouseX, mouseY, delta);
			context.drawTextWithShadow(this.textRenderer, USE_PREVENTING_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 125, Colors.LIGHT_GRAY);
			this.usePreventingStatusEffectIdentifierField.render(context, mouseX, mouseY, delta);
			context.drawTextWithShadow(this.textRenderer, REQUIRED_ADVANCEMENT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 160, Colors.LIGHT_GRAY);
			this.requiredAdvancementIdentifierField.render(context, mouseX, mouseY, delta);
			context.drawTextWithShadow(this.textRenderer, RECOVERY_TIMER_THRESHOLD_LABEL_TEXT, this.width / 2 - 153, 140, Colors.LIGHT_GRAY);
			this.recoveryTimerThresholdField.render(context, mouseX, mouseY, delta);
		}

	}

	@Override
	public boolean shouldPause() {
		return false;
	}

	private boolean updateFoodBlock() {
		ClientPlayNetworking.send(new UpdateFoodBlockPacket(
				this.foodBlockEntity.getPos(),
				new FoodBlockEntity.FoodBlockData(
						this.appliedStatusEffectIdentifierField.getText(),
						parseInt(this.appliedStatusEffectDurationField.getText()),
						parseInt(this.appliedStatusEffectAmplifierField.getText()),
						appliedStatusEffectAmbient,
						appliedStatusEffectShowParticles,
						appliedStatusEffectShowIcon,
						this.interactionResultItemIdentifierField.getText(),
						this.interactionToolItemIdentifierField.getText(),
						this.usePreventingStatusEffectIdentifierField.getText(),
						this.requiredAdvancementIdentifierField.getText(),
						parseInt(this.recoveryTimerThresholdField.getText()),
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

	public boolean deferSubtitles() {
		return true;
	}

	public static enum ScreenPage implements StringIdentifiable {
		APPLIED_EFFECT("applied_effect"),
		TRIGGER_SETTINGS("interaction_settings");

		private final String name;

		private ScreenPage(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return this.name;
		}

		public static Optional<ScreenPage> byName(String name) {
			return Arrays.stream(ScreenPage.values()).filter(screenPage -> screenPage.asString().equals(name)).findFirst();
		}

		public Text asText() {
			return Text.translatable("gui.food_block.screenPage." + this.name);
		}
	}
}
