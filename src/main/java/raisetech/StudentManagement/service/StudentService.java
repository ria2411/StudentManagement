package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentCourses> searchStudentCoursesList() {
    return repository.searchStudentCourses();
  }

  // updateStudent メソッドで repository を使うように修正
  public void updateStudent(Student student) {
    repository.updateStudent(student);  // 修正: studentRepository → repository
  }

  // リポジトリを呼び出して新規登録するメソッド
  public void registerStudent(Student student) {
    repository.insertStudent(student);
  }

  public List<Student> searchStudentsInTheir30s() {
    return repository.search().stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() < 40)
        .collect(Collectors.toList());
  }

  public List<StudentCourses> searchJavaCourses() {
    return repository.searchStudentCourses().stream()
        .filter(course -> "Javaコース".equals(course.getCourseName()))
        .collect(Collectors.toList());
  }

  // findStudentById メソッドで repository を使うように修正
  public Student findStudentById(int id) {
    return repository.findById(id);  // 修正: studentRepository → repository
  }
}
