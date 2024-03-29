package sth;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;
import java.text.Collator;

import sth.exceptions.StudentAlreadyEnrolledException;
import sth.exceptions.ProfessorAlreadyTeachingException;
import sth.exceptions.ProjectAlreadyExistsException;
import sth.exceptions.DisciplineLimitReachedException;
import sth.exceptions.AlienStudentException;
import sth.exceptions.EnrollmentLimitReachedException;
import sth.exceptions.DisciplineNotFoundException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.SurveyNotFoundException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/* methods are package on purpose */

class Discipline 
  implements Serializable {

  private String _name;
  private Course _course;
  private int _capacity;
  private Map<Integer, Student> _students = new TreeMap<>(); // sorted by id
  private Map<Integer, Professor> _professors = new TreeMap<>();
  private Map<String, Project> _projects = new TreeMap<>();

  Discipline(String name, int cap, Course c) {
    _name = name;
    _capacity = cap;
    _course = c;
  }
  
  String name() { return _name; }
  Course course() { return _course; }

  void enrollStudent(Student s) 
    throws StudentAlreadyEnrolledException,
                    DisciplineLimitReachedException,
                    EnrollmentLimitReachedException,
                    AlienStudentException {
    if (!s.getCourse().equals(_course)) throw new AlienStudentException(s);
    if (_students.containsKey(s.id())) throw new StudentAlreadyEnrolledException(s);
    if (_students.size() == _capacity) throw new DisciplineLimitReachedException(name());
    s.enrollInDiscipline(this);
    _students.put(s.id(), s);
  }

  Collection<Student> getStudents() { return _students.values(); }
  Collection<Professor> getProfessors() { return _professors.values(); }
  Collection<Student> getCourseRepresentatives() { return _course.getRepresentatives(); }
                                                                
  void addProject(String name) 
    throws ProjectAlreadyExistsException {
    if (hasProject(name)) 
      throw new ProjectAlreadyExistsException(name(), name);
    _projects.put(name, new Project(name, this));
  }

  boolean hasProject(String name) { return _projects.containsKey(name); }

  Project getProject(String name)
    throws ProjectNotFoundException
  { 
    if (!hasProject(name)) 
      throw new ProjectNotFoundException(name(), name);

    return _projects.get(name); 
  }

  Collection<Project> getProjects() { return _projects.values(); }

  Collection<Survey> getSurveys() { 
    Collection<Survey> surveys = new LinkedList<>();
    Collection<Project> projects = getProjects();
    for (Project p : projects) {
      if (p.hasSurvey()) {
        try {
          surveys.add(p.getSurvey());
        }
        catch (SurveyNotFoundException e) {
          // ignored because verification was made
          e.printStackTrace();
        }
      }
    }

    return surveys;
  }

  void addProfessor(Professor p) 
    throws ProfessorAlreadyTeachingException {
    if (_professors.containsKey(p.id()))
      throw new ProfessorAlreadyTeachingException(p.id(), name());

    _professors.put(p.id(), p);
    p.addDiscipline(this);
  }

  public String toString() { 
    DisciplinePrinter printer = new DisciplineDefaultPrinter(); 
    return printer.print(this); 
  }
}
