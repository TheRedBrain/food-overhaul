# 3.1.0

## Additions

- added creative mode tab for Food Overhaul

## Changes

- food blocks can now apply a list of effects

## Fixes

- fixed data component registration

# 3.0.0

- updated to 26.1.x

> The base value for the "foodoverhaul:maximum_food_effects" entity attribute was changed. Existing players in updated worlds should reset the attribute using the /attribute reset command.

## Additions

- added "natural_maximum_food_effects" server config option
- added Food Display Block, can hold up to 4 items which can be consumed by interacting with the block. The usual checks for existing food effects and hunger apply.
  - This block is mainly designed for adventure maps and servers, so it can't be obtained in survival gameplay. Feedback and suggestions are very welcome, as always!
  - has a config screen for creative mode players

## Changes

- reworked the Food Block
  - allows for more configuration
  - has a config screen for creative mode players

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