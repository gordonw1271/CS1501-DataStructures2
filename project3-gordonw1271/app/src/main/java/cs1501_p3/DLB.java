package cs1501_p3;

public class DLB
{
    private DLBNode root;
    
    public DLB(){
        this.root = null;
    }
// -------------------------------------------------------------
    public void add(String key, int index){
        if(key.length()==0 || key ==null){
            return;
        }
        add_rec(this.root, key, 0, index);
    }
    private void add_rec(DLBNode cur,String key, int pos, int index){
        if(cur == null){
            this.root = new DLBNode(key.charAt(0));
            add_rec(this.root, key, 0,index);
        }else{
            if(cur.getLet() == key.charAt(pos)){
                if(pos == key.length()-1){
                    if(cur.getDown()==null){
                        cur.setDown(new DLBNode('!',index));
                    }else{
                        cur = cur.getDown();
                        while(cur.getRight()!=null){
                            cur = cur.getRight();
                        }
                        if(cur.getLet() == '!'){
                            if(cur.getIndex() == -1){
                                cur.setIndex(index);
                            }
                            return;
                        }else{
                            cur.setRight(new DLBNode('!',index));
                        }
                    }
                }else{
                    if(cur.getDown() == null){
                        cur.setDown(new DLBNode(key.charAt(pos+1)));
                        add_rec(cur.getDown(), key, pos+1,index);
                    }else{
                        DLBNode up = cur;
                        cur = cur.getDown();
                        if(cur.getRight()==null){
                            if(key.charAt(pos+1) == cur.getLet()){
                                add_rec(cur,key,pos+1,index);
                            }else{
                                if(key.charAt(pos+1) < cur.getLet()){
                                    cur.setRight(new DLBNode(key.charAt(pos+1)));
                                    add_rec(cur.getRight(), key, pos+1,index);
                                }else{
                                    DLBNode temp = new DLBNode(key.charAt(pos+1));
                                    up.setDown(temp);
                                    temp.setRight(cur);
                                    add_rec(temp, key, pos+1,index);
                                }
                            }
                        }else{
                            while(cur.getRight()!=null && key.charAt(pos+1) <= cur.getRight().getLet()){
                                cur = cur.getRight();
                            }
                            if(key.charAt(pos+1) == cur.getLet()){
                                add_rec(cur,key,pos+1,index);
                            }else{
                                DLBNode temp = new DLBNode(key.charAt(pos+1));
                                if(cur == up.getDown() && key.charAt(pos+1) > cur.getLet()){
                                    temp.setRight(cur);
                                    up.setDown(temp);
                                    add_rec(temp,key,pos+1,index);
                                }else{
                                    temp.setRight(cur.getRight());
                                    cur.setRight(temp);
                                    add_rec(temp,key,pos+1,index);
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
                        add_rec(temp, key, pos,index);
                    }else{
                        cur.setRight(temp);
                        add_rec(temp, key, pos,index);
                    }
                }else{
                    while(cur.getRight()!=null && key.charAt(pos) <= cur.getRight().getLet()){
                        cur = cur.getRight();
                    }
                    if(key.charAt(pos) == cur.getLet()){
                        add_rec(cur,key,pos,index);
                    }else{
                        DLBNode temp = new DLBNode(key.charAt(pos));
                        if(cur == this.root && key.charAt(pos) > cur.getLet()){
                            temp.setRight(cur);
                            this.root = temp;
                            add_rec(temp,key,pos,index);
                        }else{
                            temp.setRight(cur.getRight());
                            cur.setRight(temp);
                            add_rec(temp,key,pos,index);
                        }
                    }
                }
            }
        }
    }
// -------------------------------------------------------------
    public DLBNode find(String key){
        return find_rec(this.root,key,0);
    }
    private DLBNode find_rec(DLBNode cur, String key, int pos){
        if(cur == null){
            return null;
        }else{
            if(cur.getLet()==key.charAt(pos)){
                if(pos == key.length()-1){
                    if(cur.getDown()==null){
                        return null;
                    }else{
                        cur = cur.getDown();
                        while(cur.getLet()!='!' && cur.getRight()!=null){
                            cur=cur.getRight();
                        }
                        if(cur.getLet()=='!'){
                            if(cur.getIndex()<0){
                                return null;
                            }else{
                                return cur;
                            }
                        }else{
                            return null;
                        }
                    }
                }else{
                    if(cur.getDown()==null){
                        return null;
                    }else{
                        cur = cur.getDown();
                        while(cur.getLet()!=key.charAt(pos+1) && cur.getRight()!=null){
                            cur=cur.getRight();
                        }
                        if(cur.getLet()==key.charAt(pos+1)){
                            return find_rec(cur,key,pos+1);
                        }else{
                            return null;
                        }
                    }
                }
            }else{
                while(cur.getLet()!=key.charAt(pos) && cur.getRight()!=null){
                    cur=cur.getRight();
                }
                if(cur.getLet()!=key.charAt(pos)){
                    return null;
                }else{
                    return find_rec(cur,key,pos);
                }
            }
        }
    }
}