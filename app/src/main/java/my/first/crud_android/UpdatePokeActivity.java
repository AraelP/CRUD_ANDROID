package my.first.crud_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePokeActivity extends AppCompatActivity {

    private EditText etPokemonName, etPokemonType, etPokemonLevel;
    private Button btnUpdatePokemon;
    private String pokemonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poke);

        etPokemonName = findViewById(R.id.et_pokemon_name);
        etPokemonType = findViewById(R.id.et_pokemon_type);
        etPokemonLevel = findViewById(R.id.et_pokemon_level);
        btnUpdatePokemon = findViewById(R.id.btn_update_pokemon);

        // Obtener los datos del Intent
        pokemonId = getIntent().getStringExtra("pokemonId");
        String pokemonName = getIntent().getStringExtra("pokemonName");
        String pokemonType = getIntent().getStringExtra("pokemonType");
        int pokemonLevel = getIntent().getIntExtra("pokemonLevel", 0);

        // Rellenar los campos con los datos actuales del Pokémon
        etPokemonName.setText(pokemonName);
        etPokemonType.setText(pokemonType);
        etPokemonLevel.setText(String.valueOf(pokemonLevel));

        btnUpdatePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = etPokemonName.getText().toString();
                String updatedType = etPokemonType.getText().toString();
                int updatedLevel = Integer.parseInt(etPokemonLevel.getText().toString());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pokemonRef = database.getReference("pokemons").child(pokemonId);

                pokemonRef.child("name").setValue(updatedName);
                pokemonRef.child("type").setValue(updatedType);
                pokemonRef.child("level").setValue(updatedLevel);

                Toast.makeText(UpdatePokeActivity.this, "Pokémon actualizado con éxito", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}