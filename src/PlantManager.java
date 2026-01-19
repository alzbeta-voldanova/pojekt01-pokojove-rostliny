import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlantManager {

    private final List<Plant> plants = new ArrayList<>();
    private SortMode sortMode = SortMode.NAME; // výchozí řazení

    // 1) přidání nové květiny
    public void addPlant(Plant plant) {
        plants.add(plant);
        applyCurrentSorting();
    }

    // 2) získání květiny na indexu
    public Plant getPlant(int index) {
        return plants.get(index);
    }

    // 3) odebrání květiny
    public Plant removePlant(int index) {
        return plants.remove(index);
    }

    // 4) kopie seznamu
    public List<Plant> getPlants() {
        return new ArrayList<>(plants);
    }

    // 5) rostliny, které je třeba zalít
    public List<Plant> getPlantsToWater() {
        List<Plant> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Plant plant : plants) {
            LocalDate nextWatering =
                    plant.getWatering().plusDays(plant.getFrequencyOfWatering());

            if (!nextWatering.isAfter(today)) {
                result.add(plant);
            }
        }
        return result;
    }

    // --- ŘAZENÍ ---

    public void sortByName() {
        plants.sort(Comparator.comparing(
                Plant::getName,
                String.CASE_INSENSITIVE_ORDER
        ));
        sortMode = SortMode.NAME;
    }

    public void sortByWatering() {
        plants.sort(Comparator.comparing(Plant::getWatering));
        sortMode = SortMode.WATERING;
    }

    private void applyCurrentSorting() {
        if (sortMode == SortMode.NAME) {
            sortByName();
        } else {
            sortByWatering();
        }
    }

    public int size() {
        return plants.size();
    }

    private static final DateTimeFormatter DATE_ISO = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter DATE_CZ = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Načte rostliny ze souboru (tabulátory). Načítá do dočasného seznamu:
     * - pokud vše proběhne OK, přepíše interní seznam plants,
     * - pokud nastane chyba, plants zůstane beze změny.
     *
     * Formát řádku: name \t notes \t frequency \t watering \t planted
     */
    public void loadFromFile(Path path) throws IOException, PlantException {
        List<Plant> loaded = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\t", -1);
                if (parts.length != 5) {
                    throw new PlantException("Řádek " + lineNumber + ": očekávám 5 položek oddělených tabulátorem, ale je "
                            + parts.length + ". Řádek: " + line);
                }

                String name = parts[0].trim();
                String notes = parts[1]; // může být prázdné
                String frequencyText = parts[2].trim();
                String wateringText = parts[3].trim();
                String plantedText = parts[4].trim();


                int frequency;
                try {
                    frequency = Integer.parseInt(frequencyText);
                } catch (NumberFormatException e) {
                    throw new PlantException("Řádek " + lineNumber + ": frekvence zálivky není číslo: '" + frequencyText + "'");
                }

                LocalDate watering = parseDate(wateringText, lineNumber, "watering");
                LocalDate planted = parseDate(plantedText, lineNumber, "planted");


                try {
                    Plant plant = new Plant(name, notes, planted, watering, frequency);
                    loaded.add(plant);
                } catch (PlantException e) {
                    throw new PlantException("Řádek " + lineNumber + ": " + e.getMessage());
                }
            }
        }

        // úspěšné načtení: teď teprve přepíšeme interní seznam
        plants.clear();
        plants.addAll(loaded);
        applyCurrentSorting(); // respektuje sortMode (výchozí NAME)
    }

    /**
     * Uloží interní seznam do souboru (tabulátory) ve formátu:
     * name \t notes \t frequency \t watering \t planted
     */
    public void saveToFile(Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Plant plant : plants) {
                String line = plant.getName() + "\t"
                        + plant.getNotes() + "\t"
                        + plant.getFrequencyOfWatering() + "\t"
                        + plant.getWatering().format(DATE_ISO) + "\t"
                        + plant.getPlanted().format(DATE_ISO);

                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static LocalDate parseDate(String value, int lineNumber, String fieldName) throws PlantException {
        if (value == null || value.isBlank()) {
            throw new PlantException("Řádek " + lineNumber + ": chybí datum '" + fieldName + "'");
        }

        try {
            return LocalDate.parse(value, DATE_ISO);
        } catch (DateTimeParseException ignored) {
            try {
                return LocalDate.parse(value, DATE_CZ);
            } catch (DateTimeParseException e) {
                throw new PlantException("Řádek " + lineNumber + ": neplatné datum '" + fieldName + "': '" + value
                        + "' (čekám yyyy-MM-dd nebo dd.MM.yyyy)");
            }
        }
    }


}
