package sth.app.representative;

import java.util.Collection;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.Survey;

import sth.exceptions.DisciplineNotFoundException;
import sth.app.exceptions.NoSuchDisciplineException;

import sth.SurveyRepresentativePrinter;

/**
 * 4.5.6. Show discipline surveys.
 */
public class DoShowDisciplineSurveys extends Command<SchoolManager> {

  private Input<String> _disciplineName;

  /**
   * @param receiver
   */
  public DoShowDisciplineSurveys(SchoolManager receiver) {
    super(Label.SHOW_DISCIPLINE_SURVEYS, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    Collection<Survey> surveys; 
    try {
      surveys = _receiver.getDisciplineSurveys(_disciplineName.value()); 
      SurveyRepresentativePrinter printer = new SurveyRepresentativePrinter();
      for (Survey s : surveys) 
        _display.addLine(s.print(printer)); 

      _display.display();
    }
    catch (DisciplineNotFoundException e) {
      throw new NoSuchDisciplineException(e.getName());
    }
  }

}
