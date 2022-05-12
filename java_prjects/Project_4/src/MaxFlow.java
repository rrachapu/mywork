import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MaxFlow
{
    HashMap<Integer,ArrayList<Edge>> adj_list;      // adjacency list representation of graph
    int []parent;                                   // parent array used in bfs
    int N;
    int pond;
    // total number of nodes

    /** TODO
     * initialize constructor function.
     * @param N : number of nodes
     */
    public MaxFlow(int N)
    {
        this.N = N;
        this.adj_list = new HashMap<>(N + 2);
        this.parent = new int[N + 2];
        Arrays.fill(parent, -1);
    }

    /** TODO
     * gradually build the graph by inserting edges
     * this function inserts a new edge into the graph
     *
     * hint : remember to consider the opposite direction of flow
     *
     * @param source : source node
     * @param destination : destination node
     * @param flow_rate : maximum rate of flow through the edge
     */
    public void insEdge(int source, int destination, int flow_rate)
    {
        if (!adj_list.containsKey(source)) {
            adj_list.put(source, new ArrayList<Edge>());
        }
        adj_list.get(source).add(new Edge(source, destination, flow_rate));

        if (!adj_list.containsKey(destination)) {
            adj_list.put(destination, new ArrayList<Edge>());
        }
        adj_list.get(destination).add(new Edge(destination, source, 0));

    }

    /** TODO
     * implement BFS function
     *
     * @return true if there is a path; if no, return false.
     */
    boolean bfs() {
        LinkedList<Integer> arr = new LinkedList<>();
        boolean[] v = new boolean[N + 2];

        parent[0] = -1;
        v[0] = true;
        arr.add(0);

        while (!arr.isEmpty()) {
            int cur = arr.remove();
            ArrayList<Edge> adj = adj_list.get(cur);

            for (int i = 0; i < adj.size(); i++) {
                int nxt = adj.get(i).destination;
                if (!v[nxt] & adj.get(i).flow_rate > 0) {
                    arr.add(nxt);
                    v[nxt] = true;
                    parent[nxt] = cur;
                }
            }
        }
        return v[v.length - 1];
    }


    /** TODO
     * implement path augmentation
     *
     * traverse the graph using BFS to find a path from source to sink
     * find the possible amount of flow along the path
     * add the flow to the total maximum flow
     * update the flow rate of the edges along the path
     * repeat as long as a path exist from source to sink with nonzero flow
     *
     * @return maximum amount of flow
     */
    int pathAugmentation() {
        pond = 0;
        int barrel = this.N + 1;
        int maxFlow = 0;
        boolean check = bfs();
        while (check) {
            ArrayList<Edge> P = new ArrayList<>();
            int val = N + 1;

            while (val != 0) {
                ArrayList<Edge> graph = adj_list.get(parent[val]);

                for (int i = 0; i < graph.size(); i++) {
                    if (graph.get(i).destination == val) {
                        P.add(graph.get(i));
                    }
                }
                val = parent[val];
            }
            int flow = Integer.MAX_VALUE;

            for (int i = 0; i < P.size(); i++) {
                flow = Math.min(flow, getFlow(P.get(i).source, P.get(i).destination));
            }
            for (int i = 0; i < P.size(); i++) {
                setFlow(P.get(i).source, P.get(i).destination, getFlow(P.get(i).source, P.get(i).destination) - flow);
                setFlow(P.get(i).destination, P.get(i).source, getFlow(P.get(i).destination, P.get(i).source) + flow);
            }
            maxFlow += flow;
            check = bfs();
        }
        return maxFlow;
    }

    /** TODO
     * get the flow along a certain edge
     *
     * @param source : source node of the directed edge
     * @param destination : destination node of the directed edge
     *
     * @return flow rate along the edge
     */
    int getFlow(int source, int destination)
    {
        ArrayList<Edge> sc = adj_list.get(source);
        for (int i = 0; i < sc.size(); i++) {
            if (sc.get(i).destination == destination) {
                return sc.get(i).flow_rate;
            }
        }
        return 0;
    }

    /** TODO
     * set the value of flow along a certain edge
     *
     * @param source : source node of the directed edge
     * @param destination : destination node of the directed edge
     * @param flow_rate : flow rate along the edge
     */
    void setFlow(int source, int destination, int flow_rate)
    {
        ArrayList<Edge> sc = adj_list.get(source);
        for (int i = 0; i < sc.size(); i++) {
            if (sc.get(i).destination == destination) {
                sc.get(i).flow_rate = flow_rate;
                break;
            }
        }
        adj_list.put(source, sc);
    }

    public static void main(String []args)
    {
        try {
            MaxFlow obmax = new MaxFlow(0);
            File myObj = new File("./src/sampleMaxFlowData.txt");
            Scanner myReader = new Scanner(myObj);
            int line = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(line == 0)
                {
                    int tot = Integer.parseInt(data);
                    obmax = new MaxFlow(tot);
                }
                else
                {
                    String []comp = data.split(" ");
                    int s = Integer.parseInt(comp[0]);
                    int d = Integer.parseInt(comp[1]);
                    int f = Integer.parseInt(comp[2]);
                    obmax.insEdge(s, d, f);
                }
                line += 1;
            }
            myReader.close();
            int mflow = obmax.pathAugmentation();
            System.out.println("Maxflow is: "+mflow);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}