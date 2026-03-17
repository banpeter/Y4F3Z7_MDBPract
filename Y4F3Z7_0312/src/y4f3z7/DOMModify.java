package y4f3z7;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class DOMModify {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("./gyakorlat.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            // =========================
            // 1. Vendég (v1) módosítása
            // =========================
            NodeList vendegLista = doc.getElementsByTagName("vendeg");

            for (int i = 0; i < vendegLista.getLength(); i++) {
                Node node = vendegLista.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element vendeg = (Element) node;

                    if ("v1".equals(vendeg.getAttribute("vkod"))) {

                        // név módosítása
                        vendeg.getElementsByTagName("nev").item(0)
                                .setTextContent("Lajos Gábor");

                        // életkor módosítása
                        vendeg.getElementsByTagName("eletkor").item(0)
                                .setTextContent("30");
                    }
                }
            }

            // =====================================
            // 2. Gyakornok e_gy attribútum módosítása
            // =====================================
            NodeList gyakornokLista = doc.getElementsByTagName("gyakornok");

            for (int i = 0; i < gyakornokLista.getLength(); i++) {
                Node node = gyakornokLista.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element gyakornok = (Element) node;

                    // minden gyakornoknál e3-ra állítjuk
                    gyakornok.setAttribute("e_gy", "e3");
                }
            }

            // =====================================
            // 3. Összes "rendeles" elem törlése
            // =====================================
            NodeList rendelesLista = doc.getElementsByTagName("rendeles");

            // FONTOS: visszafelé kell törölni!
            for (int i = rendelesLista.getLength() - 1; i >= 0; i--) {
                Node node = rendelesLista.item(i);
                node.getParentNode().removeChild(node);
            }

            // =====================================
            // 4. Kiírás konzolra (módosított XML)
            // =====================================
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // szebb formázás
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(System.out);

            transformer.transform(source, console);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}