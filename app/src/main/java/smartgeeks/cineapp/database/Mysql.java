package smartgeeks.cineapp.database;

/**
 * Created by cesarlizcano on 26/03/17.
 */

public class Mysql {

    private static final String IP = "http://smartgeeks.com.co/cineapp";
    private static final String PROJECT = "Admin/";
    //private static final String IP = "http://192.168.1.5/mispeliculas";
    //private static final String PROJECT = "Admin/";

    private static final String WEB_SITE = IP ;

    //QUERY
    public static final String CONSULTA_GENERO = IP+"/"+PROJECT+ "consultarGenero.php";
    public static final String CONSULTA_PELICULA = IP +"/"+PROJECT+ "consultarPelicula.php?idgenero=" ;
    public static final String CONSULTA_PELICULA_LIKE = IP + "/" + PROJECT+ "ConsultarLikePelicula.php?nombre=";
    public static final String REGISTRAR_USUARIO = IP + "/" + PROJECT+ "registrarUsuario.php?" ;
    public static final String LOGIN = IP +"/"+ PROJECT + "Login.php?" ;
    public static final String PEDIDO = IP +"/" + PROJECT + "enviarPedido.php?";
    public static final String EDITAR_USUARIO = IP + "/"+ PROJECT + "editarDatos.php?id=";
 }
