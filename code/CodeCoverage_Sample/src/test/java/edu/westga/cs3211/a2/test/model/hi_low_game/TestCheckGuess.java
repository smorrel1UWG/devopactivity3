package edu.westga.cs3211.a2.test.model.hi_low_game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.a2.model.GuessReport;
import edu.westga.cs3211.a2.model.HiLowGame;

class TestCheckGuess {
	
	private class RandomFakeSpy extends Random {
		public int callCount;
		public int lastBoundProvided;
		
		public RandomFakeSpy() {
			super();
			this.callCount = 0;
			this.lastBoundProvided = -1;
		}
		
		@Override
		public int nextInt(int bound) {
			this.lastBoundProvided = bound;
			this.callCount++;
			return 2;
		}
	}

	@Test
	void testGuessTooHigh() {
		RandomFakeSpy spy = new RandomFakeSpy();
		HiLowGame game = new HiLowGame(spy);
		
		GuessReport result = game.checkGuess(4);
		
		assertAll(
			()->{assertEquals(GuessReport.TOO_HIGH, result, "checking result of check guess");},
			()->{assertEquals(1, game.getGuessCount(), "checking guess count");},
			()->{assertEquals(1, spy.callCount, "checking number of times random number was generated");},
			()->{assertEquals(100, spy.lastBoundProvided, "checking last bound provided when generating random number");}
		);
	}

	@Test
	void testGuessTooLow() {
		RandomFakeSpy spy = new RandomFakeSpy();
		HiLowGame game = new HiLowGame(spy);
		
		GuessReport result = game.checkGuess(2);
		
		assertAll(
			()->{assertEquals(GuessReport.TOO_LOW, result, "checking result of check guess");},
			()->{assertEquals(1, game.getGuessCount(), "checking guess count");},
			()->{assertEquals(1, spy.callCount, "checking number of times random number was generated");},
			()->{assertEquals(100, spy.lastBoundProvided, "checking last bound provided when generating random number");}
		);
	}

	@Test
	void testGuessCorrect() {
		RandomFakeSpy spy = new RandomFakeSpy();
		HiLowGame game = new HiLowGame(spy);
		
		GuessReport result = game.checkGuess(3);
		
		assertAll(
			()->{assertEquals(GuessReport.CORRECT, result, "checking result of check guess");},
			()->{assertEquals(0, game.getGuessCount(), "checking guess count");},
			()->{assertEquals(3, game.getSecretNumber(), "checking secret number");},
			()->{assertEquals(2, spy.callCount, "checking number of times random number was generated");},
			()->{assertEquals(100, spy.lastBoundProvided, "checking last bound provided when generating random number");}
		);
	}

	@Test
	void testGuessLimitReached() {
		RandomFakeSpy spy = new RandomFakeSpy();
		HiLowGame game = new HiLowGame(spy);
		game.checkGuess(2);
		game.checkGuess(2);
		
		GuessReport result = game.checkGuess(4);
		
		assertAll(
			()->{assertEquals(GuessReport.GUESS_LIMIT_REACHED, result, "checking result of check guess");},
			()->{assertEquals(0, game.getGuessCount(), "checking guess count");},
			()->{assertEquals(3, game.getSecretNumber(), "checking secret number");},
			()->{assertEquals(2, spy.callCount, "checking number of times random number was generated");},
			()->{assertEquals(100, spy.lastBoundProvided, "checking last bound provided when generating random number");}
		);
	}

}
