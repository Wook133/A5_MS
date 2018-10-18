package deVilliers_214062813;

public class EuclideanDistance extends Distance {
    public EuclideanDistance(Double x1, Double x2, Double y1, Double y2) {
        super(x1, x2, y1, y2);
    }

    /**
      Straight line distance*
      * @return
     **/
    @Override

    public Double calculateDistance() {
        Double x = Math.pow((x1 - x2), 2);
        Double y = Math.pow((y1 - y2), 2);
        return Math.sqrt((x+y));
    }
}
