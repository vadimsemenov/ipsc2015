import java.io.*;
import java.util.Scanner;


/**
 * Created by daria on 19.06.15.
 */
public class S {

    Scanner in;
    PrintWriter out;

    public void solve() throws IOException {
        int tests = in.nextInt();
        for (int j = 0; j < tests; j++) {
            int n = in.nextInt();
            long[][] cards = new long[4][n];
            for (long i = 0; i < 4 * n; i++) {
                String s = in.next();
                int slot = 0;
                switch (s.charAt(0)) {
                    case 'H':
                        slot = 0;
                        break;
                    case 'D':
                        slot = 1;
                        break;
                    case 'S':
                        slot = 2;
                        break;
                    case 'C':
                        slot = 3;
                        break;
                }
                cards[slot][Integer.parseInt(s.substring(1)) - 1] = i;
            }

            long[] ans = new long[4];
            for (int i = 0; i < 4; i++) {
                ans[i] = 1;
                for (int k = 1; k < n; k++) {
                    if (cards[i][k] < cards[i][k - 1]) {
                        ans[i]++;
                    }
                }
            }
            long max = ans[0];
            for (int i = 1; i < 4; i++) {
                if (ans[i] > max) {
                    max = ans[i];
                }
            }

            out.println(max);
        }
    }


    public void run() {
        try {
            in = new Scanner(new File("s2" + ".in"));
            out = new PrintWriter("s2" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new S().run();
    }
}