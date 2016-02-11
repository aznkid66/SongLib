package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.Song;

public class ListController {
	Song selectedSong;
	@FXML ListView<Song> listView;
	
	@FXML VBox detailBox;	
	@FXML TextField detailName;
	@FXML TextField detailArtist;

	@FXML ButtonBar editButton;
	@FXML ButtonBar editToolbar;
	
	@FXML Button add;
	@FXML Button delete;
	@FXML Button edit;
	@FXML Button save;
	@FXML Button cancel;
	
	private ObservableList<Song> ol;

	public void start(Stage mainStage) throws Exception {
		// init empty ObservableList
		ol = FXCollections.observableArrayList();
		ol.add(new Song("Jesus of Suburbia", "Greenday"));
		SortedList<Song> sl = new SortedList<Song>(ol, new Song.Compare());
		listView.setItems(sl);
		ol.add(new Song("Birdplane", "Axis of Awesome"));

		// select the first item
		listView.getSelectionModel().select(0);
		showItem(mainStage);

		// set listener for the items
		listView
		  .getSelectionModel()
		  .selectedItemProperty()
		  .addListener(
				  (obs, oldVal, newVal) ->
				  showItem(mainStage));
		
		// set listeners for buttons
		ButtonListeners.attachAddListener(add, add, delete, listView, detailBox, editButton, editToolbar);
		ButtonListeners.attachSaveListener(save, add, delete, listView, detailName, detailArtist, editButton, editToolbar, ol, sl);
		ButtonListeners.attachDeleteListener(delete, listView, ol, sl);
	}

	private void showItem(Stage mainStage) {
		Song selectedSong = listView.getSelectionModel().getSelectedItem();
		if (null!=selectedSong) {
			detailName.setText(selectedSong.name);
			detailName.setEditable(false);
			detailArtist.setText(selectedSong.artist);
			detailArtist.setEditable(false);
		}
	}
}
