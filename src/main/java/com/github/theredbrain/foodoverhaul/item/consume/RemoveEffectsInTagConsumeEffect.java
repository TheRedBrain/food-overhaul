package com.github.theredbrain.foodoverhaul.item.consume;

import com.github.theredbrain.foodoverhaul.registry.ConsumeEffectRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.World;

public record RemoveEffectsInTagConsumeEffect(RegistryEntryList<StatusEffect> effects) implements ConsumeEffect {
	public static final MapCodec<RemoveEffectsInTagConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(RegistryCodecs.entryList(RegistryKeys.STATUS_EFFECT).fieldOf("effects").forGetter(RemoveEffectsInTagConsumeEffect::effects))
					.apply(instance, RemoveEffectsInTagConsumeEffect::new)
	);
	public static final PacketCodec<RegistryByteBuf, RemoveEffectsInTagConsumeEffect> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.registryEntryList(RegistryKeys.STATUS_EFFECT), RemoveEffectsInTagConsumeEffect::effects, RemoveEffectsInTagConsumeEffect::new
	);

	@Override
	public ConsumeEffect.Type<RemoveEffectsInTagConsumeEffect> getType() {
		return ConsumeEffectRegistry.REMOVE_EFFECTS_IN_TAG;
	}

	@Override
	public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
		if (user.getEntityWorld().isClient()) {
			return false;
		}

		for (StatusEffectInstance currentEffect : user.getStatusEffects().stream().toList()) {
			RegistryEntry<StatusEffect> statusEffectRegistryEntry = currentEffect.getEffectType();
			if (this.effects.contains(statusEffectRegistryEntry)) {
				user.removeStatusEffect(statusEffectRegistryEntry);
			}
		}

		return true;
	}
}
