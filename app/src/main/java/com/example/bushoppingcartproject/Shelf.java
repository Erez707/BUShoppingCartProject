package com.example.bushoppingcartproject;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Shelf {

    public boolean purchased;


    public Shelf() {
        purchased = false;
    }

    public Item[] createStoreCatalog(String username) {
        // Instantiate all Items on the Shelf
        // 23 total items total price = $106.77

        Item coffee = new Item();
        coffee.setName("Coffee");
        coffee.setPrice(5.99);
        coffee.setImage(R.drawable.coffee_bag);

        Item tea = new Item();
        tea.setName("Tea");
        tea.setPrice(6.99);
        tea.setImage(R.drawable.tea);

        Item honey = new Item();
        honey.setName("Honey");
        honey.setPrice(2.99);

        Item brownSugar = new Item();
        brownSugar.setName("Brown Sugar");
        brownSugar.setPrice(8.99);

        Item whiteSugar = new Item();
        whiteSugar.setName("White Sugar");
        whiteSugar.setPrice(6.99);

        Item cookies = new Item();
        cookies.setName("Cookies");
        cookies.setPrice(6.99);

        Item crackers = new Item();
        crackers.setName("Crackers");
        crackers.setPrice(4.99);

        Item milk = new Item();
        milk.setName("Milk");
        milk.setPrice(1.99);

        Item redApples = new Item();
        redApples.setName("Red Apples");
        redApples.setPrice(3.99);

        Item greenApples = new Item();
        greenApples.setName("Green Apples");
        greenApples.setPrice(3.99);

        Item oranges = new Item();
        oranges.setName("Oranges");
        oranges.setPrice(3.99);

        Item kiwis = new Item();
        kiwis.setName("Kiwis");
        kiwis.setPrice(4.99);

        Item dragonFruit = new Item();
        dragonFruit.setName("Dragon Fruit");
        dragonFruit.setPrice(1.99);

        Item watermelon = new Item();
        watermelon.setName("Watermelon");
        watermelon.setPrice(1.99);

        Item bananas = new Item();
        bananas.setName("Bananas");
        bananas.setPrice(6.99);

        Item mango = new Item();
        mango.setName("Mango");
        mango.setPrice(6.99);

        Item lemons = new Item();
        lemons.setName("Lemons");
        lemons.setPrice(6.99);

        Item limes = new Item();
        limes.setName("Limes");
        limes.setPrice(3.99);

        Item guava = new Item();
        guava.setName("Guava");
        guava.setPrice(1.99);

        Item grapefruit = new Item();
        grapefruit.setName("Grapefruit");
        grapefruit.setPrice(1.99);

        Item greenGrape = new Item();
        greenGrape.setName("Green Grapes");
        greenGrape.setPrice(1.99);

        Item redGrapes = new Item();
        redGrapes.setName("Red Grapes");
        redGrapes.setPrice(1.99);

        Item samuraiSword = new Item();
        samuraiSword.setName("Samurai Sword");
        samuraiSword.setPrice(6.99);


        // create an array of all items on the shelf

        Item[] itemsOnShelf = {
                coffee,
                tea,
                honey,
                brownSugar,
                whiteSugar,
                cookies,
                crackers,
                milk,
                redApples,
                greenApples,
                oranges,
                kiwis,
                dragonFruit,
                watermelon,
                bananas,
                mango,
                lemons,
                limes,
                guava,
                grapefruit,
                greenGrape,
                redGrapes,
                samuraiSword
        };


        for (int i = 0; i<itemsOnShelf.length; i++) {
            SaveStoreCatalogToDatabase(itemsOnShelf[i], username);
        }
        return itemsOnShelf;
    }

    // loop through store catalog and save items on shelf to the firebase database


    // Save item in the firebase database if it doesn't exist
    private void SaveStoreCatalogToDatabase(final Item itemOnShelf, String username) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference shelfReference = database.getReference();

        shelfReference.child("Users").child(username).child("Shelf").child(itemOnShelf.getName()).setValue(itemOnShelf);
    }
}
