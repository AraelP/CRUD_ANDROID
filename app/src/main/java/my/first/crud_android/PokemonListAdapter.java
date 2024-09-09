package my.first.crud_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

public class PokemonListAdapter extends ArrayAdapter<Pokemon>{
    public PokemonListAdapter(@NonNull Context context, @NonNull List<Pokemon> pokemons) {
        super(context, 0, pokemons);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokemon_list_item, parent, false);
        }

        // Obtener el Pokémon actual
        Pokemon pokemon = getItem(position);

        // Referenciar las vistas del layout
        TextView tvPokemonName = convertView.findViewById(R.id.tv_pokemon_name);
        TextView tvPokemonType = convertView.findViewById(R.id.tv_pokemon_type);
        ProgressBar pbPokemonLevel = convertView.findViewById(R.id.tv_pokemon_level);

        // Asignar datos a las vistas
        tvPokemonName.setText(pokemon.getName());
        tvPokemonType.setText(pokemon.getType());
        pbPokemonLevel.setProgress(pokemon.getLevel());


        return convertView;
    }
}
