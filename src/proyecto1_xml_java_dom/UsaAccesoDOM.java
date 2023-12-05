package proyecto1_xml_java_dom;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class UsaAccesoDOM {

    public static void main(String[] args) {
        AccesoDOM accesoDom = new AccesoDOM();//objeto AccesoDom para aceder a los metodos
        ArrayList<Libro> catalogo = new ArrayList<>();//cremoas un arraylist de libros
        Scanner sc = new Scanner(System.in);//scanner
        String opcion;//eleccion en el menu
        boolean condicionSalida = false;//condicion salida bucle menu

        File f = new File("books.xml");//un file con el archivo xml
        accesoDom.abriXMLaDOM(f);//abrir el archivo 
        catalogo = accesoDom.recorreDOMyGuardarloEnArrayList();//cargar los datos en el arraylist

        //menu
        do {
            System.out.println("Bienvenido a Nuestro Programa!");
            System.out.println("================================");
            System.out.println("Tienes las Siguientes Opciones: ");
            System.out.println("Teclea 1 Para Ver Los Datos!");
            System.out.println("Teclea 2 Para Crear un Nuevo Libro!");
            System.out.println("Teclea 3 Para Borrar un Libro!");
            System.out.println("Teclea 4 Para Guardarlo de Nuevo en el Archivo!");
            System.out.println("Teclea 5 Para Salir!");
            System.out.println("");
            opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    if (catalogo.isEmpty()) {//catalogo esta vacio
                        catalogo = accesoDom.recorreDOMyGuardarloEnArrayList();//recorre todo el dom y va guardando los datos en el arraylist
                        verTodosLosLibros(catalogo);//metodo para ver todos los libros del arraylist
                    } else {
                        verTodosLosLibros(catalogo);//metodo para ver todos los libros del arraylist
                    }
                    System.out.println("================================");
                    break;
                case "2":
                    if (catalogo != null) {
                        Operaciones operacionesLibro = new Operaciones();//objeto de la clase operaciones para acceder a sus metodos
                        Libro libro = new Libro();//objeto libro
                        libro = operacionesLibro.crearLibro(catalogo);//crear un nuevo libro
                        accesoDom.insertarLibroEnDOM(libro);//insertar un libro en el dom
                        //guardamos de nuevo los datos en el arraylist
                        catalogo.clear();
                        catalogo = accesoDom.recorreDOMyGuardarloEnArrayList();
                        verTodosLosLibros(catalogo);
                    } else {
                        System.out.println("Catalogo esta Vacío!");
                    }
                    System.out.println("================================");
                    break;
                case "3":
                    verTitulosDeLosLibros(catalogo);//ver todos los libros
                    accesoDom.deleteNode();//borrar un nodo
                    System.out.println("================================");
                    break;
                case "4":
                    accesoDom.guardarDOMcomoArchivo("books.xml");//guardar el dom como un archivo
                    System.out.println("================================");
                    break;
                case "5":
                    System.out.println("Bye, Que Tenga un Bonito Dia!");
                    System.out.println("================================");
                    condicionSalida = true;//salir del bucle
                    break;
                default:
                    System.out.println("No es una Opcion Valida!");
                    System.out.println("================================");
                    break;
            }
        } while (!condicionSalida);
    }

    //recorrer el arraylist y mostrar los datos
    private static void verTodosLosLibros(ArrayList<Libro> catalogo) {
        System.out.println("<catalog>");
        for (Libro libro : catalogo) {
            System.out.println(libro.toString());
        }
        System.out.println("</catalog>");
    }

    //recorrer el arraylist y mostrar los titulos
    private static void verTitulosDeLosLibros(ArrayList<Libro> catalogo) {
        for (Libro libro : catalogo) {
            System.out.println("\t<Titulo>" + libro.getTitle() + "</Titulo>\n");
        }
    }
}
