package raisetech.StudentManagement.data;

import java.util.List;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentDataService {
  private final StudentRepository repository;

  public StudentDataService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<StudentCourses> getAllCourses() {
    return repository.findAllCourses();
  }
}
