package no.uib.mof077.shortbytes.som;

import java.util.List;

/**
 *
 * @author Cabrera, L. A. V. & Oftedal, M.
 *
 */
public class Layer {
	private List<Neuron> neurons;

	/**
	 * Constructs a layer with the provided list of neurons
	 *
	 * @param neurons
	 */
	public Layer(List<Neuron> neurons) {
		this.neurons = neurons;
	}

	/**
	 * Constructs an empty layer, instantiating the List<Neuron> as an ArrayList
	 */
	public Layer() {
		this.neurons = null;
	}

	public List<Neuron> getNeurons() {
		return this.neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

	public void addNeurons(Neuron... neurons) {
		for (Neuron neuron : neurons) {
			this.neurons.add(neuron);
		}
	}

	public void removeNeuron(Neuron neuron) {
		this.neurons.remove(neuron);
	}

	public void removeNeuron(int index) {
		this.neurons.remove(index);
	}
}
