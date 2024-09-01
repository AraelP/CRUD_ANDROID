package my.first.crud_android;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePokeActivity extends AppCompatActivity {

    private EditText etPokemonName, etPokemonType, etPokemonLevel;
    private Button btnUpdatePokemon, btnDeletePokemon;;
    private String pokemonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poke);

        etPokemonName = findViewById(R.id.et_pokemon_name);
        etPokemonType = findViewById(R.id.et_pokemon_type);
        etPokemonLevel = findViewById(R.id.et_pokemon_level);
        btnUpdatePokemon = findViewById(R.id.btn_update_pokemon);
        btnDeletePokemon = findViewById(R.id.btn_delete_pokemon);

        // Obtener los datos pasados desde la actividad anterior
        pokemonId = getIntent().getStringExtra("pokemonId");
        etPokemonName.setText(getIntent().getStringExtra("pokemonName"));
        etPokemonType.setText(getIntent().getStringExtra("pokemonType"));
        etPokemonLevel.setText(String.valueOf(getIntent().getIntExtra("pokemonLevel", 1)));

        // Configurar el botón de eliminar
        btnDeletePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar un diálogo de confirmación
                new AlertDialog.Builder(UpdatePokeActivity.this)
                    .setTitle("Eliminar Pokémon")
                    .setMessage("¿Estás seguro de que quieres eliminar este Pokémon?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Eliminar el Pokémon de Firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference pokemonRef = database.getReference("pokemons").child(pokemonId);

                            pokemonRef.removeValue().addOnSuccessListener(aVoid -> {
                                   Toast.makeText(UpdatePokeActivity.this, "Pokémon eliminado", Toast.LENGTH_SHORT).show();
                                   finish(); // Cerrar la actividad y regresar
                                })
                               .addOnFailureListener(e -> Toast.makeText(UpdatePokeActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show());
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            }
        });
    }
}