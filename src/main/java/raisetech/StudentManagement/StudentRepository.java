package raisetech.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> search();

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> findAllCourses();

}