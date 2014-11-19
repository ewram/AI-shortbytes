package no.uib.mof077.shortbytes.som;

public class HomeworkSOM {
	public static void main(String[] args) {

		float[] i1 = { 1.1f, 1.7f, 1.8f };
		float[] i2 = { 0, 0, 0 };
		float[] i3 = { 0, 0.5f, 1.5f };
		float[] i4 = { 1, 0, 0 };
		float[] i5 = { 0.5f, 0.5f, 0.5f };
		float[] i6 = { 1, 1, 1 };
		float[][] samples = { i1, i2, i3, i4, i5, i6 };

		float[] wa = { 0.2f, 0.7f, 0.3f };
		float[] wb = { 0.1f, 0.1f, 0.9f };
		float[] wc = { 1, 1, 1 };
		float[][] outputs = { wa, wb, wc };

		for (int i = 0; i <= 5; i++) {
			float learningRate = 0.6f;
			calculateNewWeights(samples, outputs, learningRate);
			printWeights(outputs);
		}

		for (int i = 6; i <= 12; i++) {
			float learningRate = 0.25f;
			calculateNewWeights(samples, outputs, learningRate);
			printWeights(outputs);
		}
		for (int i = 13; i <= 100; i++) {
			float learningRate = 0.1f;
			calculateNewWeights(samples, outputs, learningRate);
			printWeights(outputs);
		}

	}

	public static void printWeights(float[][] outputs) {
		for (int unit = 0; unit < outputs.length; unit++) {
			System.out.println("Unit" + (unit + 1));
			for (int weight = 0; weight < outputs[unit].length; weight++) {
				System.out.print(outputs[unit][weight] + " ");
			}
			System.out.println();
		}
	}

	public static void calculateNewWeights(float[][] samples,
			float[][] outputs, float learningRate) {
		for (int sample = 0; sample < samples.length; sample++) {
			float[] sampleValues = samples[sample];

			// Find winning unit
			float winningScore = Float.MAX_VALUE;
			int winningIndex = -1;
			for (int output = 0; output < outputs.length; output++) {
				float[] outputValues = outputs[output];
				double posZero = Math.pow((outputValues[0] - sampleValues[0]),
						2);
				double posOne = Math
						.pow((outputValues[1] - sampleValues[1]), 2);
				double posTwo = Math
						.pow((outputValues[2] - sampleValues[2]), 2);

				float score = (float) (posZero + posOne + posTwo);

				if (score < winningScore) {
					winningScore = score;
					winningIndex = output;
				}
			}

			// Calculate new weights for winning unit
			// [1.0 1.0 1.0] + 0.6 * ([1.1, 1.7, 1.8] - [1.0 1.0 1.0) =
			// [1.06 1.42 1.48]

			for (int weight = 0; weight < outputs[winningIndex].length; weight++) {
				float weightValue = outputs[winningIndex][weight];
				float sampleValue = samples[sample][weight];
				float newWeightValue = weightValue + learningRate
						* (sampleValue - weightValue);
				outputs[winningIndex][weight] = newWeightValue;
			}
		}
	}
}
