package y4f3z7;

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


public class ProcessJsongy {

    public static void main(String[] args) throws Exception {

        // =====================================================
        // 0.c. Feladat: JSON fa betöltése memóriába
        // =====================================================
        ObjectMapper m = new ObjectMapper();
        JsonNode root = m.readTree(new File("JSON.json"));
        JsonNode vendeglatas = root.get("vendeglatas");
        JsonNode foszakacsok = vendeglatas.get("foszakacs");
        JsonNode szakacsok = vendeglatas.get("szakacs");
        JsonNode ettermek = vendeglatas.get("etterem");
        JsonNode rendelesek = vendeglatas.get("rendeles");
        JsonNode vendegek = vendeglatas.get("vendeg");

        // Sikeres betöltés ellenőrzése
        System.out.println(vendeglatas.toPrettyString());

        JsonNode schemaNode = m.readTree(new File("JSON_SCHEMA.json"));
        JsonSchema schema = JsonSchemaFactory
                .getInstance(SpecVersion.VersionFlag.V4)  // Schema verziója
                .getSchema(schemaNode);

        Set<ValidationMessage> errors = schema.validate(root);
        if (errors.isEmpty()) {
            System.out.println("Valid JSON");
        } else {
            System.out.println("Hibás JSON:");
            errors.forEach(e -> System.out.println(e.getMessage()));
        }

        if (foszakacsok != null && foszakacsok.isArray()) {
            for (JsonNode szakacs : foszakacsok) {
                String nev = szakacs.get("nev").asText();
                String kor = szakacs.get("eletkor").asText();
                String iskola = szakacs.get("vegzettseg").asText();
                System.out.println("Név: " + nev + " | Kor: " + kor + " | Végzettség: " + iskola);
            }
        }

        System.out.println("=== ÉTTERMEK ÉS SZAKÁCSAIK ===");
        for (JsonNode etterem : ettermek) {
            String eKod = etterem.get("_ekod").asText();
            String eNev = etterem.get("nev").asText();
            System.out.println("\nÉtterem: " + eNev + " [" + eKod + "]");
            System.out.println("---------------------------");
            for (JsonNode szakacs : szakacsok) {
                if (szakacs.get("_e_sz").asText().equals(eKod)) {
                    System.out.println("- " + szakacs.get("nev").asText()
                            + " (" + szakacs.get("reszleg").asText() + ")");
                }
            }
        }

        System.out.println("=== Átlagos rendelési érték ===");
        double osszeg = 0;
        int db = 0;
        for (JsonNode r : rendelesek) {
            osszeg += r.get("osszeg").asDouble();
            db++;
        }
        System.out.println("AVG: " + (osszeg / db) + " Ft");
        //3 részre ostani előadás, gyak, téma


    }
}