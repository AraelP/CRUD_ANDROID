package my.first.crud_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import my.first.crud_android.database.DatabaseHelper;

public class AddCrudActivity extends AppCompatActivity {

    private EditText etPokemonName, etPokemonType, etPokemonLevel;
    private Button btnCreatePokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crud);

        // Vincular las vistas
        etPokemonName = findViewById(R.id.et_pokemon_name);
        etPokemonType = findViewById(R.id.et_pokemon_type);
        etPokemonLevel = findViewById(R.id.et_pokemon_level);
        btnCreatePokemon = findViewById(R.id.btn_create_pokemon);

        btnCreatePokemon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = etPokemonName.getText().toString();
                String type = etPokemonType.getText().toString();
                int level = Integer.parseInt(etPokemonLevel.getText().toString());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pokemonRef = database.getReference("pokemons");

                String pokemonId = pokemonRef.push().getKey();
                Pokemon pokemon = new Pokemon(name, type, level);

                if (pokemonId != null){
                    pokemonRef.child(pokemonId).setValue(pokemon);
                    Toast.makeText(AddCrudActivity.this, "Pokemon creado con exito", Toast.LENGTH_SHORT).show();
                    finish();
                } else{
                    Toast.makeText(AddCrudActivity.this, "Error al crear el Pokemon", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}