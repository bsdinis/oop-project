package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

/**
 * 4.5.5. Finish survey.
 */
public class DoFinishSurvey extends Command<SchoolManager> {

  private Input<String> _disciplineName;
  private Input<String> _projectName;

  /**
   * @param receiver
   */
  public DoFinishSurvey(SchoolManager receiver) {
    super(Label.FINISH_SURVEY, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.finishSurvey(_disciplineName.value(), _projectName.value()); 
    }
    catch (UnsuportedOperationException e) {
      _display.popup("Operação não suportada");
    }
  }

}
