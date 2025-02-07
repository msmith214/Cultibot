package com.mycompany.cultibot;

enum Type {
    BOON,
    BREEDBOON
}

public class Item {
    public Item(String name, Type type, double modifier, int price, String desc){
        this.name = name;
        this.type = type;
        this.modifier = modifier;
        this.price = price;
        this.desc = desc;
    }
    public String name;
    public Type type;
    public double modifier;
    public int price;
    public String desc;
    public static Item[] items = {
        new Item("Non-GMO Water", Type.BOON, 0.2, 5, "Use this item to increase your plant's growth rate slightly."), //0
        new Item("Sunshine in a Bag", Type.BOON, 0.2, 5, "Use this item to increase your plant's growth rate slightly."),
        new Item("Free-Range Water", Type.BOON, 0.2, 5, "Use this item to increase your plant's growth rate slightly."),
        new Item("Vegetarian Water", Type.BOON, 0.3, 10, "Use this item to increase your plant's growth rate moderately."),
        new Item("Liquid Sunshine", Type.BOON, 0.3, 10, "Use this item to increase your plant's growth rate moderately."),
        new Item("Broken Wind", Type.BOON, 0.3, 10, "Use this item to increase your plant's growth rate moderately."), //5
        new Item("Uncle Ray's Sun Tablets", Type.BOON, 0.4, 15, "Use this item to increase your plant's growth rate significantly."),
        new Item("\"Water You Waiting For?\" Brand DiHydrogen Monoxide", Type.BOON, 0.4, 15, "Use this item to increase your plant's growth rate significantly."),
        new Item("Ethically Sourced Breeze", Type.BOON, 0.4, 15, "Use this item to increase your plant's growth rate significantly."),
        new Item("Flower Power Fertilizer", Type.BREEDBOON, 0.5, 20, "Use this item to increase your plant's growth rate moderately, or remarkably if it's a flower."),
        new Item("Veggie Might Fertilizer", Type.BREEDBOON, 0.5, 20, "Use this item to increase your plant's growth rate moderately, or remarkably if it's a veggie."), //10
        new Item("Oh My Gourd Fertilizer", Type.BREEDBOON, 0.5, 20, "Use this item to increase your plant's growth rate moderately, or remarkably if it's a gourd."),
        new Item("Fronds in High Places Fertilizer", Type.BREEDBOON, 0.5, 25, "Use this item to increase your plant's growth rate moderately, or remarkably if it's not a flower, veggie, gourd, or tuber."),
        new Item("Tuber or Not Tuber Fertilizer", Type.BREEDBOON, 0.5, 20, "Use this item to increase your plant's growth rate moderately, or remarkably if it's a tuber.")
    };
}
