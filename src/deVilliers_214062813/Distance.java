package deVilliers_214062813;

public abstract class Distance {
    Double x1;
    Double x2;
    Double y1;
    Double y2;

    public Distance(Double x1, Double x2, Double y1, Double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public abstract Double calculateDistance();

}
