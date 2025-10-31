package com.github.theredbrain.foodoverhaul.render.block.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class FoodDisplayBlockEntityRendererState extends BlockEntityRenderState {
	public List<ItemRenderState> displayedItemStates = Collections.emptyList();
	public int[] displayedItemRotations = new int[4];

	public FoodDisplayBlockEntityRendererState() {
	}
}
