package raisetech.StudentManagement.data;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentValidationTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void 入力値がすべて正しい場合はバリデーションエラーにならない() {
    Student student = createValidStudent();
    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations).isEmpty();
  }

  @Test
  void 必須項目が未入力の場合はバリデーションエラーになる() {
    Student student = new Student(); // 全項目空

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // name, furigana, nickname, email, region, gender が @NotBlank、age が @Min
    assertThat(violations).hasSize(7);
    assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("furigana"));
    assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("nickname"));
    assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("region"));
    assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("gender"));
    assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("age"));
  }

  @Test
  void 年齢が0だとバリデーションエラーになる() {
    Student student = createValidStudent();
    student.setAge(0);

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("age");
  }

  @Test
  void メールアドレスが不正な形式だとバリデーションエラーになる() {
    Student student = createValidStudent();
    student.setEmail("invalid-email");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
  }

  private Student createValidStudent() {
    Student student = new Student();
    student.setId(1);
    student.setName("寺島海斗");
    student.setFurigana("テラシマカイト");
    student.setNickname("カイト");
    student.setEmail("kaito.terashima@gmail.co.jp");
    student.setRegion("千葉");
    student.setAge(27);
    student.setGender("男性");
    student.setRemark("特になし");
    student.setDeleted(false);
    return student;
  }
}