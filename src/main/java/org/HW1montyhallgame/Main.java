package org.HW1montyhallgame;

import org.HW1montyhallgame.game.FirstDoorSelectionStrategy;
import org.HW1montyhallgame.game.MontyHallGame;
import org.HW1montyhallgame.game.RandomDoorSelectionStrategy;

public class Main {
    public static void main(String[] args) {
        MontyHallGame game1 = new MontyHallGame(new RandomDoorSelectionStrategy());
        System.out.println("Using random door selection strategy:");
        game1.runSimulation(1000);

        MontyHallGame game2 = new MontyHallGame(new FirstDoorSelectionStrategy());
        System.out.println("\nUsing always-first-door selection strategy:");
        game2.runSimulation(1000);
    }
}
