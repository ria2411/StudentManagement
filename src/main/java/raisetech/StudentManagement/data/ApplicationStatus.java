package raisetech.StudentManagement.data;


public class ApplicationStatus {
  private int id;
  private int studentCourseId;
  private String status;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getStudentCourseId() {
    return studentCourseId;
  }

  public void setStudentCourseId(int studentCourseId) {
    this.studentCourseId = studentCourseId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
