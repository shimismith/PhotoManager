package backend.models;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class used for Configuration writing/reading.
 *
 * it contains information of All Pictures
 */
public class AppSettings implements Serializable {

  /**
   * List of all the pictures
   */
  private List<Picture> historicalPictures = new ArrayList<>();

  /**
   * A list of all the tags
   */
  private List<Tag> availableTags = new ArrayList<>();

  /**
   * defualt file name is set to "Config"
   */
  private static final String defualtFileName = "Config";

  /**
   * Adds all Pictures and tags to the PictureManager
   *
   * @param manager a PictureManager
   */
  public void addPicToManager(PictureManager manager) {
    for (Picture picture : historicalPictures) {
      for (Picture picture1 : manager.getPictures()) {
        if (picture1.equals(picture)) {
          manager.untrackPicture(picture1);
          manager.addPicture(picture);
        }
      }
    }

    for (Tag tag : this.availableTags) {
      if (!manager.contains(tag)) {
        manager.addTagToCollection(tag);
      }
    }
  }

  /**
   * Adds all the pictures and tags in the given PictureManager to this class.
   *
   * @param manager The picture manager to grab the tags and pictures from
   */
  public void addPicFromManager(PictureManager manager) {
    for (Picture picture : manager.getPictures()) {
      if (!this.historicalPictures.contains(picture)) {
        this.historicalPictures.add(picture);
      }
    }
    this.availableTags = manager.getAvailableTags();
  }

  /**
   * Save configuration to given fileName
   *
   * @param fileName the name of the file to save to
   * @throws IOException When it is not a valid file name
   */
  public void save(String fileName) throws IOException {
    OutputStream buffer = new BufferedOutputStream(new FileOutputStream(fileName));
    ObjectOutput output = new ObjectOutputStream(buffer);

    // serialize the Map
    output.writeObject(this);
    output.close();
  }

  /**
   * Save the file to the default file name: Config
   *
   * @throws IOException when the config file does not exist.
   */
  public void save() throws IOException {
    save(defualtFileName);
  }

  /**
   * Load the serialized AppSetting file from given fileName
   *
   * @param fileName the file name
   * @return the deserialized AppSetting Object
   */
  public static AppSettings loadFromFile(String fileName)
      throws IOException, ClassNotFoundException {
    InputStream buffer = new BufferedInputStream(new FileInputStream(fileName));
    ObjectInput input = new ObjectInputStream(buffer);

    // Deserialize the app settings
    AppSettings settings = (AppSettings) input.readObject();
    input.close();

    return settings;
  }

  /**
   * Load the Serialized AppSetting from the default file Name: Config
   *
   * @return the deserialized AppSetting Object
   */
  public static AppSettings loadFromFile() throws IOException, ClassNotFoundException {
    return loadFromFile(defualtFileName);
  }

  /**
   * Get a copy of the list of historical pictures stored in this instance
   *
   * @return Copy of the list of historical pictures stored in this instance.
   */
  public List<Picture> getHistoricalPicture() {
    return new ArrayList<Picture>(this.historicalPictures);
  }

  /**
   * Return a copy of the list of available tags stored in this instance.
   *
   * @return A copy of the list of available tags stored in this instance.
   */
  public List<Tag> getAvailableTags() {
    return new ArrayList<>(this.availableTags);
  }
}
