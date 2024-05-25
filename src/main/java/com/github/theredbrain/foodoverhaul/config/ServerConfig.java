package com.github.theredbrain.foodoverhaul.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(
        name = "server"
)
public class ServerConfig implements ConfigData {
    @Comment("""
            When food effects have less than this many ticks of duration left,
            their corresponding food type can be eaten again
            """)
    public int food_effect_duration_threshold_to_allow_eating = 200;
    public ServerConfig() {

    }
}
