import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by daria on 19.06.15.
 */
public class T {
    private final long[] powersTen;
    private final long[] powersNine;
    private final long[][] combinations;

    public T() {
        final int MAX = 18;
        powersTen = new long[MAX];
        powersTen[0] = 1;
        powersNine = new long[MAX];
        powersNine[0] = 1;
        combinations = new long[MAX][];

        for (int i = 1; i < MAX; i++) {
            powersNine[i] = powersNine[i - 1] * 9;
            powersTen[i] = powersTen[i - 1] * 10;
            combinations[i] = new long[i + 1];
            combinations[i][0] = combinations[i][i] = 1;
            for (int j = 1; j < i; ++j) {
                combinations[i][j] = combinations[i - 1][j] + combinations[i - 1][j - 1];
            }
        }
    }

    public void run() {
        test();
        try (Scanner in = new Scanner(System.in);
             PrintWriter out = new PrintWriter(System.out)) {
            int tests = in.nextInt();
            int[] digits = new int[10];
            for (int i = 0; i < tests; i++) {
                for (int d = 0; d < 10; ++d) digits[d] = in.nextInt();
                out.println(solveCase(digits));
            }
        }
    }

    private void test() {
        final int MAX = 7_000_000;
        int[] digits = new int[10];
        for (int i = 1; i < MAX; ++i) {
            int num = i;
            while (num > 0) {
                ++digits[num % 10];
                num /= 10;
            }
            long found = solveCase(Arrays.copyOf(digits, digits.length));
            if (found != i) {
                System.err.println("FAIL :(");
                for (int d : digits) System.out.print(d + " ");
                System.out.println();
                System.out.println(Arrays.toString(digits));
                System.out.println("Expected " + i + ", but found " + found);
                System.exit(42);
            }
            System.err.println(i);
        }
    }

    private long solveCase(int[] digits) {
        for (int d = 1; d < 10; d++) {
            if (digits[d] == 0) {
                return d - 1;
            }
            --digits[d];
        }
        if (digits[0] == 0) return 9;
        int[] result = null;
        int[] count = new int[10];
        length: for (int length = 2; length < 18; length++) {
            result = new int[length];
            Arrays.fill(count, 0);
            rad: for (int rad = length - 1; rad >= 0; rad--) {
                long foo = 0;
                for (int cnt = 1; cnt <= rad; ++cnt) {
                    foo += combinations[rad][cnt] * powersNine[rad - cnt];
                }
                final int startDigit = (rad == length - 1 ? 1 : 0);
                for (int digit = startDigit; digit < 10; digit++) {
                    boolean can = true;
                    ++count[digit];
                    for (int d = 0; d < 10; ++d) {
                        if (count[d] * powersTen[rad] + foo > digits[d]) {
                            can = false;
                            break;
                        }
                    }
                    --count[digit];
                    if (can) {
                        digits[digit] -= powersTen[rad];
                        for (int d = 0; d < 10; ++d) {
                            digits[d] -= count[d] * powersTen[rad] + foo;
                        }
                        int nextDigit = digit == 9 ? 1 : digit + 1;
                        int needZeros = digit == 9 ? length : rad;
                        boolean canContinue = digits[nextDigit] >= count[nextDigit] + 1;
                        canContinue &= digits[0] >= count[0] + needZeros;
                        if (digit < 9) for (int d = 0; d < 10; ++d) {
                            canContinue &= digits[d] >= count[d];
                        } else if (canContinue) {
                            continue length;
                        }
                        if (canContinue) continue;
                        result[rad--] = digit;
                        while (rad >= 0) {
                            result[rad--] = 9;
                        }
                        break length;
                    }
                    result[rad] = digit;
                    ++count[digit];
                    if (rad == 0) break length;
                    continue rad; // go to next rad
                }
            }
        }
        long answer = 0;
        for (int i = result.length - 1; i >= 0; --i) {
            answer = answer * 10 + result[i];
        }
        return answer;
    }

    public static void main(String[] args) {
        new T().run();
    }
}