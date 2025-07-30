package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourses {

  private int id;
  private int studentId;

  @NotBlank(message = "コース名は必須です")
  private String courseName;
  private String startDate;
  private String endDate;
}
