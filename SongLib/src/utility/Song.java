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
	public String album;
	public String year;
	
	public Song (String name, String artist) {
		this.name = name;
		this.artist = artist;
		this.album = "";
		this.year = "";
	}
	
	public Song (String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public String toString() {
		return name;
	}
}
