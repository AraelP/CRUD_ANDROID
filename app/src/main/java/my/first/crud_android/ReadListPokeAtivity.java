package my.first.crud_android;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class ReadListPokeAtivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_list_poke_ativity);

        listView = findViewById(R.id.listView);
        pokemonList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pokemonList);
        listView.setAdapter(adapter);

        // Leer la lista de Pokémon desde Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference pokemonRef = database.getReference("pokemons");

        pokemonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pokemonList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String type = snapshot.child("type").getValue(String.class);
                    int level = snapshot.child("level").getValue(Integer.class);

                    pokemonList.add("Name: " + name + ", Type: " + type + ", Level: " + level);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("PokemonListActivity", "loadPokemon:onCancelled", databaseError.toException());
            }
        });
    }
}