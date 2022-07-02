package Model.DataDescriptor;

public class MotherInfor extends Human {
    private int weight;
    private String date;
    private String nextDate;

    public MotherInfor(String medicalIdentifier, int weight, String date, String nextDate) {
        super(medicalIdentifier);
        this.weight = weight;
        this.date = date;
        this.nextDate = nextDate;
    }

    public String getMedicalIdentifier() {
        return medicalIdentifier;
    }

    public void setMedicalIdentifier(String medicalIdentifier) {
        this.medicalIdentifier = medicalIdentifier;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }
}