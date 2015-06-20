import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author Vadim Semenov (semenov@rain.ifmo.ru)
 */
public class M {
    public static class IO {
        private final ScriptEngine engine;
        public IO(final ScriptEngine engine) {
            this.engine = engine;
        }

        public void print(final String message) {
            System.out.print(message);
        }

        public void println(final String message) {
            System.out.println(message);
        }

        public void printlnd(final String message) {
            System.err.println(message);
        }

        public void include(final String file) throws Exception {
            engine.eval(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        }
    }

    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    private static final char[] chars = "![]+-*".toCharArray();
    private static int MAX_SIZE = 7_000;
    private static final String FOO = "!![]";
    private static final String ONE = "+!![]";
    private static final String TWO = FOO + ONE;
    private static final String TWO_IN_BRACKETS = "[" + TWO + "]";
    private static final int need = 1000;

    private static String[] dp = new String[need + 1];
    private static String[] dpMul = new String[need + 1];

    public static void main(String[] args) {
        dp[0] = "+![]";
        dp[1] = "!![]";
        dpMul[1] = "!![]";
        dp[2] = dp[1] + "+" + dp[1];
        dpMul[2] = "[" + dp[2] + "]";
        boolean relaxed = true;
        while (relaxed) {
            relaxed = false;
            for (int i = 3; i <= need; ++i) {
                for (int f = 1; f < i; ++f) {
                    int s = i - f;
                    String next = dp[f] + "+" + dp[s];
                    if (dp[i] == null || dp[i].length() > next.length()) {
                        relaxed = true;
                        dp[i] = next;
                    }
                    if (i % f == 0) {
                        s = i / f;
                        String fst = "[" + dp[f] + "]";
                        if (dpMul[f] != null && dpMul[f].length() < fst.length()) {
                            fst = dpMul[f];
                        }
                        String snd = "[" + dp[s] + "]";
                        if (dpMul[s] != null && dpMul[s].length() < snd.length()) {
                            snd = dpMul[s];
                        }
                        next = fst + "*" + snd;
                        if (dpMul[i] == null || dpMul[i].length() > next.length()) {
                            relaxed = true;
                            dpMul[i] = next;
                        }
                    }
                }
            }
            for (int i = 3; i <= need; ++i) {
                for (int j = 1; j < i; ++j) {
                    String next = dp[i] + "-[" + dp[j] +"]";
                    if (j == 1) {
                        next = dp[i] + "-!![]";
                    }
                    if (dp[j] == null || dp[i - j].length() > next.length()) {
                        relaxed = true;
                        dp[i - j] = next;
                        if (dpMul[i - j] == null || dpMul[i - j].length() > dp[i - j].length() + 2) {
                            dpMul[i - j] = "[" + dp[i - j] + "]";
                        }
                    }
                }
            }
            for (int i = 0; i < dp.length; ++i) {
                if (dpMul[i] != null && dpMul[i].length() < dp[i].length()) {
                    dp[i] = dpMul[i];
                    relaxed = true;
                }
            }
        }
        dp[1] = "+!![]";
//        dp[727] = "[!![]+!![]+!![]]*[[!![]+!![]+!![]]*[[!![]+!![]+!![]]*[[!![]+!![]+!![]]*[[!![]+!![]+!![]]*[!![]+!![]+!![]]]]]]-!![]-!![]";
        try (PrintWriter out = new PrintWriter(new FileOutputStream("m-small.out"))) {
            for (String exp : dp) {
                out.println(exp);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.err.println(dp[727]);
        int max = -1;
        for (int i = 0; i < dp.length; ++i) {
            max = Math.max(max, dp[i].length());
//            System.out.println(i + " " + dp[i]);
        }
        System.out.println(max);
//        System.err.println(Arrays.stream(dp).max((first, second) -> Integer.compare(first.length(), second.length())));
//        if (true) return;
//        int qty = 0;
            try {
                engine.put("io", new IO(engine));
                engine.put("global", engine.getContext().getBindings(ScriptContext.ENGINE_SCOPE));
                engine.eval("var println = function() { io.println(Array.prototype.map.call(arguments, String).join(' ')); };");
                engine.eval("var print   = function() { io.print  (Array.prototype.map.call(arguments, String).join(' ')); };");
                engine.eval("var printlnd= function() { io.printlnd(Array.prototype.map.call(arguments, String).join(' ')); };");
                engine.eval("var include = function(file) { io.include(file); }");
                try {
                    engine.eval(new InputStreamReader(new FileInputStream("script.js"), "UTF-8"));
                } catch (UnsupportedEncodingException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for (String exp : dp) engine.eval("put(" + exp + ")");
                engine.eval("writeAll()");
//                for (int i = 0; i <= need; ++i) {
//                    StringBuilder res = new StringBuilder();
//                    boolean first = true;
//                    for (int bit = 31; bit >= 0; --bit) if ((i >>> bit & 1) == 1) {
//                        if (first) {
//                            first = false;
//                        } else {
//                            res.append("+");
//                        }
//                        if (bit == 0) res.append(FOO);
//                        for (int k = 0; k < bit; ++k) {
//                            if (k > 0) res.append("*");
//                            res.append(TWO_IN_BRACKETS);
//                        }
//                    }
//                    System.err.println(res.length());
//                    System.err.println(res.toString());
////                    assert res.length() <= 75;
//                    engine.eval("put(" + res.toString() + ")");
//                }
//                char[] string = new char[75];
//                for (int i = 0; i < string.length; ++i) {
//                    string[i++] = '[';
//                    string[i++] = ']';
//                }
//                generate(string, 2);
//                System.err.println("generated");
//                out.println(context.getWriter().toString());
//                StringTokenizer tokenizer = new StringTokenizer(context.getWriter().toString());
//                for (String exp : list) {
//                    String token = tokenizer.nextToken();
//                    System.err.println(exp + " " + token);
//                    if (token.equals("true")) {
//                        out.println(exp);
//                        ++qty;
//                        if (qty == need) break;
//                    }
//                }
//                System.err.println(qty);
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
    }

    private static void generate(char[] string, int i) throws ScriptException {
        if (i > string.length) return;
        if (i > 2) engine.eval("put(" + String.valueOf(string, 0, i) + ")");
        for (char ch : new char[]{'-', '*', '+'}) {
            string[i] = ch;
            generate(string, i + 3);
        }
    }
}
