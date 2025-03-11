package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.data.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentList")
  public List<Student> getStudentlist() {
    return service.seachStudentList();
  }

  @GetMapping("/StudentCoursesList")
  public List<StudentCourses> getStudentCoursesList() {
    return service.searchStudentCourseList();
  }
}
