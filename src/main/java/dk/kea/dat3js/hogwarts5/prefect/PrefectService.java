package dk.kea.dat3js.hogwarts5.prefect;

import org.springframework.stereotype.Service;
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


    public void appointPrefect(Integer studentId) throws Exception {
        StudentResponseDTO student = studentService.findById(studentId)
        .orElseThrow(() -> new Exception("Student not found"));

        // if student is not in 5th year or higher, throw exception
        if (student.schoolYear() < 5) {
            throw new Exception("Only students in 5th year or higher can be appointed as prefects.");
        }

        // List of all prefects in same house
        List<StudentResponseDTO> currentPrefects = getPrefectsByHouse(student.house());

        // Check gender diversity and count
        long countSameGender = currentPrefects.stream()
        .filter(prefect -> prefect.gender().equals(student.gender()))
        .count();

        if (currentPrefects.size() >= 2) {
            throw new Exception("There can only be two prefects per house.");
        }

        if (countSameGender >= 1) {
            throw new Exception("Prefects in the same house must be of different genders");
        }

        // Ipdate the prefect status
        studentService.updatePrefectStatus(studentId, true);
    }

    public void removePrefect(Integer studentId) {
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
