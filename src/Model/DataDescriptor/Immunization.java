package Model.DataDescriptor;

public class Immunization {
    private String id;
    //dùng để làm khoá, mỗi khóa sẽ là viết tắt, đại diện cho người được tiêm, loại vaccine và ngày tiêm
    private String medicalIdentifier;
    // Mã định danh y tế, là khóa ngoài liên kết với bảng thông tin cá nhân
    private String type;
    //loại vaccine
    private String date;
    //ngày tiêm
    private String lotNumber;
    //Mã lô vaccine
    private String postInjectionCondition;
    //tình trạng sau tiêm
    private int numberInjections;
    //Số mũi tiêm

    public Immunization() {
    }

    public Immunization(String id, String medicalIdentifier,
                        String type, String date, String lotNumber,
                        String postInjectionCondition,
                        int numberInjections) {

        this.id = id;
        this.medicalIdentifier = medicalIdentifier;
        this.type = type;
        this.date = date;
        this.lotNumber = lotNumber;
        this.postInjectionCondition = postInjectionCondition;
        this.numberInjections = numberInjections;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicalIdentifier() {
        return medicalIdentifier;
    }

    public void setMedicalIdentifier(String medicalIdentifier) {
        this.medicalIdentifier = medicalIdentifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getPostInjectionCondition() {
        return postInjectionCondition;
    }

    public void setPostInjectionCondition(String postInjectionCondition) {
        this.postInjectionCondition = postInjectionCondition;
    }

    public int getNumberInjections() {
        return numberInjections;
    }

    public void setNumberInjections(Integer numberInjections) {
        this.numberInjections = numberInjections;
    }
}
