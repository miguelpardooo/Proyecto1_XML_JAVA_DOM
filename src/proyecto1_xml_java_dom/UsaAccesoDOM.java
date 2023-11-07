package proyecto1_xml_java_dom;

import java.io.File;
import java.util.ArrayList;

public class UsaAccesoDOM {

    public static void main(String[] args) {
        AccesoDOM accesoDom = new AccesoDOM();
        ArrayList<Libro> catalogo = new ArrayList<>();

        File f = new File("books2.xml");
        //Abrimos el Fichero y Guardamos lo datos en ArrayList
        accesoDom.abriXMLaDOM(f);
        catalogo = accesoDom.recorreDOMyGuardarloEnArrayList();

        //Ver los Libros del Dom que estan en el ArrayList
        System.out.println("\nVER LOS DATOS DEL ARCHIVO XML");
        System.out.println("******************************\n");
        verTodosLosLibros(catalogo);

        //Creamos un nuevo libro
        System.out.println("\nCREAMOS UN NUEVO NODO");
        System.out.println("******************************\n");
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

        //Borramos un nodo
        System.out.println("\nBORRAMOS UN LIBRO DEL DOM");
        System.out.println("******************************\n");
        verTitulosDeLosLibros(catalogo);
        accesoDom.deleteNode();
        //Mostrar de Nuevo los Nodos para comprobar que esta borrado
        catalogo.clear();
        catalogo = accesoDom.recorreDOMyGuardarloEnArrayList();
        verTitulosDeLosLibros(catalogo);

        System.out.println("\nGUARDAMOS EL DOM EN EL ARCHIVO");
        System.out.println("******************************\n");
        accesoDom.guardarDOMcomoArchivo("books2.xml");
    }

    private static void verTodosLosLibros(ArrayList<Libro> catalogo) {
        System.out.println("<catalog>");
        for (Libro libro : catalogo) {
            System.out.println(libro.toString());
        }
        System.out.println("</catalog>");
    }

    private static void verTitulosDeLosLibros(ArrayList<Libro> catalogo) {
        for (Libro libro : catalogo) {
            System.out.println("\t<Titulo>" + libro.getTitle() + "</Titulo>\n");
        }
    }

}
