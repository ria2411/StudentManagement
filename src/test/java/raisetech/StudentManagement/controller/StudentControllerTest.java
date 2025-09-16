package raisetech.StudentManagement.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.print.attribute.standard.Media;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
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

  @Test
  void 存在しないIDは404になること() throws Exception {
    int studentId = 9999;
    when(service.findStudentById(studentId)).thenReturn(null);

    mockMvc.perform(get("/student/{id}", studentId))
        .andExpect(status().isNotFound());
  }

  @Test
  void 受講生一覧が取得できること() throws Exception {
    Student student1 = new Student(1, "酒井栞", "サカイシオリ", "シオリ", "shiori.sakai@abcc.co.jp", "東京", 36, "女性", "特になし", false);
    Student student2 = new Student(2, "深田涼太", "フカダリョウタ", "リョウタ", "ryota.fukada@gmail.com", "東京", 30, "男性", "特になし", false);

    StudentDetail detail1 = new StudentDetail();
    detail1.setStudent(student1);
    detail1.setStudentCourses(List.of());

    StudentDetail detail2 = new StudentDetail();
    detail2.setStudent(student2);
    detail2.setStudentCourses(List.of());

    List<StudentDetail> allStudents = List.of(detail1, detail2);

    when(service.searchStudentsByCriteria(null, null, null, null, null))
        .thenReturn(allStudents);

    String expectedJson = objectMapper.writeValueAsString(allStudents);

    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedJson));
  }

  @Test
  void 受講生とコース登録が成功すること() throws Exception {

  }

  @Test
  void 名前で検索できること() throws Exception {
    String searchName = "酒井";

    Student student = new Student(1, "酒井栞", "サカイシオリ", "シオリ", "shiori.sakai@abcc.co.jp", "東京", 36, "女性", "特になし", false);
    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourses(List.of());

    when(service.searchStudentList()).thenReturn(List.of(detail));

    mockMvc.perform(get("/studentList")
        .param("name", searchName))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(List.of(detail))));
  }

  @Test
  void statusで検索できること() throws Exception {
    String searchStatus = "受講中";
    Student student = new Student(6, "深澤遼", "フカザワリョウ", "リョウタ", "ryo.fukazawa@gmail.jp", "東京", 30, "男性", "特になし", false);
    StudentCourses course = new StudentCourses();
    course.setCourseName("AWSコース");
    course.setStatus(searchStatus);
    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourses(List.of(course));

    when(service.searchStudentList()).thenReturn(List.of(detail));

    mockMvc.perform(get("/studentList").param("status", searchStatus))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].studentCourses[0].status").value("受講中"));
  }

  @Test
  void 論理削除された学生は検索結果に出ないこと() throws Exception {
    int studentId = 99;

    Student deletedStudent = new Student(
        studentId,
        "山田太郎",
        "ヤマダタロウ",
        "タロウ",
        "tarou.yamada@gmail.com",
        "東京",
        25,
        "Male",
        "",
        true
    );

    when(service.findStudentById(studentId)).thenReturn(deletedStudent);

    mockMvc.perform(get("/student/{id}", studentId))
        .andExpect(status().isNotFound());
  }


  @Test
  void 受講生情報の更新が成功すること() throws Exception {
    Student student = new Student(
        1,
        "田中花蓮",
        "タナカカレン",
        "カレン",
        "karen.tanaka@gmail.com",
        "神奈川",
        23,
        "Female",
        "",
        false
    );

    doNothing().when(service).updateStudent(student);

    String requestBody = objectMapper.writeValueAsString(new StudentDetail());
    mockMvc.perform(put("/updateStudent")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().string("更新処理が成功しました"));
  }
}