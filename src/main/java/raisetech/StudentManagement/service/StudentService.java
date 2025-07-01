package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生の一覧検索を行います。
   * 全件検索を行うので、条件指定は行いません。
   * @return　受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourses> studentCoursesList = repository.searchStudentCourses();
    return converter.convertStudentDetails(studentList, studentCoursesList);
  }

  public List<StudentCourses> searchStudentCoursesList() {
    return repository.searchStudentCourses();
  }

  // updateStudent メソッドで repository を使うように修正
  public void updateStudent(Student student) {
    repository.updateStudent(student);  // 修正: studentRepository → repository
  }

  // リポジトリを呼び出して新規登録するメソッド
  public StudentDetail registerStudent(Student student) {
    repository.insertStudent(student);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    return studentDetail;
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


  public List<Student> findActiveStudents() {
    return repository.findByIsDeletedFalse();
  }

  public List<StudentCourses> findCoursesByStudentId(int studentId) {
    return repository.findCoursesByStudentId(studentId);
  }

}
