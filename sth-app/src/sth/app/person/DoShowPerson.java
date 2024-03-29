package sth.app.person;

import pt.tecnico.po.ui.Command;
import sth.SchoolManager;
import sth.Person;

/**
 * 4.2.1. Show person.
 */
public class DoShowPerson extends Command<SchoolManager> {

  /**
   * @param receiver
   */
  public DoShowPerson(SchoolManager receiver) {
    super(Label.SHOW_PERSON, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    Person p = _receiver.getPersonLoggedIn();
    if (p != null)
      _display.addLine(p.toString());
    else
      _display.addLine("No person logged in");

    _display.display();
  }

}
