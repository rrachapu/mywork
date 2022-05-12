/**
 * CS 251: Data Structures and Algorithms
 * Project 1
 * <p>
 * TODO: Complete Expression.
 *
 * @author , TODO: add your name here
 * @username , TODO: add your Purdue username here
 * @sources TODO: list your sources here
 */


public class Expression {

    /**
     * The expression to validate and evaluate
     */
    private final String expression;
    /**
     * Used for testing please do not modify
     */
    StringBuilder treeString;

    /**
     * Constructor to initialize the expression
     *
     * @param expression - the expression to evaluate
     */
    public Expression(String expression) {
        this.expression = expression;
        treeString = null;
    }

    /**
     * Checks whether the expression is valid or not
     *
     * @return true if the expression is valid
     */
    public boolean isValid() {
        MyStack<String> r = new MyStack<String>();
        MyStack<String> n = new MyStack<String>();
        String exp = this.expression.replaceAll("\\s", "");
        for (int k = 0; k < exp.length(); k++) {
            String temp = exp.substring(k, k + 1);
            System.out.println(temp);
            if (("(+-*/%").indexOf(temp) != -1) {
                r.push(temp);
            } else if (Character.isLowerCase(temp.charAt(0))) {
                n.push(temp);
            } else {
                while(true) {
                    try {
                        //System.out.println("Peek " + (r.peek().value));
                        if (r.peek().equals("(")) {
                            r.pop();
                            break;
                        }
                        else {
                            r.pop();
                            n.pop();
                            n.push(n.pop());
                        }
                    } catch (EmptyStackException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }


        }
        while (r.getSize() != 0) {
            try {
                r.pop();
                n.pop();
                n.push(n.pop());
            } catch (EmptyStackException e) {
                System.out.println("failed at 114");
                e.printStackTrace();
                return false;
            }
        }
        if (n.getSize() == 1 && r.getSize() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Makes an expression tree of the expression
     *
     * @return the root of the expression tree
     */
    public TreeNode makeTree() {
        //TODO complete makeTree
        MyStack<TreeNode> r = new MyStack<TreeNode>();
        MyStack<TreeNode> n = new MyStack<TreeNode>();
        TreeNode tree = new TreeNode();
        String exp = this.expression.replaceAll("\\s", "");
        for (int k = 0; k < exp.length(); k++) {
            String temp = exp.substring(k, k + 1);
            System.out.println(temp);
            if (("(+-*/%").indexOf(temp) != -1) {
                r.push(new TreeNode(temp.charAt(0)));
            } else if (Character.isLowerCase(temp.charAt(0))) {
                n.push(new TreeNode(temp.charAt(0)));
            } else {
                while(true) {
                    try {
                        System.out.println("Peek " + (r.peek().value));
                        if (r.peek().value == '(') {
                            r.pop();
                            break;
                        }
                        else {
                            TreeNode optr = r.pop();
                            TreeNode nd_f = n.pop();
                            TreeNode nd_s = n.pop();
                            optr.right = nd_f;
                            optr.left = nd_s;
                            System.out.println("Right: optr.right.value");
                            System.out.println(optr.left.value);
                            n.push(optr);
                        }
                    } catch (EmptyStackException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }


        }
        while (r.getSize() != 0) {
            try {
                System.out.println(184);
                TreeNode optr = r.pop();
                TreeNode nd_f = n.pop();
                TreeNode nd_s = n.pop();
                //System.out.println(optr.value);
                //System.out.println(nd_f.value);
                optr.right = nd_f;
                optr.left = nd_s;
                n.push(optr);
            } catch (EmptyStackException e) {
                System.out.println("failed at 114");
                e.printStackTrace();
                return null;
            }
        }
        try {
            return n.peek();
        } catch (EmptyStackException e) {
            e.printStackTrace();
            System.out.println("186");
            return null;
        }
        //System.out.println(r.getSize());
        //System.out.println(n.getSize());

            /*else if (temp.equals(")")) {
                while (true) {
                    try {
                        if (r.peek().equals("(") || r.getSize() < 1 || n.getSize() < 2) {
                            break;
                        } else {
                            TreeNode optr = new TreeNode(r.pop().value);
                            TreeNode nd_f = new TreeNode(n.pop().value);
                            TreeNode nd_s = new TreeNode(n.pop().value);
                            optr.right = nd_f;
                            optr.left = nd_s;
                            n.push(optr);
                        }
                    } catch (EmptyStackException e) {
                        System.out.println("failed at error");
                        e.printStackTrace();
                        return null;
                    }
                }
            } else {
                System.out.println("ERROR");
                return null;
            }
            while (!r.isEmpty()) {
                TreeNode optr = null;
                try {
                    optr = new TreeNode(r.pop().value);
                    TreeNode nd_f = new TreeNode(n.pop().value);
                    TreeNode nd_s = new TreeNode(n.pop().value);
                    optr.right = nd_f;
                    optr.left = nd_s;
                    n.push(optr);
                    return n.peek();
                } catch (EmptyStackException e) {
                    e.printStackTrace();
                }
            }
        }*/


    }

    /**
     * Evaluate the expression tree
     *
     * @param root   of the expression tree
     * @param values of all the variables the values is an int array of size 26.
     *               values[0] represent the value of ‘a’ and values[1] represent the value of ‘b’ and so on
     * @return the value of the evaluated expression
     */
    public int evaluate(TreeNode root, int[] values) {
        //TODO complete evaluate
        System.out.println("running on " + root.value);
        if (("+-*/%").indexOf(root.value) != -1) {
            return operate(evaluate(root.left, values), evaluate(root.right, values), root.value);
            //System.out.println("this is what i got: " + evaluate(root.left, values));
            //System.out.println("this is what i got: " + evaluate(root.right, values));
            //System.out.println(root.value);
        } else if (Character.isLowerCase(root.value)) {
            //System.out.println("got here");
            return values[(int)(root.value) - 97];
        }
        return 999;
    }

    private int operate(int a, int b, char op) {
        if (op == '+') {
            System.out.println("returning " + (a+b));
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else if (op == '/') {
            return a/b;
        } else if (op == '%') {
            return a%b;
        }
        System.out.println("failed in operate()");
        return 888;
    }

    /**
     * DO NOT MODIFY
     * Used to print the tree and for testing
     * source = https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java/42449385#42449385
     *
     * @param root
     * @return
     */
    public String print(TreeNode root) {
        treeString = new StringBuilder();
        print("", root, false);
        return treeString.toString();
    }

    /**
     * DO NOT MODIFY
     * Used to print the tree and for testing
     * source = https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java/42449385#42449385
     *
     * @param prefix
     * @param n
     * @param isLeft
     */
    public void print(String prefix, TreeNode n, boolean isLeft) {
        if (n != null) {
            treeString.append(prefix + (isLeft ? "|-- " : "\\-- ") + n.value + "\n");
            print(prefix + (isLeft ? "|   " : "    "), n.left, true);
            print(prefix + (isLeft ? "|   " : "    "), n.right, false);
        }
    }


    /**
     * Main Can be used for manual testing
     *
     * @param args
     */
//    public static void main(String[] args) {
//        Expression expression = new Expression("(a + (a + b * z) + (x % u) + (p * x))");
//        System.out.println(expression.isValid());
//        TreeNode root = expression.makeTree();
//        System.out.println(expression.print(root));
//        int[] chars = new int[26];
//        for (int i = 0; i < 26; i++) {
//            chars[i] = i + i;
//            System.out.println((char)('a' + i));
//            System.out.println(chars[i]);
//        }
//        System.out.println(expression.evaluate(root, chars));
//
//    }

}
