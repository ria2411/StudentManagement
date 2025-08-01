package raisetech.StudentManagement.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private StudentService service;

  @MockitoBean
  private StudentRepository studentRepository;


  @Test
  void 指定したIDの受講生情報が取得できること() throws Exception {
    int studentId = 1;

    Student student = new Student(
        studentId,
        "酒井栞",
        "サカイシオリ",
        "シオリ",
        "shiori.sakai@abcc.co.jp",
        "東京",
        36,
        "女性",
        "特になし",
        false
    );

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourses(List.of());

    when(service.findStudentById(studentId)).thenReturn(student);
    when(service.findCoursesByStudentId(studentId)).thenReturn(List.of());

    String expectedJson = objectMapper.writeValueAsString(studentDetail);

    mockMvc.perform(get("/student/{id}", studentId))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedJson));
  }
}