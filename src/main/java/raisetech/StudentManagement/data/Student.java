package raisetech.StudentManagement.data;

import lombok.Data;

@Data
public class Student {

  private Long Id;
  private Long studentId;
  private String courseName;
  private String startDate;
  private String endDate;
}
