package com.bolt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Modern Adatbázis Rendszerek MSc – JSON házi feladat
 * Adatszerkezet: Bolt (ügyfelek, áruk, rendelések, futárok, kiszállítások, járművek, üzlethelyiségek, alkalmazottak)
 */
public class ProcessJson_sajat {

    public static void main(String[] args) throws Exception {

        // =========================================================
        // 0. Feladat: Beolvasás és validálás
        // =========================================================
        ObjectMapper m = new ObjectMapper();
        JsonNode root       = m.readTree(new File("bolt.json"));
        JsonNode bolt       = root.get("bolt");

        JsonNode ugyfelek       = bolt.get("ugyfelek");
        JsonNode aruk           = bolt.get("aruk");
        JsonNode rendelesek     = bolt.get("rendelesek");
        JsonNode futarok        = bolt.get("futarok");
        JsonNode kiszallitasok  = bolt.get("kiszallitasok");
        JsonNode jarmuvek       = bolt.get("jarmuvek");
        JsonNode uzlethelyisegek= bolt.get("uzlethelyisegek");
        JsonNode alkalmazottak  = bolt.get("alkalmazottak");

        System.out.println("=== JSON beolvasva ===");
        System.out.println(bolt.toPrettyString());

        // JSON Schema validálás
        System.out.println("\n=== JSON Schema validálás ===");
        JsonNode schemaNode = m.readTree(new File("bolt_schema.json"));
        JsonSchema schema = JsonSchemaFactory
                .getInstance(SpecVersion.VersionFlag.V4)
                .getSchema(schemaNode);
        Set<ValidationMessage> errors = schema.validate(root);
        if (errors.isEmpty()) {
            System.out.println("Valid JSON – nincs hiba.");
        } else {
            System.out.println("Hibás JSON:");
            errors.forEach(e -> System.out.println("  " + e.getMessage()));
        }

        // =========================================================
        // 1. Feladat: Fizetetlen rendelések listázása (Szűrés)
        //    Írjuk ki azokat a rendeléseket, amelyek még nincsenek kifizetve,
        //    az ügyfél nevével együtt.
        // =========================================================
        System.out.println("\n=== 1. Feladat: Fizetetlen rendelések ===");
        for (JsonNode r : rendelesek) {
            if (!r.get("fizetve").asBoolean()) {
                String ugyfelRef = r.get("_ugyfel_ref").asText();
                String ugyfelNev = "";
                for (JsonNode u : ugyfelek) {
                    if (u.get("_cid").asText().equals(ugyfelRef)) {
                        ugyfelNev = u.get("nev").asText();
                        break;
                    }
                }
                System.out.println("Rendelés: " + r.get("_rid").asText()
                        + " | Ügyfél: " + ugyfelNev
                        + " | Összeg: " + r.get("osszeg").asInt() + " Ft"
                        + " | Dátum: " + r.get("rendeles_datum").asText());
            }
        }

        // =========================================================
        // 2. Feladat: Kategóriánkénti átlagos értékelés (Aggregáció)
        //    Számítsuk ki az összes kategóriára az áruk átlagos értékelését.
        // =========================================================
        System.out.println("\n=== 2. Feladat: Átlagos értékelés kategóriánként ===");
        Map<String, Double> kategoriaSzorzat = new HashMap<>();
        Map<String, Integer> kategoriaDb     = new HashMap<>();
        for (JsonNode a : aruk) {
            String kat = a.get("kategoria").asText();
            double sum = 0;
            int cnt = 0;
            for (JsonNode e : a.get("ertekelesek")) {
                sum += e.asDouble();
                cnt++;
            }
            kategoriaSzorzat.merge(kat, sum, Double::sum);
            kategoriaDb.merge(kat, cnt, Integer::sum);
        }
        for (String kat : kategoriaSzorzat.keySet()) {
            double atlag = kategoriaSzorzat.get(kat) / kategoriaDb.get(kat);
            System.out.printf("%-15s  átlag értékelés: %.2f%n", kat, atlag);
        }

        // =========================================================
        // 3. Feladat: Ügyfél + rendelés + kiszállítás (Összekapcsolás)
        //    Írjuk ki, hogy melyik ügyfél rendelését melyik futár szállította ki,
        //    és mikor.
        // =========================================================
        System.out.println("\n=== 3. Feladat: Kiszállítások részletei (JOIN) ===");
        for (JsonNode k : kiszallitasok) {
            String rRef = k.get("_rendeles_ref").asText();
            String fRef = k.get("_futar_ref").asText();

            String ugyfelNev = "";
            for (JsonNode r : rendelesek) {
                if (r.get("_rid").asText().equals(rRef)) {
                    String uRef = r.get("_ugyfel_ref").asText();
                    for (JsonNode u : ugyfelek)
                        if (u.get("_cid").asText().equals(uRef)) {
                            ugyfelNev = u.get("nev").asText();
                            break;
                        }
                    break;
                }
            }

            String futarNev = "";
            for (JsonNode f : futarok)
                if (f.get("_fid").asText().equals(fRef)) {
                    futarNev = f.get("nev").asText();
                    break;
                }

            System.out.println("Rendelés: " + rRef
                    + " | Ügyfél: " + ugyfelNev
                    + " | Futár: " + futarNev
                    + " | Kiszállítva: " + k.get("kiszallitva").asText());
        }

        // =========================================================
        // 4. Feladat: Legjobban fogyó termék (Aggregáció)
        //    Melyik árut rendelték a legtöbb példányban összesen?
        // =========================================================
        System.out.println("\n=== 4. Feladat: Legjobban fogyó termék ===");
        Map<String, Integer> aruDb = new HashMap<>();
        for (JsonNode r : rendelesek) {
            for (JsonNode t : r.get("tetelek")) {
                String aRef = t.get("_aru_ref").asText();
                int db = t.get("darab").asInt();
                aruDb.merge(aRef, db, Integer::sum);
            }
        }
        String topAid = "";
        int topDb = 0;
        for (Map.Entry<String, Integer> e : aruDb.entrySet()) {
            if (e.getValue() > topDb) {
                topDb  = e.getValue();
                topAid = e.getKey();
            }
        }
        String topNev = "";
        for (JsonNode a : aruk)
            if (a.get("_aid").asText().equals(topAid)) {
                topNev = a.get("nev").asText();
                break;
            }
        System.out.println("Legtöbbet rendelt termék: " + topNev + " (" + topDb + " db)");

        // =========================================================
        // 5. Feladat: Alkalmazottak üzlethelyiségenként (Összekapcsolás + szűrés)
        //    Írjuk ki üzlethelyiségenként az ott dolgozó alkalmazottakat,
        //    és jelöljük meg, ha valaki vezető.
        // =========================================================
        System.out.println("\n=== 5. Feladat: Alkalmazottak üzlethelyiségenként ===");
        for (JsonNode u : uzlethelyisegek) {
            String uid  = u.get("_uid").asText();
            String varos = u.get("cim").get("varos").asText();
            System.out.println("\nÜzlet [" + uid + "] – " + varos + ":");
            for (JsonNode a : alkalmazottak) {
                if (a.get("_uzlet_ref").asText().equals(uid)) {
                    String vezeto = a.get("vezeto").asBoolean() ? " [VEZETŐ]" : "";
                    System.out.println("  - " + a.get("nev").asText()
                            + " | Fizetés: " + a.get("fizetes").asInt() + " Ft"
                            + vezeto);
                }
            }
        }

        // =========================================================
        // 6. Feladat: Adatmódosítás – Raktárkészlet csökkentése + új mező
        //    Minden rendelt tételhez csökkentsük az áru raktárkészletét,
        //    és adjunk minden áruhoz egy "kapható" boolean mezőt
        //    (true, ha raktarkeszlet > 0).
        // =========================================================
        System.out.println("\n=== 6. Feladat: Raktárkészlet frissítése (Adatmódosítás) ===");
        for (JsonNode r : rendelesek) {
            for (JsonNode t : r.get("tetelek")) {
                String aRef = t.get("_aru_ref").asText();
                int rendeltDb = t.get("darab").asInt();
                for (JsonNode a : aruk) {
                    if (a.get("_aid").asText().equals(aRef)) {
                        ObjectNode obj = (ObjectNode) a;
                        int ujKeszlet = Math.max(0, a.get("raktarkeszlet").asInt() - rendeltDb);
                        obj.put("raktarkeszlet", ujKeszlet);
                        obj.put("kapható", ujKeszlet > 0);
                    }
                }
            }
        }
        System.out.println("Frissített áruk:");
        for (JsonNode a : aruk) {
            System.out.println("  " + a.get("nev").asText()
                    + " | Készlet: " + a.get("raktarkeszlet").asInt()
                    + " | Kapható: " + a.get("kapható").asBoolean());
        }

        // =========================================================
        // 7. Feladat: Új JSON fájl – ügyfelek bevétele
        //    Írjunk ki egy új JSON-t, amely ügyfélként összesíti
        //    az általuk leadott rendelések összegét.
        // =========================================================
        System.out.println("\n=== 7. Feladat: Új JSON fájl – ügyfelek bevétele ===");
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode ujLista = mapper.createArrayNode();

        for (JsonNode u : ugyfelek) {
            String uid = u.get("_cid").asText();
            double osszeg = 0;
            for (JsonNode r : rendelesek)
                if (r.get("_ugyfel_ref").asText().equals(uid))
                    osszeg += r.get("osszeg").asDouble();

            ObjectNode csomopont = mapper.createObjectNode();
            csomopont.put("ugyfel_nev", u.get("nev").asText());
            csomopont.put("ugyfel_id",  uid);
            csomopont.put("osszes_koltes", osszeg);
            ujLista.add(csomopont);
        }

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File("ugyfel_bevetel.json"), ujLista);
        System.out.println("Fájl kiírva: ugyfel_bevetel.json");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ujLista));
    }
}