package com.github.theredbrain.foodoverhaul.mixin.client.network;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.foodoverhaul.gui.screen.ingame.FoodBlockScreen;
import com.github.theredbrain.foodoverhaul.gui.screen.ingame.FoodDisplayBlockScreen;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements DuckPlayerEntityMixin {

	@Shadow
	@Final
	protected MinecraftClient client;

	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}

	@Override
	public void foodoverhaul$openFoodBlockScreen(FoodBlockEntity foodBlockEntity) {
		this.client.setScreen(new FoodBlockScreen(foodBlockEntity));
	}

	@Override
	public void foodoverhaul$openFoodDisplayBlockScreen(FoodDisplayBlockEntity foodDisplayBlockEntity) {
		this.client.setScreen(new FoodDisplayBlockScreen(foodDisplayBlockEntity));
	}

}
