package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.service.StudentService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return　受講生検索（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id　受講生ID
   * @return　受講生
   */
  @GetMapping("/student/{id}")
  public ResponseEntity<StudentDetail> getStudentById(@PathVariable int id) {
    Student student = service.findStudentById(id);

    if (student == null || student.isDeleted()) {
      return ResponseEntity.notFound().build();
    }

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);

    List<StudentCourses> courses = service.findCoursesByStudentId(id); // ←ここ修正
    studentDetail.setStudentCourses(courses);

    return ResponseEntity.ok(studentDetail);
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourses> getStudentCourseList() {
    return service.searchStudentCoursesList();
  }

  // registerStudentメソッドをPOST処理に反映
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail responceStudentDetail = service.registerStudent(studentDetail.getStudent());
    return ResponseEntity.ok(responceStudentDetail);
  }

  @GetMapping("/editStudent/{id}")
  public String editStudent(@PathVariable int id) {
    Student student = service.findStudentById(id);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    return "editStudent";
  }

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail.getStudent());
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}