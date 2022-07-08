package Model.DataDescriptor;

public class Detail extends InforChildren{
    private int fid;
    private String medical_id;
    private int month;
    private double height;
    private double weight;

    public Detail() {}

    public Detail(int fid, String medical_id, int month, double height, double weight) {
        this.fid = fid;
        this.medical_id = medical_id;
        this.month = month;
        this.height = height;
        this.weight = weight;
    }

    public int getFid() {
        return fid;
    }
    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getMedical_id() {
        return medical_id;
    }
    public void setMedical_id(String medical_id) {
        this.medical_id = medical_id;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public double getHeight() {return height;}
    public void setHeight(double height) {this.height = height;}

    public double getWeight() {return weight;}
    public void setWeight(double weight) {this.weight = weight;}
}
