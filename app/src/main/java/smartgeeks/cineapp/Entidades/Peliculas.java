package smartgeeks.cineapp.Entidades;

/**
 * Created by cesarlizcano on 06/03/17.
 */

public class Peliculas {

    int idPelicula;
    String NombrePelicula ;
    String YearPelicula;
    int GeneroPelicula;
    String Sinpnosis;
    String Trailer;
    String ImageURl;
    int Precio;
    String EstadoPelicula;


    public Peliculas(int idPelicula, String Nombre, String Year, int genero, String Sipnosis,
                     String Trailer, String ImageURl, int precio, String Estado) {

        this.idPelicula = idPelicula;
        this.NombrePelicula = Nombre;
        this.YearPelicula = Year;
        this.GeneroPelicula = genero;
        this.Sinpnosis = Sipnosis;
        this.Trailer = Trailer;
        this.ImageURl = ImageURl;
        this.Precio= precio;
        this.EstadoPelicula = Estado;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getNombrePelicula() {
        return NombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        NombrePelicula = nombrePelicula;
    }

    public String getYearPelicula() {
        return YearPelicula;
    }

    public void setYearPelicula(String yearPelicula) {
        YearPelicula = yearPelicula;
    }

    public int getGeneroPelicula() {
        return GeneroPelicula;
    }

    public void setGeneroPelicula(int generoPelicula) {
        GeneroPelicula = generoPelicula;
    }

    public String getSinpnosis() {
        return Sinpnosis;
    }

    public void setSinpnosis(String sinpnosis) {
        Sinpnosis = sinpnosis;
    }

    public String getTrailer() {
        return Trailer;
    }

    public void setTrailer(String trailer) {
        Trailer = trailer;
    }

    public String getImageURl() {
        return ImageURl;
    }

    public void setImageURl(String imageURl) {
        ImageURl = imageURl;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public String getEstadoPelicula() {
        return EstadoPelicula;
    }

    public void setEstadoPelicula(String estadoPelicula) {
        EstadoPelicula = estadoPelicula;
    }
}
