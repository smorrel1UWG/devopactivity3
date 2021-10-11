package edu.westga.cs3211.a2.test.model.hi_low_game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.a2.model.HiLowGame;

class TestConstructor {
	
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
	void testDefaultConstructor() {
		HiLowGame game = new HiLowGame();

		
		assertAll(
			()->{assertEquals(0, game.getGuessCount(), "checking guess count");},
			()->{assertTrue(1 <= game.getSecretNumber() && game.getSecretNumber() <= 100, "checking secret number is within expected bounds");}
		);
	}

	@Test
	void test1ParamConstructorWithValidRandomObject() {
		RandomFakeSpy spy = new RandomFakeSpy();
		HiLowGame game = new HiLowGame(spy);

		
		assertAll(
			()->{assertEquals(0, game.getGuessCount(), "checking guess count");},
			()->{assertEquals(3, game.getSecretNumber(), "checking secret number");},
			()->{assertEquals(1, spy.callCount, "checking number of times random number was generated");},
			()->{assertEquals(100, spy.lastBoundProvided, "checking last bound provided when generating random number");}
		);
	}

	@Test
	void test1ParamConstructorWithNullRandomObject() {
		assertThrows(
						IllegalArgumentException.class, 
						()->{
							new HiLowGame(null);
						}
					);
	}

}
