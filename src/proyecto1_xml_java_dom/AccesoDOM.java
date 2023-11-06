package proyecto1_xml_java_dom;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
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
}
