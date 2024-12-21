# Food Overhaul

This is an overhaul of the food system. Eating food no longer fills the hunger bar or gives saturation. Each food item now grants a corresponding status effect instead. 
These effects can grant attribute modifiers like increased health, more stamina or resistance against frost damage.

This gives players a real choice, when they decide what combination of food is the best for a given situation, which in my opinion is much more interesting than eating golden carrots all day.

> Food Overhaul adds the back end system for this new food system, but it does not add gameplay content on its own.

This ensures that mod pack authors have the most possible control over how exactly their food items are balanced.

## How does it work?

Food items in Minecraft can apply status effects. Food Overhaul uses that functionality and simply checks if one of those status effects is a "food effect".
If an item grants such an effect, the food can only be eaten when the player does not already have that food effect.

> A status effect is considered a food effect when their effect type extends [FoodStatusEffect](https://github.com/TheRedBrain/food-overhaul/blob/1.21.1/src/main/java/com/github/theredbrain/foodoverhaul/effect/FoodStatusEffect.java).

## What prevents a player from eating every available food?

Each player can only have a limited amount of food status effects active at a time. It's not possible to eat food when that limit is reached.
The limit is controlled by an entity attribute called **_generic.max_food_effects_** and is 3 by default.

The player doesn't have to wait until an effect is completely gone to refresh it.
When a food effect is running out, it's corresponding food item can be consumed again. The exact threshold after which this is possible is set in the server config.

There is also a way to remove all active food effects. Food Overhaul implements a [RemoveFoodStatusEffect](https://github.com/TheRedBrain/food-overhaul/blob/1.21.1/src/main/java/com/github/theredbrain/foodoverhaul/effect/RemoveFoodStatusEffect.java) effect typ. When effects of this type are applied, every FoodEffect and the RemoveFoodEffect are removed.

## Customization

Food effects can be added to any item (even modded) by vanilla methods (mainly commands) or by using third party mods.

https://modrinth.com/mod/item-components and https://modrinth.com/mod/default-components both allow setting the default components of items via data packs. 

This gives mod pack authors / players the option to use their own food effects.

## Just want to play?

[Food Overhaul - Vanilla Foods](https://github.com/TheRedBrain/food-overhaul-vanilla-foods) adds food effects to vanilla items.

## Hunger and saturation

Food Overhaul does not remove these systems. They can be used in combination with the food effects.

By default, food items without a food effect can be eaten like normal. This can be disabled in the server config.

> If the food system should be completely gone from your mod pack, maybe take a look at my mod [Health Regeneration Overhaul](https://modrinth.com/mod/health-regeneration-overhaul), which can completely disable vanillas hunger system including saturation and exhaustion.