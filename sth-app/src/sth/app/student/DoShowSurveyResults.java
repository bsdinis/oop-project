package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.Survey;
import sth.SurveyPrinter;

import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSurveyException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.SurveyNotFoundException;
import sth.exceptions.DisciplineNotFoundException;



/**
 * 4.4.3. Show survey results.
 */
public class DoShowSurveyResults extends Command<SchoolManager> {

  private Input<String> _disciplineName;
  private Input<String> _projectName;

  /**
   * @param receiver
   */
  public DoShowSurveyResults(SchoolManager receiver) {
    super(Label.SHOW_SURVEY_RESULTS, receiver);
    _disciplineName = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    class SurveyStudentPrinter implements SurveyPrinter {
      public String print(Survey.Open s) {
        return defaultFormat(s.disciplineName(), s.projectName(), "(aberto)");
      }
      public String print(Survey.Created s) {                                                                                
        return defaultFormat(s.disciplineName(), s.projectName(), "(por abrir)");
      }
      public String print(Survey.Closed s) {
        return defaultFormat(s.disciplineName(), s.projectName(), "(fechado)");
      }
      public String print(Survey.Finished s) {
        String res = defaultFormat(s.disciplineName(), s.projectName()) + '\n';
        res += " * Número de respostas: " + s.responsesNumber() + "\n";
        res += " * Tempos de resolução (horas) (médio): " + s.medHours();
        return res;
      }

      private String defaultFormat(String discipline, String project, String label) {
        return discipline + " - " + project + " " + label;
      }
      private String defaultFormat(String discipline, String project) {
        return discipline + " - " + project;
      }
    }

    _form.parse();
    try {
      Survey s = _receiver.studentGetSurvey( _disciplineName.value(), _projectName.value()); 
      SurveyPrinter printer = new SurveyStudentPrinter();
      _display.addLine(s.print(printer)); 
      _display.display();
    }
    catch (DisciplineNotFoundException e) {
      throw new NoSuchDisciplineException(e.getName());
    }
    catch (ProjectNotFoundException e) {
      throw new NoSuchProjectException(_disciplineName.value(), e.getName());
    }
    catch (SurveyNotFoundException e) {
      throw new NoSurveyException(_disciplineName.value(), e.getName());
    }
  }

}
