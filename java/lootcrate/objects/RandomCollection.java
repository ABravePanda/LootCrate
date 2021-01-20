package lootcrate.objects;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomCollection<E> {
    private final NavigableMap<Double, CrateItem> map = new TreeMap<Double, CrateItem>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public RandomCollection<E> add(double weight, CrateItem item) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, item);
        return this;
    }

    public CrateItem next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
