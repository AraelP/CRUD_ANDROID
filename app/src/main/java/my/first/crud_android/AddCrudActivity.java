package my.first.crud_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import my.first.crud_android.database.DatabaseHelper;

public class AddCrudActivity extends AppCompatActivity {

    private EditText etPokemonName, etPokemonType, etPokemonLevel;
    private Button btnCreatePokemon;
    private DatabaseHelper dbHelper; // Asume que ya tienes una clase para manejar la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crud);

        // Vincular las vistas
        etPokemonName = findViewById(R.id.et_pokemon_name);
        etPokemonType = findViewById(R.id.et_pokemon_type);
        etPokemonLevel = findViewById(R.id.et_pokemon_level);
        btnCreatePokemon = findViewById(R.id.btn_create_pokemon);

        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(this);

        // Configurar el listener del botón
        btnCreatePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPokemon();
            }
        });
    }

    private void createPokemon() {
        // Obtener los valores de los campos de texto
        String name = etPokemonName.getText().toString().trim();
        String type = etPokemonType.getText().toString().trim();
        int level = Integer.parseInt(etPokemonLevel.getText().toString().trim());

        // Validar que no estén vacíos
        if (name.isEmpty() || type.isEmpty() || level <= 0) {
            Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insertar el Pokémon en la base de datos
        long id = dbHelper.insertPokemon(name, type, level);
        if (id > 0) {
            Toast.makeText(this, "Pokémon creado con éxito", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad y vuelve a la anterior
        } else {
            Toast.makeText(this, "Error al crear el Pokémon", Toast.LENGTH_SHORT).show();
        }
    }
}