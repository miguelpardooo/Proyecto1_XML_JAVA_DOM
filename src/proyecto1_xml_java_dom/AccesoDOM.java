package proyecto1_xml_java_dom;

import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AccesoDOM {
    
    Document doc;
    
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
    
    public void recorreDOMyMuestra() {
        String[] datos = new String[7];
        Node nodo = null;
        Node root = doc.getFirstChild();
        NodeList nodelist = root.getChildNodes();
        
        System.out.println("");
        System.out.println("<catalog>");

        for (int i = 0; i < nodelist.getLength(); i++) {
            nodo = nodelist.item(i);
            if(nodo.getNodeType()== Node.ELEMENT_NODE){
                
                Node ntemp = null;
                int contador = 1;
                
                datos[0] = nodo.getAttributes().item(0).getNodeValue();
                
                NodeList nl2 = nodo.getChildNodes();
                for(int j = 0;j < nl2.getLength(); j++){
                    ntemp = nl2.item(j);
                    if(ntemp.getNodeType() == Node.ELEMENT_NODE){
                        datos[contador] = ntemp.getTextContent();
                        contador++;
                    }
                }
                
                System.out.println("\t<book id=\"" + datos[0] + "\">");
                System.out.println("\t\t<author>" + datos[1] + "</author>");
                System.out.println("\t\t<title>" + datos[2] + "</title>");
                System.out.println("\t\t<genre>" + datos[3] + "</genre>");
                System.out.println("\t\t<price>" + datos[4] + "</price>");
                System.out.println("\t\t<publish_date>" + datos[5] + "</publish_date>");
                System.out.println("\t\t<description>" + datos[6] + "</description>");
                System.out.println("\t</book>");
            }
        }
        
        System.out.println("</catalog>");
    }
    
    public int insertarLibroEnDOM() {
        Scanner teclado = new Scanner(System.in);
        String id = null, author = null, title = null, genre = null, price = null, publish_date = null, description = null;
        
        try {
            System.out.println("Introduce los datos del libro que desea insertar: ");
            
            System.out.print("\tID: ");
            id = teclado.nextLine();
            
            System.out.print("\tAutor: ");
            author = teclado.nextLine();

            System.out.print("\tTítulo: ");
            title = teclado.nextLine();

            System.out.print("\tGénero: ");
            genre = teclado.nextLine();

            System.out.print("\tPrecio: ");
            price = teclado.nextLine();

            System.out.print("\tFecha de publicación: ");
            publish_date = teclado.nextLine();

            System.out.print("\tDescripción: ");
            description = teclado.nextLine();
        } catch (Exception e) {
            
        }
        
        
        try {
            System.out.println("Añadir libro al árbol DOM: " + author + "; " + title + "; " 
                    + genre + "; " + price + "; " + publish_date + "; " + description);
            
            Node nauthor = doc.createElement("author");
            Node nauthor_text = doc.createTextNode(author);
            nauthor.appendChild(nauthor_text);
            
            Node ntitle = doc.createElement("title");
            Node ntitle_text=doc.createTextNode(title);
            ntitle.appendChild(ntitle_text);
            
            Node ngenre = doc.createElement("genre");
            Node ngenre_text=doc.createTextNode(genre);
            ngenre.appendChild(ngenre_text);
            
            Node nprice = doc.createElement("price");
            Node nprice_text=doc.createTextNode(price);
            nprice.appendChild(nprice_text);
            
            Node npublish_date = doc.createElement("publish_date");
            Node npublish_date_text=doc.createTextNode(publish_date);
            npublish_date.appendChild(npublish_date_text);
            
            Node ndescription = doc.createElement("description");
            Node ndescription_text=doc.createTextNode(description);
            ndescription.appendChild(ndescription_text);
            
            Node nbook=doc.createElement("book");
            ((Element)nbook).setAttribute("id", id);
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
        
        System.out.println("Indica el título del libro que desea borrar: ");
        tit = teclado.next();
        
        System.out.println("Buscando el Libro \"" + tit + "\" para borrarlo...");
    
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
}
