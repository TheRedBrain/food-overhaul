package com.github.theredbrain.foodoverhaul;

import com.github.theredbrain.foodoverhaul.config.ServerConfig;
import com.github.theredbrain.foodoverhaul.entity.effect.RemoveFoodStatusEffect;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.foodoverhaul.registry.BlockRegistry;
import com.github.theredbrain.foodoverhaul.registry.DataComponentRegistry;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import com.github.theredbrain.foodoverhaul.registry.ServerPacketRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class FoodOverhaul implements ModInitializer {
	public static final String MOD_ID = "foodoverhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig SERVER_CONFIG;

	public static RegistryEntry<EntityAttribute> MAX_FOOD_EFFECTS;

	public static TagKey<StatusEffect> FOOD_EFFECTS = TagKey.of(RegistryKeys.STATUS_EFFECT, identifier("food_effects"));

	public static boolean tryEatOverhauledFood(PlayerEntity playerEntity, RegistryEntry<StatusEffect> statusEffectEntry) {
		if (statusEffectEntry.value() instanceof RemoveFoodStatusEffect) {
			return true;
		} else if (statusEffectEntry.isIn(FoodOverhaul.FOOD_EFFECTS)) {
			int currentEatenFoods = 0;
			Collection<StatusEffectInstance> collection = playerEntity.getStatusEffects();
			for (StatusEffectInstance currentEffect : collection) {
				if (currentEffect.getEffectType() == statusEffectEntry) {
					if (currentEffect.isDurationBelow(FoodOverhaul.SERVER_CONFIG.food_effect_duration_threshold_to_allow_eating.get())) {
						return true;
					} else {
						playerEntity.sendMessage(Text.translatable("hud.message.foodEatenAlready").append(Text.translatable(currentEffect.getTranslationKey())), true);
						return false;
					}
				} else if (currentEffect.getEffectType().isIn(FoodOverhaul.FOOD_EFFECTS)) {
					currentEatenFoods++;
				}
			}
			boolean bl = currentEatenFoods < ((DuckPlayerEntityMixin) playerEntity).foodoverhaul$getMaxFoodEffects();
			if (!bl) {
				playerEntity.sendMessage(Text.translatable("hud.message.maxFoodEaten"), true);
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
		return Identifier.of(MOD_ID, path);
	}
}