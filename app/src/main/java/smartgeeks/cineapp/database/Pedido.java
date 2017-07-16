package smartgeeks.cineapp.database;

import com.orm.SugarRecord;

/**
 * Created by cesarlizcano on 24/04/17.
 */

public class Pedido extends SugarRecord {

    String Descripcion, urlImagen;
    int cantidadProducto,  idCompra, precioProducto, idProducto;

    public Pedido(){
    }

    public Pedido(String descripcion,  int cant, String imagen,int precio, int idCompra,  int idProd){
        this.Descripcion =descripcion;
        this.urlImagen = imagen;
        this.cantidadProducto = cant;
        this.idCompra = idCompra;
        this.precioProducto = precio;
        this.idProducto = idProd;
    }


    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}
