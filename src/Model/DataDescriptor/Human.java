package Model.DataDescriptor;

public class Human {
    protected String medicalIdentifier; //ma dinh danh y te
    protected String name; // ten
    protected String citizenIdentification; //can cuoc cong dan
    protected String gender; //gioi tinh
    protected String birthday; //ngay sinh

    public Human() {
    }

    public Human(String medicalIdentifier, String name, String citizenIdentification, String gender, String birthday) {
        this.medicalIdentifier = medicalIdentifier;
        this.name = name;
        this.citizenIdentification = citizenIdentification;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Human(String medicalIdentifier) {
        this.medicalIdentifier = medicalIdentifier;
    }

    public Human(String medicalIdentifier, String name, String gender, String birthday) {
        this.medicalIdentifier = medicalIdentifier;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getMedicalIdentifier() {
        return medicalIdentifier;
    }

    public void setMedicalIdentifier(String medicalIdentifier) {
        this.medicalIdentifier = medicalIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitizenIdentification() {
        return citizenIdentification;
    }

    public void setCitizenIdentification(String citizenIdentification) {
        this.citizenIdentification = citizenIdentification;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}


