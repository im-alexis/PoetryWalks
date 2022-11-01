/* EE422C Assignment #4 submission by
 * Alexis Torres
 * at39625
 */
package assignment4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Bonus {
    public static void main(String[] args)throws IOException {
        Document doc = Jsoup.connect("https://www.ece.utexas.edu/people/faculty/edison-thomaz").get();
        Elements elm = doc.getElementsByClass("field field--name-body field--type-text-with-summary field--label-hidden field__item");
        Element pTag = elm.get(3);
        String profBio = pTag.text();
        File tmpFile = File.createTempFile("test", ".tmp");
        FileWriter writer = new FileWriter(tmpFile);
        writer.write(profBio);
        writer.close();
        final GraphPoet nimoy = new GraphPoet(tmpFile);
        System.out.println(nimoy.poem(new File("bonus_input.txt")));

    }


    }

