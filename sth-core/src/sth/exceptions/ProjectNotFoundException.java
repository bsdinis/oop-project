package sth.exceptions;

/** Exception thrown when the requested project does not exist. */
public class ProjectNotFoundException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811140050L;

  /** Project name. */
  private String _name;

  /**
   * @param name
   */
  public ProjectNotFoundException(String name) {
    _name = name;
  }

  /** @return name */
  public String getName() {
    return _name;
  }

}