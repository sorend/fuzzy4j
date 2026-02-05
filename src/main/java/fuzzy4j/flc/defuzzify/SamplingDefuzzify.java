package fuzzy4j.flc.defuzzify;

/**
 * Soren A. Davidsen (soren@atmakuridavidsen.com)
 */
public abstract class SamplingDefuzzify implements Defuzzify {

    private int samples = 100;


    public void setSamples(int samples) {
        this.samples = samples;
    }

    public int getSamples() {
        return samples;
    }
}
