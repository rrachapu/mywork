import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

public class BipartiteMatching {
    HashMap<Integer,ArrayList<Node>> adj_list;          // adjacency list representation of graph
    int []match;                                        // which node a node is matched with
    int M, N;                                           // M, N are number of machines and cards respectively
    boolean []used;                                     // whether a node has been already used in matching or not

    /** TODO
     * initialize constructor function.
     * @param M : number of machines
     * @param N : number of cards
     */
    public BipartiteMatching(int M, int N)
    {
        this.M = M;
        this.N = N;
        this.adj_list = new HashMap<Integer, ArrayList<Node>>();
        this.match = new int[M+N];
        Arrays.fill(match, -1);
        this.used = new boolean[M+N];
        Arrays.fill(used, false);
        for (int i = 0; i < M + N; i++) {
            adj_list.put(i, new ArrayList<Node>());
        }
    }

    /** TODO
     * gradually build the graph by inserting edges
     * this function inserts all the nodes connected with node u

     * hint : remember that this is an undirected graph.

     * @param u : node under consideration
     * @param node_list : all the nodes connected with node u
     **/
    public void insList(int u, int []node_list)
    {

        // u is 0 indexed
        // node_list is non-zero indexed (starting from 1)
        // u is index of a card
        // node_list are indices of machines (to get actual index, subtract 0)
        Node in = new Node(u);
        ArrayList<Node> arr = adj_list.get(u);
        for (int i = 0; i < node_list.length; i++) {
            arr.add(new Node(node_list[i] - 1 + N));
            ArrayList<Node> find = adj_list.get(node_list[i] - 1 + N); // ith machine
            if (find == null) {
                System.out.println("find was null and node was: ");
                System.out.println("searched for " + (node_list[i] - 1));
                System.out.println(in);
            }
            find.add(in);
        }
        // System.out.println("insList produced: " + adj_list);

    }

    /** TODO
     * implement DFS function
     *
     * @param v : starting node for DFS
     *
     * @return true if there is an augment path; if no, return false.
     */
    boolean dfs(int v)
    {
        if (used[v]) {
            return false;
        }
        used[v] = true;
        ArrayList<Node> v_list = adj_list.get(v);
        for (int i = 0; i < v_list.size(); i++) {
            int u = v_list.get(i).node_id;
            if (match[u] == -1) {
                match[u] = v;
                match[v] = u;
                return true;
            }
            else {
                int w = match[u];
                if (!used[w] && dfs(w)) {
                    match[u] = v;
                    match[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    /** TODO
     *
     * implement the bipartite matching algorithm
     * traverse the nodes
     * call dfs to see if there exists any augment path
     *
     * @return the max matching
     */
    int bipartiteMatching()
    {
        int res = 0;
        for (int i = 0; i < N; i++) {
            if (match[i] == -1) {
                Arrays.fill(used, false);
                if (dfs(i)) {
                    res++;
                }
            }
        }
        return res;
    }

    public static void main(String []args)
    {
        try {
            BipartiteMatching model = new BipartiteMatching(0, 0);
            File myObj = new File("./src/sampleBipartiteData.txt");
            Scanner myReader = new Scanner(myObj);
            int line = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println("Line " + line + ": " + data);
//                System.out.println(data);
                if(line == 0)
                {
                    String []str = data.split(" ");
                    int M = Integer.parseInt(str[0]);
                    int N = Integer.parseInt(str[1]);
                    System.out.println(M + "  " + N);
                    model = new BipartiteMatching(M, N);
                }
                else
                {
                    String []str = data.split(" ");
                    int [] input = new int[str.length];
                    for (int i=0; i<str.length; i++)
                        input[i] = Integer.parseInt(str[i]);

//                    System.out.println(input);
                    model.insList(line-1, input);
                }
                line += 1;
            }
            myReader.close();
            System.out.println(model.adj_list);
            System.out.println(model.adj_list.size());
            int res = model.bipartiteMatching();
            System.out.println("BipartiteMatching is: "+res);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}