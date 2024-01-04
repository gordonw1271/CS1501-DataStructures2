package cs1501_p2;
import java.util.ArrayList;
public class DLB implements Dict
{
    private DLBNode root;
    private DLBNode charNode;
    private String charSearch = "";
    
    public DLB(){
        this.root = null;
    }
// -------------------------------------------------------------
    public void add(String key){
        if(key.length()==0 || key ==null){
            return;
        }
        add_rec(this.root, key, 0);
    }
    private void add_rec(DLBNode cur,String key, int pos){
        if(cur == null){
            this.root = new DLBNode(key.charAt(0));
            add_rec(this.root, key, 0);
        }else{
            if(cur.getLet() == key.charAt(pos)){
                if(pos == key.length()-1){
                    if(cur.getDown()==null){
                        cur.setDown(new DLBNode('!'));
                    }else{
                        cur = cur.getDown();
                        while(cur.getRight()!=null){
                            cur = cur.getRight();
                        }
                        if(cur.getLet() == '!'){
                            return;
                        }else{
                            cur.setRight(new DLBNode('!'));
                        }
                    }
                }else{
                    if(cur.getDown() == null){
                        cur.setDown(new DLBNode(key.charAt(pos+1)));
                        add_rec(cur.getDown(), key, pos+1);
                    }else{
                        DLBNode up = cur;
                        cur = cur.getDown();
                        if(cur.getRight()==null){
                            if(key.charAt(pos+1) == cur.getLet()){
                                add_rec(cur,key,pos+1);
                            }else{
                                if(key.charAt(pos+1) < cur.getLet()){
                                    cur.setRight(new DLBNode(key.charAt(pos+1)));
                                    add_rec(cur.getRight(), key, pos+1);
                                }else{
                                    DLBNode temp = new DLBNode(key.charAt(pos+1));
                                    up.setDown(temp);
                                    temp.setRight(cur);
                                    add_rec(temp, key, pos+1);
                                }
                            }
                        }else{
                            while(cur.getRight()!=null && key.charAt(pos+1) <= cur.getRight().getLet()){
                                cur = cur.getRight();
                            }
                            if(key.charAt(pos+1) == cur.getLet()){
                                add_rec(cur,key,pos+1);
                            }else{
                                DLBNode temp = new DLBNode(key.charAt(pos+1));
                                if(cur == up.getDown() && key.charAt(pos+1) > cur.getLet()){
                                    temp.setRight(cur);
                                    up.setDown(temp);
                                    add_rec(temp,key,pos+1);
                                }else{
                                    temp.setRight(cur.getRight());
                                    cur.setRight(temp);
                                    add_rec(temp,key,pos+1);
                                }
                            }
                        }
                    }
                }
            }else{
                // pos
                if(cur.getRight()==null){
                    DLBNode temp = new DLBNode(key.charAt(pos));
                    if(cur.getLet()<key.charAt(pos)){
                        this.root = temp;
                        temp.setRight(cur);
                        add_rec(temp, key, pos);
                    }else{
                        cur.setRight(temp);
                        add_rec(temp, key, pos);
                    }
                }else{
                    while(cur.getRight()!=null && key.charAt(pos) <= cur.getRight().getLet()){
                        cur = cur.getRight();
                    }
                    if(key.charAt(pos) == cur.getLet()){
                        add_rec(cur,key,pos);
                    }else{
                        DLBNode temp = new DLBNode(key.charAt(pos));
                        if(cur == this.root && key.charAt(pos) > cur.getLet()){
                            temp.setRight(cur);
                            this.root = temp;
                            add_rec(temp,key,pos);
                        }else{
                            temp.setRight(cur.getRight());
                            cur.setRight(temp);
                            add_rec(temp,key,pos);
                        }
                    }
                }
            }
        }
    }
// -------------------------------------------------------------
    public boolean contains(String key){
        return con_rec(this.root,key,0);
    }
    private boolean con_rec(DLBNode cur, String key, int pos){
        if(cur == null){
            return false;
        }else{
            if(cur.getLet()==key.charAt(pos)){
                if(pos == key.length()-1){
                    if(cur.getDown()==null){
                        return false;
                    }else{
                        cur = cur.getDown();
                        while(cur.getLet()!='!' && cur.getRight()!=null){
                            cur=cur.getRight();
                        }
                        if(cur.getLet()=='!'){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }else{
                    if(cur.getDown()==null){
                        return false;
                    }else{
                        cur = cur.getDown();
                        while(cur.getLet()!=key.charAt(pos+1) && cur.getRight()!=null){
                            cur=cur.getRight();
                        }
                        if(cur.getLet()==key.charAt(pos+1)){
                            return con_rec(cur,key,pos+1);
                        }else{
                            return false;
                        }
                    }
                }
            }else{
                while(cur.getLet()!=key.charAt(pos) && cur.getRight()!=null){
                    cur=cur.getRight();
                }
                if(cur.getLet()!=key.charAt(pos)){
                    return false;
                }else{
                    return con_rec(cur,key,pos);
                }
            }
        }
    }
// -------------------------------------------------------------
    public boolean containsPrefix(String pre){
        return pre_rec(this.root,pre,0);
    }
    private boolean pre_rec(DLBNode cur,String key,int pos){
        if(cur == null){
            return false;
        }else{
            if(cur.getLet()==key.charAt(pos)){
                if(pos == key.length()-1){
                    return true;
                }else{
                    if(cur.getDown()==null){
                        return false;
                    }else{
                        cur = cur.getDown();
                        while(cur.getLet()!=key.charAt(pos+1) && cur.getRight()!=null){
                            cur=cur.getRight();
                        }
                        if(cur.getLet()==key.charAt(pos+1)){
                            return pre_rec(cur,key,pos+1);
                        }else{
                            return false;
                        }
                    }
                }
            }else{
                while(cur.getLet()!=key.charAt(pos) && cur.getRight()!=null){
                    cur=cur.getRight();
                }
                if(cur.getLet()!=key.charAt(pos)){
                    return false;
                }else{
                    return pre_rec(cur,key,pos);
                }
            }
        }
    }
// -------------------------------------------------------------
    public int searchByChar(char next){
        if(this.root == null || charSearch.equals("^")){
            this.charSearch = "^";
            return -1;
        }else{
            this.charSearch = this.charSearch + next;
            if(charNode == null){
                charNode = this.root;
            }
    
            DLBNode temp = charNode;
            while(temp.getLet() != next && temp.getRight()!=null){
                temp = temp.getRight();
            }
            if(temp.getLet()!=next){
                this.charSearch = "^";
                return -1;
            }else{
                temp = temp.getDown();
                charNode = temp;
                if(temp.getRight()==null && temp.getLet()=='!'){
                    return 1;
                }else{
                    while(temp.getRight()!=null && temp.getLet()!='!'){
                        temp = temp.getRight();
                    }
                    if(temp.getLet()=='!'){
                        return 2;
                    }else{
                        return 0;
                    }
                }
            } 
        }   
    }
    public void resetByChar(){   
        charNode = null;
        this.charSearch = "";
    }
// -------------------------------------------------------------
    public ArrayList<String> suggest(){
        ArrayList<String> out = new ArrayList<String>();
        if(charSearch.equals("^") || charSearch.equals("")){
            return out;
        }else{
            return sugTrav(out,charSearch,charNode);
        }
    }
    private ArrayList<String> sugTrav(ArrayList<String> out,String word,DLBNode cur){
        if(out.size() == 5){
            return out;
        }else{
            if(cur.getLet() == '!'){
                out.add(word);
            }
            if(cur.getRight()!=null){
                out = sugTrav(out,word,cur.getRight());
            } 
            word = word + cur.getLet();
            if(cur.getDown()!=null){
                out = sugTrav(out,word,cur.getDown());
            }
            word =word.substring(0, word.length()-1);
            return out;
        }
    }
// -------------------------------------------------------------
    public ArrayList<String> traverse(){
        ArrayList<String> out = new ArrayList<String>();
        String word = "";
        if(this.root == null){
            return out;
        }else{
            return trav_rec(this.root, word, out);
        }
    }
    private ArrayList<String> trav_rec(DLBNode cur, String word, ArrayList out){
        if(cur.getLet() == '!'){
            out.add(word);
        }

        if(cur.getRight()!=null){
            out = trav_rec(cur.getRight(),word,out);
        } 
        word = word + cur.getLet();
        if(cur.getDown()!=null){
            out = trav_rec(cur.getDown(),word,out);
        }
        word =word.substring(0, word.length()-1);
        return out;
    }
// -------------------------------------------------------------
    public int count(){
        if(this.root == null){
            return 0;
        }else{
            return count_rec(this.root,0);
        }
    }
    private int count_rec(DLBNode cur, int count){
        if(cur == null){
            return 0;
        }else{
            int c = count;
            if(cur.getDown() != null){
                c = count_rec(cur.getDown(),c); 
            }
            if(cur.getRight()!=null){
                c = count_rec(cur.getRight(),c);
            }
            if(cur.getLet() == '!'){
                c += 1;
            }
            return c;
        }
    }
}