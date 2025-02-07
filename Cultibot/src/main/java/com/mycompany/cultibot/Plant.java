package com.mycompany.cultibot;

import java.util.Optional;
import java.util.Random;

enum Breed {
    MISC,
    FLOWER,
    VEGGIE,
    GOURD,
    TUBER
}

public class Plant {
    public Plant(Player owner){
        this.owner = owner;
    }
    public Player owner;
    public Breed breed = Breed.MISC;
    public int ticks = 0;
    public double growthRate = 1.25;
    public double height = 1.00;
    public double buds = 1.00;
    public double leaves = 1.00;
    public double stem = 1.00;
    public double roots = 1.00;
    public double happiness = 1.00;
    public Random rand = new Random();
    public void grow(){
        if (ticks < 24) {
            double growth = (1.00 + (((double)rand.nextInt(21) - 10) / 100)) * growthRate;
            height += growth;
            height = (double) Math.round(height * 100) / 100;
            growthRate = 1.25;
            ticks++;
            double total = buds + leaves + stem + roots;
            if (buds > total/2) {
                breed = Breed.FLOWER;
            }
            else if (leaves > total/2) {
                breed = Breed.VEGGIE;
            }
            else if (stem > total/2) {
                breed = Breed.GOURD;
            }
            else if (roots > total/2) {
                breed = Breed.TUBER;
            }
            else {
                breed = Breed.MISC;
            }
        }
        else {
            owner.user.sendMessage("Your Cultivate plant has expired after 24 hours. Its final height was "+String.valueOf(height)+" inches. Thanks for playing! :green_heart:");
            owner.plant = Optional.empty();
        }
    }
}
