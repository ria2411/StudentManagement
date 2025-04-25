package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.service.StudentService;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.findActiveStudents();
    List<StudentCourses> studentCourses = service.searchStudentCoursesList();

    model.addAttribute("studentList",converter.convertStudentDetails(students, studentCourses));
    return "studentList";
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourses> getStudentCourseList() {
    return service.searchStudentCoursesList();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  // registerStudentメソッドをPOST処理に反映
  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }

    try {
      service.registerStudent(studentDetail.getStudent()); // DBへ登録
      System.out.println(studentDetail.getStudent().getName() + " さんが新規受講生として登録されました。");
    } catch (Exception e) {
      System.err.println("受講生登録エラー: " + e.getMessage());
      return "registerStudent"; // エラー時に登録ページに戻る
    }

    return "redirect:/studentList";
  }

  @GetMapping("/editStudent/{id}")
  public String editStudent(@PathVariable int id, Model model) {
    Student student = service.findStudentById(id);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    model.addAttribute("studentDetail", studentDetail);
    return "editStudent";
  }

  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "editStudent";
    }

    try {
      service.updateStudent(studentDetail.getStudent());
      System.out.println("受講生ID " + studentDetail.getStudent().getId() + " の情報を更新しました。");
    } catch (Exception e) {
      System.err.println("更新エラー: " + e.getMessage());
      return "editStudent";
    }

    return "redirect:/studentList";
  }

}