package cs1501_p4;

public class PQ
{
    public Node[] arry;
    private int count;
    //==============================================================================
    public PQ(){
        arry = new Node[1];
        this.count = 0;
    }
    //=========================== EMPTY ============================================
    public boolean isEmpty(){
        if(count == 0){
            return true;
        }else{
            return false;
        }
    }
    //=========================== ADD ==============================================
    public void add(Node n){
        if(arry.length == this.count){     //array is full
            Node[] temp = new Node[arry.length * 2];
            for(int i=0;i<count;i++){
                temp[i] = this.arry[i];
            }
            temp[count] = n;
            this.arry = temp;
        }else{          //array is not full
            this.arry[count] = n;
        }
        count++;
        restoreArrayUp(count-1);
    }
    //=========================== RESTORE ===========================================
    private void restoreArrayUp(int index){
        if(index == 0){
            //do nothing
        }else{
            int parentIndex = (index - 1) / 2;

            if(arry[index].getLat() < arry[parentIndex].getLat()){
                Node temp = arry[index];
                arry[index] = arry[parentIndex];
                arry[parentIndex] = temp;

                restoreArrayUp(parentIndex);
            }
        }
    }

    private void restoreArrayDown(int index){
        int rightIndex = 2*index+2;
        int leftIndex = 2*index+1;

        if(leftIndex > this.count-1){
            //do nothing
        }else{
            if(rightIndex <= this.count-1){ // has left and right child
                int ind;
                if(arry[rightIndex].getLat() < arry[leftIndex].getLat()){
                    // Right has higher priority
                    ind = rightIndex;
                }else{
                    ind = leftIndex;
                }
                
                if(arry[index].getLat() > arry[ind].getLat()){
                    Node temp = arry[index];
                    arry[index] = arry[ind];
                    arry[ind] = temp;

                    restoreArrayDown(ind);
                }
            }else{  // has only left child
                if(arry[index].getLat() > arry[leftIndex].getLat()){
                    Node temp = arry[index];
                    arry[index] = arry[leftIndex];
                    arry[leftIndex] = temp;

                    restoreArrayDown(leftIndex);
                }
            }
        }
    }

    //================================= REMOVE =================================
    public void remove(){
        if(count == 0){
            //do nothing
        }else if(count == 1){
            arry[0] = null;
            count--;
        }else{
            arry[0] = arry[count - 1];
            arry[count - 1] = null;
            
            count--;
            restoreArrayDown(0);
        }
    }
    //=========================== GET LOW  ====================================
    public Node getLow(){
        if(count == 0){
            return null;
        }else{
            return arry[0];
        }
    }
}