package my.first.crud_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadListPokeAtivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> pokemonList;
    private Map<String, DataSnapshot> pokemonSnapshotMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_list_poke_activity);

        listView = findViewById(R.id.listView);
        pokemonList = new ArrayList<>();
        pokemonSnapshotMap = new HashMap<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pokemonList);
        listView.setAdapter(adapter);

        // Leer la lista de Pokémon desde Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference pokemonRef = database.getReference("pokemons");

        pokemonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pokemonList.clear();
                pokemonSnapshotMap.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String pokemonId = snapshot.getKey();
                    String name = snapshot.child("name").getValue(String.class);
                    String type = snapshot.child("type").getValue(String.class);
                    int level = snapshot.child("level").getValue(Integer.class);

                    // Almacenar el snapshot para poder acceder al ID luego
                    pokemonSnapshotMap.put(pokemonId, snapshot);

                    pokemonList.add("Name: " + name + ", Type: " + type + ", Level: " + level);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("ReadListPokeActivity", "loadPokemon:onCancelled", databaseError.toException());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el ID del Pokémon seleccionado
                String selectedPokemonId = (String) pokemonSnapshotMap.keySet().toArray()[position];
                DataSnapshot snapshot = pokemonSnapshotMap.get(selectedPokemonId);

                String pokemonName = snapshot.child("name").getValue(String.class);
                String pokemonType = snapshot.child("type").getValue(String.class);
                int pokemonLevel = snapshot.child("level").getValue(Integer.class);

                // Lanzar la actividad de actualización
                Intent intent = new Intent(ReadListPokeAtivity.this, UpdatePokeActivity.class);
                intent.putExtra("pokemonId", selectedPokemonId);
                intent.putExtra("pokemonName", pokemonName);
                intent.putExtra("pokemonType", pokemonType);
                intent.putExtra("pokemonLevel", pokemonLevel);
                startActivity(intent);
            }
        });

    }

}