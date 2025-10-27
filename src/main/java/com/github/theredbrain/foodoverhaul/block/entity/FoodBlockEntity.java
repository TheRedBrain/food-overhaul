package com.github.theredbrain.foodoverhaul.block.entity;

import com.github.theredbrain.foodoverhaul.block.GenericFoodBlock;
import com.github.theredbrain.foodoverhaul.registry.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FoodBlockEntity extends BlockEntity {

	private String appliedStatusEffectIdentifier = "";
	private int appliedStatusEffectDuration = 0;
	private int appliedStatusEffectAmplifier = 0;
	private boolean appliedStatusEffectAmbient = false;
	private boolean appliedStatusEffectShowParticles = false;
	private boolean appliedStatusEffectShowIcon = true;

	private String interactionToolItemIdentifier = "";
	private String interactionResultItemIdentifier = "";

	private String usePreventingStatusEffectIdentifier = "";
	private String requiredAdvancementIdentifier = "";

	private int recoveryTimer = 0;
	private int recoveryTimerThreshold = 0;
	private boolean infiniteUses = false;

	public FoodBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public FoodBlockEntity(BlockPos pos, BlockState state) {
		this(EntityRegistry.GENERIC_FOOD_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void writeData(WriteView view) {

		super.writeData(view);

		if (!this.appliedStatusEffectIdentifier.isEmpty()) {
			view.putString("appliedStatusEffectIdentifier", this.appliedStatusEffectIdentifier);
		} else {
			view.remove("appliedStatusEffectIdentifier");
		}

		if (this.appliedStatusEffectDuration != 0) {
			view.putInt("appliedStatusEffectDuration", this.appliedStatusEffectDuration);
		} else {
			view.remove("appliedStatusEffectDuration");
		}

		if (this.appliedStatusEffectAmplifier != 0) {
			view.putInt("appliedStatusEffectAmplifier", this.appliedStatusEffectAmplifier);
		} else {
			view.remove("appliedStatusEffectAmplifier");
		}

		if (this.appliedStatusEffectAmbient) {
			view.putBoolean("appliedStatusEffectAmbient", true);
		} else {
			view.remove("appliedStatusEffectAmbient");
		}

		if (this.appliedStatusEffectShowParticles) {
			view.putBoolean("appliedStatusEffectShowParticles", true);
		} else {
			view.remove("appliedStatusEffectShowParticles");
		}

		if (this.appliedStatusEffectShowIcon) {
			view.putBoolean("appliedStatusEffectShowIcon", true);
		} else {
			view.remove("appliedStatusEffectShowIcon");
		}


		if (!this.interactionToolItemIdentifier.isEmpty()) {
			view.putString("interactionToolItemIdentifier", this.interactionToolItemIdentifier);
		} else {
			view.remove("interactionToolItemIdentifier");
		}

		if (!this.interactionResultItemIdentifier.isEmpty()) {
			view.putString("interactionResultItemIdentifier", this.interactionResultItemIdentifier);
		} else {
			view.remove("interactionResultItemIdentifier");
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

		this.appliedStatusEffectDuration = view.getInt("appliedStatusEffectDuration", 0);

		this.appliedStatusEffectAmplifier = view.getInt("appliedStatusEffectAmplifier", 0);

		this.appliedStatusEffectAmbient = view.getBoolean("appliedStatusEffectAmbient", false);

		this.appliedStatusEffectShowParticles = view.getBoolean("appliedStatusEffectShowParticles", false);

		this.appliedStatusEffectShowIcon = view.getBoolean("appliedStatusEffectShowIcon", true);


		this.interactionToolItemIdentifier = view.getString("interactionToolItemIdentifier", "");

		this.interactionResultItemIdentifier = view.getString("interactionResultItemIdentifier", "");


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

	public boolean setAppliedStatusEffectIdentifier(String appliedStatusEffectIdentifier) {
		if (Registries.STATUS_EFFECT.get(Identifier.tryParse(appliedStatusEffectIdentifier)) != null || appliedStatusEffectIdentifier.equals("")) {
			this.appliedStatusEffectIdentifier = appliedStatusEffectIdentifier;
			return true;
		}
		return false;
	}

	public int getAppliedStatusEffectAmplifier() {
		return this.appliedStatusEffectAmplifier;
	}

	public boolean setAppliedStatusEffectAmplifier(int appliedStatusEffectAmplifier) {
		if (appliedStatusEffectAmplifier >= 0 && appliedStatusEffectAmplifier < 127) {
			this.appliedStatusEffectAmplifier = appliedStatusEffectAmplifier;
			return true;
		}
		return false;
	}

	public int getAppliedStatusEffectDuration() {
		return appliedStatusEffectDuration;
	}

	public void setAppliedStatusEffectDuration(int appliedStatusEffectDuration) {
		if (appliedStatusEffectDuration < -1) {
			appliedStatusEffectDuration = 100;
		}
		this.appliedStatusEffectDuration = appliedStatusEffectDuration;
	}

	public boolean getAppliedStatusEffectAmbient() {
		return appliedStatusEffectAmbient;
	}

	public void setAppliedStatusEffectAmbient(boolean appliedStatusEffectAmbient) {
		this.appliedStatusEffectAmbient = appliedStatusEffectAmbient;
	}

	public boolean getAppliedStatusEffectShowParticles() {
		return appliedStatusEffectShowParticles;
	}

	public void setAppliedStatusEffectShowParticles(boolean appliedStatusEffectShowParticles) {
		this.appliedStatusEffectShowParticles = appliedStatusEffectShowParticles;
	}

	public boolean getAppliedStatusEffectShowIcon() {
		return appliedStatusEffectShowIcon;
	}

	public void setAppliedStatusEffectShowIcon(boolean appliedStatusEffectShowIcon) {
		this.appliedStatusEffectShowIcon = appliedStatusEffectShowIcon;
	}

	public String getInteractionResultItemIdentifier() {
		return this.interactionResultItemIdentifier;
	}

	public void setInteractionResultItemIdentifier(String interactionResultItemIdentifier) {
		this.interactionResultItemIdentifier = interactionResultItemIdentifier;
	}

	public String getInteractionToolItemIdentifier() {
		return this.interactionToolItemIdentifier;
	}

	public void setInteractionToolItemIdentifier(String interactionToolItemIdentifier) {
		this.interactionToolItemIdentifier = interactionToolItemIdentifier;
	}

	public String getUsePreventingStatusEffectIdentifier() {
		return this.usePreventingStatusEffectIdentifier;
	}

	public void setUsePreventingStatusEffectIdentifier(String usePreventingStatusEffectIdentifier) {
		this.usePreventingStatusEffectIdentifier = usePreventingStatusEffectIdentifier;
	}

	public String getRequiredAdvancementIdentifier() {
		return this.requiredAdvancementIdentifier;
	}

	public void setRequiredAdvancementIdentifier(String requiredAdvancementIdentifier) {
		this.requiredAdvancementIdentifier = requiredAdvancementIdentifier;
	}

	public int getRecoveryTimer() {
		return this.recoveryTimer;
	}

	public void setRecoveryTimer(int recoveryTimer) {
		this.recoveryTimer = recoveryTimer;
	}

	public int getRecoveryTimerThreshold() {
		return this.recoveryTimerThreshold;
	}

	public void setRecoveryTimerThreshold(int recoveryTimerThreshold) {
		this.recoveryTimerThreshold = recoveryTimerThreshold;
	}

	public boolean getInfiniteUses() {
		return this.infiniteUses;
	}

	public void setInfiniteUses(boolean infiniteUses) {
		this.infiniteUses = infiniteUses;
	}
	//endregion --- getter & setter ---
}
