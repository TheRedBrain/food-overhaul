package com.github.theredbrain.foodoverhaul.block.entity;

import com.github.theredbrain.foodoverhaul.block.GenericFoodBlock;
import com.github.theredbrain.foodoverhaul.component.type.FoodBlockDataComponent;
import com.github.theredbrain.foodoverhaul.registry.FoodOverhaulEntities;
import com.github.theredbrain.foodoverhaul.registry.FoodOverhaulDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.List;

public class FoodBlockEntity extends BlockEntity {

	private int recoveryTimer = 0;

	private FoodBlockData foodBlockData = FoodBlockData.DEFAULT;

	public FoodBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public FoodBlockEntity(BlockPos pos, BlockState state) {
		this(FoodOverhaulEntities.FOOD_BLOCK_ENTITY, pos, state);
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
		this.foodBlockData = components.getOrDefault(FoodOverhaulDataComponents.FOOD_BLOCK_DATA, FoodBlockDataComponent.DEFAULT).food_block_data();
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder builder) {
		super.collectImplicitComponents(builder);
		builder.set(FoodOverhaulDataComponents.FOOD_BLOCK_DATA, new FoodBlockDataComponent(this.foodBlockData));
	}

	public record FoodBlockData(
			List<MobEffectInstance> applied_status_effects,
			String interaction_result_item_identifier,
			String interaction_tool_item_identifier,
			String use_preventing_status_effect_identifier,
			String required_advancement_identifier,
			int recovery_timer_threshold,
			boolean reduce_uses,
			boolean last_use_provides_effects,
			boolean consume_last_use
	) {

		public static final FoodBlockData DEFAULT = new FoodBlockData(
				List.of(),
				"",
				"",
				"",
				"",
				0,
				true,
				true,
				true
		);

		public static final Codec<FoodBlockData> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								MobEffectInstance.CODEC.listOf().fieldOf("applied_status_effects").forGetter(FoodBlockData::applied_status_effects),
								Codec.STRING.fieldOf("interaction_result_item_identifier").forGetter(FoodBlockData::interaction_result_item_identifier),
								Codec.STRING.fieldOf("interaction_tool_item_identifier").forGetter(FoodBlockData::interaction_tool_item_identifier),
								Codec.STRING.fieldOf("use_preventing_status_effect_identifier").forGetter(FoodBlockData::use_preventing_status_effect_identifier),
								Codec.STRING.fieldOf("required_advancement_identifier").forGetter(FoodBlockData::required_advancement_identifier),
								Codec.INT.fieldOf("recovery_timer_threshold").forGetter(FoodBlockData::recovery_timer_threshold),
								Codec.BOOL.fieldOf("reduce_uses").forGetter(FoodBlockData::reduce_uses),
								Codec.BOOL.fieldOf("last_use_provides_effects").forGetter(FoodBlockData::last_use_provides_effects),
								Codec.BOOL.fieldOf("consume_last_use").forGetter(FoodBlockData::consume_last_use)
						)
						.apply(instance, FoodBlockData::new)
		);

		public static final StreamCodec<RegistryFriendlyByteBuf, FoodBlockData> STREAM_CODEC = StreamCodec.composite(
				MobEffectInstance.STREAM_CODEC.apply(ByteBufCodecs.list()),
				FoodBlockData::applied_status_effects,
				ByteBufCodecs.STRING_UTF8,
				FoodBlockData::interaction_result_item_identifier,
				ByteBufCodecs.STRING_UTF8,
				FoodBlockData::interaction_tool_item_identifier,
				ByteBufCodecs.STRING_UTF8,
				FoodBlockData::use_preventing_status_effect_identifier,
				ByteBufCodecs.STRING_UTF8,
				FoodBlockData::required_advancement_identifier,
				ByteBufCodecs.INT,
				FoodBlockData::recovery_timer_threshold,
				ByteBufCodecs.BOOL,
				FoodBlockData::reduce_uses,
				ByteBufCodecs.BOOL,
				FoodBlockData::last_use_provides_effects,
				ByteBufCodecs.BOOL,
				FoodBlockData::consume_last_use,
				FoodBlockData::new
		);
	}

}
