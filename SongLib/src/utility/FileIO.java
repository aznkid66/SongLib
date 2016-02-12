package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

public class FileIO {
	public static Collection<Song> getSongs(String fileName) {
		ArrayList<Song> ret = new ArrayList<Song>();
		File file = new File(fileName);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] args = line.split("\\t");
	    		ret.add(new Song(args[0], 
	    						 args[1], 
	    						 args[2], 
	    						 args[3]));
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static void saveSongs(String fileName, Collection<Song> songs) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(fileName)))) {
			for (Song s : songs) {
				writer.write(s.toCsvRow());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
