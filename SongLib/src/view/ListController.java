package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
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

	private ObservableList<Song> obsList;

	public void start(Stage mainStage) throws Exception {
		// init empty ObservableList
		obsList = FXCollections.observableArrayList();
		obsList.add(new Song("Jesus of Suburbia", "Greenday"));
		listView.setItems(new SortedList<Song>(obsList, new Song.Compare()));
		obsList.add(new Song("Birdplane", "Axis of Awesome"));

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
	}

	private void showItem(Stage mainStage) {
		Song selectedSong = listView.getSelectionModel().getSelectedItem();
		detailName.setText(selectedSong.name);
		detailName.setEditable(false);
		detailArtist.setText(selectedSong.artist);
		detailArtist.setEditable(false);
	}

}
