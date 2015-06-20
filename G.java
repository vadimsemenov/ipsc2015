import java.io.*;
import java.util.*;

/**
 * Created by daria on 20.06.15.
 */
public class G {
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

    int[] ties;
    boolean[] visited;
    final int MOD = 1000000007;

    public void solve() throws IOException {
        int tests = in.nextInt();
        for (int test = 0; test < tests; test++) {
            int workers = in.nextInt();
            int colors = in.nextInt();
            int events = in.nextInt();

            ArrayList<Integer>[] workersGraph = new ArrayList[workers];
            for (int i = 0; i < workers; i++) {
                workersGraph[i] = new ArrayList<Integer>();
            }
            for (int i = 1; i < workers; i++) {
                int superVisor = in.nextInt();
                workersGraph[superVisor - 1].add(i);
            }


            ties = new int[workers];
            Arrays.fill(ties, 1);
            long answer = 0;
            visited = new boolean[workers];
            for (int i = 0; i < events; i++) {
                int person = in.nextInt() - 1;
                int importance = in.nextInt();
                int color = in.nextInt();

                if (color == 0) {
                    answer += ((long) (i + 1) * (long) ties[person]);
                    continue;
                }

                Arrays.fill(visited, false);
                dfs(workersGraph, person, importance, color);
            }
            out.println(answer % MOD);
        }
    }


    void dfs(ArrayList<Integer>[] graph, int vertex, int importance, int color) {
        visited[vertex] = true;
        ties[vertex] = color;

        if (importance != 0) {
            for (int to : graph[vertex]) {
                if (!visited[to]) {
                    dfs(graph, to, importance - 1, color);
                }
            }
        }
    }



    public void run() {
        try {
            in = new Scanner(new File("g2" + ".in"));
            out = new PrintWriter("g2" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new G().run();
    }
}