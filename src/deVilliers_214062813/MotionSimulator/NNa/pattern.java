package deVilliers_214062813.MotionSimulator.NNa;

import java.io.Serializable;

public class pattern implements Serializable {
    double[] input;
    double[] output;

    public pattern(double[] in, double[] out) {
        this.input = in;
        this.output = out;
    }
}
