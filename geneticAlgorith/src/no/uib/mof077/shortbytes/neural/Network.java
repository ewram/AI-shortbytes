package no.uib.mof077.shortbytes.neural;

public class Network {

	private double globalError;
	private int inputCount;
	private int hiddenCount;
	private int outputCount;
	private double learningRate;
	private double momentum;
	private int neuronCount;
	private int weightCount;
	private double[] fire;
	private double[] matrix;
	private double[] error;
	private double[] accMatrixDelta;
	private double[] thresholds;
	private double[] matrixDelta;
	private double[] accThresholdDelta;
	private double[] thresholdDelta;
	private double[] errorDelta;

	public Network(int inputCount, int hiddenCount, int outputCount,
			double learningRate, double momentum) {
		this.inputCount = inputCount;
		this.hiddenCount = hiddenCount;
		this.outputCount = outputCount;
		this.learningRate = learningRate;
		this.momentum = momentum;

		this.neuronCount = inputCount + hiddenCount + outputCount;
		this.weightCount = (inputCount * hiddenCount)
				+ (hiddenCount * outputCount);

		this.fire = new double[this.neuronCount];
		this.matrix = new double[this.weightCount];
		this.matrixDelta = new double[this.weightCount];
		this.thresholds = new double[this.neuronCount];
		this.errorDelta = new double[this.neuronCount];
		this.error = new double[this.neuronCount];
		this.accThresholdDelta = new double[this.neuronCount];
		this.accMatrixDelta = new double[this.weightCount];
		this.thresholdDelta = new double[this.neuronCount];

		reset();
	}

	public double[] computeOutputs(double[] input) {
		int i, j;
		final int hiddenIndex = this.inputCount;
		final int outIndex = this.inputCount + this.hiddenCount;

		for (i = 0; i < this.inputCount; i++) {
			this.fire[i] = input[i];
		}

		// First layer
		int inx = 0;

		for (i = hiddenIndex; i < outIndex; i++) {
			double sum = this.thresholds[i];

			for (j = 0; j < this.inputCount; j++) {
				sum += this.fire[j] * this.matrix[inx++];
			}
			this.fire[i] = threshold(sum);
		}

		// Hidden layer
		double[] result = new double[this.outputCount];

		for (i = outIndex; i < this.neuronCount; i++) {
			double sum = this.thresholds[i];

			for (j = hiddenIndex; j < outIndex; j++) {
				sum += this.fire[j] * this.matrix[inx++];
			}
			this.fire[i] = threshold(sum);
			result[i - outIndex] = this.fire[i];
		}
		return result;
	}

	public void calcError(double[] ideal) {
		int i, j;
		final int hiddenIndex = this.inputCount;
		final int outputIndex = this.inputCount + this.hiddenCount;

		// Clear hidden layer errors
		for (i = this.inputCount; i < this.neuronCount; i++) {
			this.error[i] = 0;
		}

		// Layer errors and deltas for output layer
		for (i = outputIndex; i < this.neuronCount; i++) {
			this.error[i] = ideal[i - outputIndex] - this.fire[i];
			this.globalError += this.error[i] * this.error[i];
			this.errorDelta[i] = this.error[i] * this.fire[i]
					* (1 - this.fire[i]);
		}

		// Hidden layer errors
		int winx = this.inputCount * this.hiddenCount;

		for (i = outputIndex; i < this.neuronCount; i++) {
			for (j = hiddenIndex; j < outputIndex; j++) {
				this.accMatrixDelta[winx] += this.errorDelta[i] * this.fire[j];
				this.error[j] += this.matrix[winx] * this.errorDelta[i];
				winx++;
			}
			this.accThresholdDelta[i] += this.errorDelta[i];
		}

		// Hidden layer deltas
		for (i = hiddenIndex; i < outputIndex; i++) {
			this.errorDelta[i] = this.error[i] * this.fire[i]
					* (1 - this.fire[i]);
		}

		// Input layer errors
		winx = 0; // Offset into weight array
		for (i = hiddenIndex; i < outputIndex; i++) {
			for (j = 0; j < hiddenIndex; j++) {
				this.accMatrixDelta[winx] += this.errorDelta[i] * this.fire[j];
				this.error[j] += this.matrix[winx] * this.errorDelta[i];
				winx++;
			}
			this.accThresholdDelta[i] += this.errorDelta[i];
		}
	}

	public double getError(int len) {
		double error = Math.sqrt(this.globalError / (len * this.outputCount));
		this.globalError = 0;
		return error;
	}

	public double threshold(double sum) {
		return 1.0 / (1 + Math.exp(-1.0 * sum));
	}

	public void learn() {
		int i;

		// process the matrix
		for (i = 0; i < this.matrix.length; i++) {
			this.matrixDelta[i] = (this.learningRate * this.accMatrixDelta[i])
					+ (this.momentum * this.matrixDelta[i]);
			this.matrix[i] += this.matrixDelta[i];
			this.accMatrixDelta[i] = 0;
		}

		// process the thresholds
		for (i = this.inputCount; i < this.neuronCount; i++) {
			this.thresholdDelta[i] = this.learningRate
					* this.accThresholdDelta[i]
							+ (this.momentum * this.thresholdDelta[i]);
			this.thresholds[i] += this.thresholdDelta[i];
			this.accThresholdDelta[i] = 0;
		}
	}

	public void reset() {
		int i;

		for (i = 0; i < this.neuronCount; i++) {
			this.thresholds[i] = 0.5 - (Math.random());
			this.thresholdDelta[i] = 0;
			this.accThresholdDelta[i] = 0;
		}

		for (i = 0; i < this.matrix.length; i++) {
			this.matrix[i] = 0.5 - (Math.random());
			this.matrixDelta[i] = 0;
			this.accMatrixDelta[i] = 0;
		}
	}
}