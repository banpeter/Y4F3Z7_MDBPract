import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;

@SuppressWarnings("ALL")
public class Y4F3Z7DomReadOwn {

    public static void main(String[] args) {

        var filePath = "./Y4F3Z7_XDM.xml";
        var file = new File(filePath);

        Document document = null;

        try {
            document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(file);
        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
            return;
        }

        document.normalize();

        var root = document.getDocumentElement();
        System.out.println("Root element: " + root.getTagName() + "\n");

        readUgyfel(root);
        readAru(root);
        readRendeles(root);
        readFutar(root);
        readKiszallitas(root);
        readJarmu(root);
        readUzlethelyseg(root);
        readAlkalmazott(root);
    }



    public static void readUgyfel(Element element) {

        var ugyfelek = element.getElementsByTagName("ugyfel");

        for (int i = 0; i < ugyfelek.getLength(); i++) {

            var ugyfel = (Element) ugyfelek.item(i);

            System.out.println("Current element: " + ugyfel.getTagName());

            var id = ugyfel.getAttribute("cid");
            System.out.println("Ügyfél ID: " + id);

            var nev = ugyfel.getElementsByTagName("nev").item(0).getTextContent();
            System.out.println("Név: " + nev);

            var email = ugyfel.getElementsByTagName("email").item(0).getTextContent();
            System.out.println("Email: " + email);

            var telefon = ugyfel.getElementsByTagName("telefonszam").item(0).getTextContent();
            System.out.println("Telefon: " + telefon);

            var kartya = ugyfel.getElementsByTagName("kartyaszam").item(0).getTextContent();
            System.out.println("Kártyaszám: " + kartya);

            var ceg = ugyfel.getElementsByTagName("ceg").item(0).getTextContent();
            System.out.println("Cég: " + ceg);

            var cim = (Element) ugyfel.getElementsByTagName("cim").item(0);

            var orszag = cim.getElementsByTagName("orszag").item(0).getTextContent();
            var varos = cim.getElementsByTagName("varos").item(0).getTextContent();
            var irany = cim.getElementsByTagName("iranyitoszam").item(0).getTextContent();
            var utca = cim.getElementsByTagName("utca").item(0).getTextContent();
            var hazszam = cim.getElementsByTagName("hazszam").item(0).getTextContent();

            System.out.println("Cím: " + orszag + ", " + varos + ", " + irany + ", " + utca + " " + hazszam);

            System.out.println();
        }
    }



    public static void readAru(Element element) {

        var aruk = element.getElementsByTagName("aru");

        for (int i = 0; i < aruk.getLength(); i++) {

            var aru = (Element) aruk.item(i);

            System.out.println("Current element: " + aru.getTagName());

            var id = aru.getAttribute("aid");
            System.out.println("Áru ID: " + id);

            var nev = aru.getElementsByTagName("nev").item(0).getTextContent();
            System.out.println("Név: " + nev);

            var gyarto = aru.getElementsByTagName("gyarto").item(0).getTextContent();
            System.out.println("Gyártó: " + gyarto);

            var kategoria = aru.getElementsByTagName("kategoria").item(0).getTextContent();
            System.out.println("Kategória: " + kategoria);

            var raktar = aru.getElementsByTagName("raktarkeszlet").item(0).getTextContent();
            System.out.println("Raktárkészlet: " + raktar);

            var garancia = aru.getElementsByTagName("garancia_honap").item(0).getTextContent();
            System.out.println("Garancia (hó): " + garancia);

            var ertekek = aru.getElementsByTagName("ertek");

            System.out.print("Értékelések: ");

            for (int j = 0; j < ertekek.getLength(); j++) {

                var ertek = ertekek.item(j).getTextContent();

                System.out.print(ertek);

                if (j < ertekek.getLength() - 1)
                    System.out.print(", ");
            }

            System.out.println("\n");
        }
    }



    public static void readRendeles(Element element) {

        var rendelesek = element.getElementsByTagName("rendeles");

        for (int i = 0; i < rendelesek.getLength(); i++) {

            var rendeles = (Element) rendelesek.item(i);

            System.out.println("Current element: " + rendeles.getTagName());

            var id = rendeles.getAttribute("rid");
            var ugyfel = rendeles.getAttribute("ugyfel_ref");

            System.out.println("Rendelés ID: " + id);
            System.out.println("Ügyfél referencia: " + ugyfel);

            var datum = rendeles.getElementsByTagName("rendeles_datum").item(0).getTextContent();
            System.out.println("Dátum: " + datum);

            var fizetve = rendeles.getElementsByTagName("fizetve").item(0).getTextContent();
            System.out.println("Fizetve: " + fizetve);

            var osszeg = rendeles.getElementsByTagName("osszeg").item(0).getTextContent();
            System.out.println("Összeg: " + osszeg);

            var tetelek = rendeles.getElementsByTagName("tetel");

            for (int j = 0; j < tetelek.getLength(); j++) {

                var tetel = (Element) tetelek.item(j);

                var aru = tetel.getAttribute("aru_ref");

                var darab = tetel.getElementsByTagName("darab").item(0).getTextContent();

                System.out.println("Termék ref: " + aru);
                System.out.println("Darab: " + darab);
            }

            System.out.println();
        }
    }


