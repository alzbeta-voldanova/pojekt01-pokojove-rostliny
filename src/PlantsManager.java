import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlantsManager {

    private final List<Plant> plants = new ArrayList<>();

    // 1) přidání nové květiny
    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    // 2) získání květiny na zadaném indexu
    public Plant getPlant(int index) {
        return plants.get(index);
    }

    // 3) odebrání květiny ze seznamu
    public Plant removePlant(int index) {
        return plants.remove(index);
    }

    // 4) získání kopie seznamu květin
    public List<Plant> getPlants() {
        return new ArrayList<>(plants);
    }

    // 5) seznam rostlin, které je třeba zalít
    public List<Plant> getPlantsToWater() {
        List<Plant> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Plant plant : plants) {
            LocalDate nextWatering = plant.getWatering()
                    .plusDays(plant.getFrequencyOfWatering());

            if (!nextWatering.isAfter(today)) {
                result.add(plant);
            }
        }
        return result;
    }

    // pomocná metoda – není v zadání, ale hodí se
    public int size() {
        return plants.size();
    }
}
