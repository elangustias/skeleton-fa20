import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        GuitarString[] keys = new GuitarString[keyboard.length()];
        for (int i = 0; i < keys.length; i++) {
            int pow = (i - 24) / 12;
            double freq = 440 * Math.pow(2, pow);
            GuitarString string = new GuitarString(freq);
            keys[i] = string;
        }
        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int kb = keyboard.indexOf(key);
                keys[kb].pluck();
            }
            /* compute the superposition of samples */
            // double sample = stringA.sample() + stringC.sample();
            /* play the sample on standard audio */
            // StdAudio.play(sample);
            /* advance the simulation of each guitar string by one step */
            // stringA.tic();
            // stringC.tic();

        }
    }
}