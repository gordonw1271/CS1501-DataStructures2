package cs1501_p4;

public class Node{

    private int from;
    private int vertex;
    private int length;
    private int bandwidth;
    private char type;
    private double latency;

    private Node next;

    public Node(int f,int to, int l,int b,char t) {
        this.from = f;
        this.vertex = to;
        this.length = l;
        this.bandwidth = b;
        this.type = t;

        if(t == 'c'){
            latency = l/230000000.0;
        }else{
            latency = l/200000000.0;
        }

        this.next = null;
    }

    public Node(int f,int to, int l,char t,double lat) {
        this.from = f;
        this.vertex = to;
        this.length = l;
        this.type = t;

        if(t == 'c'){
            latency = l/230000000.0 + lat;
        }else{
            latency = l/200000000.0 + lat;
        }

        this.next = null;
    }

    public int getFrom() {
        return from;
    }
    public int getVertex() {
        return vertex;
    }
    public int getLength() {
        return length;
    }
    public int getBandwidth() {
        return bandwidth;
    }
    public char getType() {
        return type;
    }
    public double getLat() {
        return latency;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node n) {
        next = n;
    }
}