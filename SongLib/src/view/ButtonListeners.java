package view;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import utility.Song;

public class ButtonListeners {

	public static void attachAddListener(Button b, 
			Button add, Button delete, 
			ListView<?> listView, 
			VBox detailBox, ButtonBar editButton, 
			ButtonBar editToolbar) {
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				listView.getSelectionModel().clearSelection();
//				listView.setDisable(true);

				clearFields(detailBox, true);

				add.setDisable(true);
				delete.setDisable(true);
				editButton.setVisible(false);
				editToolbar.setVisible(true);

			}
		});
	}

	public static void attachDeleteListener(Button b, 
			ListView<?> listView, 
			VBox detailBox, 
			ButtonBar editButton,
			ObservableList<Song> ol, SortedList<Song> sl) {
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				deleteSelected(listView, ol, sl);
				if (sl.isEmpty()) {
					b.setDisable(true);
					clearFields(detailBox, false);
					editButton.setVisible(false);
				}
			}
			
		});
	}
	
	private static void deleteSelected(
			ListView<?> listView, 
			ObservableList<Song> ol, SortedList<Song> sl) {
		int slIndex = listView.getSelectionModel().getSelectedIndex();
		ol.remove(sl.getSourceIndex(slIndex));
		slIndex -= (slIndex < sl.size())? 0 : 1;
		listView.getSelectionModel().clearAndSelect(slIndex); 
	}

	public static void attachEditListener(Button b,
			VBox detailBox,
			ButtonBar editButton, ButtonBar editToolbar,
			ObservableList<Song> ol, SortedList<Song> sl) {
	
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
//				for (Node detail : detailBox.getChildren()) {
//					if (detail instanceof TextField) {
//						((TextField)detail).setEditable(true);
//					}
//				}
				editButton.setVisible(false);
				editToolbar.setVisible(true);
			}
		});
	}
	
	public static void attachSaveListener(Button b, 
			Button add, Button delete, 
			ListView<?> listView, 
			TextField detailName, TextField detailArtist,
			TextField detailAlbum, TextField detailYear,
			ButtonBar editButton, ButtonBar editToolbar, 
			ObservableList<Song> ol, SortedList<Song> sl) {
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				String name = detailName.getText();
				String artist = detailArtist.getText();
				String album = detailAlbum.getText();
				String year = detailYear.getText();
				// check if (name, artist) pair already exists
				if (!sl.filtered(
						(s) -> (s != listView.getSelectionModel().getSelectedItem()
								&& s.name.equals(name) 
								&& s.artist.equals(artist)))
						.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("Duplicate Song");
					alert.setContentText("The song name and song artist are the same as an existing song.");
					alert.show();
					return; // do not complete the add/edit
				}
				Song s;
				if (listView.getSelectionModel().getSelectedItems().isEmpty()) {
					s = new Song(name, artist, album, year);
					ol.add(s);
				} else {
					s = sl.get(listView.getSelectionModel().getSelectedIndex());
					ol.remove(s); // force on-change event to trigger
					s.name = name;
					s.artist = artist;
					s.album = album;
					s.year = year;
					ol.add(s); // force on-change event to trigger
					
				}

				listView.getSelectionModel().clearAndSelect(sl.indexOf(s));
//				listView.setDisable(false);

				add.setDisable(false);
				delete.setDisable(false);
				editToolbar.setVisible(false);
				editButton.setVisible(true);
			}
		});
	}
	
	public static void attachCancelListener(Button b, 
			Button add, Button delete, 
			ListView<?> listView,
			VBox detailBox,
			ButtonBar editButton, ButtonBar editToolbar,
			ObservableList<Song> ol, SortedList<Song> sl) {
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Song s = (Song)listView.getSelectionModel().getSelectedItem();
				if (null==s) {
					clearFields(detailBox, false);
					if (!sl.isEmpty())
						listView.getSelectionModel().clearAndSelect(0);
				} else {
					ol.remove(s);
					ol.add(s);
					listView.getSelectionModel().clearAndSelect(sl.indexOf(s));
				}
				
				add.setDisable(false);
				if (!sl.isEmpty()) {
					delete.setDisable(false);
				}
				editToolbar.setVisible(false);
				if (!sl.isEmpty()) {
					editButton.setVisible(true);
				}
			}
		});
	}
	
	private static void clearFields(VBox box, boolean isEditable) {
		for (Node detail : box.getChildren()) {
			if (detail instanceof TextField) {
				((TextField)detail).clear();
//				detail.setDisable(!isEditable);
//				((TextField)detail).setEditable(isEditable);
			}
		}
	}
}