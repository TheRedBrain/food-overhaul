package com.github.theredbrain.foodoverhaul;

import com.github.theredbrain.foodoverhaul.component.type.FoodBlockDataComponent;
import com.github.theredbrain.foodoverhaul.component.type.FoodDisplayBlockDataComponent;
import com.github.theredbrain.foodoverhaul.config.ServerConfig;
import com.github.theredbrain.foodoverhaul.entity.effect.RemoveFoodStatusEffect;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.foodoverhaul.registry.BlockRegistry;
import com.github.theredbrain.foodoverhaul.registry.DataComponentRegistry;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import com.github.theredbrain.foodoverhaul.registry.ServerPacketRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class FoodOverhaul implements ModInitializer {
	public static final String MOD_ID = "foodoverhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig SERVER_CONFIG;

	public static Holder<Attribute> MAX_FOOD_EFFECTS;

	public static TagKey<MobEffect> FOOD_EFFECTS = TagKey.create(Registries.MOB_EFFECT, identifier("food_effects"));

	public static DataComponentType<FoodBlockDataComponent> FOOD_BLOCK_DATA;

	public static DataComponentType<FoodDisplayBlockDataComponent> FOOD_DISPLAY_BLOCK_DATA;

	public static boolean tryEatOverhauledFood(Player playerEntity, Holder<MobEffect> statusEffectEntry) {
		if (statusEffectEntry.value() instanceof RemoveFoodStatusEffect) {
			return true;
		} else if (statusEffectEntry.is(FoodOverhaul.FOOD_EFFECTS)) {
			int currentEatenFoods = 0;
			Collection<MobEffectInstance> collection = playerEntity.getActiveEffects();
			for (MobEffectInstance currentEffect : collection) {
				if (currentEffect.getEffect() == statusEffectEntry) {
					if (currentEffect.endsWithin(FoodOverhaul.SERVER_CONFIG.food_effect_duration_threshold_to_allow_eating.get())) {
						return true;
					} else {
						playerEntity.sendOverlayMessage(Component.translatable("hud.message.food_eaten_already").append(Component.translatable(currentEffect.getDescriptionId())));
						return false;
					}
				} else if (currentEffect.getEffect().is(FoodOverhaul.FOOD_EFFECTS)) {
					currentEatenFoods++;
				}
			}
			boolean bl = currentEatenFoods < ((DuckPlayerEntityMixin) playerEntity).foodoverhaul$getMaxFoodEffects();
			if (!bl) {
				playerEntity.sendOverlayMessage(Component.translatable("hud.message.max_food_eaten"));
			}
			return bl;
		}
		return true;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Enjoy your overhauled food!");
		SERVER_CONFIG = ConfigApiJava.registerAndLoadConfig(ServerConfig::new);

		BlockRegistry.init();
		DataComponentRegistry.init();
		EntityRegistry.init();
		ServerPacketRegistry.init();
	}

	public static Identifier identifier(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}