package cs1501_p4;

public class NodeList{

    private Node head;
    private int count;

    public NodeList(Node a){
        head = a;
        count = 1;
    }
    public Node getHead(){
        return head;
    }

    public void add(Node a){
        if(count == 1){
            if(head.getVertex() < a.getVertex()){
                head.setNext(a);
                a.setNext(null);
            }else{
                a.setNext(head);
                head = a;
            }
        }else{
            if(a.getVertex() < head.getVertex()){
                a.setNext(head);
                head = a;
            }else{
                Node cur = head;
                while(cur.getNext() !=null && cur.getNext().getVertex() < a.getVertex()){
                    cur = cur.getNext();
                }
                a.setNext(cur.getNext());
                cur.setNext(a);
            }
        }
        count ++;
    }
}