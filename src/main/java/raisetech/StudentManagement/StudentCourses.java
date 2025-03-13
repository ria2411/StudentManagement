package raisetech.StudentManagement;

import lombok.Data;

@Data
public class StudentCourses {

  private Long Id;
  private Long studentId;
  private String courseName;
  private String startDate;
  private String endDate;
}
