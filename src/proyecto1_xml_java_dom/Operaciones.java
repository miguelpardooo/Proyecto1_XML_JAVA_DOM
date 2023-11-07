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

    private ArrayList<Libro> catalogo;

    public Operaciones() {
    }
    

    public void cargarDatos(ArrayList<Libro> catalogo) {

        try {
            this.catalogo = catalogo;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public Libro crearLibro(ArrayList<Libro> catalogo) {

        cargarDatos(catalogo);
        Libro libro = null;

        if (catalogo != null) {

            Scanner teclado = new Scanner(System.in);
            String id = null, author = null, title = null, genre = null, price = null, publish_date = null, description = null;

            try {
                System.out.println("Introduce los datos del libro que desea insertar: ");

                //Comprobamos que el id no existe ya!
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

                //Comprobar que es un nombre valido y que no esta repetido
                do {
                    System.out.print("\tAutor: ");
                    author = teclado.nextLine();
                } while (!esNombre(author));

                //Agreagr titulo comprobar que no esta repetido
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

                //Comprobar que el genero no sea un numero
                do {
                    System.out.print("\tGénero: ");
                    genre = teclado.nextLine();
                } while (!esNombre(genre));

                //Comprobar que es un numero
                do {
                    System.out.print("\tPrecio: ");
                    price = teclado.nextLine();
                } while (!esNumero(price));

                //Comprobar que sea una fecha con un formato correcto
                do {
                    System.out.print("\tFecha de publicación: ");
                    publish_date = teclado.nextLine();
                } while (!ComprobarFormatoFecha(publish_date));

                //Agreagar una descripcion
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

    private boolean estaElID(String id) {
        boolean salida = false;

        for (Libro libro : catalogo) {
            if (libro.getId().equals(id)) {
                salida = true;
            }
        }
        return salida;
    }

    private boolean estaTitulo(String nombre) {
        boolean salida = false;

        for (Libro libro : catalogo) {
            if (libro.getTitle().equals(nombre)) {
                salida = true;
            }
        }
        return salida;
    }
    
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
