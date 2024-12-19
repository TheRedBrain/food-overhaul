package com.github.theredbrain.foodoverhaul.component.type;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.List;

public record OverhauledFoodComponent(
		List<OverhauledFoodComponent.StatusEffectEntry> effects
) {
	public static final Codec<OverhauledFoodComponent> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							OverhauledFoodComponent.StatusEffectEntry.CODEC.listOf().optionalFieldOf("effects", List.of()).forGetter(OverhauledFoodComponent::effects)
					)
					.apply(instance, OverhauledFoodComponent::new)
	);
	public static final PacketCodec<RegistryByteBuf, OverhauledFoodComponent> PACKET_CODEC = PacketCodec.tuple(
			OverhauledFoodComponent.StatusEffectEntry.PACKET_CODEC.collect(PacketCodecs.toList()),
			OverhauledFoodComponent::effects,
			OverhauledFoodComponent::new
	);

	public static class Builder {
		private final ImmutableList.Builder<OverhauledFoodComponent.StatusEffectEntry> effects = ImmutableList.builder();

		public OverhauledFoodComponent.Builder statusEffect(StatusEffectInstance effect, float chance) {
			this.effects.add(new OverhauledFoodComponent.StatusEffectEntry(effect, chance));
			return this;
		}

		public OverhauledFoodComponent build() {
			return new OverhauledFoodComponent(this.effects.build());
		}
	}

	public static record StatusEffectEntry(StatusEffectInstance effect, float probability) {
		public static final Codec<OverhauledFoodComponent.StatusEffectEntry> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								StatusEffectInstance.CODEC.fieldOf("effect").forGetter(OverhauledFoodComponent.StatusEffectEntry::effect),
								Codec.floatRange(0.0F, 1.0F).optionalFieldOf("probability", 1.0F).forGetter(OverhauledFoodComponent.StatusEffectEntry::probability)
						)
						.apply(instance, OverhauledFoodComponent.StatusEffectEntry::new)
		);
		public static final PacketCodec<RegistryByteBuf, OverhauledFoodComponent.StatusEffectEntry> PACKET_CODEC = PacketCodec.tuple(
				StatusEffectInstance.PACKET_CODEC,
				OverhauledFoodComponent.StatusEffectEntry::effect,
				PacketCodecs.FLOAT,
				OverhauledFoodComponent.StatusEffectEntry::probability,
				OverhauledFoodComponent.StatusEffectEntry::new
		);

		public StatusEffectInstance effect() {
			return new StatusEffectInstance(this.effect);
		}
	}
}
