/* EE422C Assignment #4 submission by
 * Alexis Torres
 * at39625
 */

package assignment4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


public class GraphPoet {

    private ArrayList<Vertex<String>> graph = new ArrayList<>();

    private boolean graphHasVertex (String test){ // this function is to see if the graph contains a vertex already
        for(int i = 0; i < graph.size(); i++){
            if(graph.get(i).getName().equals(test)){
                return true;
            }
        }
        return false;
    }
    private int graphGetSpecficVertexIndex (String word){ // this function returns the index of a specific vertex in arraylist graph takes a String as input
        for(int i = 0; i < graph.size(); i++){
            if(graph.get(i).getName().equals(word)){
                return  i;
            }
        }
        return -1;
    }
    private String findBridgeWord (Vertex <String> word1, String word2){//this function returns the string value of the highest weight bridge word
        String bridge = "";
        Integer weight = 0;
        for(Map.Entry<String, Integer> entry : word1.getEdges().entrySet()){ // loops through all elements of the Vertex HashMap (edges)
            if(graph.get(graphGetSpecficVertexIndex(entry.getKey())).getEdges().containsKey(word2) // check if potential candidate vertex contains an edge to the next word (ie word2)
                    && (graph.get(graphGetSpecficVertexIndex(entry.getKey())).getEdges().get(word2)) > weight){ // if as you are looping something with a higher weight apears then it becomes the bridge word
                bridge = graph.get(graphGetSpecficVertexIndex(entry.getKey())).getName();
            }
        }
        return bridge;
    }
    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */

    public GraphPoet(File corpus) throws IOException {

        /* Read in the File and place into graph here */
        Scanner scan = new Scanner(corpus);
        while (scan.hasNextLine()){
            String [] line = (scan.nextLine()) .split(" "); // the line would be split at every world
            for (int i = 0; i < line.length-1; i++) {
               if(!graphHasVertex(line[i])){ // if the graph doesn't have the vertex, add it to the graph and add an edge to the next word as well
                    Vertex <String> temp = new Vertex<>(line[i]);
                    graph.add(temp);
                    temp.addEdge(line[i+1]);
                }
                else{
                    graph.get(graphGetSpecficVertexIndex(line[i])).addEdge(line[i+1]); // else just add an edge to the vertex that exists
                }
            }
            /*
            * This next block just adds the last word to the graph and loops it back to the first word with the same idea as above
            *
            * */
            if(!graphHasVertex(line[line.length - 1])){
                Vertex <String> temp = new Vertex<>(line[line.length - 1]);
                graph.add(temp);
                temp.addEdge(line[0]);
            }
            else{
                graph.get(graphGetSpecficVertexIndex(line[line.length - 1])).addEdge(line[0]);
            }
        }
    }

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {
        String poem = "";
        Scanner scan = new Scanner(input);
        int lineTracker = 0;
        while (scan.hasNextLine()){ // loop until all lines are used
            if(lineTracker > 0){ // in the case there are multiple lines then add a new line to the poem
              poem = poem + "\n";
            }
            String [] line = (scan.nextLine()) .split(" "); //Split the line into an array of Strings
            for(int i = 0; i < line.length - 1; i++){ // since looking at two words at a time length-1 prevents going over
                if(graphHasVertex(line[i]) && graphHasVertex(line[i+1])
                        && !graph.get(graphGetSpecficVertexIndex(line[i])).getEdges().containsKey(line[i+1])){ // only time a bridge word can be used is when both line[i] and line[i+1] are in the graph, but no edge exist
                    if(i == 0){  // check if it's the first word in the line
                        poem = poem + line[i];
                    }
                    else {
                        poem = poem + " " + line[i] + " ";
                        String bridge = findBridgeWord(graph.get(graphGetSpecficVertexIndex(line[i])), line[i + 1]); // call a function that get the bridge word
                        poem = poem + bridge;
                    }
                }
                else {
                    if(i== 0){
                        poem = poem + line[i];
                    }
                    else {
                        poem = poem + " " + line[i];
                    }
                }
            }
            poem = poem + " " +  line[line.length - 1]; // add the last word, no bridge would ever be there
            lineTracker++; // increment the line tracker in the case there are multiple lines

        }
        /* Read in input and use graph to complete poem */

        return poem;
    }

}
