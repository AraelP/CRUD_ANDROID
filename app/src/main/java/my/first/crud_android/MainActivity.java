package my.first.crud_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar el botón para agregar un Pokémon
        Button btnAddPokemon = findViewById(R.id.btn_add_pokemon);
        btnAddPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCrudActivity.class);
                startActivity(intent);
            }
        });
        Button btnViewPokemon = findViewById(R.id.btn_view_pokemon);
        btnViewPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReadListPokeAtivity.class);
                startActivity(intent);
            }
        });
    }
}