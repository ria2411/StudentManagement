package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
<<<<<<< HEAD
=======
import raisetech.StudentManagement.data.Student;
>>>>>>> origin/kadai11
import raisetech.StudentManagement.data.StudentCourses;

@Mapper
public interface StudentRepository {

<<<<<<< HEAD
  @Select("SELECT * FROM student_courses")
  List<StudentCourses> search();

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> findAllCourses();

=======
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> searchStudentCourses();
>>>>>>> origin/kadai11
}