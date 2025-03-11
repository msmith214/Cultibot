<h2>About</h2>
Cultibot is an application for the social media platform Discord. It facilitates gameplay for the game Cultivate, an activity in the spirit of Tamogatchi or Neko Atsume where the player plants a seedling, guides its growth, and is challenged to see how tall the seedling can grow over a 24-hour period. Gameplay is accessed by slash commands, an integral part of Discord's API wherein users can interact with applications by typing commands such as "/help" directly into Discord's chat UI.

<h2>Commands</h2>
The following commands may be of use to you:</br>
<b>/plant</b> This command creates a new plant that belongs to the user, which grows once an hour over the course of 24 hours. The objective of Cultivate is to see how tall you can grow your plant. The plant's growth can be affected by using items on your plant. </br>
<b>/check</b> This command tells you how tall your plant is currently.</br>
<b>/rummage</b> This command allows you to acquire free items and currency to spend.</br>
<b>/shop</b> This command shows the shop UI.</br>
<b>/inv</b> This command shows your inventory of items.</br>

<h2>Mechanics</h2>
Plants "grow" once every "tick" (which happens at the top of every hour) for 24 hours. Plants normally grow by 0.9 to 1.1 inches every hour multiplied by a growth rate (base x1.25), but this growth rate can be increased by items purchased from the shop or obtained using the /rummage command (which sometimes yields quartz shards, the shop's currency, instead of items.)
Plants also belong to one of five types: Flower, Gourd, Veggie, Tuber, or Miscellaneous. It starts as a Miscellaneous plant and develops into a Flower, Gourd, Veggie, or Tuber if you interact with it predominantly by socializing, venting, encouraging, or complimenting it respectively.
Shopkeepers also rotate based on time (morning/afternoon vs evening/night) and day (weekday vs weekend), though this is purely for flavor.

<h2>Shop Items</h2>
The following items can appear in the shop:</br>

|Name|Cost|Effect|
|---|---|---|
|Non-GMO Water|5 quartz|Increases your plant's growth rate by 0.2 for one tick.|
|Sunshine in a Bag|5 quartz|Increases your plant's growth rate by 0.2 for one tick.|
|Free-Range Water|5 quartz|Increases your plant's growth rate by 0.2 for one tick.|
|Vegetarian Water|10 quartz|Increases your plant's growth rate by 0.3 for one tick.|
|Liquid Sunshine|10 quartz|Increases your plant's growth rate by 0.3 for one tick.|
|Broken Wind|10 quartz|Increases your plant's growth rate by 0.3 for one tick.|
|Uncle Ray's Sun Tablets|15 quartz|Increases your plant's growth rate by 0.4 for one tick.|
|"Water You Waiting For?" Brand Dihydrogen Monoxide|15 quartz|Increases your plant's growth rate by 0.4 for one tick.|
|Ethically Sourced Breeze|15 quartz|Increases your plant's growth rate by 0.4 for one tick.|
|Flower Power Fertilizer|20 quartz|Increases your plant's growth rate by 0.1 for one tick, unless it's a Flower, in which case it increases your plant's growth rate by 0.5 for one tick.|
|Veggie Might Fertilizer|20 quartz|Increases your plant's growth rate by 0.1 for one tick, unless it's a Veggie, in which case it increases your plant's growth rate by 0.5 for one tick.|
|Oh My Gourd Fertilizer|20 quartz|Increases your plant's growth rate by 0.1 for one tick, unless it's a Gourd, in which case it increases your plant's growth rate by 0.5 for one tick.|
|Fronds in High Places Fertilizer|25 quartz|Increases your plant's growth rate by 0.5 for one tick, unless it's a Flower, Veggie, Gourd, or Tuber, in which case it increases your plant's growth rate by 0.1 for one tick.|
|Tuber or Not Tuber Fertilizer|20 quartz|Increases your plant's growth rate by 0.1 for one tick, unless it's a Tuber, in which case it increases your plant's growth rate by 0.5 for one tick.|
