package com.github.theredbrain.foodoverhaul.registry;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.item.consume.RemoveEffectsInTagConsumeEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ConsumeEffectRegistry {
	public static final ConsumeEffect.Type<RemoveEffectsInTagConsumeEffect> REMOVE_EFFECTS_IN_TAG = register(
			"remove_effects_in_tag", RemoveEffectsInTagConsumeEffect.CODEC, RemoveEffectsInTagConsumeEffect.PACKET_CODEC
	);

	private static <T extends ConsumeEffect> ConsumeEffect.Type<T> register(String name, MapCodec<T> codec, PacketCodec<RegistryByteBuf, T> packetCodec) {
		return Registry.register(Registries.CONSUME_EFFECT_TYPE, FoodOverhaul.identifier(name), new ConsumeEffect.Type<>(codec, packetCodec));
	}

	public static void init() {
	}
}
