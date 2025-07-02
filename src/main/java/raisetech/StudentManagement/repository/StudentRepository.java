package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import org.apache.ibatis.annotations.Update;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return　受講生一覧（全件）
   */

  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   * 受講生の検索を行います。
   *
   * @param id　受講生ID
   * @return　受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findById(int id);

  /**
   *受講生のコース情報の全件検索を行います。
   * @return　受講生のコース情報（全件）
   */
  @Select("SELECT * FROM student_courses")
  List<StudentCourses> findAllCourses();


  @Select("SELECT * FROM student_courses")
  List<StudentCourses> searchStudentCourses();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId　受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM student_courses WHERE student_id = #{studentId}")
  List<StudentCourses> findCoursesByStudentId(int studentId);

  // 新規受講生情報を登録するためのINSERT文
  @Insert("INSERT INTO students (name,furigana,nickname,email,region,age,gender,remark,is_deleted)"
      +"VALUES (#{name}, #{furigana}, #{nickname}, #{email}, #{region}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Update("UPDATE students SET name = #{name}, furigana = #{furigana}, nickname = #{nickname}, " +
      "email = #{email}, region = #{region}, age = #{age}, gender = #{gender}, " +
      "remark = #{remark}, is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Select("SELECT * FROM students WHERE is_deleted = false")
  List<Student> findByIsDeletedFalse();

}