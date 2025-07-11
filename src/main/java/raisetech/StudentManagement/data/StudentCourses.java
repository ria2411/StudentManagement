package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourses {

  private int id;
  private int studentId;
  private String courseName;
  private String startDate;
  private String endDate;
}
