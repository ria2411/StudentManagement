package raisetech.StudentManagement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@RequestMapping("/students")
public class StudentManagementApplication {

	private final Map<String , String > studentMap = Collections.synchronizedMap(new HashMap<>());

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@GetMapping
	public Map<String, String> 全学生を取得() {
		return studentMap;
	}

	@PostMapping
	public String 学生を追加(@RequestParam String 名前, @RequestParam String 年齢) {
		studentMap.put(名前, 年齢);
		return "学生を追加しました: " + 名前 + " (" + 年齢 + "歳)";
	}

	@PutMapping("/{name}")
	public String 学生を更新(@PathVariable("name") String 名前, @RequestParam String 年齢) {
		if (studentMap.containsKey(名前)) {
			studentMap.put(名前, 年齢);
			return "学生情報を更新しました: " + 名前 + "の年齢を " + 年齢 + " に変更";
		}
		return "学生が見つかりません: " + 名前;
	}
}