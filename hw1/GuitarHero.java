import edu.princeton.cs.algs4.StdAudio;
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
                GuitarString string = keys[keyboard.indexOf(key)];
                string.pluck();
            }
            // UNFINISHED - Look at GuitarHeroLite
        }
    }
}