package tests.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;

import backend.commands.AddTagToPictureCommand;
import backend.models.Picture;
import backend.models.Tag;
import org.junit.jupiter.api.Test;

class AddTagToPictureCommandTest {

  private final String picturePath =
      "C:\\Users\\Emilio K\\Desktop\\FileManagerTestCases\\deleteFile\\chick @Chicken.jpg";

  @Test
  void undo() {
    Picture picture = new Picture(picturePath);
    Tag tag = new Tag("Grandma");
    AddTagToPictureCommand command = new AddTagToPictureCommand(picture, tag);
    command.execute();
    command.undo();
    assertFalse(picture.containsTag(tag));
  }

  @Test
  void execute() {
    Picture picture = new Picture(picturePath);
    Tag tag = new Tag("Grandma");
    AddTagToPictureCommand command = new AddTagToPictureCommand(picture, tag);
    command.execute();
    assert (picture.containsTag(tag));
  }
}
