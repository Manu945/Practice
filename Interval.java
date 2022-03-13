package fr.manu.ssuhc.utils.maths;

import java.util.Random;

public class Interval<T> {
    private Random random;

    private T min;

    private T max;

    public Interval(T min, T max) {
        this.min = min;
        this.max = max;
        this.random = new Random();
    }

    public Integer getAsRandomInt() {
        return Integer.valueOf(this.random.nextInt(((Integer)this.max).intValue() - ((Integer)this.min).intValue() + 1) + ((Integer)this.min).intValue());
    }

    public Double getAsRandomDouble() {
        return Double.valueOf(((Double)this.min).doubleValue() + this.random.nextDouble() * (((Double)this.max).doubleValue() - ((Double)this.min).doubleValue()));
    }
}

