package smartgeeks.cineapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import smartgeeks.cineapp.Entidades.Categoria;
import smartgeeks.cineapp.R;

/**
 * Created by cesarlizcano on 06/03/17.
 */

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriasHolder>{

    List<Categoria> listaCategoria;
    Context contexto;

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    public CategoriaAdapter(Context contexto, ArrayList<Categoria> categoriaList) {
        this.listaCategoria = categoriaList;
        this.contexto = contexto ;
    }

    public CategoriaAdapter(List<Categoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    @Override
    public CategoriasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistarvcategorias, parent,false);
        CategoriasHolder categoriasHolder = new CategoriasHolder(v);

        return categoriasHolder;
    }

    @Override
    public void onBindViewHolder(final CategoriasHolder holder, int position) {

        String URL = listaCategoria.get(position).getImagenfondo();

        Glide.with(contexto).load(listaCategoria.get(position).getImagenfondo()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
               holder.progressBar.setVisibility(View.GONE);

                return false;
            }
        }).into(holder.ivCategorias);
        holder.tvNombreCategoria.setText(listaCategoria.get(position).getNombreCategoria());
        setFadeAnimation(holder.itemView);

    }

    @Override
    public int getItemCount() {
       return  listaCategoria.size();
    }

    public static class CategoriasHolder  extends RecyclerView.ViewHolder {

        RelativeLayout rlCategotias;
        TextView tvNombreCategoria;
        ImageView ivCategorias;
        ProgressBar progressBar;


        public CategoriasHolder(View itemView) {
            super(itemView);
            ivCategorias = (ImageView)itemView.findViewById(R.id.ivCategorias);
            rlCategotias = (RelativeLayout)itemView.findViewById(R.id.rlCategorias);
            tvNombreCategoria = (TextView)itemView.findViewById(R.id.tvTituloCategoria);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progresscate);

        }
    }
}

