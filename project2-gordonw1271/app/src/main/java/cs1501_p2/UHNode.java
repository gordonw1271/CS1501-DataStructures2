package cs1501_p2;

import java.io.Serializable;

public class UHNode implements Serializable {

    /**
     * Letter represented by this UHNode
     */
    private char let;

    private int value;

    /**
     * Lead to other alternatives for current letter in the path
     */
    private UHNode right;

    /**
     * Leads to keys with prefixed by the current path
     */
    private UHNode down;

    /**
     * Constructor that accepts the letter for the new node to represent
     */
    public UHNode(char let) {
        this.let = let;

        this.right = null;
        this.down = null;
    }
    
    public UHNode(char let,int val) {
        this.let = let;
        this.value = val;

        this.right = null;
        this.down = null;
    }

    /**
     * Getter for the letter this DLBNode represents
     *
     * @return The letter
     */
    public char getLet() {
        return let;
    }

    public int getVal() {
        return value;
    }

    /**
     * Getter for the next linked-list DLBNode
     *
     * @return Reference to the right DLBNode
     */
    public UHNode getRight() {
        return right;
    }

    /**
     * Getter for the child DLBNode
     *
     * @return Reference to the down DLBNode
     */
    public UHNode getDown() {
        return down;
    }

    /**
     * Setter for the next linked-list DLBNode
     *
     * @param r DLBNode to set as the right reference
     */
    public void setRight(UHNode r) {
        right = r;
    }

    /**
     * Setter for the child DLBNode
     *
     * @param d DLBNode to set as the down reference
     */
    public void setDown(UHNode d) {
        down = d;
    }

    public void setVal(int val) {
        value = val;
    }
}