    public static void readFutar(Element element) {

        var futarok = element.getElementsByTagName("futar");

        for (int i = 0; i < futarok.getLength(); i++) {

            var futar = (Element) futarok.item(i);

            System.out.println("Current element: " + futar.getTagName());

            var id = futar.getAttribute("fid");

            System.out.println("Futár ID: " + id);

            var nev = futar.getElementsByTagName("nev").item(0).getTextContent();
            System.out.println("Név: " + nev);

            var munkaido = (Element) futar.getElementsByTagName("munkaido").item(0);

            var kezd = munkaido.getElementsByTagName("kezd").item(0).getTextContent();
            var vegez = munkaido.getElementsByTagName("vegez").item(0).getTextContent();

            System.out.println("Munkaidő: " + kezd + " - " + vegez);

            var fizetes = futar.getElementsByTagName("fizetes").item(0).getTextContent();
            System.out.println("Fizetés: " + fizetes);

            var kartya = futar.getElementsByTagName("benzinkartya").item(0).getTextContent();
            System.out.println("Benzinkártya: " + kartya);

            System.out.println();
        }
    }



    public static void readKiszallitas(Element element) {

        var kiszallitasok = element.getElementsByTagName("kiszallitas");

        for (int i = 0; i < kiszallitasok.getLength(); i++) {

            var k = (Element) kiszallitasok.item(i);

            System.out.println("Current element: " + k.getTagName());

            var rendeles = k.getAttribute("rendeles_ref");
            var futar = k.getAttribute("futar_ref");

            System.out.println("Rendelés ref: " + rendeles);
            System.out.println("Futár ref: " + futar);

            var felvett = k.getElementsByTagName("felvett").item(0).getTextContent();
            var kiszallitva = k.getElementsByTagName("kiszallitva").item(0).getTextContent();

            System.out.println("Felvett: " + felvett);
            System.out.println("Kiszállítva: " + kiszallitva);

            System.out.println();
        }
    }



    public static void readJarmu(Element element) {

        var jarmuvek = element.getElementsByTagName("jarmu");

        for (int i = 0; i < jarmuvek.getLength(); i++) {

            var j = (Element) jarmuvek.item(i);

            System.out.println("Current element: " + j.getTagName());

            var id = j.getAttribute("jid");

            System.out.println("Jármű ID: " + id);

            System.out.println("Rendszám: " + j.getElementsByTagName("rendszam").item(0).getTextContent());
            System.out.println("Márka: " + j.getElementsByTagName("marka").item(0).getTextContent());
            System.out.println("Km: " + j.getElementsByTagName("megtett_km").item(0).getTextContent());
            System.out.println("Szervizben: " + j.getElementsByTagName("szervizben").item(0).getTextContent());
            System.out.println("Garancia vége: " + j.getElementsByTagName("garancia_vege").item(0).getTextContent());
            System.out.println("Teherbírás: " + j.getElementsByTagName("teherbiras_tonna").item(0).getTextContent());

            System.out.println();
        }
    }

    

    public static void readUzlethelyseg(Element element) {

        var uzletek = element.getElementsByTagName("uzlethelyseg");

        for (int i = 0; i < uzletek.getLength(); i++) {

            var uzlet = (Element) uzletek.item(i);

            System.out.println("Current element: " + uzlet.getTagName());

            var id = uzlet.getAttribute("uid");

            System.out.println("Üzlet ID: " + id);

            var cim = (Element) uzlet.getElementsByTagName("cim").item(0);

            System.out.println("Város: " + cim.getElementsByTagName("varos").item(0).getTextContent());
            System.out.println("Utca: " + cim.getElementsByTagName("utca").item(0).getTextContent());

            var atvetel = uzlet.getElementsByTagName("atvetel_lehetseges").item(0).getTextContent();
            System.out.println("Átvétel lehetséges: " + atvetel);

            System.out.println();
        }
    }


    public static void readAlkalmazott(Element element) {

        var alkalmazottak = element.getElementsByTagName("alkalmazott");

        for (int i = 0; i < alkalmazottak.getLength(); i++) {

            var al = (Element) alkalmazottak.item(i);

            System.out.println("Current element: " + al.getTagName());

            var id = al.getAttribute("alid");
            var uzlet = al.getAttribute("uzlet_ref");

            System.out.println("Alkalmazott ID: " + id);
            System.out.println("Üzlet ref: " + uzlet);

            System.out.println("Név: " + al.getElementsByTagName("nev").item(0).getTextContent());
            System.out.println("Fizetés: " + al.getElementsByTagName("fizetes").item(0).getTextContent());
            System.out.println("Kezdés éve: " + al.getElementsByTagName("kezdes_eve").item(0).getTextContent());
            System.out.println("Vezető: " + al.getElementsByTagName("vezeto").item(0).getTextContent());

            System.out.println();
        }
    }
}
