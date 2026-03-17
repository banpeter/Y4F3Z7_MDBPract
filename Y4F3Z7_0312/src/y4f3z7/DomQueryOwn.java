package y4f3z7;

import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class DomQueryOwn {

    public static void main(String[] args) {
        try {
            File file = new File("Y4F3Z7_XDM.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            // =========================
            // 1. Céges ügyfelek
            // =========================
            System.out.println("Céges ügyfelek:\n");

            NodeList ugyfelek = doc.getElementsByTagName("ugyfel");

            for (int i = 0; i < ugyfelek.getLength(); i++) {
                Element u = (Element) ugyfelek.item(i);

                String ceg = u.getElementsByTagName("ceg").item(0).getTextContent();

                if ("true".equals(ceg)) {
                    System.out.println("ID: " + u.getAttribute("cid"));
                    System.out.println("Név: " + u.getElementsByTagName("nev").item(0).getTextContent());
                    System.out.println();
                }
            }

            // =========================
            // 2. Nagy értékű rendelések
            // =========================
            System.out.println("100000 Ft feletti rendelések:\n");

            NodeList rendelesek = doc.getElementsByTagName("rendeles");

            for (int i = 0; i < rendelesek.getLength(); i++) {
                Element r = (Element) rendelesek.item(i);

                int osszeg = Integer.parseInt(
                        r.getElementsByTagName("osszeg").item(0).getTextContent()
                );

                if (osszeg > 100000) {
                    System.out.println("Rendelés ID: " + r.getAttribute("rid"));
                    System.out.println("Összeg: " + osszeg);
                    System.out.println();
                }
            }

            // =========================
            // 3. Futárok benzinkártyával
            // =========================
            System.out.println("Benzinkártyás futárok:\n");

            NodeList futarok = doc.getElementsByTagName("futar");

            for (int i = 0; i < futarok.getLength(); i++) {
                Element f = (Element) futarok.item(i);

                String kartya = f.getElementsByTagName("benzinkartya").item(0).getTextContent();

                if ("true".equals(kartya)) {
                    System.out.println("Futár: " + f.getElementsByTagName("nev").item(0).getTextContent());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}