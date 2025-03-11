package raisetech.StudentManagement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourses {

  private String Id;
  private String studentId;
  private String courseName;
  private String startDate;
  private String endDate;
}
