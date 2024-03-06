package org.HW2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToyTest {
    @Test
    void testToyItem() {
        ToyItem toyItem = new ToyItem("Test Toy");
        assertEquals("Test Toy", toyItem.getName());
    }

    @Test
    void testToyBox() {
        ToyItem doll = new ToyItem("Doll");
        ToyBox toyBox = new ToyBox();
        toyBox.add(doll);
        assertEquals(1, toyBox.getToys().size());
        assertTrue(toyBox.getToys().contains(doll));

        toyBox.remove(doll);
        assertEquals(0, toyBox.getToys().size());
    }

    @Test
    void testNestedToyBox() {
        ToyItem car = new ToyItem("Car");
        ToyItem ball = new ToyItem("Ball");

        ToyBox smallBox = new ToyBox();
        smallBox.add(car);
        smallBox.add(ball);

        ToyBox bigBox = new ToyBox();
        bigBox.add(smallBox);

        assertEquals(1, bigBox.getToys().size());
        assertTrue(bigBox.getToys().contains(smallBox));
    }
}
