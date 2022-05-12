public class TreeNode {

    /**
     * Value of the node
     */
    char value;

    /**
     * Pointer to the left child
     */
    TreeNode left;

    /**
     * Pointer to the right child
     */
    TreeNode right;

    /**
     * Default constructor
     */
    public TreeNode() {
        value = '\0';
        this.left = null;
        this.right = null;
    }

    /**
     * Constructor to initialize with value
     * @param value
     */
    public TreeNode(char value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public String toString() {
        return this.value + " " + this.left.value + " " + this.right.value;
    }
}
