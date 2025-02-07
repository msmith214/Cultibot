package com.mycompany.cultibot;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;

public class Cultibot {
    public static HashMap<Server, CultivateGame> games = new HashMap<>();
    public static Random rand = new Random();
    public static void main(String[] args) {        
        DiscordApi cult = new DiscordApiBuilder()
                .setToken("MY_DISCORD_SECRET")
                .setIntents(Intent.MESSAGE_CONTENT)
                .login()
                .join();
        
        SlashCommand helpCommand = SlashCommand.with("help", "Get information on how to play Cultivate.")
                    .createGlobal(cult)
                    .join();
        SlashCommand plantCommand = SlashCommand.with("plant", "Get started on a plant of your very own.")
                    .createGlobal(cult)
                    .join();
        SlashCommand checkCommand = SlashCommand.with("check", "Check your plant's progress.")
                    .createGlobal(cult)
                    .join();
        SlashCommand rummageCommand = SlashCommand.with("rummage", "Rummage for free items.")
                    .createGlobal(cult)
                    .join();
        SlashCommand shopCommand = SlashCommand.with("shop", "Shop for items using quartz shards.")
                    .createGlobal(cult)
                    .join();
        SlashCommand invCommand = SlashCommand.with("inv", "See what's in your inventory.")
                    .createGlobal(cult)
                    .join();
        
        cult.addSlashCommandCreateListener(lstnr -> {
            SlashCommandInteraction inter = lstnr.getSlashCommandInteraction();
            if (inter.getCommandName().equals("help")){
                lstnr.getSlashCommandInteraction().createImmediateResponder()
                    .setContent(
                    """
                    Welcome to the **Cultivate** help page.
                    
                    The following commands may be of use to you:
                    **/plant** This command creates a new plant that belongs to the user, which grows once an hour over the course of 24 hours. The objective of Cultivate is to see how tall you can grow your plant. The plant's growth can be affected by using items on your plant.
                    **/check** This command tells you how tall your plant is currently.
                    **/rummage** This command allows you to acquire free items and currency to spend.
                    **/shop** This command shows the shop UI.
                    **/inv** This command shows your inventory of items.
                    """                                
                    ).respond();
            }
            else {
                if (inter.getServer().isPresent()){
                    if (!games.containsKey(inter.getServer().get())){
                        games.put(inter.getServer().get(), new CultivateGame(inter.getServer().get()));
                    }
                    CultivateGame game = games.get(inter.getServer().get());
                    if (!game.players.containsKey(inter.getUser())){
                        game.players.put(inter.getUser(), new Player(inter.getUser()));
                    }
                    Player player = game.players.get(inter.getUser());
                    if (inter.getCommandName().equals("plant")){
                        lstnr.getSlashCommandInteraction().createImmediateResponder()
                        .setContent(player.plant())
                        .respond();
                    }
                    else if (inter.getCommandName().equals("check")){
                        lstnr.getSlashCommandInteraction().createImmediateResponder()
                        .setContent(player.check())
                        .respond();
                    }
                    else if (inter.getCommandName().equals("rummage")){
                        lstnr.getSlashCommandInteraction().createImmediateResponder()
                        .setContent(player.rummage())
                        .respond();
                    }
                    else if (inter.getCommandName().equals("shop")){
                        lstnr.getSlashCommandInteraction().respondLater().thenAccept(updtr -> {
                        inter.createFollowupMessageBuilder()
                                .addEmbed(games.get(lstnr.getSlashCommandInteraction().getServer().get()).shop.updateEmbed(player))
                                .addComponents(ActionRow.of(
                                                Button.secondary("shop:back:"+player.user.getIdAsString(), "◀" ), 
                                                Button.primary("shop:buy:"+player.user.getIdAsString(), "Buy"),
                                                Button.secondary("shop:forward:"+player.user.getIdAsString(), "▶")
                                )).send();
                        });  
                    }
                    else if (inter.getCommandName().equals("inv")){
                        lstnr.getSlashCommandInteraction().respondLater().thenAccept(updtr -> {
                        inter.createFollowupMessageBuilder()
                                .addEmbed(player.updateEmbed())
                                .addComponents(ActionRow.of(
                                                Button.secondary("inv:back:"+player.user.getIdAsString(), "◀" ), 
                                                Button.primary("inv:use:"+player.user.getIdAsString(), "Use"),
                                                Button.secondary("inv:forward:"+player.user.getIdAsString(), "▶")
                                )).send();
                        });
                    }
                }
            }
            System.out.println("["+LocalDateTime.now().toString()+"] "+lstnr.getSlashCommandInteraction().getUser().getName()+" has used slash command /"+lstnr.getSlashCommandInteraction().getFullCommandName()+" in server "+lstnr.getSlashCommandInteraction().getServer().get().getName());
        });
        
        cult.addMessageComponentCreateListener(buttonEvent -> {
            MessageComponentInteraction msgCmpntIntrctn = buttonEvent.getMessageComponentInteraction();
            String Id = msgCmpntIntrctn.getCustomId();
            Server server = msgCmpntIntrctn.getServer().get();
            TextChannel channel = msgCmpntIntrctn.getChannel().get();
            User clicker = msgCmpntIntrctn.getUser();
            CultivateGame game = games.get(server);
            if (Id.endsWith(clicker.getIdAsString())) {
                if (game.players.containsKey(clicker)){
                    if (Id.contains("interact")){
                        msgCmpntIntrctn.acknowledge();
                        msgCmpntIntrctn.getMessage().delete();
                        Plant plant = games.get(server).players.get(clicker).plant.get();
                        if (Id.contains("socialize")){
                            channel.sendMessage(CultivateGame.socializes[rand.nextInt(CultivateGame.socializes.length)]+"\n*Socializing with your plant causes it to grow into a flower over time.*");
                            plant.buds += 1.5;
                        }
                        else if (Id.contains("vent")){
                            channel.sendMessage(CultivateGame.vents[rand.nextInt(CultivateGame.vents.length)]+"\n*Venting to your plant causes it to grow into a gourd over time.*");
                            plant.stem += 1.5;
                        }
                        else if (Id.contains("encourage")){
                            channel.sendMessage(CultivateGame.encourages[rand.nextInt(CultivateGame.encourages.length)]+"\n*Encouraging your plant causes it to grow into a veggie over time.*");
                            plant.leaves += 1.5;
                        }
                        else if (Id.contains("compliment")){
                            channel.sendMessage(CultivateGame.compliments[rand.nextInt(CultivateGame.compliments.length)]+"\n*Complimenting your plant causes it to grow into a tuber over time.*");
                            plant.roots += 1.5;
                        }
                        else if (Id.contains("cancel")){
                            channel.sendMessage("You garden quietly today. Your plant misses your voice, but is happy to spend time with you nonetheless.");
                        }
                    }
                    else if (Id.startsWith("shop")){
                        msgCmpntIntrctn.acknowledge();
                        Player clickerPlayer = game.players.get(clicker);
                        Shop thisShop = game.shop;
                        if (Id.contains("back")){
                            if (thisShop.cursor > 0){
                                thisShop.cursor--;
                            }
                        }
                        else if (Id.contains("forward")){
                            if (thisShop.cursor < thisShop.wares.size()-1){
                                thisShop.cursor++;
                            }
                        }
                        else if (Id.contains("buy")){
                            Item item = (Item)thisShop.wares.keySet().toArray()[thisShop.cursor];
                            if (clickerPlayer.balance >= item.price) {
                                if (thisShop.wares.get(item) > 0) {
                                    clickerPlayer.balance -= item.price;
                                    if (clickerPlayer.inventory.containsKey(item)){
                                        clickerPlayer.inventory.put(item, clickerPlayer.inventory.get(item)+1);
                                    }
                                    else {
                                        clickerPlayer.inventory.put(item, 1);
                                    }
                                    int newStock = thisShop.wares.get(item)-1;
                                    thisShop.wares.put(item, newStock);                                    
                                    channel.sendMessage("Thank you for your purchase!");
                                }
                                else {
                                    channel.sendMessage("Sorry, we're fresh out of that!");
                                }
                            }
                            else {
                                channel.sendMessage("Sorry, but you can't afford that item right now!");
                            }
                        }
                        try {
                            List<Message> someMessages = channel.getMessages(20).get().stream().filter(m -> !m.getEmbeds().isEmpty()).collect(Collectors.toList());
                            Message target = someMessages.get(someMessages.size()-1);
                            target.edit(thisShop.updateEmbed(clickerPlayer));
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            game.shop.display(clickerPlayer).send(channel);
                        }
                    }
                    else if (Id.startsWith("inv")){
                        msgCmpntIntrctn.acknowledge();
                        Player clickerPlayer = game.players.get(clicker);
                        if (Id.contains("back")){
                            if (clickerPlayer.cursor > 0){
                                clickerPlayer.cursor--;
                            }
                        }
                        else if (Id.contains("forward")){
                            if (clickerPlayer.cursor < clickerPlayer.inventory.keySet().size()-1){
                                clickerPlayer.cursor++;
                            }
                        }
                        else if (Id.contains("use")){
                            msgCmpntIntrctn.getMessage().delete();
                            if (clickerPlayer.plant.isPresent()){
                                Item item = (Item)clickerPlayer.inventory.keySet().toArray()[clickerPlayer.cursor];
                                if (clickerPlayer.inventory.get(item) > 1){
                                    int newStock = clickerPlayer.inventory.get(item) - 1;
                                    clickerPlayer.inventory.put(item, newStock);
                                }
                                else {
                                    clickerPlayer.inventory.remove(item);
                                }
                                if (item.type == Type.BOON) {
                                    clickerPlayer.plant.get().growthRate += item.modifier;
                                }
                                else if (item.type == Type.BREEDBOON) {
                                    if ((item.name.startsWith("Flower Power") && clickerPlayer.plant.get().breed == Breed.FLOWER) || 
                                            (item.name.startsWith("Veggie Might") && clickerPlayer.plant.get().breed == Breed.VEGGIE) ||
                                            (item.name.startsWith("Oh My Gourd") && clickerPlayer.plant.get().breed == Breed.GOURD) ||
                                            (item.name.startsWith("Fronds in High Places") && clickerPlayer.plant.get().breed == Breed.MISC) ||
                                            (item.name.startsWith("Tuber or Not Tuber") && clickerPlayer.plant.get().breed == Breed.TUBER)){
                                        clickerPlayer.plant.get().growthRate += item.modifier;
                                    }
                                    else {
                                        clickerPlayer.plant.get().growthRate += 0.1;
                                    }
                                }
                                channel.sendMessage("You used **"+item.name+"** on your plant.");
                                clickerPlayer.getInteractions().send(channel);
                            }
                            else {
                                channel.sendMessage("You don't currently have a plant growing.");
                            }
                        }
                        try {
                            List<Message> someMessages = channel.getMessages(20).get().stream().filter(m -> !m.getEmbeds().isEmpty()).collect(Collectors.toList());
                            Message target = someMessages.get(someMessages.size()-1);
                            target.edit(clickerPlayer.updateEmbed());
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            clickerPlayer.displayInventory().send(channel);
                        }
                    }
                    else if (Id.startsWith("talk")){
                        msgCmpntIntrctn.getMessage().delete();
                        if (Id.contains("socialize")){
                            channel.sendMessage(CultivateGame.socializes[rand.nextInt(CultivateGame.socializes.length)]);
                        }
                        else if (Id.contains("vent")){
                            channel.sendMessage(CultivateGame.vents[rand.nextInt(CultivateGame.vents.length)]);
                        }
                        else if (Id.contains("encourage")){
                            channel.sendMessage(CultivateGame.encourages[rand.nextInt(CultivateGame.encourages.length)]);
                        }
                        else if (Id.contains("compliment")){
                            channel.sendMessage(CultivateGame.compliments[rand.nextInt(CultivateGame.compliments.length)]);
                        }
                    }
                }
            }
            else {
                channel.sendMessage("Hey! That button's not yours!");
            }
        });
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TickTask(), 0, 24*60*60*1000);
    }
}

class TickTask extends TimerTask{
    @Override
    public void run(){
        for (CultivateGame game : Cultibot.games.values()){
            game.shop.restock();
            for (Player player : game.players.values()){
                if (player.plant.isPresent()){
                    player.plant.get().grow();
                }
            }
        }
    }
}