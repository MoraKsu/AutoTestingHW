package org.HW1montyhallgame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.HW1montyhallgame.game.FirstDoorSelectionStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstDoorSelectionStrategyTest {
    private FirstDoorSelectionStrategy strategy;

    @BeforeEach
    public void setUp() {
        strategy = new FirstDoorSelectionStrategy();
    }

    @AfterEach
    public void tearDown() {
        strategy = null;
    }
    @Test
    public void testFirstDoorSelection() {
        FirstDoorSelectionStrategy strategy = new FirstDoorSelectionStrategy();
        int selectedDoor = strategy.selectDoor();
        assertEquals(1, selectedDoor);
    }

    @Test
    public void testConsistency() {
        FirstDoorSelectionStrategy strategy = new FirstDoorSelectionStrategy();
        int selectedDoor1 = strategy.selectDoor();
        int selectedDoor2 = strategy.selectDoor();
        int selectedDoor3 = strategy.selectDoor();
        assertEquals(selectedDoor1, selectedDoor2);
        assertEquals(selectedDoor1, selectedDoor3);
    }

    @Test
    public void testMultipleSelections() {
        int selectedDoor1 = strategy.selectDoor();
        int selectedDoor2 = strategy.selectDoor();
        assertEquals(selectedDoor1, selectedDoor2);
    }
}
