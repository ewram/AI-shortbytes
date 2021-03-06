package no.uib.mof077.shortbytes.neural;

import java.util.Random;

public class XORHomework {
	// Input nodes, plus the bias input.
	private static final int NUM_INPUTS = 3;

	// Input patterns for XOR experiment.
	private static final int NUM_PATTERNS = 4;

	private static final int NUM_HIDDEN = 4;
	private static final int NUM_EPOCHS = 200;

	// Learning rate, input to hidden weights.
	private static final double LR_IH = 0.7;

	// Learning rate, hidden to output weights.
	private static final double LR_HO = 0.07;

	// Hidden node outputs.
	private static double hiddenVal[] = new double[NUM_HIDDEN];

	// Input to Hidden weights.
	private static double weightsIH[][] = new double[NUM_INPUTS][NUM_HIDDEN];

	// Hidden to Output weights.
	private static double weightsHO[] = new double[NUM_HIDDEN];

	private static int trainInputs[][] = new int[NUM_PATTERNS][NUM_INPUTS];

	// "Actual" output values.
	private static int trainOutput[] = new int[NUM_PATTERNS];

	private static double errThisPat = 0.0;

	// "Expected" output values.
	private static double outPred = 0.0;

	// Root Mean Squared error.
	private static double RMSerror = 0.0;

	private static void algorithm() {
		int patNum = 0;

		initWeights();
		initData();

		// Train the network.
		for (int j = 0; j < NUM_EPOCHS; j++) {
			for (int i = 0; i < NUM_PATTERNS; i++) {
				// Select a pattern at random.
				patNum = new Random().nextInt(4);

				// Calculate the output and error for this pattern.
				calcNet(patNum);

				// Adjust network weights.
				weightChangesHO();
				weightChangesIH(patNum);

			} // i
			RMSerror = calcOverallError();

			// Display the overall network error after each epoch
			System.out.println("epoch = " + j + " RMS Error = " + RMSerror);

		} // j

		displayResults();

		return;
	}

	private static void initWeights() {
		// Initialize weights to random values.
		for (int j = 0; j < NUM_HIDDEN; j++) {
			weightsHO[j] = (new Random().nextDouble() - 0.5) / 2.0;
			for (int i = 0; i < NUM_INPUTS; i++) {
				weightsIH[i][j] = (new Random().nextDouble() - 0.5) / 5.0;
				System.out.println("Weight = " + weightsIH[i][j]);
			} // i
		} // j

		return;
	}

	private static void initData() {
		// The data here is the XOR data which has been rescaled to the range -1
		// to 1.
		// An extra input value of 1 is also added to act as the bias.
		// The output must lie in the range -1 to 1.

		trainInputs[0][0] = 1;
		trainInputs[0][1] = -1;
		trainInputs[0][2] = 1; // Bias
		trainOutput[0] = 1;

		trainInputs[1][0] = -1;
		trainInputs[1][1] = 1;
		trainInputs[1][2] = 1; // Bias
		trainOutput[1] = 1;

		trainInputs[2][0] = 1;
		trainInputs[2][1] = 1;
		trainInputs[2][2] = 1; // Bias
		trainOutput[2] = -1;

		trainInputs[3][0] = -1;
		trainInputs[3][1] = -1;
		trainInputs[3][2] = 1; // Bias
		trainOutput[3] = -1;

		return;
	}

	private static void calcNet(final int patNum) {
		// Calculates values for Hidden and Output nodes.
		for (int i = 0; i < NUM_HIDDEN; i++) {
			hiddenVal[i] = 0.0;
			for (int j = 0; j < NUM_INPUTS; j++) {
				hiddenVal[i] += (trainInputs[patNum][j] * weightsIH[j][i]);
			} // j
			hiddenVal[i] = Math.tanh(hiddenVal[i]);
		} // i

		outPred = 0.0;

		for (int i = 0; i < NUM_HIDDEN; i++) {
			outPred += hiddenVal[i] * weightsHO[i];
		}

		errThisPat = outPred - trainOutput[patNum]; // Error = "Expected" -
		// "Actual"
		return;
	}

	private static void weightChangesHO() {
		// Adjust the Hidden to Output weights.
		for (int k = 0; k < NUM_HIDDEN; k++) {
			double weightChange = LR_HO * errThisPat * hiddenVal[k];
			weightsHO[k] -= weightChange;

			// Regularization of the output weights.
			if (weightsHO[k] < -5.0) {
				weightsHO[k] = -5.0;
			} else if (weightsHO[k] > 5.0) {
				weightsHO[k] = 5.0;
			}
		}
		return;
	}

	private static void weightChangesIH(final int patNum) {
		// Adjust the Input to Hidden weights.
		for (int i = 0; i < NUM_HIDDEN; i++) {
			for (int k = 0; k < NUM_INPUTS; k++) {
				double x = 1 - Math.pow(hiddenVal[i], 2);
				x = x * weightsHO[i] * errThisPat * LR_IH;
				x = x * trainInputs[patNum][k];

				double weightChange = x;
				weightsIH[k][i] -= weightChange;
			} // k
		} // i
		return;
	}

	private static double calcOverallError() {
		double errorValue = 0.0;

		for (int i = 0; i < NUM_PATTERNS; i++) {
			calcNet(i);
			errorValue += Math.pow(errThisPat, 2);
		}

		errorValue /= NUM_PATTERNS;

		return Math.sqrt(errorValue);
	}

	private static void displayResults() {
		for (int i = 0; i < NUM_PATTERNS; i++) {
			calcNet(i);
			System.out.println("pat = " + (i + 1) + " actual = "
					+ trainOutput[i] + " neural model = " + outPred);
		}
		return;
	}

	public static void main(String[] args) {
		algorithm();
		return;
	}

}
