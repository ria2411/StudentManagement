package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.data.StudentDataService;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);

  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourses> studentCoursesList = new ArrayList<>();
    Mockito.when(repository.search()).thenReturn(studentList);
    Mockito.when(repository.searchStudentCourses()).thenReturn(studentCoursesList);


    List<StudentDetail> actual = sut.searchStudentList();

    Mockito.verify(repository, Mockito.times(1)).search();
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourses();
    Mockito.verify(converter, Mockito.times(1)).convertStudentDetails(studentList, studentCoursesList);

  }

  @Test
  void 受講生登録_リポジトリのINSERTが呼び出されること() {
    Student student = new Student();  // デフォルトコンストラクタ

    student.setId(1);
    student.setName("酒井栞");
    student.setFurigana("サカイシオリ");
    student.setNickname("シオリ");
    student.setEmail("shiori.sakai@abccc.co.jp");
    student.setRegion("東京");
    student.setAge(36);
    student.setGender("Female");
    student.setRemark("特になし");
    student.setDeleted(false);
  }

  @Test
  void 受講生更新_repositoryのupdateが呼ばれること() {
    // given
    Student student = new Student();
    student.setId(1);
    student.setName("酒井栞");
    student.setFurigana("サカイシオリ");
    student.setNickname("シオリ");
    student.setEmail("shiori.sakai@abccc.co.jp");
    student.setRegion("東京");
    student.setAge(36);
    student.setGender("Female");
    student.setRemark("特になし");
    student.setDeleted(false);

    // repository.updateはvoidなので特にwhenは不要

    // when
    sut.updateStudent(student);

    // then
    Mockito.verify(repository, Mockito.times(1)).updateStudent(student);
  }

}