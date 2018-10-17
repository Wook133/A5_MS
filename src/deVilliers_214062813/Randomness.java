package deVilliers_214062813;

import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.UniformRandomGenerator;
import org.apache.commons.math3.random.Well19937c;

import java.util.Random;

public class Randomness {

    /**
     *
     * @param Range [-range; range]
     * @return uniform number from uniform distribution in range
     */
    public static Double UniformRandomNumber(Double Range)
    {
        //double dmax =  1.7320508071499143;
        double dmax = Math.sqrt(3.0);
        Random r = new Random();
        UniformRandomGenerator rnd = new UniformRandomGenerator(new Well19937c(r.nextInt()));
        double cur = rnd.nextNormalizedDouble();
        return (cur/dmax) * Range;
    }
    /**
     * @param Range [0; Range]
     * @return  pseudorandom number from a Uniform Distribution within the range
     * */
    public static Double UniformPositiveRandomNumber(Double Range)
    {
        //double dmax =  1.7320508071499143;
        double dmax = Math.sqrt(3.0);
        Random r = new Random();
        UniformRandomGenerator rnd = new UniformRandomGenerator(new Well19937c(r.nextInt()));
        double cur = rnd.nextNormalizedDouble();
        cur = (cur/dmax) * Range;
        if (cur < 0)
            cur = cur *-1.0;
        return cur;
    }
    /**
     * @param Range [-Range; Range]
     * @return  pseudorandom number from a Uniform Distribution within the range
     */
    public static Integer UniformRandomInteger(Double Range)
    {
        //double dmax =  1.7320508071499143;
        double dmax = Math.sqrt(3.0);
        Random r = new Random();
        UniformRandomGenerator rnd = new UniformRandomGenerator(new Well19937c(r.nextInt()));
        double cur = rnd.nextNormalizedDouble();
        cur = (cur/dmax) * Range;
        return (int)cur;
    }


    /**
     * @param Range [1; Range]
     * @return  pseudorandom number from a Uniform Distribution within the range
     */
    public static Integer UniformPositiveRandomNaturalNumber(Double Range)
    {
        double dmax = Math.sqrt(3.0);
        Random r = new Random();
        UniformRandomGenerator rnd = new UniformRandomGenerator(new Well19937c(r.nextInt()));
        double cur = rnd.nextNormalizedDouble();
        cur = (cur/dmax) * (Range - 1);
        if (cur < 0)
            cur = cur *-1.0;
        cur = cur + 1;
        return (int)cur;
    }

    /**
     * @param Range [-Range; Range]
     * @return  pseudorandom number from a Normal Distribution within the range
     */
    public static Double NormalRandomNumber(Double Range)
    {
        Random r = new Random();
        double dmax =  5.539589635447753;
        GaussianRandomGenerator rnd = new GaussianRandomGenerator(new Well19937c(r.nextInt()));
        double cur = rnd.nextNormalizedDouble();
        cur = (cur/dmax) * Range;
        return cur;
    }
    /**
     * @param Range [0; Range]
     * @return  pseudorandom number from a Uniform Distribution within the range
     */
    public static Integer normalRandomInteger(Double Range)
    {
        Random r = new Random();
        double dmax =  5.539589635447753;

        GaussianRandomGenerator rnd = new GaussianRandomGenerator(new Well19937c(r.nextInt()));
        double cur = rnd.nextNormalizedDouble();
        if (cur < 0)
            cur = cur *-1.0;

        cur = (cur/dmax) * Range;
        cur = cur - 1;

        Integer i = (int)cur;
        return i;
    }

    /**
     * @return  pseudorandom number from a Normal Distribution within the range
     */
    public static Double StandardNormalRandomNumber()
    {
        Random r = new Random();
        double dmax =  5.539589635447753;
        GaussianRandomGenerator rnd = new GaussianRandomGenerator(new Well19937c(r.nextInt()));
        double cur = rnd.nextNormalizedDouble();
        cur = (cur/dmax);
        return cur;
    }
}
