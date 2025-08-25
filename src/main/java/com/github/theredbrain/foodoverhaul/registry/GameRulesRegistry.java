package com.github.theredbrain.foodoverhaul.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;

public class GameRulesRegistry {
	public static final GameRules.Key<DoubleRule> NATURAL_MAXIMUM_FOOD_EFFECTS =
			GameRuleRegistry.register("naturalMaximumFoodEffects", GameRules.Category.PLAYER, GameRuleFactory.createDoubleRule(3.0, 0.0, 1024.0));

	public static void init() {
	}
}
