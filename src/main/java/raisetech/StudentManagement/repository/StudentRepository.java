package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.ApplicationStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧（全件）
   */

  List<Student> search();

  /**
   * 受講生の検索を行います。
   *
   * @param id　受講生ID
   * @return 受講生
   */
  Student findById(int id);

  /**
   *受講生のコース情報の全件検索を行います。
   * @return 受講生のコース情報（全件）
   */
  List<StudentCourses> findAllCourses();


  List<StudentCourses> searchStudentCourses();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId　受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourses> findCoursesByStudentId(int studentId);

  // 新規受講生情報を登録するためのINSERT文
  void insertStudent(Student student);

  void updateStudent(Student student);

  void insertStudentCourse(StudentCourses studentCourses);

  List<Student> findByIsDeletedFalse();


  // 全申込状況
  @Select("SELECT id, student_course_id AS studentCourseId, status FROM application_status")
  List<ApplicationStatus> getAllStatuses();

  // コースIDで取得
  @Select("SELECT id, student_course_id AS studentCourseId, status FROM application_status WHERE student_course_id = #{courseId}")
  ApplicationStatus getStatusByCourseId(int courseId);
}