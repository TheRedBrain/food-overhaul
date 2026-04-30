package com.github.theredbrain.foodoverhaul;

import com.github.theredbrain.foodoverhaul.component.type.FoodBlockDataComponent;
import com.github.theredbrain.foodoverhaul.component.type.FoodDisplayBlockDataComponent;
import com.github.theredbrain.foodoverhaul.config.ServerConfig;
import com.github.theredbrain.foodoverhaul.registry.BlockRegistry;
import com.github.theredbrain.foodoverhaul.registry.DataComponentRegistry;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import com.github.theredbrain.foodoverhaul.registry.ServerPacketRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoodOverhaul implements ModInitializer {
	public static final String MOD_ID = "foodoverhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig SERVER_CONFIG;

	public static Holder<Attribute> MAX_FOOD_EFFECTS;

	public static TagKey<MobEffect> FOOD_EFFECTS = TagKey.create(Registries.MOB_EFFECT, identifier("food_effects"));

	public static DataComponentType<FoodBlockDataComponent> FOOD_BLOCK_DATA;

	public static DataComponentType<FoodDisplayBlockDataComponent> FOOD_DISPLAY_BLOCK_DATA;

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