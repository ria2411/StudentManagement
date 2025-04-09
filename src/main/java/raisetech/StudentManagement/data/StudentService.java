package raisetech.StudentManagement.data;

import java.util.List;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {
  private final StudentRepository repository;

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<StudentCourses> getAllCourses() {
    return repository.findAllCourses();
  }
}
