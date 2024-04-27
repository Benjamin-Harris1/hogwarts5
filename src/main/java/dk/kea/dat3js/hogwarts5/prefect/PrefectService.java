package dk.kea.dat3js.hogwarts5.prefect;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;
import dk.kea.dat3js.hogwarts5.students.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class PrefectService {

    private final StudentService studentService;

    public PrefectService(StudentService studentService) {
        this.studentService = studentService;
    }


    public void appointPrefect(@PathVariable Integer studentId) {
        studentService.updatePrefectStatus(studentId, true);
    }

    public void removePrefect(@PathVariable Integer studentId) {
        studentService.updatePrefectStatus(studentId, false);
    }

    public Optional<StudentResponseDTO> getPrefectById(Integer studentId) {
        return studentService.findById(studentId)
                .filter(StudentResponseDTO::prefect);
    }

    public List<StudentResponseDTO> getAllPrefects() {
        return studentService.findAll().stream()
                .filter(StudentResponseDTO::prefect)
                .toList();
    }

    public List<StudentResponseDTO> getPrefectsByHouse(String house) {
        return studentService.findAll().stream()
                .filter(student -> student.house().equals(house))
                .filter(StudentResponseDTO::prefect)
                .toList();
    }

    

    
}
