package deVilliers_214062813.MotionSimulator;

public class State
{
    double x;
    double y;
    double a;

    public State(double x, double y, double a) {
        this.x = x;
        this.y = y;
        this.a = a;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getA() {
        return a;
    }
}
