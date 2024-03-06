package org.HW1montyhallgame.game;

import java.util.Random;

public class RandomDoorSelectionStrategy implements DoorSelectionStrategy {
    private final Random random;

    public RandomDoorSelectionStrategy() {
        this.random = new Random();
    }

    @Override
    public int selectDoor() {
        return random.nextInt(3) + 1;
    }
}
