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

    private ArrayList<Libro> catalogo = new ArrayList<>(); // CREAMOS UN ARRAYLIST PARA PODER GUARDAR LOS DATOS Y USARLO SI LO NECESITAMOS EN ALGUN MOMENTO
    Document doc; // CREAMOS UN DOCUMENT

    //METODO PARA ABRIR EL ARCHIVO XML
    public int abriXMLaDOM(File f) {
        try {
            System.out.println("Abriendo archivo XML file y generando DOM....");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);

            System.out.println("DOM creado con éxito.");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }

    //METODO PARA RECORRER EL DOM E IR GUARDANDO LOS DATOS EN LIBRO Y DESPUES EN EL ARRAYLIST
    public ArrayList<Libro> recorreDOMyGuardarloEnArrayList() {
        String[] datos = new String[7];
        Node nodo = null;
        Node root = doc.getFirstChild();
        NodeList nodelist = root.getChildNodes();

        for (int i = 0; i < nodelist.getLength(); i++) {
            nodo = nodelist.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Node ntemp = null;
                int contador = 1;
                datos[0] = nodo.getAttributes().item(0).getNodeValue();
                NodeList nl2 = nodo.getChildNodes();
                for (int j = 0; j < nl2.getLength(); j++) {
                    ntemp = nl2.item(j);
                    if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                        datos[contador] = ntemp.getTextContent();
                        contador++;
                    }
                }
                try {
                    Libro libro = new Libro(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6]);
                    this.catalogo.add(libro);
                } catch (NullPointerException ex) {
                    ex.getStackTrace();
                } catch (Exception ex) {
                    ex.getStackTrace();
                }
            }
        }
        return catalogo;
    }

    public int insertarLibroEnDOM(Libro libro) {

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

            Node nauthor = doc.createElement("author");
            Node nauthor_text = doc.createTextNode(author);
            nauthor.appendChild(nauthor_text);

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

            Node nbook = doc.createElement("book");
            ((Element) nbook).setAttribute("id", id);
            nbook.appendChild(nauthor);
            nbook.appendChild(ntitle);
            nbook.appendChild(ngenre);
            nbook.appendChild(nprice);
            nbook.appendChild(npublish_date);
            nbook.appendChild(ndescription);

            nbook.appendChild(doc.createTextNode("\n"));
            Node raiz = doc.getFirstChild();
            raiz.appendChild(nbook);
            System.out.println("Libro insertado en DOM.");

            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }

    public int deleteNode() {
        Scanner teclado = new Scanner(System.in);
        String tit;
        boolean salidaBucle = false;
        Operaciones operaciones = new Operaciones();

        do {
            System.out.println("Indica el título del libro que desea borrar: ");
            tit = teclado.nextLine();
            System.out.println("Buscando el Libro \"" + tit + "\" para borrarlo...");
            if (operaciones.estaTitulo(tit, catalogo)) {
                salidaBucle = true;
            } else {
                System.out.println("Titulo No Esta! Indique uno Válido! Gracias!/n");
            }
        } while (!salidaBucle);

        try {
            Node raiz = doc.getDocumentElement();
            NodeList nl1 = doc.getElementsByTagName("title");
            Node n1;
            for (int i = 0; i < nl1.getLength(); i++) {
                n1 = nl1.item(i);
                if (n1.getNodeType() == Node.ELEMENT_NODE) {
                    if (n1.getChildNodes().item(0).getNodeValue().equals(tit)) {
                        System.out.println("Borrando el nodo <book> con título " + tit);
                        n1.getParentNode().getParentNode().removeChild(n1.getParentNode());
                    }
                }
            }
            System.out.println("Nodo borrado");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return -1;
        }
    }

    public void guardarDOMcomoArchivo(String nuevoArchivo) {
        try {
            Source src = new DOMSource(doc); // Definimos el origen
            StreamResult rst = new StreamResult(new File(nuevoArchivo)); // Definimos el resultado

            // Declaramos el Transformer que tiene el método .transform() que necesitamos.
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            // Opción para indentar el archivo
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(src, rst);
            System.out.println("Archivo creado del DOM con éxito\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
