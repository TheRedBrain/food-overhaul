package com.github.theredbrain.foodoverhaul.block.entity;

import com.github.theredbrain.foodoverhaul.block.GenericFoodBlock;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
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
	protected void writeData(WriteView view) {

		super.writeData(view);

		if (!this.appliedStatusEffectIdentifier.isEmpty()) {
			view.putString("appliedStatusEffectIdentifier", this.appliedStatusEffectIdentifier);
		} else {
			view.remove("appliedStatusEffectIdentifier");
		}

		if (this.duration != 0) {
			view.putInt("duration", this.duration);
		} else {
			view.remove("duration");
		}

		if (this.amplifier != 0) {
			view.putInt("amplifier", this.amplifier);
		} else {
			view.remove("amplifier");
		}

		if (this.ambient) {
			view.putBoolean("ambient", true);
		} else {
			view.remove("ambient");
		}

		if (this.showParticles) {
			view.putBoolean("showParticles", true);
		} else {
			view.remove("showParticles");
		}

		if (this.showIcon) {
			view.putBoolean("showIcon", true);
		} else {
			view.remove("showIcon");
		}


		if (!this.usePreventingStatusEffectIdentifier.isEmpty()) {
			view.putString("usePreventingStatusEffectIdentifier", this.usePreventingStatusEffectIdentifier);
		} else {
			view.remove("usePreventingStatusEffectIdentifier");
		}

		if (!this.requiredAdvancementIdentifier.isEmpty()) {
			view.putString("requiredAdvancementIdentifier", this.requiredAdvancementIdentifier);
		} else {
			view.remove("requiredAdvancementIdentifier");
		}


		if (this.recoveryTimer > 0) {
			view.putInt("recoveryTimer", this.recoveryTimer);
		} else {
			view.remove("recoveryTimer");
		}

		if (this.recoveryTimerThreshold > 0) {
			view.putInt("recoveryTimerThreshold", this.recoveryTimerThreshold);
		} else {
			view.remove("recoveryTimerThreshold");
		}

		if (this.infiniteUses) {
			view.putBoolean("infiniteUses", true);
		} else {
			view.remove("infiniteUses");
		}

	}

	@Override
	protected void readData(ReadView view) {

		this.appliedStatusEffectIdentifier = view.getString("appliedStatusEffectIdentifier", "");

		this.duration = view.getInt("duration", 0);

		this.amplifier = view.getInt("amplifier", 0);

		this.ambient = view.getBoolean("ambient", false);

		this.showParticles = view.getBoolean("showParticles", false);

		this.showIcon = view.getBoolean("showIcon", true);


		this.usePreventingStatusEffectIdentifier = view.getString("usePreventingStatusEffectIdentifier", "");

		this.requiredAdvancementIdentifier = view.getString("requiredAdvancementIdentifier", "");


		this.recoveryTimer = view.getInt("recoveryTimer", 0);

		this.recoveryTimerThreshold = view.getInt("recoveryTimerThreshold", 0);

		this.infiniteUses = view.getBoolean("infiniteUses", false);

	}

	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
		return this.createComponentlessNbt(registries);
	}

	public static void tick(World world, BlockPos pos, BlockState state, FoodBlockEntity foodBlockEntity) {
		if (!world.isClient() && world.getTime() % 20L == 0L && foodBlockEntity.recoveryTimerThreshold > 0) {
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
