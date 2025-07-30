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

  @NotBlank(message = "フリガナは必須です")
  private String furigana;

  @NotBlank(message = "ニックネームは必須です")
  private String nickname;

  @Email(message = "正しいメールアドレス形式で入力してください")
  @NotBlank(message = "メールアドレスは必須です")
  private String email;

  @NotBlank(message = "地域は必須です")
  private String region;

  @Min(value = 1, message = "年齢は1以上で入力してください")
  private int age;

  @NotBlank(message = "性別は必須です")
  private String gender;
  private String remark;
  private boolean isDeleted;

}