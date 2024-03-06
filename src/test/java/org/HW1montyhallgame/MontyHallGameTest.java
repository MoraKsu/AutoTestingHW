package org.HW1montyhallgame;

import org.junit.jupiter.api.Test;
import org.HW1montyhallgame.game.MontyHallGame;
import org.HW1montyhallgame.game.RandomDoorSelectionStrategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MontyHallGameTest {
    @Test
    public void testWinningProbability() {
        MontyHallGame game = new MontyHallGame(new RandomDoorSelectionStrategy());
        game.runSimulation(1000);
        double winPercentage = (double) game.getWins() / game.getTotalPlays();
        assertTrue(winPercentage > 0.6);
    }
}
