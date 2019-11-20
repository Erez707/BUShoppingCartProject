package com.example.bushoppingcartproject.StoreShelf;


import android.net.Uri;
import com.example.bushoppingcartproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;


public class Shelf {

    public boolean purchased;
    private StorageTask imageUploadTask;
    private Uri imageUri;

    public Shelf() {
        purchased = false;
    }

    public Item[] createStoreCatalog() {
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
        honey.setImage(R.drawable.honey);

        Item brownSugar = new Item();
        brownSugar.setName("Brown Sugar");
        brownSugar.setPrice(8.99);
        brownSugar.setImage(R.drawable.brown_sugar);

        Item whiteSugar = new Item();
        whiteSugar.setName("White Sugar");
        whiteSugar.setPrice(6.99);
        whiteSugar.setImage(R.drawable.white_sugar);

        Item cookies = new Item();
        cookies.setName("Cookies");
        cookies.setPrice(6.99);
        cookies.setImage(R.drawable.cookies);

        Item crackers = new Item();
        crackers.setName("Crackers");
        crackers.setPrice(4.99);
        crackers.setImage(R.drawable.crackers);

        Item milk = new Item();
        milk.setName("Milk");
        milk.setPrice(1.99);
        milk.setImage(R.drawable.milk);

        Item redApples = new Item();
        redApples.setName("Red Apples");
        redApples.setPrice(3.99);
        redApples.setImage(R.drawable.red_apples);

        Item greenApples = new Item();
        greenApples.setName("Green Apples");
        greenApples.setPrice(3.99);
        greenApples.setImage(R.drawable.green_apples);

        Item oranges = new Item();
        oranges.setName("Oranges");
        oranges.setPrice(3.99);
        oranges.setImage(R.drawable.oranges);

        Item kiwis = new Item();
        kiwis.setName("Kiwis");
        kiwis.setPrice(4.99);
        kiwis.setImage(R.drawable.kiwis);

        Item dragonFruit = new Item();
        dragonFruit.setName("Dragon Fruit");
        dragonFruit.setPrice(1.99);
        dragonFruit.setImage(R.drawable.dragon_fruit);

        Item watermelon = new Item();
        watermelon.setName("Watermelon");
        watermelon.setPrice(1.99);
        watermelon.setImage(R.drawable.watermelon);

        Item bananas = new Item();
        bananas.setName("Bananas");
        bananas.setPrice(6.99);
        bananas.setImage(R.drawable.bananas);

        Item mango = new Item();
        mango.setName("Mango");
        mango.setPrice(6.99);
        mango.setImage(R.drawable.mangos);

        Item lemons = new Item();
        lemons.setName("Lemons");
        lemons.setPrice(6.99);
        lemons.setImage(R.drawable.lemons);

        Item limes = new Item();
        limes.setName("Limes");
        limes.setPrice(3.99);
        limes.setImage(R.drawable.limes);

        Item guava = new Item();
        guava.setName("Guava");
        guava.setPrice(1.99);
        guava.setImage(R.drawable.guavas);

        Item grapefruit = new Item();
        grapefruit.setName("Grapefruit");
        grapefruit.setPrice(1.99);
        grapefruit.setImage(R.drawable.grapefruit);

        Item greenGrape = new Item();
        greenGrape.setName("Green Grapes");
        greenGrape.setPrice(1.99);
        greenGrape.setImage(R.drawable.green_grapes);

        Item redGrapes = new Item();
        redGrapes.setName("Red Grapes");
        redGrapes.setPrice(1.99);
        redGrapes.setImage(R.drawable.red_grapes);

        Item samuraiSword = new Item();
        samuraiSword.setName("Samurai Sword");
        samuraiSword.setPrice(6.99);
        samuraiSword.setImage(R.drawable.katana);


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
            SaveStoreCatalogToDatabase(itemsOnShelf[i]);
            SaveImageFilesToDatabase(itemsOnShelf[i]);
        }
        return itemsOnShelf;
    }

    // loop through store catalog and save items on shelf to the firebase database


    // Save item in the firebase database if it doesn't exist
    private void SaveStoreCatalogToDatabase(final Item itemOnShelf) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference shelfReference = database.getReference();

        shelfReference.child("Shelf").child(itemOnShelf.getName()).setValue(itemOnShelf);
    }


    private void SaveImageFilesToDatabase(final Item itemOnShelf) {

        imageUri = Uri.parse("android.resource://com.example.bushoppingcartproject.StoreShelf" + itemOnShelf.getImage());

        if (imageUri != null) {
            // Create a storage reference
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("images");

            // Create a reference to each item's image
            StorageReference itemImageRef = storageRef.child(itemOnShelf.getName());

//            // Reference to full image path in firebase storage
//            StorageReference itemImagePathRef = storageRef.child("images/" + itemOnShelf.getName());

            imageUploadTask = itemImageRef.putFile(imageUri);

        } else {
           System.out.println("NO IMAGE WAS LOADED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

    }
}
