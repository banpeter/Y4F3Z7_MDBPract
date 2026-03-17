package y4f3z7;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.File;

public class DOMWrite {

    public static void main(String[] args) {
        try {

            // =========================
            // 1. Dokumentum létrehozása
            // =========================
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // ==========================================
            // 2. XML feldolgozási utasítás (Processing Instruction)
            // ==========================================
            ProcessingInstruction pi = doc.createProcessingInstruction(
                    "xml-stylesheet",
                    "type=\"text/xsl\" href=\"style.xsl\""
            );
            doc.appendChild(pi);

            // =========================
            // 3. Gyökérelem
            // =========================
            Element root = doc.createElement("vendeglatas");
            doc.appendChild(root);

            // =========================
            // 4. Étterem létrehozása
            // =========================
            Element etterem = doc.createElement("etterem");
            etterem.setAttribute("ekod", "e1");

            Element nev = doc.createElement("nev");
            nev.setTextContent("Trófea");

            Element cim = doc.createElement("cim");

            Element varos = doc.createElement("varos");
            varos.setTextContent("Budapest");

            Element utca = doc.createElement("utca");
            utca.setTextContent("Visegrád");

            Element hazszam = doc.createElement("hazszam");
            hazszam.setTextContent("13");

            cim.appendChild(varos);
            cim.appendChild(utca);
            cim.appendChild(hazszam);

            Element csillag = doc.createElement("csillag");
            csillag.setTextContent("4");

            etterem.appendChild(nev);
            etterem.appendChild(cim);
            etterem.appendChild(csillag);

            root.appendChild(etterem);

            // =========================
            // 5. Szakács létrehozása
            // =========================
            Element szakacs = doc.createElement("szakacs");
            szakacs.setAttribute("szkod", "sz1");
            szakacs.setAttribute("e_sz", "e1");

            Element szakacsNev = doc.createElement("nev");
            szakacsNev.setTextContent("Ötlet Elek");

            Element reszleg = doc.createElement("reszleg");
            reszleg.setTextContent("Saucier");

            Element vegzettseg1 = doc.createElement("vegzettseg");
            vegzettseg1.setTextContent("Szakközépiskola");

            Element vegzettseg2 = doc.createElement("vegzettseg");
            vegzettseg2.setTextContent("Le Cordon Bleu");

            szakacs.appendChild(szakacsNev);
            szakacs.appendChild(reszleg);
            szakacs.appendChild(vegzettseg1);
            szakacs.appendChild(vegzettseg2);

            root.appendChild(szakacs);

            // =========================
            // 6. Gyakornok létrehozása
            // =========================
            Element gyakornok = doc.createElement("gyakornok");
            gyakornok.setAttribute("gykod", "gy1");
            gyakornok.setAttribute("e_gy", "e1");

            Element gyNev = doc.createElement("nev");
            gyNev.setTextContent("Bordás Dávid");

            Element gyakorlat = doc.createElement("gyakorlat");

            Element kezdete = doc.createElement("kezdete");
            kezdete.setTextContent("2021.08.20.");

            Element idotartama = doc.createElement("idotartama");
            idotartama.setTextContent("2 hónap");

            gyakorlat.appendChild(kezdete);
            gyakorlat.appendChild(idotartama);

            Element muszak = doc.createElement("muszak");
            muszak.setTextContent("Délelőtt");

            gyakornok.appendChild(gyNev);
            gyakornok.appendChild(gyakorlat);
            gyakornok.appendChild(muszak);

            root.appendChild(gyakornok);




            // =========================
// 5. Főszakács létrehozása
// =========================
            Element foszakacs = doc.createElement("foszakacs");
            foszakacs.setAttribute("fkod", "f1");
            foszakacs.setAttribute("e_f", "e1");

            Element fNev = doc.createElement("nev");
            fNev.setTextContent("Havas Péter");

            Element fEletkor = doc.createElement("eletkor");
            fEletkor.setTextContent("35");

            Element fVegzettseg = doc.createElement("vegzettseg");
            fVegzettseg.setTextContent("Paul Bocuse Institute");

            foszakacs.appendChild(fNev);
            foszakacs.appendChild(fEletkor);
            foszakacs.appendChild(fVegzettseg);

            root.appendChild(foszakacs);

// =========================
// 6. Vendég létrehozása
// =========================
            Element vendeg = doc.createElement("vendeg");
            vendeg.setAttribute("vkod", "v1");

            Element vNev = doc.createElement("nev");
            vNev.setTextContent("Gábor Sándor");

            Element vEletkor = doc.createElement("eletkor");
            vEletkor.setTextContent("23");

            Element vCim = doc.createElement("cim");

            Element vVaros = doc.createElement("varos");
            vVaros.setTextContent("Budapest");

            Element vUtca = doc.createElement("utca");
            vUtca.setTextContent("Visegrád");

            Element vHazszam = doc.createElement("hazszam");
            vHazszam.setTextContent("13");

            vCim.appendChild(vVaros);
            vCim.appendChild(vUtca);
            vCim.appendChild(vHazszam);

            vendeg.appendChild(vNev);
            vendeg.appendChild(vEletkor);
            vendeg.appendChild(vCim);

            root.appendChild(vendeg);

            // =========================
            // 7. Kiírás (konzol + fájl)
            // =========================
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // formázás
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);

            // konzol
            StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);

            // fájl
            StreamResult file = new StreamResult(new File("Y4F3Z7XML1.xml"));
            transformer.transform(source, file);

            System.out.println("\nXML fájl sikeresen létrehozva!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}