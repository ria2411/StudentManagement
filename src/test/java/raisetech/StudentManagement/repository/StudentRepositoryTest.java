package raisetech.StudentManagement.repository;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jdbcTemplate.execute("DELETE FROM students");
    jdbcTemplate.execute("""
    INSERT INTO students
    (name, furigana, nickname, email, region, age, gender, remark, is_deleted)
    VALUES
    ('酒井栞', 'シオリサカイ', 'シオリ', 'shiori.sakai@abccc.co.jp', '東京', 36, 'Female', '特になし', FALSE)
  """);
    // H2の場合は自動採番の位置を調整（任意）
    jdbcTemplate.execute("ALTER TABLE students ALTER COLUMN id RESTART WITH 10");
  }



  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();

    Assertions.assertFalse(actual.isEmpty(), "受講生一覧が空であってはいけません");

    Assertions.assertNotNull(actual.get(0).getName(), "1件目の受講生の名前がnullではいけません");
  }

  @Test
  void idで受講生を検索できること() {
    Student actual = sut.findById(1);

    Assertions.assertNotNull(actual, "ID 1の受講生が取得できるはず");

    Assertions.assertEquals("酒井栞", actual.getName(), "ID 1の受講生名が一致すること");
  }

  @Test
  void 受講生コースの全件検索ができること() {
    List<StudentCourses> courses = sut.findAllCourses();

    Assertions.assertFalse(courses.isEmpty(), "受講生コース一覧が空であってはいけません");

    Assertions.assertNotNull(courses.get(0).getCourseName(), "1件目のコース名がnullではいけません");
  }

  @Test
  void studentIdで受講生コースを検索できること() {
    int targetStudentId = 1;
    List<StudentCourses> courses = sut.findCoursesByStudentId(targetStudentId);

    Assertions.assertFalse(courses.isEmpty(), "指定した学生IDのコースが空ではいけません");

    Assertions.assertTrue(courses.stream().allMatch(c -> c.getStudentId() == targetStudentId),
        "すべてのコースのstudentIdが一致していること");
  }

  @Test
  void 新しい受講生を登録できること() {
    Student student = new Student();

    student.setName("山本太郎");
    student.setFurigana("ヤマモトタロウ");
    student.setNickname("タロウ");
    student.setEmail("tarou.yamamoto@example.com");
    student.setRegion("埼玉");
    student.setAge(25);
    student.setGender("Male");
    student.setRemark("テスト用登録");
    student.setDeleted(false);

    sut.insertStudent(student);

    Assertions.assertTrue(student.getId() > 0, "insert後にIDがセットされていること");

    Student inserted = sut.findById(student.getId());
    Assertions.assertNotNull(inserted, "insert後に受講生が取得できること");
  }

  @Test
  void 受講生情報を更新できること() {
    Student student = sut.findById(1);
    Assertions.assertNotNull(student, "更新対象の受講生が存在すること");

    String newNickname = "シオリ改";
    student.setNickname(newNickname);
    student.setRemark("更新テスト");

    sut.updateStudent(student);

    Student updated = sut.findById(1);

    Assertions.assertEquals(newNickname, updated.getNickname(), "ニックネームが更新されていること");
    Assertions.assertEquals("更新テスト", updated.getRemark(), "備考が更新されていること");

  }
}