package raisetech.StudentManagement.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.ApplicationStatus;
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

  private final StudentRepository repository;
  private final StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索を行います。全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourses> studentCoursesList = repository.searchStudentCourses();


    // 全申込状況を取得
    List<ApplicationStatus> statusList = repository.getAllStatuses();

    // StudentCourses に status をセット
    for (StudentCourses course : studentCoursesList) {
      statusList.stream()
          .filter(s -> s.getStudentCourseId() == course.getId())
          .findFirst()
          .ifPresent(s -> course.setStatus(s.getStatus()));
    }
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


  public StudentDetail registerStudentWithCourses(StudentDetail studentDetail) {
    // 1. 受講生登録
    repository.insertStudent(studentDetail.getStudent());

    // 2. コース情報があれば student_id をセットして登録
    if (studentDetail.getStudentCourses() != null) {
      for (StudentCourses course : studentDetail.getStudentCourses()) {
        course.setStudentId(studentDetail.getStudent().getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // startDate が null の場合は今日の日付
        if (course.getStartDate() == null) {
          course.setStartDate(LocalDate.now().format(formatter));
        }

        // endDate が null の場合は startDate + 1年
        if (course.getEndDate() == null) {
          LocalDate start = LocalDate.parse(course.getStartDate(), formatter);
          course.setEndDate(start.plusYears(1).format(formatter));
        }

        repository.insertStudentCourse(course);
      }
    }

    // 3. 登録した内容をそのまま返す
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

  /**
   * 全申込状況を取得
   */
  public List<ApplicationStatus> getAllApplicationStatuses() {
    return repository.getAllStatuses();
  }

  /**
   * コースIDで申込状況を取得
   */
  public ApplicationStatus getApplicationStatusByCourseId(int courseId) {
    return repository.getStatusByCourseId(courseId);
  }

  public List<StudentDetail> searchStudentsByCriteria(
      String name,
      String region,
      Integer minAge,
      Integer maxAge,
      String status) {

    List<Student> students = repository.search(); // 全件取得
    List<StudentCourses> courses = repository.searchStudentCourses();
    List<ApplicationStatus> statuses = repository.getAllStatuses();

    // 学生情報での絞り込み
    if (name != null && !name.isEmpty()) {
      students = students.stream()
          .filter(s -> s.getName().contains(name))
          .collect(Collectors.toList());
    }
    if (region != null && !region.isEmpty()) {
      students = students.stream()
          .filter(s -> s.getRegion().equals(region))
          .collect(Collectors.toList());
    }
    if (minAge != null) {
      students = students.stream()
          .filter(s -> s.getAge() >= minAge)
          .collect(Collectors.toList());
    }
    if (maxAge != null) {
      students = students.stream()
          .filter(s -> s.getAge() <= maxAge)
          .collect(Collectors.toList());
    }

    // courses に status をセット
    for (StudentCourses course : courses) {
      statuses.stream()
          .filter(a -> a.getStudentCourseId() == course.getId())
          .findFirst()
          .ifPresent(a -> course.setStatus(a.getStatus()));
    }

    // status での絞り込み
    if (status != null && !status.isEmpty()) {
      courses = courses.stream()
          .filter(c -> status.equals(c.getStatus()))
          .collect(Collectors.toList());

      // courses に紐づく学生だけ残す
      List<Integer> studentIds = courses.stream()
          .map(StudentCourses::getStudentId)
          .distinct()
          .toList();

      students = students.stream()
          .filter(s -> studentIds.contains(s.getId()))
          .collect(Collectors.toList());
    }

    return converter.convertStudentDetails(students, courses);
  }

}