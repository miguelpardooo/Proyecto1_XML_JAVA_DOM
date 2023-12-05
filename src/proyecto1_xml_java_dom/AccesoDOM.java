package proyecto1_xml_java_dom;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AccesoDOM {

    private ArrayList<Libro> catalogo = new ArrayList<>();//arraylist de libros para poder guardar todos los libros dentro
    Document doc;//creamos un docuemnto

    //metodo para abrir el archivo con dom
    public int abriXMLaDOM(File f) {
        try {

            System.out.println("Abriendo archivo XML file y generando DOM....");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//creamos una instancia de documentbuilderfactory para configurar la creacion del dom
            factory.setIgnoringComments(true);//ignorar los comentarios
            factory.setIgnoringElementContentWhitespace(true);//ignorar espacios en blanco dentro de los elemetos
            DocumentBuilder builder = factory.newDocumentBuilder();//creamos un documentbuilder a partir del anterios para crear el dom
            doc = builder.parse(f);//el docuemnto creado antes se le pasa el buider al que se le parsea el archivo que se le pasa con el metodo

            System.out.println("DOM creado con éxito.");

            return 0;//retorna un 0 si todo ha ido bien
        } catch (Exception e) {
            System.out.println(e);
            return -1;//retorna un -1 si algo ha fallado en el proceso
        }
    }

    //metodo para recorrer el dom y guardar los datos objetos dentro de un arraylist
    public ArrayList<Libro> recorreDOMyGuardarloEnArrayList() {
        String[] datos = new String[7];//array que almacenara los datos de un libro
        Node nodo = null; //nodo que itinera sobre el dom
        Node root = doc.getFirstChild();//para el nodo raiz
        NodeList nodelist = root.getChildNodes();//lista de nodos hijos del raiz

        for (int i = 0; i < nodelist.getLength(); i++) {//recorremos los hijos del nodo raiz
            nodo = nodelist.item(i);//nodo hijo
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {//si el nodo es un elemento
                Node ntemp = null;//nodo temporal para los hijos del nodo hijo del raiz
                int contador = 1;
                datos[0] = nodo.getAttributes().item(0).getNodeValue();

                NodeList nl2 = nodo.getChildNodes();//para obtener los hijos de los hijos del nodo raiz
                for (int j = 0; j < nl2.getLength(); j++) {
                    ntemp = nl2.item(j);//variable del nodo temporal
                    if (ntemp.getNodeType() == Node.ELEMENT_NODE) {//si el nodo temporal es un elemento
                        datos[contador] = ntemp.getTextContent(); //obtener los datos de cada nodo temporal
                        contador++;//aumetar el contador
                    }
                }
                try {
                    //creamos un objeto con los datos obtenidos de los nodos y los metemos en un objeto de la clase libro
                    Libro libro = new Libro(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6]);
                    this.catalogo.add(libro);//agregamos un libro al catalogo
                } catch (NullPointerException ex) {
                    ex.getStackTrace();
                } catch (Exception ex) {
                    ex.getStackTrace();
                }
            }
        }
        return catalogo;//devolvemos el arraylist con todos los libros
    }

    //metodo para agregar un libro al dom al que se le pasa un objeto libro
    public int insertarLibroEnDOM(Libro libro) {

        //variable con todos los datos del libro
        String id = libro.getId(),
                author = libro.getAuthor(),
                title = libro.getTitle(),
                genre = libro.getGenre(),
                price = libro.getPrice(),
                publish_date = libro.getPublish_date(),
                description = libro.getDescription();

        try {

            System.out.println("Añadir libro al árbol DOM: " + author + "; " + title + "; "
                    + genre + "; " + price + "; " + publish_date + "; " + description);

            //creamos nodos para cada atributo del libro y se les asigna su valor correpsondiente
            Node nauthor = doc.createElement("author");//nodo se crea un elemento sobre el documento
            Node nauthor_text = doc.createTextNode(author);//nodo que contendra el texto del elemento
            nauthor.appendChild(nauthor_text);//se le asigna al nodo elemento el hijo que en este caso es el texto

            Node ntitle = doc.createElement("title");
            Node ntitle_text = doc.createTextNode(title);
            ntitle.appendChild(ntitle_text);

            Node ngenre = doc.createElement("genre");
            Node ngenre_text = doc.createTextNode(genre);
            ngenre.appendChild(ngenre_text);

            Node nprice = doc.createElement("price");
            Node nprice_text = doc.createTextNode(price);
            nprice.appendChild(nprice_text);

            Node npublish_date = doc.createElement("publish_date");
            Node npublish_date_text = doc.createTextNode(publish_date);
            npublish_date.appendChild(npublish_date_text);

            Node ndescription = doc.createElement("description");
            Node ndescription_text = doc.createTextNode(description);
            ndescription.appendChild(ndescription_text);

            //creamos por ultimo el nodo libro del que cuelgan todos los elementos
            //y le asignamos los elementos hijo
            Node nbook = doc.createElement("book");
            ((Element) nbook).setAttribute("id", id);//le asignamos el id al elmento id
            nbook.appendChild(nauthor);
            nbook.appendChild(ntitle);
            nbook.appendChild(ngenre);
            nbook.appendChild(nprice);
            nbook.appendChild(npublish_date);
            nbook.appendChild(ndescription);

            nbook.appendChild(doc.createTextNode("\n"));//por ultimo creamos todos los text del libro

            Node raiz = doc.getFirstChild();//se obtiene  el nodo raiz
            raiz.appendChild(nbook);// al nodo raiz se le asigna el nodo de libro que hemos creado

            System.out.println("Libro insertado en DOM.");
            return 0;//todo ha tenido exito
        } catch (Exception e) {
            System.out.println(e);
            return -1;//en caso de que falle algo
        }
    }

    //metodo para eliminar un nodo del dom pasandole un titulo
    public int deleteNode() {
        Scanner teclado = new Scanner(System.in);//scanner
        String tit;//variable dle titulo
        boolean salidaBucle = false;//variable para la salida del bucle
        Operaciones operaciones = new Operaciones();//objeto de la clase operaciones para poder acceder a sus metodos

        //buscar dentro de catalogo y ver si el libro esta
        do {
            System.out.println("Indica el título del libro que desea borrar: ");
            tit = teclado.nextLine();
            System.out.println("Buscando el Libro \"" + tit + "\" para borrarlo...");
            if (operaciones.estaTitulo(tit, catalogo)) {//ver si esta el libro en el catalogo
                salidaBucle = true;
            } else {
                System.out.println("Titulo No Esta! Indique uno Válido! Gracias!/n");
            }
        } while (!salidaBucle);

        try {

            Node raiz = doc.getDocumentElement();//nodo raiz
            NodeList nl1 = doc.getElementsByTagName("title");//lista de nodos coger el elemento titulo en este caso
            Node n1;//nodo

            for (int i = 0; i < nl1.getLength(); i++) {//recorremos la lista de nodos
                n1 = nl1.item(i);//al nodo se le asigna el item de la lista
                if (n1.getNodeType() == Node.ELEMENT_NODE) {//comprobamos si el nodo es un elemento
                    if (n1.getChildNodes().item(0).getNodeValue().equals(tit)) {//si el valor del nodo es igual que el titulo pasado
                        System.out.println("Borrando el nodo <book> con título " + tit);
                        n1.getParentNode().getParentNode().removeChild(n1.getParentNode());//borramos el hijo del padre y del raiz
                    }
                }
            }
            System.out.println("Nodo borrado");
            return 0;//si todo ha tenido exito
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return -1;//si ha fallado por algun motivo
        }
    }

    //metodo para guardar el dom como un archivo
    public void guardarDOMcomoArchivo(String nuevoArchivo) {
        try {

            Source src = new DOMSource(doc);//documento dom que se guardara
            StreamResult rst = new StreamResult(new File(nuevoArchivo));//archivo de salida donde se guardara el documento dom

            Transformer transformer = TransformerFactory.newInstance().newTransformer();//objeto para poder transformar el dom al archivod e salida
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");//le da formato al xml para que sea mas legible
            transformer.transform(src, rst);//donde se transforma y escribe el documento
            System.out.println("Archivo creado del DOM con éxito\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
