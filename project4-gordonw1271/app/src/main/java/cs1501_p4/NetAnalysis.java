package cs1501_p4;

import java.util.ArrayList;

import java.lang.IllegalArgumentException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NetAnalysis implements NetAnalysis_Inter
{
    public NodeList[] graph;

    //======================= CONSTRUCTOR ======================================
    public NetAnalysis(String filenmae){
        BufferedReader carFile = null;
        try {
            carFile = new BufferedReader(new FileReader(filenmae));
            String line;

            // read first line (# of verticies) and initialize array
            line = carFile.readLine();
            graph = new NodeList[Integer.parseInt(line)];

            while ((line = carFile.readLine()) != null) {
                String[] props = line.split(" ");
                int vertex = Integer.parseInt(props[1]);
                int length = Integer.parseInt(props[4]);
                int band = Integer.parseInt(props[3]);
                char type = props[2].charAt(0);
                
                // create undirected graph
                int index = Integer.parseInt(props[0]);
                if(graph[index] == null){
                    graph[index] = new NodeList(new Node(index,vertex,length,band,type));
                }else{
                    graph[index].add(new Node(index,vertex,length,band,type));
                }
                if(graph[vertex] == null){
                    graph[vertex] = new NodeList(new Node(vertex,index,length,band,type));
                }else{
                    graph[vertex].add(new Node(vertex,index,length,band,type));
                }
            }
            carFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //======================= MIN PATH ======================================
    public ArrayList<Integer> lowestLatencyPath(int u, int w){
        //check for illegal arguments
        if(u >= graph.length || w >= graph.length || u < 0 || u < 0){
            return null;
        }
        // initialize arrays and queue
        ArrayList<Integer> out = new ArrayList<>();
        if(u == w){
            out.add(u);
            return out;
        }

        int[] via = new int[graph.length];
        for(int i = 0;i<via.length;i++){
            via[i] = -1;
        }

        boolean[] visited = new boolean[graph.length];
        visited[u] = true;

        double[] latency = new double[graph.length];
        for(int i = 0;i<graph.length;i++){
            latency[i] = Integer.MAX_VALUE; 
        }
        latency[u] = 0;
    
        PQ queue = new PQ();

        //add neighbors of starting node to PQ
        Node cur = graph[u].getHead();
        while(cur!=null){
            queue.add(cur);
            cur = cur.getNext();
        }

        while(!queue.isEmpty()){
            Node temp = queue.getLow();
            if(temp.getLat()<latency[temp.getVertex()]){
                via[temp.getVertex()] = temp.getFrom();
                latency[temp.getVertex()] = temp.getLat();
                visited[temp.getVertex()] = true;

                Node nextNode = graph[temp.getVertex()].getHead();
                while(nextNode!=null){
                    // add node with added latency
                    queue.add(new Node(nextNode.getFrom(),nextNode.getVertex(),nextNode.getLength(),nextNode.getType(),temp.getLat()));
                    nextNode = nextNode.getNext();
                }
            }
            queue.remove();
        }

        out.add(w);
        int vert = -1;

        //while vert != start vertext work backwards through via array
        while(vert!=u){
            vert=via[w];
            if(vert == -1){
                return null;
            }
            out.add(0,vert);
            w=vert;
        }
        return out;
    }
    //===================== BAND ALONG PATH ===================================
    public int bandwidthAlongPath(ArrayList<Integer> p) throws IllegalArgumentException{
        int out = Integer.MAX_VALUE;
        for(int i =0;i<p.size()-1;i++){
            Node cur = graph[p.get(i)].getHead();
            while(cur!=null&&cur.getVertex()!=p.get(i+1)){
                cur = cur.getNext();
            }
            if(cur==null){
                throw new IllegalArgumentException();
            }else{
                if(cur.getBandwidth()<out){
                    out = cur.getBandwidth();
                }
            }
        }
        return out;
    }
    //===================== COPPER ONLY ================================
    public boolean copperOnlyConnected(){
        // edge case: only one node
        if(graph.length == 1){
            Node cur = graph[0].getHead();
            if(cur.getType() == 'c'){
                return true;
            }else{
                return false;
            }
        }
        // initialize arrays and queue
        boolean[] visited = new boolean[graph.length];
        copperDFS(graph[0].getHead(),visited);
        for(int i = 0;i<graph.length;i++){
            if(visited[i]==false){
                return false;
            }
        }
        return true;
    }

    private void copperDFS(Node n,boolean[] v){
        v[n.getFrom()] = true;
        while(n!=null){
            if(n.getType()=='c'){
                if(v[n.getVertex()]==false){
                    copperDFS(graph[n.getVertex()].getHead(),v);
                }
            }
            n=n.getNext();
        }
    }
    //======================= BICONNECTED =================================
    public boolean connectedTwoVertFail(){
        if(graph.length<=2){
            return false;
        }
        //get rid of two nodes
        for(int delNode1 = 0;delNode1<graph.length-1;delNode1++){
            for(int delNode2 = delNode1+1;delNode2<graph.length;delNode2++){
                boolean[] visited = new boolean[graph.length];

                //find start node
                int start = -1;
                for(int i = 0;i<graph.length;i++){
                    if(i!=delNode1 && i!=delNode2){
                        start = i;
                        break;
                    }
                }

                DFS(graph[start].getHead(),visited,delNode1,delNode2);

                for(int i = 0;i<visited.length;i++){
                    if(i!=delNode1 && i!= delNode2){
                        if(visited[i]==false){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void DFS(Node n,boolean[] v,int delNode1,int delNode2){
        v[n.getFrom()] = true;

        while(n!=null){
            if(n.getVertex()!=delNode1 && n.getVertex()!=delNode2 && v[n.getVertex()]==false){
                DFS(graph[n.getVertex()].getHead(),v,delNode1,delNode2);
            }
            n=n.getNext();
        }
    }
    //========================== MST ======================================
    public ArrayList<STE> lowestAvgLatST(){
        ArrayList<STE> out = new ArrayList<STE>();
        if(graph.length == 1){
            out.add(new STE(0,0));
            return out;
        }
        boolean[] visited = new boolean[graph.length];
        visited[0] = true;
        PQ queue = new PQ();

        //add neighbors of starting node to PQ
        Node cur = graph[0].getHead();
        while(cur!=null){
            queue.add(cur);
            cur = cur.getNext();
        }

        while(!queue.isEmpty()){
            Node temp = queue.getLow();
            queue.remove();
            
            if(visited[temp.getVertex()]==false){
                visited[temp.getVertex()] = true;
                out.add(new STE(temp.getFrom(), temp.getVertex()));

                cur = graph[temp.getVertex()].getHead();
                while(cur!=null){
                    queue.add(cur);
                    cur = cur.getNext();
                }
            }
        }
        for(int i = 0;i<visited.length;i++){
            if(visited[i]==false){
                return null;
            }
        }

        return out;
    }
}