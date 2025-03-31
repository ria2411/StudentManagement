package raisetech.StudentManagement.data;

<<<<<<< HEAD
import lombok.Data;

@Data
public class Student {

  private Long Id;
  private Long studentId;
  private String courseName;
  private String startDate;
  private String endDate;
}
=======
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;
  private String name;
  private String furigana;
  private String nickname;
  private String email;
  private String region;
  private int age;
  private String gender;
}
>>>>>>> origin/kadai11
