package cs1501_p1;

public class BST <T extends Comparable<T>> implements BST_Inter<T>
{
    /**
     * Root of BST
     */
    private BTNode<T> root;

    /**
     * Constructor that sets root to null
     */
    public BST() {
        this.root = null;
    }
    // ------------------------------------------------------------------------------------------
    public void put(T key){
        this.root = put_rec(this.root,key);
    }

    public BTNode<T> put_rec(BTNode<T> cur,T key){
        if(cur == null){
            BTNode<T> out = new BTNode<T>(key);
            return out;
        }else if(cur.getKey().compareTo(key) > 0){
            cur.setLeft(put_rec(cur.getLeft(), key));
        }else if(cur.getKey().compareTo(key) < 0){
            cur.setRight(put_rec(cur.getRight(), key));
        }
        return cur;
    }
    // ------------------------------------------------------------------------------------------
    public boolean contains(T key){
        return contains_rec(this.root,key);
    }

    public boolean contains_rec(BTNode<T> cur,T key){
        if(cur == null){
            return false;
        }else if(cur.getKey().compareTo(key)>0){
            return contains_rec(cur.getLeft(),key); 
        }else if(cur.getKey().compareTo(key)<0){
            return contains_rec(cur.getRight(),key);
        }else{
           return true; 
        }
    }
    // ------------------------------------------------------------------------------------------
    public void delete(T key){
        BTNode<T> parentNode = null;
        BTNode<T> delNode = null;
        int direction = 2;

        // case 1: no root
        if(this.root == null){
            return;
        }
        //Find parent node of the node to be deleted
        else{
            if(this.root.getKey().compareTo(key)==0){
                parentNode = this.root;
            }else if(this.root.getKey().compareTo(key)>0){
                parentNode = find_prev_node(this.root.getLeft(),key,this.root);
            }else if(this.root.getKey().compareTo(key)<0){
                parentNode = find_prev_node(this.root.getRight(),key,this.root);
            }
            //key not found, nothing to delete
            if(parentNode == null){
                return;
            }
        }  
        //find the node that needs to be deleted
        // direction tells us relationship between parent and child
        // -1 = root to be deleted, 0 = node to be deleted is left child of parent
        // 1 = node to be deleted is right child of parent
        if(parentNode.getKey() == key){
            delNode = parentNode;
            direction = -1;
        }else if(parentNode.getLeft() != null && parentNode.getLeft().getKey() == key){
            delNode = parentNode.getLeft();
            direction = 0;
        }else if(parentNode.getRight() != null && parentNode.getRight().getKey() == key){
            delNode = parentNode.getRight();
            direction = 1;
        }
        //case 2: delNode has no children
        if(delNode.getLeft()==null&&delNode.getRight()==null){
            if(direction == -1){
                this.root = null;
            }else if(direction == 0){
                parentNode.setLeft(null);
            }else if(direction == 1){
                parentNode.setRight(null);
            }
        }
        //case 3: delNode only has left child
        else if(delNode.getLeft()!=null && delNode.getRight()==null){
            if(direction == -1){
                this.root = this.root.getLeft();
            }else if(direction == 0){
                parentNode.setLeft(delNode.getLeft());
            }else if(direction == 1){
                parentNode.setRight(delNode.getLeft());
            }
            return;
        }
        //case 4: delNode only has right child
        else if(delNode.getLeft()==null && delNode.getRight()!=null){
            if(direction == -1){
                this.root = this.root.getRight();
            }else if(direction == 0){
                parentNode.setLeft(delNode.getRight());
            }else if(direction == 1){
                parentNode.setRight(delNode.getRight());
            }
            return;
        }else{
        //case 5: delNode has 2 children
        // find the left most leaf of the right tree
            BTNode<T> temp = delNode.getRight();
            BTNode<T> tempPrev = delNode;
            while(temp.getLeft()!= null){
                tempPrev = temp;
                temp = temp.getLeft();
            }
            if(direction == -1){
                if(temp == delNode.getRight()){
                    temp.setLeft(this.root.getLeft());
                    this.root = temp;
                }else{
                    tempPrev.setLeft(temp.getRight());
                    temp.setLeft(this.root.getLeft());
                    temp.setRight(this.root.getRight());
                    this.root = temp;
                }
            }else{
                if(temp == delNode.getRight()){
                    temp.setLeft(delNode.getLeft());
                    if(direction == 0){
                        parentNode.setLeft(temp);
                    }else{
                        parentNode.setRight(temp);
                    }
                }else{
                    tempPrev.setLeft(temp.getRight());
                    if(direction == 0){
                        parentNode.setLeft(temp);
                    }else{
                        parentNode.setRight(temp);
                    }
                    temp.setLeft(delNode.getLeft());
                    temp.setRight(delNode.getRight());
                }
            } 
        }
    }

