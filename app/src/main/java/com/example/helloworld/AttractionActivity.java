package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
public class AttractionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AttractionAdapter attractionAdapter;
    private DatabaseHandler db;
    private EditText searchEditText;
    private ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attractions);

        Button backButton = findViewById(R.id.backBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start to go back to MainActivity
                Intent intent = new Intent(AttractionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        db = new DatabaseHandler(this);
        initializeDatabaseWithAttractions();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        attractionAdapter = new AttractionAdapter();
        recyclerView.setAdapter(attractionAdapter);

        // Load food category items automatically when the page loads
        List<Attraction> foodItems = getItemsForCategory(R.id.foodBtn);
        attractionAdapter.setItems(foodItems);

        searchEditText = findViewById(R.id.searchAttractionsText);
        searchButton = findViewById(R.id.searchAttractionsBtn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchEditText.getText().toString();
                performSearch(query);
            }
        });
    }

    private void performSearch(String query) {
        // Perform search in the database and get results
        List<Attraction> searchResults = db.searchAttractionsByTitle(query);

        // Update the RecyclerView with the search results
        attractionAdapter.setItems(searchResults);
    }

    public void onCategoryBtnClick(View view) {
        List<Attraction> cardItemList = getItemsForCategory(view.getId());
        attractionAdapter.setItems(cardItemList);
    }

    private void initializeDatabaseWithAttractions() {
        //db.clearAllData();
        if (db.getAttractionsCount() == 0) {
            // Add initial attractions
            db.addAttraction(new Attraction("Food", R.drawable.harajuku_gyoza, "Harajuku Gyoza Lou", "Famous for its delicious gyoza in a casual setting.", 4.7, "Harajuku, Tokyo", 3.2, "http://harajukugyozalou.jp"));
            db.addAttraction(new Attraction("Food", R.drawable.sushiro, "Sushiro", "Popular conveyor belt sushi restaurant offering a variety of dishes.", 4.8, "Shibuya, Tokyo", 2.5, "http://sushiro.jp"));
            db.addAttraction(new Attraction("Food", R.drawable.nisshin, "Nisshin Tasuke", "Renowned for its traditional unagi (eel) dishes.", 4.6, "Asakusa, Tokyo", 4.0, "http://nisshintasuke.jp"));
            db.addAttraction(new Attraction("Food", R.drawable.ramen, "Oreryu Shio Ramen", "Specializes in shio (salt) ramen, known for its rich flavor.", 4.9, "Shinjuku, Tokyo", 1.8, "http://oreryushioramen.jp"));
            db.addAttraction(new Attraction("Entertainment", R.drawable.disneyland, "DisneyLand", "The classic Disney theme park experience with a Japanese twist.", 4.7, "Urayasu, Tokyo", 5.5, "http://tokyodisneyland.jp"));
            db.addAttraction(new Attraction("Entertainment", R.drawable.disneysea, "DisneySea", "A unique Disney park themed around nautical explorations and adventures.", 4.8, "Urayasu, Tokyo", 5.3, "http://tokyodisneysea.jp"));
            db.addAttraction(new Attraction("Nature", R.drawable.euno_park, "Ueno Park", "A large public park known for its museums, zoo, and beautiful cherry blossoms.", 4.5, "Ueno, Tokyo", 2.1, "http://uenopark.jp"));
            db.addAttraction(new Attraction("Nature", R.drawable.shinjuku_gyoen, "Shinjuku Gyoen National Garden", "A spacious park blending traditional Japanese, English, and French garden styles.", 4.6, "Shinjuku, Tokyo", 3.7, "http://shinjukugyoen.jp"));
            db.addAttraction(new Attraction("Nature", R.drawable.yoyogi, "Yoyogi Park", "Known for its wide-open spaces, popular for jogging and picnics.", 4.8, "Shibuya, Tokyo", 2.9, "http://yoyogipark.jp"));
            db.addAttraction(new Attraction("Sports", R.drawable.volleyball, "Beach Volleyball at Odaiba Beach", "Odaiba Beach is a popular spot for beach volleyball and other activities.", 4.4, "Odaiba, Tokyo", 6.2, "http://odaibabeachvolleyball.jp"));
            db.addAttraction(new Attraction("Hotel", R.drawable.hotel, "Park Hyatt Tokyo", "Luxurious hotel with stunning city views, famous from 'Lost in Translation'.", 4.9, "Shinjuku, Tokyo", 3.4, "http://parkhyatttokyo.jp"));
            db.addAttraction(new Attraction("Culture", R.drawable.imperial, "The Imperial Palace", "The primary residence of the Emperor of Japan, surrounded by beautiful gardens.", 4.7, "Chiyoda, Tokyo", 1.5, "http://imperialpalacetokyo.jp"));
            db.addAttraction(new Attraction("Culture", R.drawable.museum, "Tokyo National Museum", "Houses the largest collection of Japanese art, located in Ueno Park.", 4.6, "Ueno, Tokyo", 2.6, "http://tokyonationalmuseum.jp"));
        }
    }


    private List<Attraction> getItemsForCategory(int categoryId) {
        List<Attraction> items = new ArrayList<>();

        String category = getCategoryForId(categoryId);
        items = db.getAttractionsByCategory(category);

        return items;
    }

    private String getCategoryForId(int categoryId) {
        switch (categoryId) {
            case R.id.foodBtn:
                return "Food";
            case R.id.entertainBtn:
                return "Entertainment";
            case R.id.natureBtn:
                return "Nature";
            case R.id.sportsBtn:
                return "Sports";
            case R.id.hotelBtn:
                return "Hotel";
            case R.id.cultureBtn:
                return "Culture";
            default:
                return "All";
        }
    }


}
