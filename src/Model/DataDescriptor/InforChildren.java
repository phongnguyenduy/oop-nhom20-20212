package Model.DataDescriptor;

public class InforChildren extends Human {
    private String farName;
    private String morName;

    public InforChildren() {
    }

    public InforChildren(String medicalIdentifier, String name, String birthday, String gender, String farName, String morName) {
        super(medicalIdentifier, name, gender, birthday);
        this.farName = farName;
        this.morName = morName;
    }

    public String getiD() {
        return medicalIdentifier;
    }

    public void setiD(String medicalIdentifier) {
        this.medicalIdentifier = medicalIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return gender;
    }

    public void setSex(String gender) {
        this.gender = gender;
    }

    public String getdOB() {
        return birthday;
    }

    public void setdOB(String birthday) {
        this.birthday = birthday;
    }

    public String getFarName() {
        return farName;
    }

    public void setFarName(String farName) {
        this.farName = farName;
    }

    public String getMorName() {
        return morName;
    }

    public void setMorName(String morName) {
        this.morName = morName;
    }
}

