package backend.commands;

import backend.models.Tag;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Command for renaming a Tag
 *
 * Severity Level: INFO
 */
public class RenameTagCommand implements Command {

  /**
   * the Tag to be renmaed
   */
  private Tag thisTag;

  /**
   * the new name of the tag
   */
  private String newTagName;

  /**
   * the old name o the tag
   */
  private String oldTagName;

  /**
   * Construtor that takes in the tag to be renamed and a new name for this tag
   */
  public RenameTagCommand(Tag tagToBeRenamed, String newName) {
    this.thisTag = tagToBeRenamed;
    this.newTagName = newName;
  }

  @Override
  public void undo() {
    thisTag.setLabel(oldTagName);
  }

  @Override
  public void execute() {
    this.oldTagName = thisTag.getLabel();
    this.thisTag.setLabel(newTagName);
  }

  /**
   * @return LogRecord a logRecord for this command with a severity level of FINE.
   */
  @Override
  public LogRecord getLogRecord() {
    return new LogRecord(Level.FINE, "Renamed Tag @" + this.oldTagName + " to @" + this.newTagName);
  }
}
