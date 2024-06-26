package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.house.HouseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
  private final StudentRepository studentRepository;
  private final HouseService houseService;

  public StudentService(StudentRepository studentRepository, HouseService houseService) {
    this.studentRepository = studentRepository;
    this.houseService = houseService;
  }

  public List<StudentResponseDTO> findAll() {
    return studentRepository.findAll().stream().map(this::toDTO).toList();
  }

  public Optional<StudentResponseDTO> findById(int id) {
    return studentRepository.findById(id).map(this::toDTO);
  }

  public StudentResponseDTO save(StudentRequestDTO studentDTO) throws Exception {
    Student student = fromDTO(studentDTO);
    if (student.isPrefect()) {
        checkPrefectEligibility(student);
    }
    return toDTO(studentRepository.save(student));
  }

  public Optional<StudentResponseDTO> updateIfExists(int id, StudentRequestDTO studentDTO) throws Exception {
    Optional<Student> existingStudentOpt = studentRepository.findById(id);
    if (existingStudentOpt.isPresent()) {
        Student existingStudent = existingStudentOpt.get();

        // Update the existing student with new details from DTO
        Student updatedStudent = fromDTO(studentDTO);
        updatedStudent.setId(id); // Ensure the ID remains the same

        // Check if prefect status is being updated and perform eligibility checks if setting to true
        if (studentDTO.prefect() != existingStudent.isPrefect()) {
          if (studentDTO.prefect()) {
              checkPrefectEligibility(updatedStudent);
          }
        }

        // Save the updated student and convert to DTO
        return Optional.of(toDTO(studentRepository.save(updatedStudent)));
    } else {
        return Optional.empty();
    }
}

  public Optional<StudentResponseDTO> partialUpdate(int id, StudentRequestDTO student) {
    Optional<Student> existingStudent = studentRepository.findById(id);
    if(existingStudent.isPresent()) {
      Student studentToUpdate = existingStudent.get();
      if(student.firstName() != null) {
        studentToUpdate.setFirstName(student.firstName());
      }
      if(student.middleName() != null) {
        studentToUpdate.setMiddleName(student.middleName());
      }
      if(student.lastName() != null) {
        studentToUpdate.setLastName(student.lastName());
      }
      if(student.house() != null) {
        studentToUpdate.setHouse(houseService.findById(student.house()).orElseThrow());
      }
      if(student.schoolYear() != null) {
        studentToUpdate.setSchoolYear(student.schoolYear());
      }
      return Optional.of(toDTO(studentRepository.save(studentToUpdate)));
    } else {
      // TODO: handle error
      return Optional.empty();
    }
  }

  public Optional<StudentResponseDTO> deleteById(int id) {
    Optional<StudentResponseDTO> existingStudent = studentRepository.findById(id).map(this::toDTO);
    studentRepository.deleteById(id);
    return existingStudent;
  }

  private StudentResponseDTO toDTO(Student studentEntity) {
    StudentResponseDTO dto = new StudentResponseDTO(
        studentEntity.getId(),
        studentEntity.getFirstName(),
        studentEntity.getMiddleName(),
        studentEntity.getLastName(),
        studentEntity.getFullName(),
        studentEntity.getHouse().getName(),
        studentEntity.getSchoolYear(),
        studentEntity.isPrefect(),
        studentEntity.getGender()
    );

    return dto;
  }

  private Student fromDTO(StudentRequestDTO studentDTO) {
    Student entity = new Student(
        studentDTO.firstName(),
        studentDTO.middleName(),
        studentDTO.lastName(),
        houseService.findById(studentDTO.house()).orElseThrow(),
        studentDTO.schoolYear(),
        studentDTO.prefect(),
        studentDTO.gender()
    );

    if (studentDTO.name() != null) {
      entity.setFullName(studentDTO.name());
    }

    return entity;
  }

  public Optional<StudentResponseDTO> setPrefectStatus(int id, boolean prefect) throws Exception {
    Student student = studentRepository.findById(id)
        .orElseThrow(() -> new Exception("Student not found"));

    if (prefect) {
        checkPrefectEligibility(student);
    }

    student.setPrefect(prefect);
    studentRepository.save(student);
    return Optional.of(toDTO(student));
  }

  public void checkPrefectEligibility(Student student) throws Exception {
    if (student.getSchoolYear() < 5) {
        throw new Exception("Only students in 5th year or higher can be appointed as prefects.");
    }

    List<Student> currentPrefects = studentRepository.findAll().stream()
        .filter(s -> s.getHouse().equals(student.getHouse()) && s.isPrefect())
        .toList();

    if (currentPrefects.size() >= 2) {
        throw new Exception("There can only be two prefects per house.");
    }

    long countSameGender = currentPrefects.stream()
        .filter(prefectStudent -> prefectStudent.getGender().equals(student.getGender()))
        .count();

    if (countSameGender >= 1) {
        throw new Exception("Prefects in the same house must be of different genders");
    }
}

  

}
