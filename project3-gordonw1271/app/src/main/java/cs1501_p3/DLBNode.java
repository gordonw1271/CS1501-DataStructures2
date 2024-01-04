package cs1501_p3;

public class DLBNode{

    private char let;
    private int index;
    private PricePQ pricePQ;
    private MilePQ milePQ;

    private DLBNode right;
    private DLBNode down;

    public DLBNode(char let) {
        this.let = let;

        pricePQ = new PricePQ();
        milePQ = new MilePQ();

        this.right = null;
        this.down = null;
    }

    public DLBNode(char let, int index) {
        this.let = let;
        this.index = index;

        pricePQ = new PricePQ();
        milePQ = new MilePQ();

        this.right = null;
        this.down = null;
    }

    public char getLet() {
        return let;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index){
        this.index = index;
    }
    public PricePQ getPricePQ() {
        return pricePQ;
    }
    public MilePQ getMilePQ(){
        return milePQ;
    }

    public DLBNode getRight() {
        return right;
    }

    public DLBNode getDown() {
        return down;
    }

    public void setRight(DLBNode r) {
        right = r;
    }

    public void setDown(DLBNode d) {
        down = d;
    }
}