package view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
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
	@FXML TextField detailAlbum;
	@FXML TextField detailYear;

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
		listView.getSelectionModel().clearAndSelect(0);
		showItem(mainStage);

		// set listener for the items
		listView
		  .getSelectionModel()
		  .selectedItemProperty()
		  .addListener(
				  (obs, oldVal, newVal) ->
				  showItem(mainStage));
		
		// set listeners for buttons
		ButtonListeners.attachAddListener(add, 
				add, delete, 
				listView, 
				detailBox, 
				editButton, editToolbar);
		ButtonListeners.attachDeleteListener(delete, 
				listView, 
				detailBox,
				editButton,
				ol, sl);
		ButtonListeners.attachEditListener(edit,
				detailBox,
				editButton, editToolbar,
				ol, sl);
		ButtonListeners.attachSaveListener(save, 
				add, delete, 
				listView, 
				detailName, detailArtist, 
				detailAlbum, detailYear,
				editButton, editToolbar, 
				ol, sl);
		ButtonListeners.attachCancelListener(cancel,
				add, delete,
				listView,
				detailBox,
				editButton, editToolbar,
				ol, sl);
		
		// text-field-editable bindings
		detailName.editableProperty().bind(
				editToolbar.visibleProperty());
		detailArtist.editableProperty().bind(
				editToolbar.visibleProperty());
		
		// additional bindings
		listView.disableProperty().bind(Bindings.or(
				editToolbar.visibleProperty(),
					// disabled when editing
				delete.disabledProperty()));
					// disabled when listView empty
		save.disableProperty().bind(Bindings.or(
				detailName.textProperty().isEmpty(),
					// name required before save
				detailArtist.textProperty().isEmpty()));
					// artist required before save
	}

	private void showItem(Stage mainStage) {
		Song selectedSong = listView.getSelectionModel().getSelectedItem();
		if (null!=selectedSong) {
			detailName.setText(selectedSong.name);
			detailArtist.setText(selectedSong.artist);
			detailAlbum.setText(selectedSong.album);
			detailYear.setText(selectedSong.year);
		}
	}
}
