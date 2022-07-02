package Model.DataDescriptor;

public class Person extends Human{

    private String address;
    //dia chi
    private String numberPhone;
    //so dien thoai
    private String email;
    //email
    private String vaccinationtatus;
    //tinh trang tiem chung ( tiem du loai vaccine hay chua?)

    public Person(){}


    public Person(String medicalIdentifier, String name,
                  String citizenIdentification, String gender,
                  String birthday, String address, String numberPhone,
                  String email, String vaccinationtatus) {

        super(medicalIdentifier,name,citizenIdentification,gender,birthday);
        this.address = address;
        this.numberPhone = numberPhone;
        this.email = email;
        this.vaccinationtatus = vaccinationtatus;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVaccinationtatus() {
        return vaccinationtatus;
    }

    public void setVaccinationtatus(String vaccinationtatus) {
        this.vaccinationtatus = vaccinationtatus;
    }

}

