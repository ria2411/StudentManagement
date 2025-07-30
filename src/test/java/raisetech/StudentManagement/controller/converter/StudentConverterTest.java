package raisetech.StudentManagement.controller.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.domain.StudentDetail;

class StudentConverterTest {

  private final StudentConverter converter = new StudentConverter();

  @Test
  void 学生一人に対してコースが一つマッピングされること() {

    Student student = new Student(1, "山本愛莉", "ヤマモトアイリ", "アイリ", "airi.yamamoto@abbccc..co.jp",
        "埼玉", 25, "女性", "特になし", false);
    StudentCourses courses = new StudentCourses(1, 1, "Javaベーシック", "2025-01-01", "2026-12-31");

    List<StudentDetail> result = converter.convertStudentDetails(
        List.of(student),
        List.of(courses)
    );

    assertEquals(1, result.size());

    StudentDetail detail = result.get(0);
    assertEquals("山本愛莉", detail.getStudent().getName());
    assertEquals(1, detail.getStudentCourses().size());
    assertEquals("Javaベーシック", detail.getStudentCourses().get(0).getCourseName());
  }

  @Test
  void 学生に対応するコースがないと空のリストになること() {

    Student student = new Student(2, "佐々木彗", "ササキスイ", "スイ", "sui.sasaki@example.co.jp", "東京",
        26, "男性", "特になし", false);

    List<StudentDetail> result = converter.convertStudentDetails(
        List.of(student),
        Collections.emptyList()
    );

    assertEquals(1, result.size());
    StudentDetail detail = result.get(0);
    assertEquals("佐々木彗", detail.getStudent().getName());
    assertTrue(detail.getStudentCourses().isEmpty());
  }

  @Test
  void 複数の学生に複数のコースが正しくマップングされること() {

    Student student1 = new Student(1, "山本愛莉", "ヤマモトアイリ", "アイリ", "airi.yamamoto@abbccc..co.jp",
        "埼玉", 25, "女性", "特になし", false);
    Student student2 = new Student(2, "佐々木彗", "ササキスイ", "スイ", "sui.sasaki@example.co.jp", "東京",
        26, "男性", "特になし", false);

    StudentCourses course1 = new StudentCourses(1, 1, "Java", null, null);
    StudentCourses course2 = new StudentCourses(2, 1, "Spring", null, null);
    StudentCourses course3 = new StudentCourses(3, 2, "MySQL", null, null);

    List<StudentDetail> result = converter.convertStudentDetails(
        List.of(student1, student2),
        List.of(course1, course2, course3)
    );

    assertEquals(2, result.size());

    StudentDetail detail1 = result.get(0);
    assertEquals(2, detail1.getStudentCourses().size());

    StudentDetail detail2 = result.get(1);
    assertEquals(1, detail2.getStudentCourses().size());
    assertEquals("MySQL", detail2.getStudentCourses().get(0).getCourseName());
  }
}