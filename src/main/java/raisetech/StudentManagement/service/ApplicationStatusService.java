package raisetech.StudentManagement.service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationStatusService {

  private final ApplicationStatusRepository repository;

  public ApplicationStatusService(ApplicationStatusRepository repository) {
    this.repository = repository;
  }

  public List<CourseApplicationStatus> getAllApplicationStatuses() {
    return repository.findAllApplicationStatuses();
  }
}
