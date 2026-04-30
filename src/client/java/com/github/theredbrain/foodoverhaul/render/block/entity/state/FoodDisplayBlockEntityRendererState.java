package com.github.theredbrain.foodoverhaul.render.block.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class FoodDisplayBlockEntityRendererState extends BlockEntityRenderState {
	public List<ItemStackRenderState> displayedItemStates = Collections.emptyList();
	public boolean singleItemMode = false;
	public int[] displayedItemRotations = new int[4];

	public FoodDisplayBlockEntityRendererState() {
	}
}
