package Model.DataDescriptor;

public class VaccinationSchedule {
    private int id;
    private String name;
    private int amount;
    private String address;
    private String time;

    public VaccinationSchedule() {
    }

    public VaccinationSchedule(int id, String name, int amount, String address, String time) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.address = address;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

