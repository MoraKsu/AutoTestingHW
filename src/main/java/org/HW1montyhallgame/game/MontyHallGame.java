package org.HW1montyhallgame.game;

import java.util.Random;

public class MontyHallGame {
    private final DoorSelectionStrategy doorSelectionStrategy;
    private final Random random;
    private int wins;
    private int totalPlays;

    public MontyHallGame(DoorSelectionStrategy doorSelectionStrategy) {
        this.doorSelectionStrategy = doorSelectionStrategy;
        this.random = new Random();
        this.wins = 0;
        this.totalPlays = 0;
    }

    // Метод для моделирования одного раунда игры
    public void playGame() {
        int prizeDoor = random.nextInt(3) + 1;
        int selectedDoor = doorSelectionStrategy.selectDoor();

        // Открыть одну из не выбранных дверей, не содержащих приз
        int revealedDoor;
        do {
            revealedDoor = random.nextInt(3) + 1;
        } while (revealedDoor == selectedDoor || revealedDoor == prizeDoor);

        // Выбрать дверь (или остаться при выборе), содержащую приз
        int finalSelectedDoor;
        if (doorSelectionStrategy instanceof FirstDoorSelectionStrategy) {
            finalSelectedDoor = selectedDoor;
        } else {
            do {
                finalSelectedDoor = random.nextInt(3) + 1;
            } while (finalSelectedDoor == selectedDoor || finalSelectedDoor == revealedDoor);
        }

        // Проверить, выиграл ли игрок
        if (finalSelectedDoor == prizeDoor) {
            wins++;
        }
        totalPlays++;
    }

    // Метод для запуска серии игр и вывода итогового счета
    public void runSimulation(int numPlays) {
        for (int i = 0; i < numPlays; i++) {
            playGame();
        }
        System.out.println("Total plays: " + totalPlays);
        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + (totalPlays - wins));
    }

    public int getWins() {
        return wins;
    }

    public int getTotalPlays() {
        return totalPlays;
    }
}
