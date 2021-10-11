package edu.westga.cs3211.a2.model;

import java.util.Random;

/** Manage checking guesses for a HiLowGame.
 * 
 * @author CS 3211
 * @version Fall 2021
 */
public class HiLowGame {
	private int secretNumber;
	private int guessCount;
	private Random randomNumberGenerator;
	
	/** Returns the secret number to be guessed
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the secret number to be guessed
	 */
	public int getSecretNumber() {
		return this.secretNumber;
	}
	
	/** Returns the number of guesses made so far
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the number of guesses made so far
	 */
	public int getGuessCount() {
		return this.guessCount;
	}
	
	/** Create a new HiLowGame
	 * 
	 * @precondition none
	 * @postcondition secret number initialized to a random number (1-100) &&
	 * 				  guess count initialized to 0
	 * 
	 */
	public HiLowGame() {
		this(new Random());
	}
	
	/** Create a new HiLowGame
	 * 
	 * @precondition randomNumberGenerator != null
	 * @postcondition secret number initialized to a random number (1-100) &&
	 * 				  guess count initialized to 0
	 * 
	 */
	public HiLowGame(Random randomNumberGenerator) {
		if (randomNumberGenerator == null) {
			throw new IllegalArgumentException("Must provide a valid Random object");
		}
		this.randomNumberGenerator = randomNumberGenerator;
		this.resetGame();
	}
	
	/** Check a guess against the secret number.
	 * 
	 * @precondition none
	 * @postcondition resets the guess count and generates a new secret number 	if guess is correct or guess limit reached
	 * 
	 * @param guess the guess trying to determine what the secret number is
	 * 
	 * @return GuessReport.CORRECT				if guess is correct 
	 * 		   GuessReport.TOO_LOW				if guess is too low
	 * 		   GuessReport.TOO_HIGH				if guess is too high
	 * 		   GuessReport.GUESS_LIMIT_REACHED	if guess count is reached
	 */
	public GuessReport checkGuess(int guess) {
		GuessReport result = GuessReport.CORRECT;
		if (guess == this.secretNumber) {
			this.resetGame();
		}
		else if (guess < this.secretNumber) {
			this.guessCount++;
			result = GuessReport.TOO_LOW;
		}
		else {
			this.guessCount++;
			result = GuessReport.TOO_HIGH;
		}
		if (this.guessCount >= 3) {
			this.resetGame();
			result = GuessReport.GUESS_LIMIT_REACHED;
		}
		return result;
	}

	private void resetGame() {
		this.guessCount = 0;
		this.secretNumber = this.randomNumberGenerator.nextInt(100)+1;
	}

}
