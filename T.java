import java.io.*;
import java.util.Scanner;

/**
 * Created by daria on 19.06.15.
 */
public class T {
    Scanner in;
    PrintWriter out;

    public void solve() throws IOException {
        int tests = in.nextInt();
        for (int i = 0; i < tests; i++) {
            long[] digits = new long[10];
            for (int j = 0; j < 10; j++) {
                digits[j] = in.nextLong();
            }

            long min = digits[0];
            for (int j = 1; j < 10; j++) {
                if (digits[j] < min) {
                    min = digits[j];
                }
            }

            long ans = 0;
            while (true) {
                long p = 1;
                long numberOfDigits = 1;
                long numberOfZeros = 0;
                int power = 0;

                long toSubtract = 0;
                while (min >= numberOfDigits + numberOfZeros) {
                    toSubtract += numberOfDigits + numberOfZeros;
                    min -= toSubtract;
                    numberOfDigits = numberOfDigits * 9 + 9;
                    numberOfZeros = numberOfZeros * 9 + 9;
                    p *= 10;
                    power++;
                }

                for (int j = 0; j < 10; j++) {
                    digits[j] -= toSubtract;
                }

                if (digits[0] >= power && digits[1] > 0) {
                    ans += p;
                } else {
                    ans += p - 1;
                    break;
                }
            }



            out.println(ans);
        }
    }

    public void run() {
        try {
            in = new Scanner(new File("t1" + ".in"));
            out = new PrintWriter("t1" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new T().run();
    }
}