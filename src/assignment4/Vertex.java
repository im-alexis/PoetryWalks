package assignment4;

import java.util.Map;


public class Vertex <T> {
    private T name; // holds the object in name (for project 4 it will hold the string)
    private Map <T, Integer> edges; // T would be the tail vertex, the Integer will be the weight

    public Vertex (T obj){
        this.name = obj;
    }

    public void addEdge (T destination){
        if(edges.get(destination) != null){ // the key has an asociated value then increment the value by 1
            edges.replace(destination, edges.get(destination) , edges.get(destination)+1);
        }
        else{
            edges.put(destination, 1); // if there is no edge yet then just add one
        }
    }

    public void removeEdge (T destination){
        if(edges.get(destination) > 1){ // the key has an asociated weight then just decremnt by one
            edges.replace(destination, edges.get(destination) , edges.get(destination)-1);
        }
        else{
            edges.remove(destination);
        }
    }

    public Map<T, Integer> getEdges(){ //return the edges map for this vertex
        return edges;
    }

    public T getName(){ // return the name
        return name;
    }


}
