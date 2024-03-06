package org.HW2;

import java.util.ArrayList;
import java.util.List;

public class ToyBox implements Toy {
    private final List<Toy> toys = new ArrayList<>();

    public void add(Toy toy) {
        toys.add(toy);
    }

    public void remove(Toy toy) {
        toys.remove(toy);
    }

    @Override
    public void displayInfo() {
        System.out.println("Toy Box contains:");
        for (Toy toy : toys) {
            toy.displayInfo();
        }
    }
}
