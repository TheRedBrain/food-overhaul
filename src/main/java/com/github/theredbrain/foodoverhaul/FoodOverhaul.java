package com.github.theredbrain.foodoverhaul;

import com.github.theredbrain.foodoverhaul.registry.FoodOverhaulBlocks;
import com.github.theredbrain.foodoverhaul.registry.FoodOverhaulCreativeModeTabs;
import com.github.theredbrain.foodoverhaul.registry.FoodOverhaulEntities;
import com.github.theredbrain.foodoverhaul.registry.FoodOverhaulConfigs;
import com.github.theredbrain.foodoverhaul.registry.FoodOverhaulDataComponents;
import com.github.theredbrain.foodoverhaul.registry.FoodOverhaulServerPackets;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoodOverhaul implements ModInitializer {
	public static final String MOD_ID = "foodoverhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceKey<CreativeModeTab> CREATIVE_MODE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, FoodOverhaul.identifier("food_overhaul"));

	public static Holder<Attribute> MAX_FOOD_EFFECTS;

	public static TagKey<MobEffect> FOOD_EFFECTS = TagKey.create(Registries.MOB_EFFECT, identifier("food_effects"));

	@Override
	public void onInitialize() {
		LOGGER.info("Enjoy your overhauled food!");

		FoodOverhaulBlocks.bootstrap();
		FoodOverhaulConfigs.bootstrap();
		FoodOverhaulDataComponents.bootstrap();
		FoodOverhaulEntities.bootstrap();

		FoodOverhaulCreativeModeTabs.init();
		FoodOverhaulServerPackets.init();
	}

	public static Identifier identifier(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}