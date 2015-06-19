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

        long[] powersTen = new long[17];
        powersTen[0] = 1;
        long[] powersNine = new long[17];
        powersNine[0] = 1;

        long[][] combinations = new long[17][17];

        for (int i = 1; i < 17; i++) {
            powersNine[i] = powersNine[i - 1] * 9;
            powersTen[i] = powersTen[i - 1] * 10;
            combinations[i][i] = 1;
            for (int j = i + 1; j < 17; j++) {
                combinations[i][j] = combinations[i][j - 1] * j / (j - i);
            }
        }


        for (int i = 0; i < tests; i++) {
            long[] digits = new long[10];

            long ans = 0;
            boolean changed = false;
            for (int j = 0; j < 10; j++) {
                digits[j] = in.nextLong();
                if (digits[j] == 0) {
                    ans = j > 0 ? j - 1 : 0;
                    changed = true;
                    break;
                }
            }
            if (changed) {
                out.println(ans);
                continue;
            }

            long used = 0;
            int length = 2;
            for (; length < 17; length++) {
                int[] result = new int[length];
                for (int rad = length - 1; rad >= 0; rad--) {

                    for (int j = rad == length - 1 ? 1 : 0; j < 10; j++) {
                        long needed = 0;
                        for (int l = 1; l < rad; l++) {
                            needed += combinations[l][rad] * powersNine[rad - l];
                        }
                        if (j > 1) {
                            needed++;
                        }

                        for (int k = 0; k < j; k++) {
                            if (digits[k] < needed + used) {
                                result[rad] = j;
                                break;
                            }
                        }
                        if (result[rad] == 0) {
                            used += needed;
                        }
                        else {
                            break;
                        }
                    }
                }

            }

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