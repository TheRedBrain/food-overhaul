package com.github.theredbrain.foodoverhaul.mixin.entity.player;

import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerMixin;
import com.github.theredbrain.foodoverhaul.entity.player.PlayerHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements DuckPlayerMixin {

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void foodoverhaul$tick(CallbackInfo ci) {
		PlayerHelper.updateNaturalAttributeModifiers((Player) (Object) this);
	}

}
