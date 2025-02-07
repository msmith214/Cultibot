package com.mycompany.cultibot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

public class Player {
    public Player(User user){
        this.user = user;
    }
    public User user;
    public Optional<Plant> plant = Optional.empty();
    public int balance = 0;
    public boolean notifyMe = false;
    public HashMap<Item, Integer> inventory = new HashMap<>();
    public Random rand = new Random();
    public int cursor = 0;
    public String plant(){
        if (plant.isEmpty()) {    
            plant = Optional.of(new Plant(this));
            return "Seed planted. I wonder how big it'll grow...";
        }
        else {
            return "You've already got a plant growing!";
        }
    }
    public String check(){
        if (plant.isPresent()){
            String output = "Your plant is "+plant.get().height+" inches tall and has been alive for "+plant.get().ticks+" hours.";
            String type = plant.get().breed.name().toLowerCase();
            if (!type.equals("misc")){
                output += "\nIt's growing into a lovely "+type+".";
            }
            return output;
        }
        else {
            return "You don't have a plant growing right now. Plant one with \"/plant\".";
        }
    }
    public String rummage(){
        String output = "You rummage around in the dirt. You find **";
        int chance = rand.nextInt(100);
        if (chance > 49) {
            Item item = Item.items[0];
            if (chance > 96){
                item = Item.items[13]; //tubert fert
            }
            else if (chance > 94){ 
                item = Item.items[12]; //misc fert
            }
            else if (chance > 91){
                item = Item.items[11]; //gourd fert
            }
            else if (chance > 88){
                item = Item.items[10]; //veggie fert
            }
            else if (chance > 85){
                item = Item.items[9]; //flower fert
            }
            else if (chance > 81){
                item = Item.items[8]; //breeze
            }
            else if (chance >77){
                item = Item.items[7]; //water you waiting for
            }
            else if (chance > 73){
                item = Item.items[6]; //sun tablets
            }
            else if (chance > 69){
                item = Item.items[5]; //broken wind
            }
            else if (chance > 65){
                item = Item.items[4]; //liquid sunshine
            }
            else if (chance > 61){ 
                item = Item.items[3]; //vegetarian water
            }
            else if (chance > 57){
                item = Item.items[2]; //free-range water
            }
            else if (chance > 53){
                item = Item.items[1]; //sunshine in a bag
            }
            //if chance is 53 or less, defaults to currently assigned value- Item.items[0].
            if (inventory.containsKey(item)){
                inventory.put(item, inventory.get(item)+1);
            }
            else {
                inventory.put(item, 1);
            }
            output = output.concat(item.name+"**! "+item.desc+"\n*You can use items from your inventory (\"/inv\").*");
        }
        else {
            if (chance > 2){
                int shards = rand.nextInt(7)+2;
                balance += shards;
                output = output.concat(shards+" quartz shards**! Your balance is now "+balance+"q.\n*Spend this currency at the shop with \"/shop\".*");
            }
            else {
                int shards = rand.nextInt(6)+15;
                balance += shards;
                output = output.concat(shards+" quartz shards**! What a haul! Your balance is now "+balance+"q\n*Spend this currency at the shop with \"/shop\".*");
            }
        }
        return output;
    }
    public MessageBuilder getInteractions(){
        MessageBuilder output = new MessageBuilder();
        output.setContent("You've heard talking to your plants can help guide their growth down different paths. How do you want to talk to your plant while you're working with it?")
            .addComponents(ActionRow.of(
                Button.success("talk:socialize:"+user.getIdAsString(), "Socialize"),
                Button.success("talk:vent:"+user.getIdAsString(), "Vent"),
                Button.success("talk:encourage:"+user.getIdAsString(), "Encourage"),
                Button.success("talk:compliment:"+user.getIdAsString(), "Compliment"),
                Button.secondary("talk:cancel:"+user.getIdAsString(), "Nothing for now")));
        return output;
    }
    public MessageBuilder displayInventory(){
        MessageBuilder output = new MessageBuilder();
        if (!inventory.isEmpty()) {
            output.addComponents(ActionRow.of(
                    Button.secondary("inv:back:"+user.getIdAsString(), "◀" ), 
                    Button.primary("inv:use:"+user.getIdAsString(), "Use"),
                    Button.secondary("inv:forward:"+user.getIdAsString(), "▶")
                ));
            output.addEmbed(updateEmbed());
        }
        else {
            output.setContent("No items to display.");
        }
        return output;
    }
    public EmbedBuilder updateEmbed(){
        String inventoryDisplay = "";
        for (int i = 0; i < inventory.size(); i++){
            Item item = (Item)inventory.keySet().toArray()[i];
            if (cursor == i) {
                inventoryDisplay = inventoryDisplay.concat("**▹");
            }
            inventoryDisplay += item.name+" (x"+Integer.toString(inventory.get(item))+")";
            if (cursor == i) {
                inventoryDisplay = inventoryDisplay.concat("**");
            }
            inventoryDisplay = inventoryDisplay.concat("\n");
        }
        EmbedBuilder embed = new EmbedBuilder();
        if (!inventory.isEmpty()){
            embed.setTitle("Your Inventory")
                        .addField("Your quartz shard balance:", balance+"q")
                        .addField("__Contents__", inventoryDisplay)
                        .addField("__Item description__", ((Item)inventory.keySet().toArray()[cursor]).desc);
        }
        else {
            embed.setTitle("Your Inventory")
                        .addField("Your quartz shard balance:", balance+"q")
                        .addField("__Contents__", "No items to display.");
        }
        return embed;
    }
}
