package raisetech.StudentManagement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerValidationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private StudentService service;

  @Test
  void 名前が空文字の場合はバリデーションエラーになる() throws Exception {
    // nameが空
    Student student = new Student();
    student.setName("");
    student.setFurigana("サカイシオリ");
    student.setNickname("シオリ");
    student.setEmail("shiori.sakai@abcc.co.jp");
    student.setRegion("東京");
    student.setAge(36);
    student.setGender("女性");
    student.setRemark("特になし");
    student.setDeleted(false);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);

    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(detail)))
        .andExpect(status().isBadRequest());
  }
}


