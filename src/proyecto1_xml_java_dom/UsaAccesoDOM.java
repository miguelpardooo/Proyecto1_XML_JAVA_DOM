package proyecto1_xml_java_dom;

import java.io.File;

public class UsaAccesoDOM {

    public static void main(String[] args) {
        AccesoDOM a = new AccesoDOM();
        File f = new File("books2.xml");
        a.abriXMLaDOM(f);
        
        //a.insertarLibroEnDOM();
        a.deleteNode();
        
        a.recorreDOMyMuestra();
        a.guardarDOMcomoArchivo("books2.xml");
    }
    
}
