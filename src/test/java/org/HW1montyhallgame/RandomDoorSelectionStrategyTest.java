package org.HW1montyhallgame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.HW1montyhallgame.game.RandomDoorSelectionStrategy;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomDoorSelectionStrategyTest {
    private RandomDoorSelectionStrategy strategy;

    @BeforeEach
    public void setUp() {
        strategy = new RandomDoorSelectionStrategy();
    }

    @AfterEach
    public void tearDown() {
        strategy = null;
    }
    @Test
    public void testRandomDoorSelection() {
        RandomDoorSelectionStrategy strategy = new RandomDoorSelectionStrategy();
        int selectedDoor = strategy.selectDoor();
        assertTrue(selectedDoor >= 1 && selectedDoor <= 3);
    }

    @Test
    public void testRandomness() {
        RandomDoorSelectionStrategy strategy = new RandomDoorSelectionStrategy();
        int selectedDoor1 = strategy.selectDoor();
        int selectedDoor2 = strategy.selectDoor();
        int selectedDoor3 = strategy.selectDoor();
        assertTrue(selectedDoor2 != selectedDoor3 || selectedDoor1 != selectedDoor3);
    }

    @Test
    public void testDifferentSelections() {
        int selectedDoor1 = strategy.selectDoor();
        int selectedDoor2 = strategy.selectDoor();
        assertNotEquals(selectedDoor1, selectedDoor2);
    }
}
