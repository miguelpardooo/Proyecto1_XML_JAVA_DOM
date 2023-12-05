package proyecto1_xml_java_dom;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//clase para manejar las operaciones que se hara con los datos del dom
public class Operaciones {

    private ArrayList<Libro> catalogo;//arraylist con todos los datos de dom

    public Operaciones() {//constructor vacio para acceder a los metodos de la clase
    }

    //metodo para cargar los datos en el arraylist
    public void cargarDatos(ArrayList<Libro> catalogo) {
        try {
            this.catalogo = catalogo;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //metodo para crear un libro
    public Libro crearLibro(ArrayList<Libro> catalogo) {

        cargarDatos(catalogo);//carga los datos en el arraylist
        Libro libro = null;//creamos un objeto libro nulo

        if (catalogo != null) {//mientras el catalogo no sea nulo
            Scanner teclado = new Scanner(System.in);//creamos el scanner
            String id = null, author = null, title = null, genre = null, price = null, publish_date = null, description = null;//variables con todos los datos

            try {
                System.out.println("Introduce los datos del libro que desea insertar: ");

                boolean salidaBucle = false;//variable para salir del bucle cuando el id no exista

                //comprobamos si el id existe
                do {
                    System.out.print("\tID: ");
                    id = teclado.nextLine();
                    if (!estaElID(id)) {//comprobar si el id esta
                        salidaBucle = true;
                    } else {
                        System.out.println("ID Repetido!");
                        System.out.println("Ingresa un ID Válido! Gracias!");
                    }

                } while (!salidaBucle);//sale del bucle si el id no esta

                //comprobamos que sea un nombre valido
                do {
                    System.out.print("\tAutor: ");
                    author = teclado.nextLine();
                } while (!esNombre(author));

                salidaBucle = false;//variable para la salida del bucle de comprobar titulo

                //comprobamos si el titulo existe ya, y si ya esta no lo introduce
                do {
                    System.out.print("\tTítulo: ");
                    title = teclado.nextLine();
                    if (!estaTitulo(title)) {
                        salidaBucle = true;
                    } else {
                        System.out.println("Titulo Duplicado!");
                    }
                } while (!salidaBucle);

                //comprobamos que el genero sea un nombre
                do {
                    System.out.print("\tGénero: ");
                    genre = teclado.nextLine();
                } while (!esNombre(genre));

                //comprobar que sea un numero el precio
                do {
                    System.out.print("\tPrecio: ");
                    price = teclado.nextLine();
                } while (!esNumero(price));

                //comprobar que el formato de la fecha sea un numero valido
                do {
                    System.out.print("\tFecha de publicación: ");
                    publish_date = teclado.nextLine();
                } while (!ComprobarFormatoFecha(publish_date));

                //agregar una descripcion al libro
                System.out.print("\tDescripción: ");
                description = teclado.nextLine();

                //al libro que creamos mas arriba como nulo le asignamos los datos metidos por teclado
                libro = new Libro(id, author, title, genre, price, publish_date, description);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No se Han Cargado los Datos de los Libros!");
        }

        return libro;//devolvemos el libro con los datos
    }

    //metodo para comprobar si es un nombre
    private boolean esNombre(String nombre) {
        boolean salida = false;

        Pattern pattern = Pattern.compile("^[a-zA-Z,\\s]+$");//patron para comprobar que es un nombre
        Matcher matcher = pattern.matcher(nombre);//machear el patron
        if (matcher.matches()) {//comprobar si cumple con el patron indicado
            salida = true;
        } else {
            System.out.println("Ingresa un Nombre valido!! Gracias");
        }
        return salida;
    }

    //metodo para comprobar que sea un numero
    private boolean esNumero(String numero) {
        boolean salida = false;//variable para la salida del bucle
        try {
            Double.parseDouble(numero);//parshea el numero a un doble y si salta la excepcion no es un numero
            salida = true;
        } catch (NumberFormatException e) {
            System.out.println("Ingresa un Numero Valido! Gracias!");
        }
        return salida;
    }

    //comprobar el formato de la fecha con un patron
    private boolean ComprobarFormatoFecha(String fecha) {
        boolean salida = false;//variable para la salida
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");//patron para la fecha
        Matcher matcher = pattern.matcher(fecha);//macheamos el patron
        if (matcher.matches()) {//comprobamos que el patron corresponde con el string pasado
            salida = true;
        } else {
            System.out.println("Ingresa una Fecha con Formato Válido (año-mes-día)! Gracias!");
        }
        return salida;
    }

    //comprobar si el id esta
    private boolean estaElID(String id) {
        boolean salida = false;//variable de salida para saber si esta el id o no

        for (Libro libro : catalogo) {//recorremos el catalogo para ver si esta el id
            if (libro.getId().equals(id)) {
                salida = true;
            }
        }
        return salida;
    }

    //comprobar si el titulo esta ya para no poder guardarlo de nuevo
    private boolean estaTitulo(String nombre) {
        boolean salida = false;//variable para la salida del metodo para saber si esta o no el titulo

        for (Libro libro : catalogo) {//recorremos el catalogo en busca de todos los libros
            if (libro.getTitle().equals(nombre)) {
                salida = true;
            }
        }
        return salida;
    }

    //metodo de buscar titulo sobrecargado para poder usarlo fuera de la clase pasandole un catalogo
    public boolean estaTitulo(String nombre, ArrayList<Libro> catalogo) {
        boolean salida = false;//variable para la salida del metodo para saber si esta o no el titulo

        for (Libro libro : catalogo) {////recorremos el catalogo en busca de todos los libros
            if (libro.getTitle().equals(nombre)) {
                salida = true;
            }
        }
        return salida;
    }
}
