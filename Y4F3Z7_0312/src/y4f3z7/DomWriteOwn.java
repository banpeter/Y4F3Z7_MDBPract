package y4f3z7;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.File;

public class DomWriteOwn {

    public static void main(String[] args) {

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();

            // Processing Instruction
            ProcessingInstruction pi = doc.createProcessingInstruction(
                    "xml-stylesheet",
                    "type=\"text/xsl\" href=\"bolt.xsl\""
            );
            doc.appendChild(pi);

            // root
            Element root = doc.createElement("bolt");
            doc.appendChild(root);

            // -------------------------
            // Ügyfél
            // -------------------------
            Element ugyfel = doc.createElement("ugyfel");
            ugyfel.setAttribute("cid", "c1");

            Element nev = doc.createElement("nev");
            nev.setTextContent("Kiss Péter");
            Element email = doc.createElement("email");
            email.setTextContent("kiss.peter@gmail.hu");
            Element telefonszam = doc.createElement("telefonszam");
            telefonszam.setTextContent("06301234567");
            Element kartyaszam = doc.createElement("kartyaszam");
            kartyaszam.setTextContent("1234567812345678");
            Element ceg = doc.createElement("ceg");
            ceg.setTextContent("false");

            Element cim = doc.createElement("cim");
            Element orszag = doc.createElement("orszag");
            orszag.setTextContent("Magyarország");
            Element varos = doc.createElement("varos");
            varos.setTextContent("Budapest");
            Element iranyitoszam = doc.createElement("iranyitoszam");
            iranyitoszam.setTextContent("1111");
            Element utca = doc.createElement("utca");
            utca.setTextContent("Fő utca");
            Element hazszam = doc.createElement("hazszam");
            hazszam.setTextContent("12");

            cim.appendChild(orszag);
            cim.appendChild(varos);
            cim.appendChild(iranyitoszam);
            cim.appendChild(utca);
            cim.appendChild(hazszam);

            ugyfel.appendChild(nev);
            ugyfel.appendChild(email);
            ugyfel.appendChild(telefonszam);
            ugyfel.appendChild(kartyaszam);
            ugyfel.appendChild(ceg);
            ugyfel.appendChild(cim);

            root.appendChild(ugyfel);

            // -------------------------
            // Termék
            // -------------------------
            Element aru = doc.createElement("aru");
            aru.setAttribute("aid", "a10");

            Element aruNev = doc.createElement("nev");
            aruNev.setTextContent("Laptop");
            Element gyarto = doc.createElement("gyarto");
            gyarto.setTextContent("Lenovo");
            Element kategoria = doc.createElement("kategoria");
            kategoria.setTextContent("Elektronika");
            Element raktarkeszlet = doc.createElement("raktarkeszlet");
            raktarkeszlet.setTextContent("5");
            Element garancia_honap = doc.createElement("garancia_honap");
            garancia_honap.setTextContent("24");

            Element ertekelesek = doc.createElement("ertekelesek");
            Element ertek1 = doc.createElement("ertek"); ertek1.setTextContent("5");
            Element ertek2 = doc.createElement("ertek"); ertek2.setTextContent("5");
            Element ertek3 = doc.createElement("ertek"); ertek3.setTextContent("4");
            ertekelesek.appendChild(ertek1);
            ertekelesek.appendChild(ertek2);
            ertekelesek.appendChild(ertek3);

            aru.appendChild(aruNev);
            aru.appendChild(gyarto);
            aru.appendChild(kategoria);
            aru.appendChild(raktarkeszlet);
            aru.appendChild(garancia_honap);
            aru.appendChild(ertekelesek);

            root.appendChild(aru);

            // -------------------------
            // Rendelés
            // -------------------------
            Element rendeles = doc.createElement("rendeles");
            rendeles.setAttribute("rid", "r100");
            rendeles.setAttribute("ugyfel_ref", "c1");

            Element rendeles_datum = doc.createElement("rendeles_datum");
            rendeles_datum.setTextContent("2025-01-10");
            Element fizetve = doc.createElement("fizetve");
            fizetve.setTextContent("true");
            Element osszeg = doc.createElement("osszeg");
            osszeg.setTextContent("250000");

            Element tetelek = doc.createElement("tetelek");
            Element tetel = doc.createElement("tetel");
            tetel.setAttribute("aru_ref", "a10");
            Element darab = doc.createElement("darab");
            darab.setTextContent("1");
            tetel.appendChild(darab);
            tetelek.appendChild(tetel);

            rendeles.appendChild(rendeles_datum);
            rendeles.appendChild(fizetve);
            rendeles.appendChild(osszeg);
            rendeles.appendChild(tetelek);

            root.appendChild(rendeles);

            // -------------------------
            // Futár
            // -------------------------
            Element futar = doc.createElement("futar");
            futar.setAttribute("fid", "f20");

            Element futarNev = doc.createElement("nev");
            futarNev.setTextContent("Nagy László");
            Element munkaido = doc.createElement("munkaido");
            Element kezd = doc.createElement("kezd"); kezd.setTextContent("08:00");
            Element vegez = doc.createElement("vegez"); vegez.setTextContent("16:00");
            munkaido.appendChild(kezd);
            munkaido.appendChild(vegez);
            Element fizetesFutar = doc.createElement("fizetes"); fizetesFutar.setTextContent("400000");
            Element benzinkartya = doc.createElement("benzinkartya"); benzinkartya.setTextContent("true");

            futar.appendChild(futarNev);
            futar.appendChild(munkaido);
            futar.appendChild(fizetesFutar);
            futar.appendChild(benzinkartya);

            root.appendChild(futar);

            // -------------------------
            // Kiszállítás
            // -------------------------
            Element kiszallitas = doc.createElement("kiszallitas");
            kiszallitas.setAttribute("rendeles_ref", "r100");
            kiszallitas.setAttribute("futar_ref", "f20");
            Element felvett = doc.createElement("felvett"); felvett.setTextContent("2025-01-11");
            Element kiszallitva = doc.createElement("kiszallitva"); kiszallitva.setTextContent("2025-01-12");

            kiszallitas.appendChild(felvett);
            kiszallitas.appendChild(kiszallitva);

            root.appendChild(kiszallitas);

            // -------------------------
            // Jármű
            // -------------------------
            Element jarmu = doc.createElement("jarmu");
            jarmu.setAttribute("jid", "j5");
            Element rendszam = doc.createElement("rendszam"); rendszam.setTextContent("ABC-123");
            Element marka = doc.createElement("marka"); marka.setTextContent("Ford");
            Element megtett_km = doc.createElement("megtett_km"); megtett_km.setTextContent("120000");
            Element szervizben = doc.createElement("szervizben"); szervizben.setTextContent("false");
            Element garancia_vege = doc.createElement("garancia_vege"); garancia_vege.setTextContent("2026-01-01");
            Element teherbiras_tonna = doc.createElement("teherbiras_tonna"); teherbiras_tonna.setTextContent("6");

            jarmu.appendChild(rendszam);
            jarmu.appendChild(marka);
            jarmu.appendChild(megtett_km);
            jarmu.appendChild(szervizben);
            jarmu.appendChild(garancia_vege);
            jarmu.appendChild(teherbiras_tonna);

            root.appendChild(jarmu);

            // -------------------------
            // Üzlethelyiség
            // -------------------------
            Element uzlet = doc.createElement("uzlethelyseg");
            uzlet.setAttribute("uid", "u3");
            Element cimUzlet = doc.createElement("cim");
            Element orszagU = doc.createElement("orszag"); orszagU.setTextContent("Magyarország");
            Element varosU = doc.createElement("varos"); varosU.setTextContent("Győr");
            Element iranyitoszamU = doc.createElement("iranyitoszam"); iranyitoszamU.setTextContent("9022");
            Element utcaU = doc.createElement("utca"); utcaU.setTextContent("Baross utca");
            Element hazszamU = doc.createElement("hazszam"); hazszamU.setTextContent("5");
            cimUzlet.appendChild(orszagU);
            cimUzlet.appendChild(varosU);
            cimUzlet.appendChild(iranyitoszamU);
            cimUzlet.appendChild(utcaU);
            cimUzlet.appendChild(hazszamU);
            Element atvetel = doc.createElement("atvetel_lehetseges"); atvetel.setTextContent("true");

            uzlet.appendChild(cimUzlet);
            uzlet.appendChild(atvetel);

            root.appendChild(uzlet);

            // -------------------------
            // Alkalmazott
            // -------------------------
            Element alkalmazott = doc.createElement("alkalmazott");
            alkalmazott.setAttribute("alid", "a50");
            alkalmazott.setAttribute("uzlet_ref", "u3");
            Element alNev = doc.createElement("nev"); alNev.setTextContent("Szabó Anna");
            Element alFizetes = doc.createElement("fizetes"); alFizetes.setTextContent("350000");
            Element kezdes = doc.createElement("kezdes_eve"); kezdes.setTextContent("2020");
            Element vezeto = doc.createElement("vezeto"); vezeto.setTextContent("false");

            alkalmazott.appendChild(alNev);
            alkalmazott.appendChild(alFizetes);
            alkalmazott.appendChild(kezdes);
            alkalmazott.appendChild(vezeto);

            root.appendChild(alkalmazott);

            // =========================
            // Kiírás
            // =========================
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);

            // konzol
            transformer.transform(source, new StreamResult(System.out));

            // fájl
            transformer.transform(source, new StreamResult(new File("Y4F3Z7XMLOwn.xml")));

            System.out.println("\nKész!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}