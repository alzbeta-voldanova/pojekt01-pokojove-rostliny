import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant {

    private static final int DEFAULT_WATERING_FREQUENCY = 7;
    private static final DateTimeFormatter CZECH_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate watering;
    private int frequencyOfWatering;

    public Plant(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
        this.name = name;
        this.notes = notes;
        setPlanted(planted);
        setFrequencyOfWatering(frequencyOfWatering);
        setWatering(watering);
    }

    public Plant(String name, int frequencyOfWatering) throws PlantException {
        this(name, "", LocalDate.now(), LocalDate.now(), frequencyOfWatering);
    }

    public Plant(String name) throws PlantException {
        this(name, DEFAULT_WATERING_FREQUENCY);
    }

    public String getWateringInfo() {
        return "Rostlina " + name +
                " byla naposledy zalita " + formatDateCz(watering) +
                " a další zaléváni je naplánováno na " + formatDateCz(nextWatering()) + ".";
    }

    private String formatDateCz(LocalDate date) {
        return date.format(CZECH_DATE);
    }

    public LocalDate nextWatering() {
        return watering.plusDays(frequencyOfWatering);
    }

    public void doWateringNow() {
        this.watering = LocalDate.now();
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getPlanted() { return planted; }
    public void setPlanted(LocalDate planted) {
        this.planted = planted;
    }

    public LocalDate getWatering() { return watering; }
    public void setWatering(LocalDate watering) throws PlantException {
        if (planted != null && watering != null && watering.isBefore(planted)) {
            throw new PlantException(
                    "Datum poslední zálivky (" + watering + ") nesmí být starší než datum zasazení (" + planted + ")."
            );
        }
        this.watering = watering;
    }

    public int getFrequencyOfWatering() { return frequencyOfWatering; }
    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        if (frequencyOfWatering <= 0) {
            throw new PlantException("Frekvence zálivky musí být kladné číslo (>= 1). Zadáno: " + frequencyOfWatering);
        }
        this.frequencyOfWatering = frequencyOfWatering;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", planted=" + planted +
                ", watering=" + watering +
                ", frequencyOfWatering=" + frequencyOfWatering +
                '}';
    }
}



