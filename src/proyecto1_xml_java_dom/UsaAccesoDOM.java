package proyecto1_xml_java_dom;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class UsaAccesoDOM {

    public static void main(String[] args) {
        AccesoDOM accesoDom = new AccesoDOM();
        ArrayList<Libro> catalogo = new ArrayList<>();// ARRAYLIST DONDE GUARDAMOS LOS DATOS DEL FICHERO
        Scanner sc = new Scanner(System.in);
        String opcion; // DONDE SE GUARDA LA ELECCION DEL SWITCH
        boolean condicionSalida = false; //CONDICION DE SALIDA DEL BUCLE DE BIENVENIDA
        
        File f = new File("books2.xml");
        //ABRIMOS EL FICHERO Y GUARDAMOS LOS DATOS EN EL ARRAYLIST
        accesoDom.abriXMLaDOM(f);
        catalogo = accesoDom.recorreDOMyGuardarloEnArrayList();

        do {
            System.out.println("Bienvenido a Nuestro Programa!");
            System.out.println("================================");
            System.out.println("Tienes las Siguientes Opciones: ");
            System.out.println("Teclea 1 Para Ver Los Datos!");
            System.out.println("Teclea 2 Para Crear un Nuevo Libro!");
            System.out.println("Teclea 3 Para Borrar un Libro!");
            System.out.println("Teclea 4 Para Guardarlo de Nuevo en el Archivo!");
            System.out.println("Teclea 5 Para Salir!");
            opcion = sc.nextLine();
            switch (opcion) {
            case "1":
                if (catalogo.isEmpty()) {
                    catalogo = accesoDom.recorreDOMyGuardarloEnArrayList();// RECORRE TODO EL DOM Y VA GUARDANDO LOS DATOS EN EL ARRAYLIST
                    verTodosLosLibros(catalogo); // METODO PARA VER TODOS LOS LIBROS DEL ARRAYLIST
                } else {
                    verTodosLosLibros(catalogo);// METODO PARA VER TODOS LOS LIBROS
                }
                System.out.println("================================");
                break;
            case "2":
                if (catalogo != null) {
                    Operaciones operacionesLibro = new Operaciones();
                    Libro libro = new Libro();
                    libro = operacionesLibro.crearLibro(catalogo);
                    //Insertamos el Libro en el DOM
                    accesoDom.insertarLibroEnDOM(libro);
                    //Guardamos los datos de nuevo en el ArrayList para ver si esta el libro
                    catalogo.clear();
                    catalogo = accesoDom.recorreDOMyGuardarloEnArrayList();
                    verTodosLosLibros(catalogo);
                } else {
                    System.out.println("Catalogo esta Vacío!");
                }
                System.out.println("================================");
                break;
            case "3":
                verTitulosDeLosLibros(catalogo);
                accesoDom.deleteNode();
                System.out.println("================================");
                break;
            case "4":
                accesoDom.guardarDOMcomoArchivo("books2.xml");
                System.out.println("================================");
                break;
            case "5":
                System.out.println("Bye, Que Tenga un Bonito Dia!");
                System.out.println("================================");
                condicionSalida = true;
                break;
            default:
                System.out.println("No es una Opcion Valida!");
                System.out.println("================================");
                break;
            }
        } while (!condicionSalida);
    }
    
    //RECORRE EL ARRAYLIST Y MUESTRA LOS DATOS
    private static void verTodosLosLibros(ArrayList<Libro> catalogo) {
        System.out.println("<catalog>");
        for (Libro libro : catalogo) {
            System.out.println(libro.toString());
        }
        System.out.println("</catalog>");
    }
    
    //RECORRE EL ARRAYLIST Y VA MOSTRANDO LOS TITULOS
    private static void verTitulosDeLosLibros(ArrayList<Libro> catalogo) {
        for (Libro libro : catalogo) {
            System.out.println("\t<Titulo>" + libro.getTitle() + "</Titulo>\n");
        }
    }
}
