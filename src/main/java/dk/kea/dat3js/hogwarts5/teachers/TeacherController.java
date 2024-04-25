package dk.kea.dat3js.hogwarts5.teachers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

  private final TeacherService teacherService;

  public TeacherController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @GetMapping
  public List<TeacherResponseDTO> getAllTeachers() {
    return teacherService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<TeacherResponseDTO> getTeacher(@PathVariable int id) {
    return ResponseEntity.of(teacherService.findById(id));
  }

  @PostMapping
  public TeacherResponseDTO createTeacher(@RequestBody TeacherRequestDTO teacher) {
    return teacherService.save(teacher);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable int id, @RequestBody TeacherRequestDTO teacher) {
    return ResponseEntity.of(teacherService.updateIfExists(id, teacher));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<TeacherResponseDTO> partialUpdateTeacher(@PathVariable int id, @RequestBody TeacherRequestDTO teacher) {
    return ResponseEntity.of(teacherService.partialUpdate(id, teacher));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<TeacherResponseDTO> deleteTeacher(@PathVariable int id) {
    return ResponseEntity.of(teacherService.deleteById(id));
  }
}
