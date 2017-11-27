package frontend.gui;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.naming.NoInitialContextException;
import backend.models.Picture;
import backend.models.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {
  
  private BackendService backendService;
  private Stage stage;

  @FXML
  private BorderPane root;  
  
  @FXML
  private TextField searchBar;

  @FXML
  private ListView<Picture> pictureListView;

  @FXML
  private ContextMenu listCellContextMenu;
  
  @FXML
  private PictureViewController pictureView;
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {    
    this.backendService = new BackendService();
    setListView();
    this.pictureView.setBackendService(this.backendService);
    this.pictureView.setMainController(this);
  }
  
  public void setStage(Stage stage) {
    this.stage = stage;
  }
  
  public Stage getStage() {
    return this.stage;
  }

  private void setListView() {
    this.pictureListView.setCellFactory(new FileListViewCallback(this.listCellContextMenu, this));
    this.pictureListView.getSelectionModel().selectedItemProperty()
        .addListener(new ListViewChangeListener(this));
  }
  
  public PictureViewController getPictureViewController() {
    return this.pictureView;
  }
  
  private File openDirectoryChooser() {
    DirectoryChooser dirChooser = new DirectoryChooser();
    dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    return dirChooser.showDialog(this.root.getScene().getWindow());
  }

  private void openDirectory(boolean recursive) {
    File file = openDirectoryChooser();

    if (file != null) {
      String directory = file.getAbsolutePath();

      this.backendService.resetBackendService(directory, recursive);

      pictureListView.getItems().setAll(this.backendService.getPictureManager().getPictures());
    }
  }

  public void openDirectory() {
    openDirectory(false);
  }

  public ListView<Picture> getListView() {
    return this.pictureListView;
  }
  
  public void openDirectryRecursively() {
    openDirectory(true);
  }

  public void openLog() {
    String logs = this.getBackendService().getCommandManager().getLogs();
    if (logs != "") {
      TextDialog dialog = new TextDialog("Logs", logs);
      dialog.show();
    }
  }

  public void manageTags() throws IOException {
    TagManagement tagManagement = new TagManagement("Tag Manager", this);
    tagManagement.show();
  }

  public void save() {
    this.getBackendService().save();
  }
  
  public void undo() {
    try {
      this.getBackendService().getCommandManager().undoRecentCommand();
    } catch (NoInitialContextException e) {
    }
  }

  public void redo() {
    try {
      this.getBackendService().getCommandManager().redoRecentCommand();
    } catch (NoInitialContextException e) {
    }
  }

  /**
   * This is the move handler for the move option in the context menu
   * 
   * @param e the action event
   */
  public void move() {
    // get the new directory
    String newDirectory = openDirectoryChooser().getAbsolutePath();

    // get the selected picture
    Picture selectedPicture = this.pictureListView.getSelectionModel().getSelectedItem();

    selectedPicture.setDirectoryPath(newDirectory); // move the picture

    this.pictureListView.getItems().setAll(this.backendService.getPictureManager().getPictures());
  }

  public void search() {
    // TODO test search functionality
    String text = this.searchBar.getText();
    if(text == "") {
      this.pictureListView.getItems().setAll(this.backendService.getPictureManager().getPictures());
    }
    else {
      this.pictureListView.getItems().setAll(this.backendService.getPictureManager().getPictureWithTag(new Tag(text)));
    }
  }
  
  /**
   * Handler for "Open in File Viewer" function Opens the OSs file viewer in the selected directory
   * 
   * Reference: https://stackoverflow.com/a/12340147
   *
   * @param e the action event
   */
  public void openInOSFileViewer(ActionEvent e) {
    if (Desktop.isDesktopSupported()) {
      File file =
          new File(this.pictureListView.getSelectionModel().getSelectedItem().getDirectoryPath());
      try {
        Desktop.getDesktop().open(file);
      } catch (IOException e1) {
        // this should never happen - at this point a picture cannot be possibly be a file
      }
    }
  }
  
  public BackendService getBackendService() {
    return this.backendService;
  }

}
