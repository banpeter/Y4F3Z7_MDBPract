package y4f3z7;

import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class DomModifyOwn {

    public static void main(String[] args) {
        try {
            File file = new File("Y4F3Z7_XDM.xml");

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            // =========================
            // 1. Ügyfél módosítása
            // =========================
            NodeList ugyfelek = doc.getElementsByTagName("ugyfel");

            for (int i = 0; i < ugyfelek.getLength(); i++) {
                Element u = (Element) ugyfelek.item(i);

                if ("c1".equals(u.getAttribute("cid"))) {
                    u.getElementsByTagName("nev").item(0).setTextContent("Módosított Péter");
                }
            }

            // =========================
            // 2. Rendelés fizetve = false
            // =========================
            NodeList rendelesek = doc.getElementsByTagName("rendeles");

            for (int i = 0; i < rendelesek.getLength(); i++) {
                Element r = (Element) rendelesek.item(i);
                r.getElementsByTagName("fizetve").item(0).setTextContent("false");
            }

            // =========================
            // 3. Jármű km növelés
            // =========================
            NodeList jarmuvek = doc.getElementsByTagName("jarmu");

            for (int i = 0; i < jarmuvek.getLength(); i++) {
                Element j = (Element) jarmuvek.item(i);

                int km = Integer.parseInt(
                        j.getElementsByTagName("megtett_km").item(0).getTextContent()
                );

                j.getElementsByTagName("megtett_km").item(0)
                        .setTextContent(String.valueOf(km + 1000));
            }

            // =========================
            // 4. Rendelések törlése
            // =========================
            NodeList rendelesLista = doc.getElementsByTagName("rendeles");

            for (int i = rendelesLista.getLength() - 1; i >= 0; i--) {
                Node node = rendelesLista.item(i);
                node.getParentNode().removeChild(node);
            }

            // =========================
            // Kiírás
            // =========================
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(doc), new StreamResult(System.out));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}