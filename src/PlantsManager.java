import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlantsManager {


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
}