    public BTNode<T> find_prev_node(BTNode<T> cur,T key,BTNode<T> prev){
        if(cur == null){
            return null;
        }

        if(cur.getKey().compareTo(key)>0){
            return find_prev_node(cur.getLeft(),key,cur);
        }else if(cur.getKey().compareTo(key)<0){
            return find_prev_node(cur.getRight(),key,cur);
        }else{
            return prev;
        }
    }
    // ------------------------------------------------------------------------------------------
    public int height(){
        return height_rec(this.root);
    }

    public int height_rec(BTNode<T> cur){
        if(cur ==null){
            return 0;
        }else{
            int left = 1 + height_rec(cur.getLeft());
            int right = 1+ height_rec(cur.getRight());

            if(left>right){
                return left;
            }else{
                return right;
            }
        }
    }
    // ------------------------------------------------------------------------------------------
    public boolean isBalanced(){
        return bal_rec(root);
    }

    public int balHeight(BTNode<T> cur){
        return height_rec(cur);
    }

    public boolean bal_rec(BTNode<T> cur){
        if(cur == null){
            return true;
        }
        boolean lBal;
        boolean rBal;

        if(bal_rec(cur.getLeft())==false){
            return false;
        }

        int left = balHeight(cur.getLeft());
        int right = balHeight(cur.getRight());
        if(left + 1 != right && left - 1 != right && left != right){
            lBal = false;
        }else{
            lBal = true;
        }

        if(bal_rec(cur.getRight())==false){
            return false;
        };

        left = balHeight(cur.getLeft());
        right = balHeight(cur.getRight());
        if(left + 1 != right && left - 1 != right&& left != right) {
            rBal = false;
        }else{
            rBal = true;
        }

        if(!lBal || ! rBal){
            return false;
        }else{
            return true;
        }
    }
    // ------------------------------------------------------------------------------------------
    public String inOrderTraversal(){
        String out = iOT_rec(this.root);
        return out.substring(0, out.length() - 1);
    }
    public String iOT_rec(BTNode<T> cur){
        if(cur == null){
            return "nulll";
        }
        String output = "";
        if(cur.getLeft() != null){
            output = iOT_rec(cur.getLeft());
        }
        output+=cur.getKey()+":";

        if(cur.getRight() != null){
            output += iOT_rec(cur.getRight());
        }
        return output;
    }
    // ------------------------------------------------------------------------------------------
    public String serialize(){
        String out = ser_rec(this.root);
        return out.substring(0, out.length() -1);  
    }

    public String ser_rec(BTNode<T> cur){
        String out = "";

        if(cur == this.root){
            if(cur == null){
                out+="nulll";
            }else{
                out = "R(" + cur.getKey()+"),";
                if(cur.getLeft()== null && cur.getRight() ==null){
                    return out;
                }else{
                    out += ser_rec(cur.getLeft());
                    out += ser_rec(cur.getRight());
                }
            }
        }else if(cur == null){
            out += "X(NULL),";
        }else if(cur.getLeft()==null &&  cur.getRight() == null){
            out += "L(" + cur.getKey()+ "),"; 
        }else{
            out += "I(" + cur.getKey()+ "),";

            out += ser_rec(cur.getLeft());
            out += ser_rec(cur.getRight());
        }

        return out;
    }
    // ------------------------------------------------------------------------------------------
    public BST_Inter<T> reverse(){

        BST<T> newTree = new BST<T>();

        if(this.root == null){
            newTree.root = null;
        }else{
            BTNode<T> revTreeRoot = new BTNode<T>(this.root.getKey());
            newTree.root = revTreeRoot;
    
            rev_rec(this.root, revTreeRoot);
        }
        
        return newTree;
    }

    public void rev_rec(BTNode<T> cur, BTNode<T> rev){
        if(cur == null){
            return;
        }else{
            if(cur.getLeft()!= null){
                BTNode<T> left = new BTNode<T>(cur.getLeft().getKey());
                rev.setRight(left);
            }
            if(cur.getRight()!=null){
                BTNode<T> right = new BTNode<T>(cur.getRight().getKey());
                rev.setLeft(right);
            }
        }
        rev_rec(cur.getLeft(), rev.getRight());
        rev_rec(cur.getRight(), rev.getLeft());   
    }
}