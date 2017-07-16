package smartgeeks.cineapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import smartgeeks.cineapp.R;
import smartgeeks.cineapp.database.Pedido;

/**
 * Created by cesarlizcano on 24/04/17.
 */

public class PeliculasenCarrito extends RecyclerView.Adapter<PeliculasenCarrito.carritoViewHolder>{

    List<Pedido> listPedido;
    Context context;
    String URl, precioPel;

    public PeliculasenCarrito() {
    }

    public PeliculasenCarrito(List<Pedido> listPedido , Context context){
        this.listPedido = listPedido;
        this.context = context;
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    @Override
    public carritoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcarrito, parent,false);

        carritoViewHolder carrito = new carritoViewHolder(view);

        return carrito;
    }

    @Override
    public void onBindViewHolder(carritoViewHolder holder, int position) {

        URl = listPedido.get(position).getUrlImagen();
        precioPel = ""+listPedido.get(position).getPrecioProducto();
        Log.i("PrecioPP",precioPel);

        //SET DATA
        Glide.with(context).load(URl).into(holder.ivFotoId);
        holder.tvNombrePeliculaId.setText(listPedido.get(position).getDescripcion());
        holder.tvCantidadPeliculaId.setText(String.valueOf(listPedido.get(position).getCantidadProducto()));
        holder.tvPrecioPeliculaId.setText(String.valueOf(listPedido.get(position).getPrecioProducto()));

        //anamation
        setFadeAnimation(holder.itemView);

    }

    @Override
    public int getItemCount() {
        if (listPedido == null){
            return 0;
        }

        return listPedido.size();
    }


    //VIEW HOLDER
    public class carritoViewHolder extends RecyclerView.ViewHolder{

        ImageView ivFotoId;
        TextView tvNombrePeliculaId, tvCantidadPeliculaId, tvPrecioPeliculaId;

        public carritoViewHolder(View itemView) {
            super(itemView);

            ivFotoId = (ImageView)itemView.findViewById(R.id.ivFotoId);
            tvNombrePeliculaId = (TextView)itemView.findViewById(R.id.tvNombreProductoId);
            tvCantidadPeliculaId = (TextView)itemView.findViewById(R.id.tvCantidadProductoId);
            tvPrecioPeliculaId = (TextView)itemView.findViewById(R.id.tvPrecioProductoId);

        }
    }

    public void removeItem(int position){
        listPedido.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listPedido.size());
    }

}
