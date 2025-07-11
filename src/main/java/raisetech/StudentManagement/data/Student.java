package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生詳細")
@Getter
@Setter
public class Student {

  private int id;
  private String name;
  private String furigana;
  private String nickname;
  private String email;
  private String region;
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;

}