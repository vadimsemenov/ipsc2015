import java.io.*;
import java.util.*;

/**
 * Created by daria on 20.06.15.
 */
public class B {
    class FastScanner {
        StreamTokenizer st;

        FastScanner() {
            st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        }

        FastScanner(File f) {
            try {
                st = new StreamTokenizer(new BufferedReader(new FileReader(f)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        int nextInt() throws IOException {
            st.nextToken();
            return (int) st.nval;
        }

        String nextString() throws IOException {
            st.nextToken();
            return st.sval;
        }
    }

    Scanner in;
    PrintWriter out;

    public void solve() throws IOException {
        int n1 = in.nextInt();
        Pair[] adjectives1 = new Pair[n1];


        HashMap<String, Integer> adj1Str = new HashMap<String, Integer>();
        HashMap<String, Integer> adj2Str = new HashMap<String, Integer>();
        HashMap<String, Integer> nounStr = new HashMap<String, Integer>();
        for (int i = 0; i < n1; i++) {
            adjectives1[i] = new Pair(in.next(), in.nextInt());
            adj1Str.put(adjectives1[i].s, adjectives1[i].n);
        }

        int n2 = in.nextInt();
        Pair[] adjectives2 = new Pair[n2];

        for (int i = 0; i < n2; i++) {
            adjectives2[i] = new Pair(in.next(), in.nextInt());
            adj2Str.put(adjectives2[i].s, adjectives2[i].n);
        }

        int n3 = in.nextInt();
        Pair[] nouns = new Pair[n3];
        for (int i = 0; i < n3; i++) {
            nouns[i] = new Pair(in.next(), in.nextInt());
            nounStr.put(nouns[i].s, nouns[i].n);
        }



        HashMap<Integer, Stack<String>> possibleInsults = new HashMap<Integer, Stack<String>>();
        for (int i = 0; i < n1; i++) {
            String s1 = adjectives1[i].s;
            int str1 = adjectives1[i].n;
            for (int j = 0; j < n2; j++) {
                String s2 = adjectives2[j].s;
                int str2 = adjectives2[j].n;
                for (int k = 0; k < n3; k++) {
                    String s3 = nouns[k].s;
                    int str3 = nouns[k].n;
                    if (!possibleInsults.containsKey(str1 + str2 + str3)) {
                        possibleInsults.put(str1 + str2 + str3, new Stack<String>());

                    }
                    possibleInsults.get(str1 + str2 + str3).push(s1 + " " + s2 + " " + s3);
                }
            }
        }



        int insults = in.nextInt();

        HashMap<String, Integer> completeInsults = new HashMap<String, Integer>();

        for (int i = 0; i < insults; i++) {
            int strength = 0;
            String adj1 = in.next();
            strength += adj1Str.get(adj1);

            String adj2 = in.next();
            strength += adj2Str.get(adj2);

            String noun = in.next();
            strength += nounStr.get(noun);

            String insult = possibleInsults.get(strength + 1).pop();

            out.println(insult);

        }
    }

    class Pair {
        String s;
        int n;

        public Pair(String s, int n) {
            this.s = s;
            this.n = n;
        }
    }




    public void run() {
        try {
            in = new Scanner(new File("b1" + ".in"));
            out = new PrintWriter("b1" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new B().run();
    }
}