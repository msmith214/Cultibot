package com.mycompany.cultibot;

import java.util.HashMap;
import java.util.Random;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class CultivateGame {
    public CultivateGame(Server server){
        this.server = server;
    }
    public Server server;
    public HashMap<User, Player> players = new HashMap<>();
    public Random rand = new Random();
    public Shop shop = new Shop();
    static String[] socializes = {
        "You tell the plant about your friend, Sheldon the snail. He’s a good guy, but he’s been looking a bit sluggish recently.",
        "You tell the plant about Marvin the mantis. He’s a very pious insect.",
        "You tell the plant about Eugene the very old centipede. In centipede years, he's a centenarian!",
        "You tell the plant about Larry the lightning bug. He's always lightning-fast with the quips!",
        "You tell the plant about Sally the squirrel. She's a little nutty.",
        "You tell the plant about Debra the deer. She's been fawning over you lately.",
        "You tell the plant about Oliver the oak tree. He's thinking of branching out in his career path.",
        "You tell the plant about Barry & Jennifer the blue jays. They're saving up a nest egg together.",
        "You tell the plant about Caroline the cat. She's been really feline herself lately."
    };
    static String[] vents = {
        "You talk to the plant about your process of self-acceptance. “I yam who I yam.”",
        "You talk to the plant about your fears surrounding the future. “Sometimes, you just gotta take a leaf of faith.”",
        "You complain about the lack of activity in the days ahead. “I haven’t any plants for this weekend.”",
        "You tell the plant about how at times you feel overwhelmed but are keeping a level head. “I’ve guac this!”",
        "You talk to the plant about how you feel your recent choices have been misguided. \"Maybe I'm barking up the wrong tree.\"",
        "You talk to the plant about Frank the firefly. \"He can be a real hothead!\"",
        "You talk to the plant about your recent attempts to learn more about your culture. \"I feel like I'm getting back to my roots.\"",
        "You talk to the plant about your tendency to prevaricate. \"Sometimes I beat around the bush.\"",
        "You talk to the plant about how you feel undervalued sometimes. \"They just don't know how to seperate the wheat from the chaff!\"",
        "You talk to the plant about a silly semantic argument you had earlier. \"A rose by any other name is just as sweet.\""
    };
    static String[] encourages = {
        "You tell the plant to know its own value. “Don’t settle for medi-okra!”",
        "You reassure the plant that it contains multitudes. “You’re more than a one trick peony!”",
        "You reassure the plant that you’ll stay by its side. “I’ll never leaf you.”",
        "You cheer on the plant. “Come on, put the petal to the metal!”",
        "You tell the plant to shake off its doubts and uncertainties. “Good chives only!\"",
        "You tell the plant to remember there's strength in numbers. \"You can't break a stick in a bundle!\"",
        "You tell the plant it will surely succeed, just like its predecessors. \"The apple doesn't fall far from the tree!\"",
        "You tell the plant to try harder in spite of its failures. \"If at first you don't suc__seed__...\"",
        "You tell the plant about some of your own struggles. \"Be-leaf me, I know how that feels.\"",
        "You tell the plant its okay to take your time with things. \"Some people are just late bloomers.\""
    };
    static String[] compliments = {
        "You cheer on the plant. “You grow girl!”",
        "You compliment the plant’s complexion. “Why, you look absolutely rosy!”",
        "You show your affection for the plant. “Aloe you vera much.”",
        "You praise the plant on its focus and determination. “You don’t moss around!”",
        "You tell the plant how much you like it. \"I'm very frond of you.\"",
        "You tell the plant you feel like you have a special connection. \"We're mint to be.\"",
        "You tell the plant it's special to you. \"Out of all the flowers around here, you're the one I'd pick.\"",
        "You tell the plant you think it's neat. \"You're peachy-keen!\"",
        "You tell the plant you like how it looks. \"You're the apple of my eye.\"",
        "You tell the plant you make a great team. \"We're some pear!\""
    };
    public void tick(){
        for (Player player : players.values()){
            if (player.plant.isPresent()){
                player.plant.get().grow();
            }
        }
    }
}
