package raisetech.StudentManagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
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
    doNothing().when(service).updateStudent(any(Student.class));

    String requestBody = """
        {
          "student": {
            "id": 1,
            "name": "田中花蓮",
            "furigana": "タナカカレン",
            "nickname": "カレン",
            "email": "karen.tanaka@gmail.com",
            "region": "神奈川",
            "age": 23,
            "gender": "Female",
            "remark": "",
            "deleted": false
          },
          "studentCourses": []
        }
        """;

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("更新処理が成功しました。")); // ←「。」つける
  }


  @Test
  void 受講生更新_必須項目が空だと400になること() throws Exception {
    // JSON の region を空にしてバリデーションエラーを誘発
    String requestBody = """
        {
          "student": {
            "id": 1,
            "name": "田中花蓮",
            "furigana": "タナカカレン",
            "nickname": "カレン",
            "email": "karen.tanaka@gmail.com",
            "region": "",
            "age": 23,
            "gender": "Female",
            "remark": "",
            "deleted": false
          },
          "studentCourses": []
        }
        """;

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andDo(print()) // どの項目でエラーになったか確認できる
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.['student.region']").value("地域は必須です"));
  }

  @Test
  void 受講生更新_必須項目複数が空だと400になること() throws Exception {
    // JSON で region と furigana を空に
    String requestBody = """
        {
          "student": {
            "id": 1,
            "name": "田中花蓮",
            "furigana": "",
            "nickname": "カレン",
            "email": "karen.tanaka@gmail.com",
            "region": "",
            "age": 23,
            "gender": "Female",
            "remark": "",
            "deleted": false
          },
          "studentCourses": []
        }
        """;

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andDo(print()) // どの項目でエラーになったか確認できる
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.['student.region']").value("地域は必須です"))
        .andExpect(jsonPath("$.['student.furigana']").value("フリガナは必須です"));
  }


}