package no.uib.mof077.shortbytes.som;

/**
 * Kohonen Self Organizing Map.
 *
 * @author Cabrera, L. A. V. & Oftedal, M.
 *
 */
public class KohonenSom {
	private Layer input;
	private Layer output;

	/**
	 * Default constructor for the Kohonen Self Organizing Map
	 *
	 * @param input
	 * @param output
	 */
	public KohonenSom(Layer input, Layer output) {
		this.input = input;
		this.output = output;
		connectLayers();
	}

	/**
	 * Connects every input neuron to each output neuron
	 */
	public void connectLayers() {
		for (Neuron outputNeuron : this.output.getNeurons()) {
			for (Neuron inputNeuron : this.input.getNeurons()) {
				// TODO Do something
			}
		}
	}

	public double neighbourhood(int i, int k) {
		// TODO implement
		return 0;
	}

	public Layer getInput() {
		return this.input;
	}

	public void setInput(Layer input) {
		this.input = input;
	}

	public Layer getOutput() {
		return this.output;
	}

	public void setOutput(Layer output) {
		this.output = output;
	}
}
