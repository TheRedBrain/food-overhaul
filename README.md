# Food Overhaul

Eating food no longer fills the hunger bar or gives saturation. Each food item now grants a corresponding status effect instead. 

These effects affect the player in various ways, like increasing health, stamina or mana.
More exotic food increases attributes like frost resistance.

Most of these attributes are provided by the mods [Health Regeneration Overhaul](https://modrinth.com/mod/health-regeneration-overhaul), [Mana Attributes](https://modrinth.com/mod/mana-attributes), [Overhauled Damage](https://modrinth.com/mod/overhauled-damage) and [Stamina Attributes](https://modrinth.com/mod/stamina-attributes).

## What prevents a player from eating every available food?

Each player can only have a limited amount of food status effects active at a time. It's not possible to eat food when that limit is reached.
The limit is controlled by an entity attribute called **_generic.max_food_effects_** and is 3 by default.

This gives the player the options to eat the best food for the given situation, which in my opinion is much more interesting than eating golden carrots all day.

When a food effect is running out, it's corresponding food item can be consumed again. The exact threshold after which this is possible is set in the server config.

When a player wants to reset their currently active food effects, they can eat a piece of rotten flesh. This will remove every food effect. 