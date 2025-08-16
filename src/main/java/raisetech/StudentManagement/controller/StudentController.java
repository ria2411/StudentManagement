package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.exception.CustomException;
import raisetech.StudentManagement.service.StudentService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです。
 */

@RestController
@Validated
public class StudentController {

  private final StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧検索（全件）
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細の検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id　受講生ID
   * @return 受講生
   */
  @Operation(summary = "受講生ID検索", description = "受講生IDに基づき受講生情報を検索します。")
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

  @Operation(summary = "受講生コース検索", description = "受講生コース一覧の検索をします。")
  @GetMapping("/studentCourseList")
  public List<StudentCourses> getStudentCourseList() {
    return service.searchStudentCoursesList();
  }

  /**
   * 受講生詳細の登録を行います。
   * @param studentDetail　受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail.getStudent());
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行います
   * キャンセルフラグの更新もここで行います（論理削除）
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生更新", description = "受講生情報を更新します。")
  @PutMapping ("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail.getStudent());
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}