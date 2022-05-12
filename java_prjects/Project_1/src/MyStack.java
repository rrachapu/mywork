import java.util.Stack;

/**
 * CS 251: Data Structures and Algorithms
 * Project 1
 * <p>
 * TODO: Complete MyStack.
 *
 * @author , TODO: add your name here
 * @username , TODO: add your Purdue username here
 * @sources TODO: list your sources here
 */

public class MyStack<Item> {


    /**
     * Pointer to top of the stack
     */
    private StackNode<Item> top;
    /**
     * Keeps track of the size of the stack
     */
    private int size;

    /**
     * Default constructor of the class initializes all
     * parameters to default values
     */
    public MyStack() {
        //TODO implement MyStack
        //Set stack as null
        //size = 0
        size = 0;
        top = null;
    }

    /**
     * @return if the stack is empty or not
     */
    public boolean isEmpty() {
        //TODO implement isEmpty
        if (size == 0) {
            return true;
        }

        return false;
    }

    /**
     * Pushes a new a new value into the stack
     * Remember to update size
     *
     * @param value the value to be pushed
     */
    public void push(Item value) {
        //TODO implement push
        // if empty, then make head have item value
        // if not empty, make head item and
        if (this.isEmpty()) {
            this.top = new StackNode(value);
        } else {
            StackNode<Item> temp = new StackNode(value);
            temp.next = this.top;
            this.top = temp;
        }
        this.size++;
    }

    /**
     * Peeks the top element of the stack
     *
     * @return the value at the top of the stack
     * @throws EmptyStackException if the stack is empty.
     *                             You can throw an exception by “throw new EmptyStackException();”
     */
    public Item peek() throws EmptyStackException {
        //TODO implement peek
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        return this.top.value;
    }

    /**
     * Pops the top element of the stack
     * Remember to update size
     *
     * @return the popped element
     * @throws EmptyStackException if the stack is empty
     *                             You can throw an exception by “throw new EmptyStackException();”
     */
    public Item pop() throws EmptyStackException {
        //TODO implement pop
        if (this.isEmpty()) {
            throw new EmptyStackException();
        } else {
            StackNode<Item> temp = this.top;
            this.top = this.top.next;
            temp.next = null;
            this.size -= 1;
            return temp.value;
        }
    }

    /**
     * @return the size of the stack
     */
    public int getSize() {
        //TODO implement getSize
        return this.size;
    }

    public void printStack(StackNode<Item> root) {
        if (root == null) {
            return;
        } else {
            System.out.print(root.value);
        }
        printStack(root.next);
    }

    public StackNode<Item> getTop() {
        return this.top;
    }

}
