
/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 1
 *
 * TODO: implement scapegoat.
 *
 * @author TODO: add your username here
 * @username TODO: add your Purdue username here
 * @sources TODO: list your sources here
 *
 * Use the algorithms written in sorting to implement this class.
 *
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Scapegoat {

    // Root node
    private Node root;
    // max node count required to implement deletion.
    private int MaxNodeCount = 0;
    // total node number
    private int NodeCount = 0;
    private int count = 0;
    // parameter alpha
    private static final double threshold = 0.57;

    public class Node {
        T data;
        Node parent;
        Node left;
        Node right;
        public Node (T data, Node parent, Node left, Node right){
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
        public String toString(){
            return "[data="+data+"]";
        }
    }




    /**
     *
     * Constructor
     *
     */
    public Scapegoat() {
        root = null;
    }


    /**
     *
     * Constructor
     *
     */
    public Scapegoat(T data) {
        root = new Node(data, null, null, null);
        NodeCount ++;
    }


    /**
     *
     * @return return the root of the scapegoat tree
     *
     */
    public Node root(){
        return this.root;
    }


    /**
     *
     * This function finds the first scapegoat node and returns it.
     *
     * @return void
     *
     */

    public void printNodes(Node node) {
        //System.out.println("--------------------");
        if (node == null) {
            return;
        }
        System.out.println(node);
        System.out.println(node + " left: " + node.left);
        System.out.println(node + " right: " + node.right);
        System.out.println(node + " parent: " + node.parent);
        printNodes(node.left);
        printNodes(node.right);


    }
    private Node scapegoatNode(Node node) {
        // TODO:
        // -----------------------

        if (node == null) {
            //System.out.println("node was null so scapegoat is null");
            return null;
        }

        if (node.parent == null) {
            //System.out.println("reached root so scapegoat node must be root");
            return node;
        }

        int leftSize;
        int rightSize;


        while (node != null) {
            leftSize = 0;
            rightSize = 0;
            if (node.left != null) {
                leftSize = size(node.left);
            }
            if (node.right != null) {
                rightSize = size(node.right);
            }

            if ((leftSize > threshold * size(node)) || (rightSize > threshold * size(node))) {
                return node;
            }

            node = node.parent;
        }
        return node;

        // -----------------------
    }


    /**
     *
     * This function re-builds the tree rooted at node into a perfectly balanced tree.
     *
     * @return void
     *
     */

    public Node rebuild(Node node) {
        // TODO
        // rebuild the subtree whose INPUT root is node
        // -----------------------
        if (node == null) {
            return null;
        }

        Node p = node.parent;
        List<Node> nodeList = inorder(node);
        node = treeFromList(nodeList);
        labelparent(node);
        node.parent = p;

        return node;
        // -----------------------
    }

    public Node treeFromList(List<Node> nodeList)
    {
        int n = nodeList.size();

        if (n == 0) {
            return null;
        }

        int m = (n / 2);

        Node r = nodeList.get(m);
        r.left = treeFromList(nodeList.subList(0, m));
        r.right = treeFromList(nodeList.subList(m + 1, n));

        return r;
    }




    public void printStuff() {
        if (this.root != null) {
            System.out.println(preorder(this.root));
            System.out.println(inorder(this.root));
        } else {
            System.out.println("[empty tree]");
        }
    }

    public int depth(Node node) {
        if (node == null) {
            return -1;
        } else {
            int ld = depth(node.left);
            int rd = depth(node.right);

            if (ld > rd) {
                return (ld + 1);
            } else {
                return rd + 1;
            }
        }
    }

    /**
     *
     * This function adds an element into the tree.
     *
     * @return void
     *
     */
    public void add(T data) {
        System.out.println("------------------------add " + data.a);
        System.out.println("before: ");
        System.out.println("mnc = " + MaxNodeCount);
        System.out.println("nc = " + NodeCount);
        if (root == null) {
            root = new Node(data, null, null, null);
            NodeCount++;
            MaxNodeCount = Math.max(MaxNodeCount, NodeCount);
        } else {
            Node look = find(data);
            if (look == null) {
                NodeCount++;
                MaxNodeCount = Math.max(MaxNodeCount, NodeCount);
                // if node with data doesn't alr exist
                Node added = new Node(data, null, null, null);
                add_help(this.root, added);
                if (depth(root) > Math.floor(Math.log(size(root)) / (Math.log(1/threshold)))) {
                    // not alpha weight balanced
                    Node sgNode = scapegoatNode(added);
                    if (sgNode != null) {
                        int childType = typeOfChild(sgNode);
                        Node p = sgNode.parent;
                        if (sgNode.parent == null) {
                            MaxNodeCount = NodeCount;
                        }
                        Node newRoot = rebuild(sgNode);
                        if (childType == 1) {
                            // root
                            newRoot.parent = null;
                            root = newRoot;
                        } else if (childType == 2) {
                            // left child
                            newRoot.parent = p;
                            p.left = newRoot;
                        } else {
                            // right child
                            newRoot.parent = p;
                            p.right = newRoot;
                        }
                    }
                }
            } else {
                System.out.println("already exists in tree");
            }

        }
        System.out.println("preorder: " + preorder(root));
        System.out.println("inorder: " + inorder(root));
        System.out.println("before: ");
        System.out.println("mnc = " + MaxNodeCount);
        System.out.println("nc = " + NodeCount);
    }


    public void labelparent(Node node) {
        if (node.right == null && node.left == null) {
            return;
        }
        if (node.left != null) {
            node.left.parent = node;
            labelparent(node.left);
        }
        if (node.right != null) {
            node.right.parent = node;
            labelparent(node.right);
        }
    }

    public Node add_help(Node node, Node newNode) {
        if (node == null) {
            return newNode;
        }
        if (newNode.data.compareTo(node.data) < 0) {
            Node l = add_help(node.left, newNode);
            node.left = l;
            l.parent = node;
        } else if (newNode.data.compareTo(node.data) > 0) {
            Node r = add_help(node.right, newNode);
            node.right = r;
            r.parent = node;
        }

        return node;
    }


    /**
     *
     * This function removes an element from the tree.
     *
     * @return void
     *
     */

    public void remove(T data) {
        // TODO
        System.out.println("-----------------------------------remove " + data.a);
        System.out.println("before: ");
        System.out.println("mnc = " + MaxNodeCount);
        System.out.println("nc = " + NodeCount);
        Node myNode = removeNode(root, data);
        if (myNode == null) {
            System.out.println("it was null");
            return;
        }
        // System.out.println("00000000000000000");
        // printNodes(root);
        if (root != null) {
           // System.out.println("root was not null");
            labelparent(root);
        }
        NodeCount--;
        //rebuild(root);
        // System.out.println("root is " + root);
        Node r = root;
        if (NodeCount <= threshold * MaxNodeCount) {
            r = rebuild(root);
            MaxNodeCount = NodeCount;
        }
        // System.out.println("-----------------------");
        root = r;
        // printNodes(root);
        if (root != null) {
            System.out.println("preorder: " + preorder(root));
            System.out.println("inorder: " + inorder(root));
        }
        //printNodes(root);
        //System.out.println("end remove");
        System.out.println("after: ");
        System.out.println("mnc = " + MaxNodeCount);
        System.out.println("nc = " + NodeCount);
    }

    public int typeOfChild(Node node) {
        if (node.parent == null) {
                return 1;
                // is orphan
        } else if (node.parent.left != null) {
            if (node.parent.left.data.compareTo(node.data) == 0) {
                return 2;
                // is left child
            }
        } else if (node.parent.right != null) {
            if (node.parent.right.data.compareTo(node.data) == 0) {
                return 3;
                // is right child
            }
        }
        return 4;
    }

    public Node removeNode(Node node, T data) {
        Node curr = node;
        Node prev = null;

        while (curr != null && curr.data.compareTo(data) != 0) {
            prev = curr;
            if (curr.data.compareTo(data) < 0) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }

        if (curr == null) {
            System.out.println("remove: didn't find node with " + data);
            return null;
        }

        if (curr.parent == null) {
            // if deleting root
            if (curr.left == null && curr.right == null) {
                root = null;
                return curr;
            } else if (curr.right == null) {
                Node succ = curr.left;
                succ.parent = null;
                curr.left = null;
                root = succ;
                return curr;
            } else if (curr.left == null){
                Node succ = curr.right;
                succ.parent = null;
                curr.right = null;
                root = succ;
                return curr;
            }
        }

        if (curr.left == null || curr.right == null) {
            Node newCurr = null;
            if (curr.left == null) {
                newCurr = curr.right;
            } else {
                newCurr = curr.left;
            }

            if (prev == null) {
                return newCurr;
            }

            if (curr == prev.left) {
                prev.left = newCurr;
            } else {
                prev.right = newCurr;
            }

            curr = null;
        } else {
            Node p = null;
            Node temp = null;

            temp = curr.right;
            while (temp.left != null) {
                p = temp;
                temp = temp.left;
            }

            if (p != null) {
                p.left = temp.right;
            } else {
                curr.right = temp.right;
            }
            curr.data = temp.data;
            temp = null;
        }

        return node;
    }

    // preorder traversal
    public List<Node> preorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(node);
        // System.out.println(node);
        if(node.left != null){
            nodes.addAll(preorder(node.left));
        }
        if(node.right != null){
            nodes.addAll(preorder(node.right));
        }
        return nodes;
    }


    // inorder traversal
    public List<Node> inorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        if(node.left != null){
            nodes.addAll(inorder(node.left));
        }
        nodes.add(node);
        if(node.right != null){
            nodes.addAll(inorder(node.right));
        }
        return nodes;
    }


    // not used, but you can use this to debug
    public void print() {
        List<Node> nodes = inorder(root);
        for(int i=0;i<nodes.size();i++){
            System.out.println(nodes.get(i).toString());
        }
    }


    // return the node whose data is the same as the given data.
    public Node find(T data) {
        Node current = root;
        int result;
        while(current != null){
            result = data.compareTo(current.data);
            if(result == 0){
                return current;
            }else if(result > 0){
                current = current.right;
            }else{
                current = current.left;
            }
        }
        return null;
    }


    // find the succNode
    public Node succNode(Node node) {
        Node succ = null;
        int result;
        Node current = node;
        while(current != null){
            result = node.data.compareTo(current.data);
            if(result < 0){
                succ = current;
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return succ;
    }

    // used in scapegoatNode function, not a delicated one...
    // Just the brute force calculating the node's children's nubmer. Can be accelerated.
    private int size (Node node) {
        if (node == null)
            return 0;
        return 1 + size(node.left) + size(node.right);
    }


    // BFS(not used, you may use this to help you debug)
    public List<Node> breadthFirstSearch() {
        Node node = root;
        List<Node> nodes = new ArrayList<Node>();
        Deque<Node> deque = new ArrayDeque<Node>();
        if(node != null){
            deque.offer(node);
        }
        while(!deque.isEmpty()){
            Node first = deque.poll();
            nodes.add(first);
            if(first.left != null){
                deque.offer(first.left);
            }
            if(first.right != null){
                deque.offer(first.right);
            }
        }
        return nodes;
    }


    public static void main(String[] args) {
        // write your code here
        Scapegoat tree = new Scapegoat();
        tree.add(new T(10));
        tree.add(new T(9));
        tree.add(new T(8));
        tree.printNodes(tree.root);
        tree.printStuff();



    }


}

