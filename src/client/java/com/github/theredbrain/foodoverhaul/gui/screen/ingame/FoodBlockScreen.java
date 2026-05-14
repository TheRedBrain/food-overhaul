package com.github.theredbrain.foodoverhaul.gui.screen.ingame;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
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
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Environment(value = EnvType.CLIENT)
public class FoodBlockScreen extends Screen {
	private static final Identifier SCROLL_BAR_BACKGROUND_8_92_TEXTURE = FoodOverhaul.identifier("scroll_bar/scroll_bar_background_8_92");
	private static final Identifier SCROLLER_VERTICAL_6_7_TEXTURE = FoodOverhaul.identifier("scroll_bar/scroller_vertical_6_7");
	public static final WidgetSprites REMOVE_ENTRY_BUTTON_TEXTURES = new WidgetSprites(
			FoodOverhaul.identifier("widgets/remove_entry_button"), FoodOverhaul.identifier("widgets/remove_entry_button_highlighted")
	);
	private static final int VISIBLE_LIST_ELEMENTS = 4;

	private static final Component ADD_NEW_APPLIED_STATUS_EFFECT_BUTTON_LABEL_TEXT = Component.translatable("gui.food_block.add_new_applied_status_effect_button_label");
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
	private static final Component REDUCE_USES_TRUE_LABEL_TEXT = Component.translatable("gui.food_block.reduce_uses_true_label");
	private static final Component REDUCE_USES_FALSE_LABEL_TEXT = Component.translatable("gui.food_block.reduce_uses_false_label");
	private static final Component LAST_USE_PROVIDES_EFFECTS_TRUE_LABEL_TEXT = Component.translatable("gui.food_block.last_use_provides_effects_true_label");
	private static final Component LAST_USE_PROVIDES_EFFECTS_FALSE_LABEL_TEXT = Component.translatable("gui.food_block.last_use_provides_effects_false_label");
	private static final Component CONSUME_LAST_USE_TRUE_LABEL_TEXT = Component.translatable("gui.food_block.consume_last_use_true_label");
	private static final Component CONSUME_LAST_USE_FALSE_LABEL_TEXT = Component.translatable("gui.food_block.consume_last_use_false_label");
	private final FoodBlockEntity foodBlockEntity;
	private final FoodBlockEntity.FoodBlockData foodBlockData;
	private CycleButton<ScreenPage> cycleScreenPageButton;
	private ScreenPage screenPage;

	private final List<MobEffectInstance> appliedStatusEffectList = new ArrayList<>();
	private Button removeListEntryButton0;
	private Button removeListEntryButton1;
	private Button removeListEntryButton2;
	private Button removeListEntryButton3;
	private Button addNewAppliedStatusEffectButton;
	private EditBox newAppliedStatusEffectIdentifierField;
	private EditBox newAppliedStatusEffectDurationField;
	private EditBox newAppliedStatusEffectAmplifierField;
	private CycleButton<Boolean> toggleNewAppliedStatusEffectAmbientButton;
	private CycleButton<Boolean> toggleNewAppliedStatusEffectShowParticlesButton;
	private CycleButton<Boolean> toggleNewAppliedStatusEffectShowIconButton;
	private boolean newAppliedStatusEffectAmbient;
	private boolean newAppliedStatusEffectShowParticles;
	private boolean newAppliedStatusEffectShowIcon;

	private EditBox interactionResultItemIdentifierField;
	private EditBox interactionToolItemIdentifierField;
	private EditBox usePreventingStatusEffectIdentifierField;
	private EditBox requiredAdvancementIdentifierField;
	private EditBox recoveryTimerThresholdField;
	private boolean reduceUses;
	private boolean lastUseProvidesEffects;
	private boolean consumeLastUse;
	private CycleButton<Boolean> toggleReduceUsesButton;
	private CycleButton<Boolean> toggleLastUseProvidesEffectsButton;
	private CycleButton<Boolean> toggleConsumeLastUseButton;
	private int scrollPosition = 0;
	private float scrollAmount = 0.0f;
	private boolean mouseClicked = false;

	public FoodBlockScreen(FoodBlockEntity foodBlockEntity) {
		super(GameNarrator.NO_TITLE);
		this.foodBlockEntity = foodBlockEntity;
		this.foodBlockData = this.foodBlockEntity.getFoodBlockData();
		this.screenPage = ScreenPage.APPLIED_EFFECTS;
	}

