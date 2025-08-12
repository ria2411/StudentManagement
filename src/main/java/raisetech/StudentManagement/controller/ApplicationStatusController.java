package raisetech.StudentManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.service.ApplicationStatusService;

import java.util.List;

@RestController
public class ApplicationStatusController {

  private final ApplicationStatusService service;

  public ApplicationStatusController(ApplicationStatusService service) {
    this.service = service;
  }

  @GetMapping("/application-statuses")
  public List<CourseApplicationStatus> getApplicationStatuses() {
    return service.getAllApplicationStatuses();
  }
}
