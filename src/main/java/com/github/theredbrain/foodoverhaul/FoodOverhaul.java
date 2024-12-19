package com.github.theredbrain.foodoverhaul;

import com.github.theredbrain.foodoverhaul.component.type.OverhauledFoodComponent;
import com.github.theredbrain.foodoverhaul.config.ServerConfig;
import com.github.theredbrain.foodoverhaul.registry.ItemComponentRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoodOverhaul implements ModInitializer {
	public static final String MOD_ID = "foodoverhaul";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig SERVER_CONFIG = ConfigApiJava.registerAndLoadConfig(ServerConfig::new);

	public static ComponentType<OverhauledFoodComponent> OVERHAULED_FOOD_COMPONENT_TYPE;
	public static RegistryEntry<EntityAttribute> MAX_FOOD_EFFECTS;

	@Override
	public void onInitialize() {
		LOGGER.info("Enjoy your overhauled food!");

		ItemComponentRegistry.init();
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}