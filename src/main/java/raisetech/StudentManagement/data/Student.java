package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
  private int id;

  @NotBlank(message = "名前は必須です")
  private String name;
  private String furigana;
  private String nickname;

  @Email(message = "正しいメールアドレス形式で入力してください")
  private String email;

  private String region;

  @Min(0)
  private int age;

  private String gender;

  private String remark;

  private boolean isDeleted;

}