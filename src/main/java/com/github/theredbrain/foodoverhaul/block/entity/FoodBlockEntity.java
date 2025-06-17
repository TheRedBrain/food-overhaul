package com.github.theredbrain.foodoverhaul.block.entity;

import com.github.theredbrain.foodoverhaul.block.GenericFoodBlock;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FoodBlockEntity extends BlockEntity {

	private String appliedStatusEffectIdentifier = "";
	private int duration = 0;
	private int amplifier = 0;
	private boolean ambient = false;
	private boolean showParticles = false;
	private boolean showIcon = true;

	private String usePreventingStatusEffectIdentifier = "";
	private String requiredAdvancementIdentifier = "";

	private int recoveryTimer = 0;
	private int recoveryTimerThreshold = 0;
	private boolean infiniteUses = false;

	public FoodBlockEntity(BlockPos pos, BlockState state) {
		super(EntityRegistry.GENERIC_FOOD_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {


		if (!this.appliedStatusEffectIdentifier.isEmpty()) {
			nbt.putString("appliedStatusEffectIdentifier", this.appliedStatusEffectIdentifier);
		} else {
			nbt.remove("appliedStatusEffectIdentifier");
		}

		if (this.duration != 0) {
			nbt.putInt("duration", this.duration);
		} else {
			nbt.remove("duration");
		}

		if (this.amplifier != 0) {
			nbt.putInt("amplifier", this.amplifier);
		} else {
			nbt.remove("amplifier");
		}

		if (this.ambient) {
			nbt.putBoolean("ambient", true);
		} else {
			nbt.remove("ambient");
		}

		if (this.showParticles) {
			nbt.putBoolean("showParticles", true);
		} else {
			nbt.remove("showParticles");
		}

		if (this.showIcon) {
			nbt.putBoolean("showIcon", true);
		} else {
			nbt.remove("showIcon");
		}


		if (!this.usePreventingStatusEffectIdentifier.isEmpty()) {
			nbt.putString("usePreventingStatusEffectIdentifier", this.usePreventingStatusEffectIdentifier);
		} else {
			nbt.remove("usePreventingStatusEffectIdentifier");
		}

		if (!this.requiredAdvancementIdentifier.isEmpty()) {
			nbt.putString("requiredAdvancementIdentifier", this.requiredAdvancementIdentifier);
		} else {
			nbt.remove("requiredAdvancementIdentifier");
		}


		if (this.recoveryTimer > 0) {
			nbt.putInt("recoveryTimer", this.recoveryTimer);
		} else {
			nbt.remove("recoveryTimer");
		}

		if (this.recoveryTimerThreshold > 0) {
			nbt.putInt("recoveryTimerThreshold", this.recoveryTimerThreshold);
		} else {
			nbt.remove("recoveryTimerThreshold");
		}

		if (this.infiniteUses) {
			nbt.putBoolean("infiniteUses", true);
		} else {
			nbt.remove("infiniteUses");
		}

	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

		this.appliedStatusEffectIdentifier = nbt.getString("appliedStatusEffectIdentifier");

		this.duration = nbt.getInt("duration");

		this.amplifier = nbt.getInt("amplifier");

		this.ambient = nbt.getBoolean("ambient");

		this.showParticles = nbt.getBoolean("showParticles");

		this.showIcon = nbt.getBoolean("showIcon");


		this.usePreventingStatusEffectIdentifier = nbt.getString("usePreventingStatusEffectIdentifier");

		this.requiredAdvancementIdentifier = nbt.getString("requiredAdvancementIdentifier");


		this.recoveryTimer = nbt.getInt("recoveryTimer");

		this.recoveryTimerThreshold = nbt.getInt("recoveryTimerThreshold");

		this.infiniteUses = nbt.getBoolean("infiniteUses");

	}

	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return this.createComponentlessNbt(registryLookup);
	}

	public static void tick(World world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity) {
		if (!world.isClient && world.getTime() % 20L == 0L && foodBlockEntity.recoveryTimerThreshold > 0) {
			foodBlockEntity.recoveryTimer++;
			if (foodBlockEntity.recoveryTimer >= foodBlockEntity.recoveryTimerThreshold) {
				foodBlockEntity.recoveryTimer = 0;
				GenericFoodBlock.recoveryTick(world, pos, state);
			}
		}
	}

	//region --- getter & setter ---
	public String getAppliedStatusEffectIdentifier() {
		return this.appliedStatusEffectIdentifier;
	}

	public void setAppliedStatusEffectIdentifier(String appliedStatusEffectIdentifier) {
		this.appliedStatusEffectIdentifier = appliedStatusEffectIdentifier;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getAmplifier() {
		return amplifier;
	}

	public void setAmplifier(int amplifier) {
		this.amplifier = amplifier;
	}

	public boolean getAmbient() {
		return ambient;
	}

	public void setAmbient(boolean ambient) {
		this.ambient = ambient;
	}

	public boolean getShowParticles() {
		return showParticles;
	}

	public void setShowParticles(boolean showParticles) {
		this.showParticles = showParticles;
	}

	public boolean getShowIcon() {
		return showIcon;
	}

	public void setShowIcon(boolean showIcon) {
		this.showIcon = showIcon;
	}

	public String getUsePreventingStatusEffectIdentifier() {
		return usePreventingStatusEffectIdentifier;
	}

	public void setUsePreventingStatusEffectIdentifier(String usePreventingStatusEffectIdentifier) {
		this.usePreventingStatusEffectIdentifier = usePreventingStatusEffectIdentifier;
	}

	public String getRequiredAdvancementIdentifier() {
		return requiredAdvancementIdentifier;
	}

	public void setRequiredAdvancementIdentifier(String requiredAdvancementIdentifier) {
		this.requiredAdvancementIdentifier = requiredAdvancementIdentifier;
	}

	public int getRecoveryTimer() {
		return recoveryTimer;
	}

	public void setRecoveryTimer(int recoveryTimer) {
		this.recoveryTimer = recoveryTimer;
	}

	public boolean getInfiniteUses() {
		return infiniteUses;
	}

	public void setInfiniteUses(boolean infiniteUses) {
		this.infiniteUses = infiniteUses;
	}
	//endregion --- getter & setter ---
}
