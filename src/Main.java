import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        PlantManager manager = new PlantManager();

        // 1) Načti seznam květin ze souboru kvetiny.txt
        try {
            manager.loadFromFile(Path.of("data/kvetiny.txt"));
            System.out.println("1) OK: Načteno kvetiny.txt, počet rostlin = " + manager.size());
        } catch (PlantException e) {
            System.err.println("1) Chyba v datech: " + e.getMessage());
            manager = new PlantManager();
        } catch (IOException e) {
            System.err.println("1) Chyba při práci se souborem: " + e.getMessage());
            manager = new PlantManager();
        }

        // 2) Vypiš informace o zálivce pro všechny květiny ze seznamu
        System.out.println("\n2) Informace o zálivce pro všechny rostliny:");
        for (Plant plant : manager.getPlants()) {
            System.out.println(plant.getWateringInfo());
        }

        // 3) Přidej novou květinu do seznamu (údaje si vymysli)
        try {
            Plant nova = new Plant(
                    "Monstera deliciosa",
                    "Nově přidaná rostlina",
                    LocalDate.now().minusDays(30),
                    LocalDate.now().minusDays(5),
                    7
            );
            manager.addPlant(nova);
            System.out.println("\n3) Přidána nová rostlina: " + nova.getName());
        } catch (PlantException e) {
            System.err.println("\n3) Nepodařilo se přidat novou rostlinu: " + e.getMessage());
        }

        // 4) Přidej 10 rostlin „Tulipán na prodej 1..10“
        for (int i = 1; i <= 10; i++) {
            try {
                Plant tulipan = new Plant(
                        "Tulipán na prodej " + i,
                        "",
                        LocalDate.now(),
                        LocalDate.now(),
                        14
                );
                manager.addPlant(tulipan);
            } catch (PlantException e) {
                System.err.println("4) Nepodařilo se přidat Tulipán " + i + ": " + e.getMessage());
            }
        }
        System.out.println("\n4) Přidáno 10 tulipánů. Aktuální počet rostlin: " + manager.size());


        // 5) Květinu na třetí pozici odeber ze seznamu (index 2)
        try {
            Plant removed = manager.removePlant(2);
            System.out.println("\n5) Odebrána rostlina na 3. pozici: " + removed.getName());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("\n5) Nešlo odebrat 3. položku – seznam je moc krátký.");
        }

        // 6) Ulož seznam květin do nového souboru
        Path output = Path.of("data/kvetiny-novy.txt");
        try {
            manager.saveToFile(output);
            System.out.println("\n6) Uloženo do: " + output);
        } catch (IOException e) {
            System.err.println("\n6) Chyba při ukládání: " + e.getMessage());
        }

        // 7) Vyzkoušej opětovné načtení vygenerovaného souboru
        PlantManager reloaded = new PlantManager();
        try {
            reloaded.loadFromFile(output);
            System.out.println("\n7) Znovu načteno z " + output + ", počet rostlin: " + reloaded.size());
        } catch (Exception e) {
            System.err.println("\n7) Chyba při opětovném načtení: " + e.getMessage());
            System.err.println("   Pokračuji s prázdným seznamem.");
            reloaded = new PlantManager();
        }

        // 8) Vyzkoušej seřazení podle různých kritérií a výpis seřazeného seznamu
        System.out.println("\n8) Seřazení podle názvu:");
        reloaded.sortByName();
        for (Plant plant : reloaded.getPlants()) {
            System.out.println(plant.getName() + " | zálivka: " + plant.getWatering());
        }

        System.out.println("\n8) Seřazení podle poslední zálivky:");
        reloaded.sortByWatering();
        for (Plant plant : reloaded.getPlants()) {
            System.out.println(plant.getName() + " | zálivka: " + plant.getWatering());
        }

    }
}

//        // B) Uložení do nového souboru
//        try {
//            manager.saveToFile(Path.of("data/kvetiny-vystup.txt"));
//            System.out.println("OK: Uloženo do data/kvetiny-vystup.txt");
//        } catch (Exception e) {
//            System.err.println("CHYBA: Ukládání selhalo: " + e.getMessage());
//        }
//
//        // C) Opětovné načtení výstupu
//        PlantManager manager2 = new PlantManager();
//        try {
//            manager2.loadFromFile(Path.of("data/kvetiny-vystup.txt"));
//            System.out.println("OK: Znovu načteno kvetiny-vystup.txt, počet rostlin = " + manager2.size());
//        } catch (Exception e) {
//            System.err.println("CHYBA: Opětovné načtení výstupu selhalo: " + e.getMessage());
//        }
//
//        // D) Vadný soubor - špatné datum
//        PlantManager badDate = new PlantManager();
//        try {
//            badDate.loadFromFile(Path.of("data/kvetiny-spatne-datum.txt"));
//            System.err.println("CHYBA: vadný soubor se špatným datem se neměl načíst!");
//        } catch (Exception e) {
//            System.out.println("OK: Chyba u špatného data zachycena: " + e.getMessage());
//            System.out.println("Počet rostlin po chybě = " + badDate.size());
//        }
//
//        // E) Vadný soubor - špatná frekvence
//        PlantManager badFreq = new PlantManager();
//        try {
//            badFreq.loadFromFile(Path.of("data/kvetiny-spatne-frekvence.txt"));
//            System.err.println("CHYBA: vadný soubor se špatnou frekvencí se neměl načíst!");
//        } catch (Exception e) {
//            System.out.println("OK: Chyba u špatné frekvence zachycena: " + e.getMessage());
//            System.out.println("Počet rostlin po chybě = " + badFreq.size());
//        }


