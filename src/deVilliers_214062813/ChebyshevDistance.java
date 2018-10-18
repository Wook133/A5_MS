package deVilliers_214062813;

public class ChebyshevDistance extends Distance {
    public ChebyshevDistance(Double x1, Double x2, Double y1, Double y2) {
        super(x1, x2, y1, y2);
    }
    /**
     * Chess king distance
     * @return
     */
    @Override
    public Double calculateDistance() {
        Double x = Math.abs((x1 - x2));
        Double y = Math.abs((y1 - y2));
        return Math.max(x, y);
    }
}
