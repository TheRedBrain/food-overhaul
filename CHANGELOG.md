# 3.0.0

- updated to 1.21.10

## Additions

- added "foodoverhaul:remove_effects_in_tag" consume effect, clears all status effects in a given effects tag when item is consumed
- added "natural_maximum_food_effects" server config option

## Changes

- removed FoodStatusEffect and RemoveFoodStatusEffect classes, all functionality is now implemented using components and tags

# 2.3.0

- reworked the checks for existing food effects, they now work with the new status effect tag "foodoverhaul:food_effects". This means that every status effect can become a food effect
- added a simple API for food blocks (similar to vanillas cake)
- added support for potions
- fixed config getting initialized too early

# 2.2.2

- fixed RemoveFoodStatusEffect, again

# 2.2.1

- fixed RemoveFoodStatusEffect
- removed "foodoverhaul:remove_food_effects_effect" status effect, a replacement is implemented in Food Overhaul - Vanilla Foods
- fixed dependencies

# 2.2.0

- split food effects into standalone mod
- removed dependency on cloth config
- added dependency on fzzy config

# 2.1.0

- update to 1.21.1

# 2.0.0

- update to 1.21
- eating a food item now applies a configurable cooldown to that item
- fixed config option "food_effect_duration_threshold_to_allow_eating" not having an effect

# 1.0.0

First release!

#