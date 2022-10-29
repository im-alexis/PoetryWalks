/* EE422C Assignment #4 submission by
 * Alexis Torres
 * at39625
 */

package assignment4;

import java.util.HashMap;
import java.util.Map;
import static java.lang.Integer.MAX_VALUE;


public class Vertex <T> {
    private T name; // holds the object in name (for project 4 it will hold the string)
    private Map <T, Integer> edges; // T would be the tail vertex, the Integer will be the weight

    public Vertex (T obj){
        this.name = obj;
        edges = new HashMap<>(); //hash map for the edges
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
    public T getHighestWeightEdge(){// return the highest weighted edge, (seem like it would be helpful)
        T highest = null;
        Integer highestValue = 0;
        if(!edges.isEmpty()) {
            for (Map.Entry<T, Integer> entry : edges.entrySet()) {
                Integer value = entry.getValue();
                if (value > highestValue) {
                    highest = entry.getKey();

                }
            }
        }
        return highest;
    }
    public T getLowestWeightEdge(){ // return the lowest weighted edge, (seems like it would come of use later)
        T lowest = null;
        Integer lowestValue = MAX_VALUE ;
        if(!edges.isEmpty()) {
            for (Map.Entry<T, Integer> entry : edges.entrySet()) {
                Integer value = entry.getValue();
                if (value < lowestValue) {
                    lowest = entry.getKey();

                }
            }
        }
        return lowest;
    }

    public Map<T, Integer> getEdges(){ //return the edges map for this vertex
        return edges;
    }

    public T getName(){ // return the name
        return name;
    }



}
