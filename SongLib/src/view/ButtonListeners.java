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
				listView.setDisable(true);

				for (Node detail : detailBox.getChildren()) {
					if (detail instanceof TextField) {
						((TextField)detail).clear();
						((TextField)detail).setEditable(true);
					}
				}

				add.setDisable(true);
				delete.setDisable(true);
				editButton.setVisible(false);
				editToolbar.setVisible(true);

			}
		});
	}

	public static void attachDeleteListener(Button b, ListView<?> listView, ObservableList<Song> ol, SortedList<Song> sl) {
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				int slIndex = listView.getSelectionModel().getSelectedIndex();
				ol.remove(sl.getSourceIndex(slIndex));
				slIndex -= (slIndex < sl.size())? 0 : 1;
				listView.getSelectionModel().clearAndSelect(slIndex); 

			}
		});
	}

	public static void attachSaveListener(Button b, 
			Button add, Button delete, 
			ListView<?> listView, 
			TextField detailName, TextField detailArtist, 
			ButtonBar editButton, ButtonBar editToolbar, 
			ObservableList<Song> ol, SortedList<Song> sl) {
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Song s = new Song(detailName.getText(), detailArtist.getText());
				ol.add(s);;

				listView.getSelectionModel().clearAndSelect(sl.indexOf(s));
				listView.setDisable(false);

				add.setDisable(false);
				delete.setDisable(false);
				editToolbar.setVisible(false);
				editButton.setVisible(true);
			}
		});
	}
}