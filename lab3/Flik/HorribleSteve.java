public class HorribleSteve {
    public static void main(String [] args) throws Exception {
        int i = 0;
        for (byte j = 0; i < 200; ++j) {
            if (!Flik.isSameNumber(i, j)) {
                throw new Exception(
                        String.format("i:%d not same as j:%d ??", i, j));
            }
            System.out.println(i + " " + j);
            i ++;
        }
        //System.out.println("i is " + i);
    }
}
