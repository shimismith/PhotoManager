package backend.commands;

import backend.models.Picture;
import backend.models.Tag;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * A command used to delete multiple tags from a picture.
 *
 * Severity Level: Fine
 */
public class DeleteTagsFromPicCommand implements Command {

  /**
   * A picture containing the tags to delete
   */
  private Picture picture;

  /**
   * The tags to delete.
   */
  private List<Tag> tagsToDelete;

  /**
   * Creates a DeleteTagFromPictureCommand specifying tags to delete from a picture. Note: the tag
   * should be already in the picture.
   *
   * @param picture A picture containing the tag
   * @param tags The tags to delete.
   */
  public DeleteTagsFromPicCommand(Picture picture, List<Tag> tags) {
    this.picture = picture;
    this.tagsToDelete = tags;
  }

  /**
   * Undo the command by re-adding the tags back to the picture. If for some reason a tag already
   * exist in the picture, it will not add the tag again to the picture.
   */
  @Override
  public void undo() {
    picture.addMultipleTags(tagsToDelete);
  }

  /**
   * Execute the command by removing the tags from the picture. If for some reason a tag is already
   * not in the picture, it will do nothing.
   */
  @Override
  public void execute() {
    picture.deleteMultipleTags(tagsToDelete);
  }

  /**
   * @return LogRecord a logRecord for this command with a severity level of FINE.
   */
  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.FINE,
        "Removed " + tagsToDelete + " from " + picture.getTaglessName());
  }
}
