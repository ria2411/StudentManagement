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
    // given
    List<Student> studentList = new ArrayList<>();
    List<StudentCourses> studentCoursesList = new ArrayList<>();
    Mockito.when(repository.search()).thenReturn(studentList);
    Mockito.when(repository.searchStudentCourses()).thenReturn(studentCoursesList);

    // when(実行)
    List<StudentDetail> actual = sut.searchStudentList();

    // then(検証)
    Mockito.verify(repository, Mockito.times(1)).search();
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourses();
    Mockito.verify(converter, Mockito.times(1)).convertStudentDetails(studentList, studentCoursesList);
  }

  @Test
  void 受講生登録_リポジトリのINSERTが呼び出されること() {
    // given
    Student student = new Student(
        1,
        "酒井栞",
        "サカイシオリ",
        "シオリ",
        "shiori.sakai@abccc.co.jp",
        "東京",
        36,
        "Female",
        "特になし",
        false
    );

    // when(実行)
    sut.registerStudent(student);

    // then(検証)
    Mockito.verify(repository, Mockito.times(1)).insertStudent(student);
  }


  @Test
  void 受講生更新_repositoryのupdateが呼ばれること() {
    // given
    Student student = new Student(
        1,
        "酒井栞",
        "サカイシオリ",
        "シオリ",
        "shiori.sakai@abccc.co.jp",
        "東京",
        36,
        "Female",
        "特になし",
        false
    );

    // repository.updateはvoidなので特にwhenは不要

    // when
    sut.updateStudent(student);

    // then
    Mockito.verify(repository, Mockito.times(1)).updateStudent(student);
  }


}