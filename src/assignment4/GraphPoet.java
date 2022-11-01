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

    private ArrayList<String> cleanInput(String input){
        String[] temp = input.split(" ");
        ArrayList<String> temp2 = new ArrayList<>();
        for(int i = 0; i < temp.length; i++){

            temp[i] = temp[i].replaceAll("[^a-zA-Z0-9]", "");
            if(!temp[i].equals("") && !temp[i].contains("\t") && !temp[i].contains(" ") ){
                temp2.add(temp[i]);
            }
        }
        return temp2;
    }
    private boolean graphHasVertex (String test){ // this function is to see if the graph contains a vertex already
        for(int i = 0; i < graph.size(); i++){
            if(graph.get(i).getName().equals(test.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    private int graphGetSpecficVertexIndex (String word){ // this function returns the index of a specific vertex in arraylist graph takes a String as input
        for(int i = 0; i < graph.size(); i++){
            if(graph.get(i).getName().equals(word.toLowerCase())){
                return  i;
            }
        }
        return -1;
    }
    private String findBridgeWord (Vertex <String> word1, String word2){//this function returns the string value of the highest weight bridge word
        String bridge = "";
        Integer weight = 0;
        for(Map.Entry<String, Integer> entry : word1.getEdges().entrySet()){ // loops through all elements of the Vertex HashMap (edges)
            if((graph.get(graphGetSpecficVertexIndex(entry.getKey())).getEdges().containsKey(word2.toLowerCase()) ) // check if potential candidate vertex contains an edge to the next word (ie word2)
                    && (graph.get(graphGetSpecficVertexIndex(entry.getKey())).getEdges().get(word2.toLowerCase())) > weight){ // if as you are looping something with a higher weight apears then it becomes the bridge word
                bridge = graph.get(graphGetSpecficVertexIndex(entry.getKey())).getName().toLowerCase();
                weight = graph.get(graphGetSpecficVertexIndex(entry.getKey())).getEdges().get(word2.toLowerCase());
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
        String lastWord = "";
        while (scan.hasNextLine()){
            String input = scan.nextLine(); // the line would be split at every world
            ArrayList <String> line = cleanInput(input);
            for (int i = 0; i < line.size()-1; i++) {
               if(!graphHasVertex(line.get(i).toLowerCase())){ // if the graph doesn't have the vertex, add it to the graph and add an edge to the next word as well
                    Vertex <String> temp = new Vertex<>(line.get(i).toLowerCase());
                    graph.add(temp);
                    temp.addEdge(line.get(i+1).toLowerCase());
                    if(i == 0 && !lastWord.equals("")){
                        graph.get(graphGetSpecficVertexIndex(lastWord.toLowerCase())).addEdge(line.get(0).toLowerCase());
                    }
                }
                else{
                    graph.get(graphGetSpecficVertexIndex(line.get(i).toLowerCase())).addEdge(line.get(i+1).toLowerCase()); // else just add an edge to the vertex that exists
                   if(i == 0 && !lastWord.equals("")){
                       graph.get(graphGetSpecficVertexIndex(lastWord.toLowerCase())).addEdge(line.get(0).toLowerCase());
                   }
                }
            }
            if(!graphHasVertex(line.get(line.size() - 1).toLowerCase())){
                Vertex <String> temp = new Vertex<>(line.get(line.size() - 1).toLowerCase());
                graph.add(temp);
            }

            lastWord = line.get(line.size()-1).toLowerCase();
        }
        scan.close();
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
            String temp = scan.nextLine(); // the line would be split at every world
            ArrayList <String> line = cleanInput(temp);
            for(int i = 0; i < line.size() - 1; i++){ // since looking at two words at a time length-1 prevents going over
                if(graphHasVertex(line.get(i).toLowerCase()) && graphHasVertex(line.get(i+1).toLowerCase())
                        && !graph.get(graphGetSpecficVertexIndex(line.get(i).toLowerCase())).getEdges().containsKey(line.get(i+1).toLowerCase())){ // only time a bridge word can be used is when both line[i] and line[i+1] are in the graph, but no edge exist
                    if(i == 0){  // check if it's the first word in the line
                        poem = line.get(i) ;
                        String bridge = findBridgeWord(graph.get(graphGetSpecficVertexIndex(line.get(i).toLowerCase())), line.get(i+1).toLowerCase()); // call a function that get the bridge word
                        if(!bridge.equals("")){ // if the bridge word was found do something, else don't do anything
                            poem = poem + " " + bridge;
                        }

                    }
                    else {
                        poem = poem + " " + line.get(i);
                        String bridge = findBridgeWord(graph.get(graphGetSpecficVertexIndex(line.get(i).toLowerCase())), line.get(i+1).toLowerCase()); // call a function that get the bridge word
                        if(!bridge.equals("")){ // if the bridge word was found do something, else don't do anything
                            poem = poem + " " + bridge;
                        }

                    }
                }
                else {
                    if(i == 0){
                        poem = poem + line.get(i);
                    }
                    else {
                        poem = poem + " " + line.get(i);
                    }
                }
            }
            poem = poem + " " +  line.get(line.size() - 1) ;// add the last word, no bridge would ever be there
            lineTracker++; // increment the line tracker in the case there are multiple lines

        }
        /* Read in input and use graph to complete poem */
        scan.close();
        return poem;
    }

}
