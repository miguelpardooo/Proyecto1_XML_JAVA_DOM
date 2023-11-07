package proyecto1_xml_java_dom;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 3rWaZzZa
 */
public class Operaciones {

    private ArrayList<Libro> catalogo; //ARRAYLISTR PARA GUARDAR LOS DATOS

    public Operaciones() { //CONSTRUCTOR VACIO PARA PODER ACCEDER A LOS METODOS
    }
    
    
    //METODO PARA CARGAR LOS DATOS EN EL ARRAYLIST
    public void cargarDatos(ArrayList<Libro> catalogo) {

        try {
            this.catalogo = catalogo;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //METODO PARA CREAR UN LIBRO
    public Libro crearLibro(ArrayList<Libro> catalogo) {

        cargarDatos(catalogo);
        Libro libro = null;

        if (catalogo != null) {

            Scanner teclado = new Scanner(System.in);
            String id = null, author = null, title = null, genre = null, price = null, publish_date = null, description = null;

            try {
                System.out.println("Introduce los datos del libro que desea insertar: ");

                //COMPROBAMOS QU EL ID NO EXISTE YA
                boolean salidaBucle = false;
                do {
                    System.out.print("\tID: ");
                    id = teclado.nextLine();
                    if (!estaElID(id)) {
                        salidaBucle = true;
                    } else {
                        System.out.println("ID Repetido!");
                        System.out.println("Ingresa un ID Válido! Gracias!");
                    }

                } while (!salidaBucle);

                //COMPROBAR QUE ES UN NOMBRE VALIDO Y QUE NO ESTA REPETIDO
                do {
                    System.out.print("\tAutor: ");
                    author = teclado.nextLine();
                } while (!esNombre(author));

                //AGREAGR TITULO COMPROBAR QUE NO ESTA REPETIDO
                salidaBucle = false;
                do {
                    System.out.print("\tTítulo: ");
                    title = teclado.nextLine();
                    if (!estaTitulo(title)) {
                        salidaBucle= true;
                    }else{
                        System.out.println("Titulo Duplicado!");
                    }
                } while (!salidaBucle);

                //COMPROBAR QUE EL GENERO NO SEA UN NUMERO
                do {
                    System.out.print("\tGénero: ");
                    genre = teclado.nextLine();
                } while (!esNombre(genre));

                //COMPROBAR QUE ES UN NUMERO
                do {
                    System.out.print("\tPrecio: ");
                    price = teclado.nextLine();
                } while (!esNumero(price));

                //COMPROBAR EL FORMATO DE LA FECHA
                do {
                    System.out.print("\tFecha de publicación: ");
                    publish_date = teclado.nextLine();
                } while (!ComprobarFormatoFecha(publish_date));

                //AGREGAR UNA DESCRIPCION
                System.out.print("\tDescripción: ");
                description = teclado.nextLine();

                libro = new Libro(id, author, title, genre, price, publish_date, description);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No se Han Cargado los Datos de los Libros!");
        }

        return libro;
    }

    //METODO PARA COMPROBAR SI ES UN NOMBRE
    private boolean esNombre(String nombre) {
        boolean salida = false;

        Pattern pattern = Pattern.compile("^[a-zA-Z,\\s]+$");
        Matcher matcher = pattern.matcher(nombre);
        if (matcher.matches()) {
            salida = true;
        } else {
            System.out.println("Ingresa un Nombre valido!! Gracias");
        }
        return salida;
    }

    //COMPROBAR SI ES UN NUMERO
    private boolean esNumero(String numero) {
        boolean salida = false;
        try {
            Double.parseDouble(numero);
            salida = true;
        } catch (NumberFormatException e) {
            System.out.println("Ingresa un Numero Valido! Gracias!");
        }
        return salida;
    }

    //COMPROBAR EL FORMATO DE LA FECHA DE MANERA SIMPLE
    private boolean ComprobarFormatoFecha(String fecha) {
        boolean salida = false;
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        Matcher matcher = pattern.matcher(fecha);
        if (matcher.matches()) {
            salida = true;
        } else {
            System.out.println("Ingresa una Fecha con Formato Válido (año-mes-día)! Gracias!");
        }
        return salida;
    }

    //COMPROBAR SI EL ID ESTA
    private boolean estaElID(String id) {
        boolean salida = false;

        for (Libro libro : catalogo) {
            if (libro.getId().equals(id)) {
                salida = true;
            }
        }
        return salida;
    }

    //COMPROBAMOS SI EL TITULO ESTA YA PARA NO VOLVER A GUARDARLO DENTRO DE LA PROPIA CLASE
    private boolean estaTitulo(String nombre) {
        boolean salida = false;

        for (Libro libro : catalogo) {
            if (libro.getTitle().equals(nombre)) {
                salida = true;
            }
        }
        return salida;
    }
    
    //METODO DE COMPROBAR TITULO SOBRECARGADO PARA USARLO FUERA D ELA CLASE
        public boolean estaTitulo(String nombre, ArrayList<Libro> catalogo) {
        boolean salida = false;

        for (Libro libro : catalogo) {
            if (libro.getTitle().equals(nombre)) {
                salida = true;
            }
        }
        return salida;
    }

}
