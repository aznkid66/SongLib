package utility;

import java.util.Comparator;

public class Song {
	public static class Compare implements Comparator<Song> {

		@Override
		public int compare(Song o1, Song o2) {
			return o1.name.toLowerCase().
					compareTo(o2.name.toLowerCase());
		}
		
	}
	public String name;
	public String artist;
	
	public Song (String name, String artist) {
		this.name = name;
		this.artist = artist;
	}
	
	public String toString() {
		return name;
	}
}
