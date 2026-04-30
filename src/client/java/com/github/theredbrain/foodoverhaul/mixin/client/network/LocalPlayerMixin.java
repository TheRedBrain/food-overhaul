package com.github.theredbrain.foodoverhaul.mixin.client.network;

import com.github.theredbrain.foodoverhaul.block.entity.FoodBlockEntity;
import com.github.theredbrain.foodoverhaul.block.entity.FoodDisplayBlockEntity;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerMixin;
import com.github.theredbrain.foodoverhaul.gui.screen.ingame.FoodBlockScreen;
import com.github.theredbrain.foodoverhaul.gui.screen.ingame.FoodDisplayBlockScreen;
import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer implements DuckPlayerMixin {

	@Shadow
	@Final
	protected Minecraft minecraft;

	public LocalPlayerMixin(ClientLevel world, GameProfile profile) {
		super(world, profile);
	}

	@Override
	public void foodoverhaul$openFoodBlockScreen(FoodBlockEntity foodBlockEntity) {
		this.minecraft.setScreen(new FoodBlockScreen(foodBlockEntity));
	}

	@Override
	public void foodoverhaul$openFoodDisplayBlockScreen(FoodDisplayBlockEntity foodDisplayBlockEntity) {
		this.minecraft.setScreen(new FoodDisplayBlockScreen(foodDisplayBlockEntity));
	}

}
