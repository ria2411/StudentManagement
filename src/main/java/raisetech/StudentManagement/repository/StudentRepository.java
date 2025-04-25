package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> findAllCourses();

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> searchStudentCourses();

  // 新規受講生情報を登録するためのINSERT文
  @Insert("INSERT INTO students (name,furigana,nickname,email,region,age,gender,remark,is_deleted)"
      +"VALUES (#{name}, #{furigana}, #{nickname}, #{email}, #{region}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findById(int id);

  @Update("UPDATE students SET name = #{name}, furigana = #{furigana}, nickname = #{nickname}, " +
      "email = #{email}, region = #{region}, age = #{age}, gender = #{gender}, " +
      "remark = #{remark}, is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Select("SELECT * FROM students WHERE is_deleted = false")
  List<Student> findByIsDeletedFalse();
}