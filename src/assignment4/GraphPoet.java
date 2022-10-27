
package assignment4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
               if(!graphHasVertex(line[i])){
                    Vertex <String> temp = new Vertex<>(line[i]);
                    graph.add(temp);
                    temp.addEdge(line[i+1]);
                }
                else{
                    graph.get(graphGetSpecficVertexIndex(line[i])).addEdge(line[i+1]);
                }
            }
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
        Scanner scan = new Scanner(input);
        while (scan.hasNextLine()){
            String [] line = (scan.nextLine()) .split(" ");

        }
        /* Read in input and use graph to complete poem */
        String poem = "";
        return poem;
    }

}
