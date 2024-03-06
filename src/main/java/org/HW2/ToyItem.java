package org.HW2;

public class ToyItem implements Toy {
    private String name;

    public ToyItem(String name) {
        this.name = name;
    }

    @Override
    public void displayInfo() {
        System.out.println("Toy: " + name);
    }
}
