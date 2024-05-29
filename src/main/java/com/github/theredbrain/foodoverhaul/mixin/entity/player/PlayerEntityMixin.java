package com.github.theredbrain.foodoverhaul.mixin.entity.player;

import com.github.theredbrain.foodoverhaul.FoodOverhaul;
import com.github.theredbrain.foodoverhaul.effect.FoodStatusEffect;
import com.github.theredbrain.foodoverhaul.entity.player.DuckPlayerEntityMixin;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DuckPlayerEntityMixin {

    @Shadow
    public abstract void sendMessage(Text message, boolean overlay);

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createPlayerAttributes", at = @At("RETURN"))
    private static void foodoverhaul$createPlayerAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue()
                .add(FoodOverhaul.MAX_FOOD_EFFECTS, 3)
        ;
    }

//    // TODO 1.60.6
//    @Unique
//    public boolean foodoverhaul$canConsumeItem(ItemStack itemStack) {
//        FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
//        if (foodComponent != null) {
//            for (FoodComponent.StatusEffectEntry statusEffectEntry : foodComponent.effects()) {
//                if (getWorld().isClient) continue;
//                return foodoverhaul$tryEatAdventureFood(statusEffectEntry.effect());
//            }
//        }
//        return false;
//    }

    @Unique
    public boolean foodoverhaul$canConsumeItem(ItemStack itemStack) {
        if (itemStack.getItem().getFoodComponent() != null) {
            List<Pair<StatusEffectInstance, Float>> list = itemStack.getItem().getFoodComponent().getStatusEffects();
            for (Pair<StatusEffectInstance, Float> pair : list) {
                if (getWorld().isClient || pair.getFirst() == null) continue;
                return !(pair.getFirst().getEffectType() instanceof FoodStatusEffect) || foodoverhaul$tryEatOverhauledFood(pair.getFirst());
            }
        }
        return false;
    }

    @Unique
    public boolean foodoverhaul$tryEatOverhauledFood(StatusEffectInstance statusEffectInstance) {
        if (getStatusEffects().isEmpty()) {
            return true;
        } else {
            int currentEatenFoods = 0;
            List<StatusEffectInstance> currentEffects = getStatusEffects().stream().toList();
            for (StatusEffectInstance currentEffect : currentEffects) {
                if (currentEffect.getEffectType() == statusEffectInstance.getEffectType() && !statusEffectInstance.isDurationBelow(FoodOverhaul.serverConfig.food_effect_duration_threshold_to_allow_eating)) {
                    this.sendMessage(Text.translatable("hud.message.foodEatenAlready").append(Text.translatable(currentEffect.getTranslationKey())), true);
                    return false;
                } else if (currentEffect.getEffectType() instanceof FoodStatusEffect) {
                    currentEatenFoods++;
                }
            }
            boolean bl = currentEatenFoods < this.foodoverhaul$getMaxFoodEffects();
            if (!bl) {
                this.sendMessage(Text.translatable("hud.message.maxFoodEaten"), true);
            }
            return bl;
        }
    }

    @Override
    public float foodoverhaul$getMaxFoodEffects() {
        return (float) this.getAttributeValue(FoodOverhaul.MAX_FOOD_EFFECTS);
    }
}
