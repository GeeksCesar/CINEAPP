package smartgeeks.cineapp.Entidades;

/**
 * Created by cesarlizcano on 12/03/17.
 */

public class Categoria {

    String nombreCategoria, imagenfondo, idCategoria, estadoCategoria;

    public Categoria(String nombreCategoria, String imagenfondo, String idCategoria, String estadoCategoria) {
        this.nombreCategoria = nombreCategoria;
        this.imagenfondo = imagenfondo;
        this.idCategoria = idCategoria;
        this.estadoCategoria = estadoCategoria;
    }


    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getImagenfondo() {
        return imagenfondo;
    }

    public void setImagenfondo(String imagenfondo) {
        this.imagenfondo = imagenfondo;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getEstadoCategoria() {
        return estadoCategoria;
    }

    public void setEstadoCategoria(String estadoCategoria) {
        this.estadoCategoria = estadoCategoria;
    }
}
