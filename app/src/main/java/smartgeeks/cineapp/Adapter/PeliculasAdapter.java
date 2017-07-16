package smartgeeks.cineapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import smartgeeks.cineapp.Entidades.Peliculas;
import smartgeeks.cineapp.R;

/**
 * Created by cesarlizcano on 27/03/17.
 */

public class PeliculasAdapter extends RecyclerView.Adapter<PeliculasAdapter.peliculasViewHolder>{

    List<Peliculas> listPeliculas;
    Context context;

    public PeliculasAdapter(List<Peliculas> listPeliculas, Context context) {
        this.listPeliculas = listPeliculas;
        this.context = context;
    }

    @Override
    public peliculasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewrvpelicula, parent, false);

        peliculasViewHolder peliculasViewHolder = new peliculasViewHolder(v);
        return peliculasViewHolder;

    }

    @Override
    public void onBindViewHolder(peliculasViewHolder holder, int position) {
        Glide.with(context).load(listPeliculas.get(position).getImageURl()).into(holder.imgPelicula);
        holder.tvTitlePelicula.setText(listPeliculas.get(position).getNombrePelicula());
        holder.tvYearPelicula.setText(listPeliculas.get(position).getYearPelicula());
        holder.tvPrecio.setText(String.valueOf(listPeliculas.get(position).getPrecio()));
    }

    @Override
    public int getItemCount() {
       return listPeliculas.size();
    }


    public static class peliculasViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPelicula;
        TextView tvTitlePelicula, tvYearPelicula, tvPrecio;

        public peliculasViewHolder(View itemView) {
            super(itemView);
            imgPelicula= (ImageView) itemView.findViewById(R.id.ivPelicula);
            tvTitlePelicula= (TextView) itemView.findViewById(R.id.tvTitlePelicula);
            tvYearPelicula= (TextView) itemView.findViewById(R.id.tvYearPelicula);
            tvPrecio= (TextView) itemView.findViewById(R.id.tvPrecioPelicula);
        }
    }
}
