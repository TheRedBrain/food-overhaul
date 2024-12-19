package com.github.theredbrain.foodoverhaul.config;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import me.fzzyhmstrs.fzzy_config.annotations.ConvertFrom;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

@ConvertFrom(fileName = "server.json5", folder = "foodoverhaul")
public class ServerConfig extends Config {

	public ServerConfig() {
		super(FoodOverhaul.identifier("server"));
	}

	public ValidatedInt food_effect_duration_threshold_to_allow_eating = new ValidatedInt(200);
	public ValidatedInt item_cooldown_after_eating = new ValidatedInt(5);
}