	private void addNewAppliedStatusEffect() {
		Optional<Holder.Reference<MobEffect>> optional_status_effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.parse(this.newAppliedStatusEffectIdentifierField.getValue()));
		if (optional_status_effect.isEmpty()) {
			return;
		}

		MobEffectInstance newMobEffectInstance = new MobEffectInstance(
				optional_status_effect.get(),
				parseInt(this.newAppliedStatusEffectDurationField.getValue()),
				parseInt(this.newAppliedStatusEffectAmplifierField.getValue()),
				this.newAppliedStatusEffectAmbient,
				this.newAppliedStatusEffectShowParticles,
				this.newAppliedStatusEffectShowIcon
		);
		for (MobEffectInstance entry : this.appliedStatusEffectList) {
			if (entry.equals(newMobEffectInstance)) {
				return;
			}
		}
		this.appliedStatusEffectList.add(newMobEffectInstance);
		this.scrollPosition = 0;
		this.scrollAmount = 0.0f;
		this.updateWidgets();
	}

	private void removeAppliedStatusEffect(int index) {
		if (index + this.scrollPosition < this.appliedStatusEffectList.size()) {
			this.appliedStatusEffectList.remove(index + this.scrollPosition);
		}
		this.scrollPosition = 0;
		this.scrollAmount = 0.0f;
		this.updateWidgets();
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

		this.cycleScreenPageButton = this.addRenderableWidget(CycleButton.builder(ScreenPage::asText, this.screenPage).withValues((ScreenPage[]) ScreenPage.values()).displayOnlyValue().create(this.width / 2 - 154, 10, 300, 20, Component.empty(), (button, screenPage) -> {
			this.screenPage = screenPage;
			this.updateWidgets();
		}));

		this.appliedStatusEffectList.clear();
		this.appliedStatusEffectList.addAll(this.foodBlockData.applied_status_effects());

		this.removeListEntryButton0 = this.addRenderableWidget(new ImageButton(this.width / 2 - 141, 34, 20, 20, REMOVE_ENTRY_BUTTON_TEXTURES, button -> this.removeAppliedStatusEffect(0)));
		this.removeListEntryButton1 = this.addRenderableWidget(new ImageButton(this.width / 2 - 141, 58, 20, 20, REMOVE_ENTRY_BUTTON_TEXTURES, button -> this.removeAppliedStatusEffect(1)));
		this.removeListEntryButton2 = this.addRenderableWidget(new ImageButton(this.width / 2 - 141, 82, 20, 20, REMOVE_ENTRY_BUTTON_TEXTURES, button -> this.removeAppliedStatusEffect(2)));
		this.removeListEntryButton3 = this.addRenderableWidget(new ImageButton(this.width / 2 - 141, 106, 20, 20, REMOVE_ENTRY_BUTTON_TEXTURES, button -> this.removeAppliedStatusEffect(3)));

		this.addNewAppliedStatusEffectButton = this.addRenderableWidget(Button.builder(ADD_NEW_APPLIED_STATUS_EFFECT_BUTTON_LABEL_TEXT, button -> this.addNewAppliedStatusEffect()).bounds(this.width / 2 - 154, 130, 300, 20).build());

		this.newAppliedStatusEffectIdentifierField = new EditBox(this.font, this.width / 2 - 154, 165, 200, 20, Component.empty());
		this.newAppliedStatusEffectIdentifierField.setMaxLength(128);
		this.addWidget(this.newAppliedStatusEffectIdentifierField);

		this.newAppliedStatusEffectDurationField = new EditBox(this.font, this.width / 2 + 50, 165, 50, 20, Component.empty());
		this.addWidget(this.newAppliedStatusEffectDurationField);

		this.newAppliedStatusEffectAmplifierField = new EditBox(this.font, this.width / 2 + 104, 165, 50, 20, Component.empty());
		this.addWidget(this.newAppliedStatusEffectAmplifierField);

		this.newAppliedStatusEffectAmbient = false;
		this.toggleNewAppliedStatusEffectAmbientButton = this.addRenderableWidget(CycleButton.booleanBuilder(AMBIENT_TRUE_LABEL_TEXT, AMBIENT_FALSE_LABEL_TEXT, false).displayOnlyValue().create(this.width / 2 - 154, 189, 100, 20, Component.empty(), (button, appliedStatusEffectAmbient) -> {
			this.newAppliedStatusEffectAmbient = appliedStatusEffectAmbient;
		}));

		this.newAppliedStatusEffectShowParticles = false;
		this.toggleNewAppliedStatusEffectShowParticlesButton = this.addRenderableWidget(CycleButton.booleanBuilder(SHOW_PARTICLES_LABEL_TEXT, HIDE_PARTICLES_LABEL_TEXT, false).displayOnlyValue().create(this.width / 2 - 50, 189, 100, 20, Component.empty(), (button, appliedStatusEffectShowParticles) -> {
			this.newAppliedStatusEffectShowParticles = appliedStatusEffectShowParticles;
		}));

		this.newAppliedStatusEffectShowIcon = true;
		this.toggleNewAppliedStatusEffectShowIconButton = this.addRenderableWidget(CycleButton.booleanBuilder(SHOW_ICON_LABEL_TEXT, HIDE_ICON_LABEL_TEXT, true).displayOnlyValue().create(this.width / 2 + 54, 189, 100, 20, Component.empty(), (button, appliedStatusEffectShowIcon) -> {
			this.newAppliedStatusEffectShowIcon = appliedStatusEffectShowIcon;
		}));


		int labelWidth = this.font.width(INTERACTION_RESULT_ITEM_IDENTIFIER_LABEL_TEXT);
		this.interactionResultItemIdentifierField = new EditBox(this.font, this.width / 2 - 149 + labelWidth, 53, 300 - 5 - labelWidth, 20, Component.empty());
		this.interactionResultItemIdentifierField.setMaxLength(128);
		this.interactionResultItemIdentifierField.setValue(this.foodBlockData.interaction_result_item_identifier());
		this.addWidget(this.interactionResultItemIdentifierField);

		labelWidth = this.font.width(INTERACTION_TOOL_ITEM_IDENTIFIER_LABEL_TEXT);
		this.interactionToolItemIdentifierField = new EditBox(this.font, this.width / 2 - 149 + labelWidth, 77, 300 - 5 - labelWidth, 20, Component.empty());
		this.interactionToolItemIdentifierField.setMaxLength(128);
		this.interactionToolItemIdentifierField.setValue(this.foodBlockData.interaction_tool_item_identifier());
		this.addWidget(this.interactionToolItemIdentifierField);

		labelWidth = this.font.width(USE_PREVENTING_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT);
		this.usePreventingStatusEffectIdentifierField = new EditBox(this.font, this.width / 2 - 149 + labelWidth, 101, 300 - 5 - labelWidth, 20, Component.empty());
		this.usePreventingStatusEffectIdentifierField.setMaxLength(128);
		this.usePreventingStatusEffectIdentifierField.setValue(this.foodBlockData.use_preventing_status_effect_identifier());
		this.addWidget(this.usePreventingStatusEffectIdentifierField);

		labelWidth = this.font.width(REQUIRED_ADVANCEMENT_IDENTIFIER_LABEL_TEXT);
		this.requiredAdvancementIdentifierField = new EditBox(this.font, this.width / 2 - 149 + labelWidth, 125, 300 - 5 - labelWidth, 20, Component.empty());
		this.requiredAdvancementIdentifierField.setMaxLength(128);
		this.requiredAdvancementIdentifierField.setValue(this.foodBlockData.required_advancement_identifier());
		this.addWidget(this.requiredAdvancementIdentifierField);

		this.recoveryTimerThresholdField = new EditBox(this.font, this.width / 2 - 154, 165, 130, 20, Component.empty());
		this.recoveryTimerThresholdField.setMaxLength(128);
		this.recoveryTimerThresholdField.setValue(Integer.toString(this.foodBlockData.recovery_timer_threshold()));
		this.addWidget(this.recoveryTimerThresholdField);

		this.lastUseProvidesEffects = this.foodBlockData.last_use_provides_effects();
		this.toggleLastUseProvidesEffectsButton = this.addRenderableWidget(CycleButton.booleanBuilder(LAST_USE_PROVIDES_EFFECTS_TRUE_LABEL_TEXT, LAST_USE_PROVIDES_EFFECTS_FALSE_LABEL_TEXT, this.lastUseProvidesEffects).displayOnlyValue().create(this.width / 2 - 16, 165, 170, 20, Component.empty(), (button, lastUseProvidesEffects) -> {
			this.lastUseProvidesEffects = lastUseProvidesEffects;
		}));

		this.reduceUses = this.foodBlockData.reduce_uses();
		this.toggleReduceUsesButton = this.addRenderableWidget(CycleButton.booleanBuilder(REDUCE_USES_TRUE_LABEL_TEXT, REDUCE_USES_FALSE_LABEL_TEXT, this.reduceUses).displayOnlyValue().create(this.width / 2 - 154, 189, 130, 20, Component.empty(), (button, infiniteUses) -> {
			this.reduceUses = infiniteUses;
		}));

		this.consumeLastUse = this.foodBlockData.consume_last_use();
		this.toggleConsumeLastUseButton = this.addRenderableWidget(CycleButton.booleanBuilder(CONSUME_LAST_USE_TRUE_LABEL_TEXT, CONSUME_LAST_USE_FALSE_LABEL_TEXT, this.consumeLastUse).displayOnlyValue().create(this.width / 2 - 16, 189, 170, 20, Component.empty(), (button, consumeLastUse) -> {
			this.consumeLastUse = consumeLastUse;
		}));

		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.done()).bounds(this.width / 2 - 4 - 150, 213, 150, 20).build());
		this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.cancel()).bounds(this.width / 2 + 4, 213, 150, 20).build());
		this.updateWidgets();
	}

	private void updateWidgets() {
		this.cycleScreenPageButton.visible = false;

		this.removeListEntryButton0.visible = false;
		this.removeListEntryButton1.visible = false;
		this.removeListEntryButton2.visible = false;
		this.removeListEntryButton3.visible = false;
		this.addNewAppliedStatusEffectButton.visible = false;

		this.newAppliedStatusEffectIdentifierField.setVisible(false);
		this.newAppliedStatusEffectDurationField.setVisible(false);
		this.newAppliedStatusEffectAmplifierField.setVisible(false);
		this.toggleNewAppliedStatusEffectAmbientButton.visible = false;
		this.toggleNewAppliedStatusEffectShowParticlesButton.visible = false;
		this.toggleNewAppliedStatusEffectShowIconButton.visible = false;

		this.interactionResultItemIdentifierField.setVisible(false);
		this.interactionToolItemIdentifierField.setVisible(false);
		this.usePreventingStatusEffectIdentifierField.setVisible(false);
		this.requiredAdvancementIdentifierField.setVisible(false);
		this.recoveryTimerThresholdField.setVisible(false);
		this.toggleReduceUsesButton.visible = false;
		this.toggleLastUseProvidesEffectsButton.visible = false;
		this.toggleConsumeLastUseButton.visible = false;

		this.cycleScreenPageButton.visible = true;
		if (this.screenPage == ScreenPage.APPLIED_EFFECTS) {

			int index = 0;
			for (int i = 0; i < Math.min(VISIBLE_LIST_ELEMENTS, this.appliedStatusEffectList.size()); i++) {
				if (index == 0) {
					this.removeListEntryButton0.visible = true;
				} else if (index == 1) {
					this.removeListEntryButton1.visible = true;
				} else if (index == 2) {
					this.removeListEntryButton2.visible = true;
				} else if (index == 3) {
					this.removeListEntryButton3.visible = true;
				}
				index++;
			}
			this.addNewAppliedStatusEffectButton.visible = true;

			this.newAppliedStatusEffectIdentifierField.setVisible(true);
			this.newAppliedStatusEffectDurationField.setVisible(true);
			this.newAppliedStatusEffectAmplifierField.setVisible(true);

			this.toggleNewAppliedStatusEffectAmbientButton.visible = true;
			this.toggleNewAppliedStatusEffectShowParticlesButton.visible = true;
			this.toggleNewAppliedStatusEffectShowIconButton.visible = true;

		} else if (this.screenPage == ScreenPage.TRIGGER_SETTINGS) {

			this.interactionResultItemIdentifierField.setVisible(true);
			this.interactionToolItemIdentifierField.setVisible(true);
			this.usePreventingStatusEffectIdentifierField.setVisible(true);
			this.requiredAdvancementIdentifierField.setVisible(true);
			this.recoveryTimerThresholdField.setVisible(true);
			this.toggleReduceUsesButton.visible = true;
			this.toggleLastUseProvidesEffectsButton.visible = true;
			this.toggleConsumeLastUseButton.visible = true;

		}
	}

	@Override
	public void resize(int width, int height) {
		ScreenPage var = this.screenPage;
		List<MobEffectInstance> list = new ArrayList<>(this.appliedStatusEffectList);
		boolean bool = this.newAppliedStatusEffectAmbient;
		boolean bool1 = this.newAppliedStatusEffectShowParticles;
		boolean bool2 = this.newAppliedStatusEffectShowIcon;
		boolean bool3 = this.reduceUses;
		boolean bool4 = this.lastUseProvidesEffects;
		boolean bool5 = this.consumeLastUse;
		String string = this.newAppliedStatusEffectIdentifierField.getValue();
		String string1 = this.newAppliedStatusEffectDurationField.getValue();
		String string2 = this.newAppliedStatusEffectAmplifierField.getValue();
		String string3 = this.interactionResultItemIdentifierField.getValue();
		String string4 = this.interactionToolItemIdentifierField.getValue();
		String string5 = this.usePreventingStatusEffectIdentifierField.getValue();
		String string6 = this.requiredAdvancementIdentifierField.getValue();
		String string7 = this.recoveryTimerThresholdField.getValue();
		this.init(width, height);
		this.screenPage = var;
		this.cycleScreenPageButton.setValue(var);
		this.appliedStatusEffectList.clear();
		this.appliedStatusEffectList.addAll(list);
		this.newAppliedStatusEffectAmbient = bool;
		this.toggleNewAppliedStatusEffectAmbientButton.setValue(bool);
		this.newAppliedStatusEffectShowParticles = bool1;
		this.toggleNewAppliedStatusEffectShowParticlesButton.setValue(bool1);
		this.newAppliedStatusEffectShowIcon = bool2;
		this.toggleNewAppliedStatusEffectShowIconButton.setValue(bool2);
		this.reduceUses = bool3;
		this.toggleReduceUsesButton.setValue(bool3);
		this.lastUseProvidesEffects = bool4;
		this.toggleLastUseProvidesEffectsButton.setValue(bool4);
		this.consumeLastUse = bool5;
		this.toggleConsumeLastUseButton.setValue(bool5);
		this.newAppliedStatusEffectIdentifierField.setValue(string);
		this.newAppliedStatusEffectDurationField.setValue(string1);
		this.newAppliedStatusEffectAmplifierField.setValue(string2);
		this.interactionResultItemIdentifierField.setValue(string3);
		this.interactionToolItemIdentifierField.setValue(string4);
		this.usePreventingStatusEffectIdentifierField.setValue(string5);
		this.requiredAdvancementIdentifierField.setValue(string6);
		this.recoveryTimerThresholdField.setValue(string7);
		this.updateWidgets();
	}

	@Override
	public boolean mouseClicked(final MouseButtonEvent event, final boolean doubleClick) {
		this.mouseClicked = false;
		if (this.appliedStatusEffectList.size() > VISIBLE_LIST_ELEMENTS && this.screenPage == ScreenPage.APPLIED_EFFECTS) {
			int i = this.width / 2 - 152;
			int j = 35;
			if (event.x() >= (double) i && event.x() < (double) (i + 6) && event.y() >= (double) j && event.y() < (double) (j + 90)) {
				this.mouseClicked = true;
			}
		}
		return super.mouseClicked(event, doubleClick);
	}

	@Override
	public boolean mouseDragged(final MouseButtonEvent event, final double dx, final double dy) {
		if (this.appliedStatusEffectList.size() > VISIBLE_LIST_ELEMENTS && this.screenPage == ScreenPage.APPLIED_EFFECTS && this.mouseClicked) {
			int i = this.appliedStatusEffectList.size() - VISIBLE_LIST_ELEMENTS;
			float f = (float) dy / (float) i;
			this.scrollAmount = Mth.clamp(this.scrollAmount + f, 0.0f, 1.0f);
			this.scrollPosition = (int) ((double) (this.scrollAmount * (float) i));
		}
		return super.mouseDragged(event, dx, dy);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
		if (this.appliedStatusEffectList.size() > VISIBLE_LIST_ELEMENTS && this.screenPage == ScreenPage.APPLIED_EFFECTS
				&& mouseX >= (double) (this.width / 2 - 153) && mouseX <= (double) (this.width / 2 + 154) && mouseY >= 34 && mouseY <= 126) {
			int i = this.appliedStatusEffectList.size() - VISIBLE_LIST_ELEMENTS;
			float f = (float) scrollY / (float) i;
			this.scrollAmount = Mth.clamp(this.scrollAmount - f, 0.0f, 1.0f);
			this.scrollPosition = (int) ((double) (this.scrollAmount * (float) i));
		}
		return true;
	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {

		super.extractRenderState(graphics, mouseX, mouseY, a);

		if (this.screenPage == ScreenPage.APPLIED_EFFECTS) {

			for (int i = this.scrollPosition; i < Math.min(this.scrollPosition + VISIBLE_LIST_ELEMENTS, this.appliedStatusEffectList.size()); i++) {
				MobEffectInstance mobEffectInstance = this.appliedStatusEffectList.get(i);
				Component text = Component.translatable("gui.food_block.list_entry.1", mobEffectInstance.getEffect().value().getDisplayName(), mobEffectInstance.getDuration(), mobEffectInstance.getAmplifier());
				MutableComponent text1 = mobEffectInstance.isAmbient() ? Component.translatable("gui.food_block.list_entry.is_ambient.true") : Component.translatable("gui.food_block.list_entry.is_ambient.false");
				text1.append(mobEffectInstance.isVisible() ? Component.translatable("gui.food_block.list_entry.is_visible.true") : Component.translatable("gui.food_block.list_entry.is_visible.false"));
				text1.append(mobEffectInstance.showIcon() ? Component.translatable("gui.food_block.list_entry.show_icon.true") : Component.translatable("gui.food_block.list_entry.show_icon.false"));
				graphics.text(this.font, text, this.width / 2 - 117, 35 + ((i - this.scrollPosition) * 24), CommonColors.LIGHT_GRAY);
				graphics.text(this.font, text1, this.width / 2 - 117, 45 + ((i - this.scrollPosition) * 24), CommonColors.LIGHT_GRAY);
				graphics.text(this.font, STATUS_EFFECT_AMPLIFIER_LABEL_TEXT, this.width / 2 + 105, 165, CommonColors.LIGHT_GRAY);
			}
			if (this.appliedStatusEffectList.size() > VISIBLE_LIST_ELEMENTS) {
				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SCROLL_BAR_BACKGROUND_8_92_TEXTURE, this.width / 2 - 153, 34, 8, 92);
				int k = (int) (81.0f * this.scrollAmount);
				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SCROLLER_VERTICAL_6_7_TEXTURE, this.width / 2 - 152, 35 + k, 6, 7);
			}
			graphics.text(this.font, STATUS_EFFECT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 155, CommonColors.LIGHT_GRAY);
			this.newAppliedStatusEffectIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, STATUS_EFFECT_DURATION_LABEL_TEXT, this.width / 2 + 51, 155, CommonColors.LIGHT_GRAY);
			this.newAppliedStatusEffectDurationField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, STATUS_EFFECT_AMPLIFIER_LABEL_TEXT, this.width / 2 + 105, 155, CommonColors.LIGHT_GRAY);
			this.newAppliedStatusEffectAmplifierField.extractRenderState(graphics, mouseX, mouseY, a);
		} else if (this.screenPage == ScreenPage.TRIGGER_SETTINGS) {
			graphics.text(this.font, INTERACTION_RESULT_ITEM_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 59, CommonColors.LIGHT_GRAY);
			this.interactionResultItemIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, INTERACTION_TOOL_ITEM_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 83, CommonColors.LIGHT_GRAY);
			this.interactionToolItemIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, USE_PREVENTING_STATUS_EFFECT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 107, CommonColors.LIGHT_GRAY);
			this.usePreventingStatusEffectIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, REQUIRED_ADVANCEMENT_IDENTIFIER_LABEL_TEXT, this.width / 2 - 153, 131, CommonColors.LIGHT_GRAY);
			this.requiredAdvancementIdentifierField.extractRenderState(graphics, mouseX, mouseY, a);
			graphics.text(this.font, RECOVERY_TIMER_THRESHOLD_LABEL_TEXT, this.width / 2 - 153, 155, CommonColors.LIGHT_GRAY);
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
						this.appliedStatusEffectList,
						this.interactionResultItemIdentifierField.getValue(),
						this.interactionToolItemIdentifierField.getValue(),
						this.usePreventingStatusEffectIdentifierField.getValue(),
						this.requiredAdvancementIdentifierField.getValue(),
						parseInt(this.recoveryTimerThresholdField.getValue()),
						reduceUses,
						lastUseProvidesEffects,
						consumeLastUse
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
		APPLIED_EFFECTS("applied_effects"),
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
			return Component.translatable("gui.food_block.screen_page." + this.name);
		}
	}
}
