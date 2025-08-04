package raisetech.StudentManagement.data;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentCoursesValidationTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void 入力値がすべて正しい場合はバリデーションエラーにならない() {
    StudentCourses course = createValidCourse();

    Set<ConstraintViolation<StudentCourses>> violations = validator.validate(course);

    assertThat(violations).isEmpty();
  }

  @Test
  void コース名が空文字だとバリデーションエラーになる() {
    StudentCourses course = createValidCourse();
    course.setCourseName("");  // 空文字に設定

    Set<ConstraintViolation<StudentCourses>> violations = validator.validate(course);

    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("courseName");
  }

  @Test
  void コース名がnullだとバリデーションエラーになる() {
    StudentCourses course = createValidCourse();
    course.setCourseName(null);  // null に設定

    Set<ConstraintViolation<StudentCourses>> violations = validator.validate(course);

    assertThat(violations).hasSize(1);
    assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("courseName");
  }

  private StudentCourses createValidCourse() {
    StudentCourses course = new StudentCourses();
    course.setId(1);
    course.setStudentId(1);
    course.setCourseName("Javaアドバンスド");
    course.setStartDate("2024-11-04");
    course.setEndDate("2025-11-03");
    return course;
  }
}
