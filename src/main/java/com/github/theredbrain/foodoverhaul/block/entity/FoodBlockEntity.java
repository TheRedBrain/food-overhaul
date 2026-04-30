package com.github.theredbrain.foodoverhaul.block.entity;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.block.GenericFoodBlock;
import com.github.theredbrain.foodoverhaul.component.type.FoodBlockDataComponent;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class FoodBlockEntity extends BlockEntity {

	private int recoveryTimer = 0;

	private FoodBlockData foodBlockData = FoodBlockData.DEFAULT;

	public FoodBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public FoodBlockEntity(BlockPos pos, BlockState state) {
		this(EntityRegistry.FOOD_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void saveAdditional(ValueOutput view) {

		super.saveAdditional(view);

		view.store("foodBlockData", FoodBlockData.CODEC, this.foodBlockData);

		if (this.recoveryTimer > 0) {
			view.putInt("recoveryTimer", this.recoveryTimer);
		} else {
			view.discard("recoveryTimer");
		}

	}

	@Override
	protected void loadAdditional(ValueInput view) {

		super.loadAdditional(view);

		this.foodBlockData = view.read("foodBlockData", FoodBlockData.CODEC).orElse(FoodBlockData.DEFAULT);

		this.recoveryTimer = view.getIntOr("recoveryTimer", 0);

	}

	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return this.saveCustomOnly(registries);
	}

	public static void tick(Level world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity) {
		if (!world.isClientSide() && world.getGameTime() % 20L == 0L && foodBlockEntity.foodBlockData.recovery_timer_threshold() > 0) {
			foodBlockEntity.recoveryTimer++;
			if (foodBlockEntity.recoveryTimer >= foodBlockEntity.foodBlockData.recovery_timer_threshold()) {
				foodBlockEntity.recoveryTimer = 0;
				GenericFoodBlock.recoveryTick(world, pos, state);
			}
		}
	}

	public FoodBlockData getFoodBlockData() {
		return this.foodBlockData;
	}

	public void setFoodBlockData(FoodBlockData foodBlockData) {
		this.foodBlockData = foodBlockData;
	}

	public int getRecoveryTimer() {
		return this.recoveryTimer;
	}

	public void setRecoveryTimer(int recoveryTimer) {
		this.recoveryTimer = recoveryTimer;
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter components) {
		super.applyImplicitComponents(components);
		this.foodBlockData = components.getOrDefault(FoodOverhaul.FOOD_BLOCK_DATA, FoodBlockDataComponent.DEFAULT).food_block_data();
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder builder) {
		super.collectImplicitComponents(builder);
		builder.set(FoodOverhaul.FOOD_BLOCK_DATA, new FoodBlockDataComponent(this.foodBlockData));
	}

	public record FoodBlockData(
			String applied_status_effect_identifier,
			int applied_status_effect_duration,
			int applied_status_effect_amplifier,
			boolean applied_status_effect_ambient,
			boolean applied_status_effect_show_particles,
			boolean applied_status_effect_show_icon,
			String interaction_result_item_identifier,
			String interaction_tool_item_identifier,
			String use_preventing_status_effect_identifier,
			String required_advancement_identifier,
			int recovery_timer_threshold,
			boolean infinite_uses
	) {

		public static final FoodBlockData DEFAULT = new FoodBlockData(
				"",
				0,
				0,
				false,
				false,
				true,
				"",
				"",
				"",
				"",
				0,
				false
		);

		public static final Codec<FoodBlockData> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Codec.STRING.fieldOf("applied_status_effect_identifier").forGetter(FoodBlockData::applied_status_effect_identifier),
								Codec.INT.fieldOf("applied_status_effect_duration").forGetter(FoodBlockData::applied_status_effect_duration),
								Codec.INT.fieldOf("applied_status_effect_amplifier").forGetter(FoodBlockData::applied_status_effect_amplifier),
								Codec.BOOL.fieldOf("applied_status_effect_ambient").forGetter(FoodBlockData::applied_status_effect_ambient),
								Codec.BOOL.fieldOf("applied_status_effect_show_particles").forGetter(FoodBlockData::applied_status_effect_show_particles),
								Codec.BOOL.fieldOf("applied_status_effect_show_icon").forGetter(FoodBlockData::applied_status_effect_show_icon),
								Codec.STRING.fieldOf("interaction_result_item_identifier").forGetter(FoodBlockData::interaction_result_item_identifier),
								Codec.STRING.fieldOf("interaction_tool_item_identifier").forGetter(FoodBlockData::interaction_tool_item_identifier),
								Codec.STRING.fieldOf("use_preventing_status_effect_identifier").forGetter(FoodBlockData::use_preventing_status_effect_identifier),
								Codec.STRING.fieldOf("required_advancement_identifier").forGetter(FoodBlockData::required_advancement_identifier),
								Codec.INT.fieldOf("recovery_timer_threshold").forGetter(FoodBlockData::recovery_timer_threshold),
								Codec.BOOL.fieldOf("infinite_uses").forGetter(FoodBlockData::infinite_uses)
						)
						.apply(instance, FoodBlockData::new)
		);

		public static final StreamCodec<RegistryFriendlyByteBuf, FoodBlockData> PACKET_CODEC = StreamCodec.ofMember(FoodBlockData::write, FoodBlockData::new);

		public FoodBlockData(RegistryFriendlyByteBuf registryByteBuf) {
			this(
					registryByteBuf.readUtf(),
					registryByteBuf.readInt(),
					registryByteBuf.readInt(),
					registryByteBuf.readBoolean(),
					registryByteBuf.readBoolean(),
					registryByteBuf.readBoolean(),
					registryByteBuf.readUtf(),
					registryByteBuf.readUtf(),
					registryByteBuf.readUtf(),
					registryByteBuf.readUtf(),
					registryByteBuf.readInt(),
					registryByteBuf.readBoolean()
			);
		}

		public void write(RegistryFriendlyByteBuf registryByteBuf) {
			registryByteBuf.writeUtf(this.applied_status_effect_identifier);
			registryByteBuf.writeInt(this.applied_status_effect_duration);
			registryByteBuf.writeInt(this.applied_status_effect_amplifier);
			registryByteBuf.writeBoolean(this.applied_status_effect_ambient);
			registryByteBuf.writeBoolean(this.applied_status_effect_show_particles);
			registryByteBuf.writeBoolean(this.applied_status_effect_show_icon);
			registryByteBuf.writeUtf(this.interaction_result_item_identifier);
			registryByteBuf.writeUtf(this.interaction_tool_item_identifier);
			registryByteBuf.writeUtf(this.use_preventing_status_effect_identifier);
			registryByteBuf.writeUtf(this.required_advancement_identifier);
			registryByteBuf.writeInt(this.recovery_timer_threshold);
			registryByteBuf.writeBoolean(this.infinite_uses);
		}
	}

}
