package com.mycompany.cultibot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Random;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class Shop {
    public Shop(){
        for (int i = 0; i < 5; i++){
            Item newItem = Item.items[rand.nextInt(Item.items.length)];
            while (wares.containsKey(newItem)){
                newItem = Item.items[rand.nextInt(Item.items.length)];
            }
            wares.put(newItem, rand.nextInt(3)+3);
        }
    }
    public HashMap<Item, Integer> wares = new HashMap<>();
    public int cursor = 0;
    public Random rand = new Random();
    public static String[] shopkeeperIntros = {
        "Marisol the ferret is taking her shift at the store this morning. “Buenos días! Beautiful morning we’re having, yeah?”",
        "The cow behind the counter greets you warmly. “Good afternoon, sweetheart! What can Mama Moo help you with today?”",
        "With a vulpine grin, Silas the fox welcomes you in to the store. “Happy Evening, two-leg. How can I assist you tonight?”",
        "Kavi the horned owl turns to greet you. “Hmm, you’re up rather late for one meant to be a daywalker. No matter, that is what I am here for. What is it you are seeking?\"",
        "An old billy goat greets you gruffly from behind the counter. “Rise and shine, laddie– much growing to be done today! Old Duncan has some special deals to help you get along.”",
        "Charlotte the chameleon emerges from behind the counter, her skin a friendly blue. \"Salutations, warm-blood. What can I help you find today?\"",
        "You approach the shop to find Elliot the goldfish swimming lazily within a glass bowl behind the counter. He looks at you, clearly bored. “*Glub* ***you*** *glub* ***deals*** *glub glub* ***tonight.***”",
        "A bat waves at you from behind the counter energetically. “I’m Taffeta, but you can call me Taffy! I was getting worried I wouldn’t see anyone tonight. I’ll give you a nice discount for keeping me company!”",
        "A figure you can't quite parse stands behind the counter, their body a haze of numbers and fractals. \"Hey,\" they say, \"I'm Glitchard. If I'm here then you entered the shop at EXACTLY noon, midnight, 6 am or 6 pm. This is pretty rare, to be honest. Congrats! What can I get you today?\""
    };
    public MessageBuilder display(Player player){
        return new MessageBuilder().addEmbed(updateEmbed(player)).addComponents(ActionRow.of(
                                                Button.secondary("shop:back:"+player.user.getIdAsString(), "◀" ), 
                                                Button.primary("shop:buy:"+player.user.getIdAsString(), "Buy"),
                                                Button.secondary("shop:forward:"+player.user.getIdAsString(), "▶")
                                        ));
    }
    public String getShopkeeperIntro(){
        String output = "*Ding-a-ling-a-ling!* The shop's bell chimes as you walk through the door. ";
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.MONDAY || LocalDate.now().getDayOfWeek() == DayOfWeek.TUESDAY || LocalDate.now().getDayOfWeek() == DayOfWeek.WEDNESDAY || LocalDate.now().getDayOfWeek() == DayOfWeek.THURSDAY) {
            if (LocalTime.now().isAfter(LocalTime.parse("06:00:00")) && LocalTime.now().isBefore(LocalTime.NOON)){
                output += shopkeeperIntros[0];
            }
            else if (LocalTime.now().isAfter(LocalTime.NOON) && LocalTime.now().isBefore(LocalTime.parse("18:00:00"))){
                output += shopkeeperIntros[1];
            }
            else if (LocalTime.now().isAfter(LocalTime.parse("18:00:00")) && LocalTime.now().isBefore(LocalTime.parse("23:59:59"))){
                output += shopkeeperIntros[2];
            }
            else if (LocalTime.now().isAfter(LocalTime.MIDNIGHT) && LocalTime.now().isBefore(LocalTime.parse("06:00:00"))){
                output += shopkeeperIntros[3];
            }
            else {
                output += shopkeeperIntros[8];
            }
        }
        else {
            if (LocalTime.now().isAfter(LocalTime.parse("06:00:00")) && LocalTime.now().isBefore(LocalTime.NOON)){
                output += shopkeeperIntros[4];
            }
            else if (LocalTime.now().isAfter(LocalTime.NOON) && LocalTime.now().isBefore(LocalTime.parse("18:00:00"))){
                output += shopkeeperIntros[5];
            }
            else if (LocalTime.now().isAfter(LocalTime.parse("18:00:00")) && LocalTime.now().isBefore(LocalTime.parse("23:59:59"))){
                output += shopkeeperIntros[6];
            }
            else if (LocalTime.now().isAfter(LocalTime.MIDNIGHT) && LocalTime.now().isBefore(LocalTime.parse("06:00:00"))){
                output += shopkeeperIntros[7];
            }
            else {
                output += shopkeeperIntros[8];
            }
        }
        return output;
    }
    public EmbedBuilder updateEmbed(Player player) {
        String waresDisplay = "";
        for (int i = 0; i < wares.size(); i++){
            Item item = (Item)wares.keySet().toArray()[i];
            if (cursor == i) {
                waresDisplay = waresDisplay.concat("**▹");
            }
            waresDisplay += item.name+" ("+item.price+"q, "+wares.get(item)+" in stock)";
            if (cursor == i) {
                waresDisplay = waresDisplay.concat("**");
            }
            waresDisplay = waresDisplay.concat("\n");
        }
        EmbedBuilder shopUI = new EmbedBuilder()
                .setTitle("Mom & Poplar Gardening Supplies")
                .setDescription(getShopkeeperIntro())
                .addField("Your balance:", player.balance+" quartz shards")
                .addField("__Wares__", waresDisplay)
                .addField("__Item description__", ((Item)wares.keySet().toArray()[cursor]).desc);
        return shopUI;
    }
    public void restock(){
        wares.clear();
        for (int i = 0; i < 5; i++){
            Item newItem = Item.items[rand.nextInt(Item.items.length)];
            while (wares.containsKey(newItem)){
                newItem = Item.items[rand.nextInt(Item.items.length)];
            }
            wares.put(newItem, rand.nextInt(3)+3);
        }
    }
}
