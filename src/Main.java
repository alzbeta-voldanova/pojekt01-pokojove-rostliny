import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        // A) Načtení správného souboru
        PlantManager manager = new PlantManager();
        try {
            manager.loadFromFile(Path.of("data/kvetiny.txt"));
            System.out.println("OK: Načteno kvetiny.txt, počet rostlin = " + manager.size());
        } catch (Exception e) {
            System.err.println("CHYBA: kvetiny.txt se nemělo pokazit: " + e.getMessage());
        }

        // B) Uložení do nového souboru
        try {
            manager.saveToFile(Path.of("data/kvetiny-vystup.txt"));
            System.out.println("OK: Uloženo do data/kvetiny-vystup.txt");
        } catch (Exception e) {
            System.err.println("CHYBA: Ukládání selhalo: " + e.getMessage());
        }

        // C) Opětovné načtení výstupu
        PlantManager manager2 = new PlantManager();
        try {
            manager2.loadFromFile(Path.of("data/kvetiny-vystup.txt"));
            System.out.println("OK: Znovu načteno kvetiny-vystup.txt, počet rostlin = " + manager2.size());
        } catch (Exception e) {
            System.err.println("CHYBA: Opětovné načtení výstupu selhalo: " + e.getMessage());
        }

        // D) Vadný soubor - špatné datum
        PlantManager badDate = new PlantManager();
        try {
            badDate.loadFromFile(Path.of("data/kvetiny-spatne-datum.txt"));
            System.err.println("CHYBA: vadný soubor se špatným datem se neměl načíst!");
        } catch (Exception e) {
            System.out.println("OK: Chyba u špatného data zachycena: " + e.getMessage());
            System.out.println("Počet rostlin po chybě = " + badDate.size());
        }

        // E) Vadný soubor - špatná frekvence
        PlantManager badFreq = new PlantManager();
        try {
            badFreq.loadFromFile(Path.of("data/kvetiny-spatne-frekvence.txt"));
            System.err.println("CHYBA: vadný soubor se špatnou frekvencí se neměl načíst!");
        } catch (Exception e) {
            System.out.println("OK: Chyba u špatné frekvence zachycena: " + e.getMessage());
            System.out.println("Počet rostlin po chybě = " + badFreq.size());
        }
    }
}
