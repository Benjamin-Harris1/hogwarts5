package dk.kea.dat3js.hogwarts5.teachers;

import dk.kea.dat3js.hogwarts5.house.HouseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TeacherService {
  private final TeacherRepository teacherRepository;
  private final HouseService houseService;

  public TeacherService(TeacherRepository teacherRepository, HouseService houseService) {
    this.teacherRepository = teacherRepository;
    this.houseService = houseService;
  }
  public List<TeacherResponseDTO> findAll() {
    return teacherRepository.findAll().stream().map(this::toDTO).toList();
  }

  public Optional<TeacherResponseDTO> findById(int id) {
    return teacherRepository.findById(id).map(this::toDTO);
  }

  public TeacherResponseDTO save(TeacherRequestDTO teacher) {
    return toDTO(teacherRepository.save(fromDTO(teacher)));
  }

  public Optional<TeacherResponseDTO> updateIfExists(int id, TeacherRequestDTO teacher) {
    if (teacherRepository.existsById(id)) {
      Teacher existingTeacher = fromDTO(teacher);
      existingTeacher.setId(id);
      return Optional.of(toDTO(teacherRepository.save(existingTeacher)));
    } else {
      // TODO: Throw error?
      return Optional.empty();
    }
  }

  public Optional<TeacherResponseDTO> partialUpdate(int id, TeacherRequestDTO teacher) {
    Optional<Teacher> existingTeacher = teacherRepository.findById(id);
    if(existingTeacher.isPresent()) {
      Teacher teacherToUpdate = existingTeacher.get();
      if(teacher.firstName() != null) {
        teacherToUpdate.setFirstName(teacher.firstName());
      }
      if(teacher.middleName() != null) {
        teacherToUpdate.setMiddleName(teacher.middleName());
      }
      if(teacher.lastName() != null) {
        teacherToUpdate.setLastName(teacher.lastName());
      }
      if(teacher.house() != null) {
        teacherToUpdate.setHouse(houseService.findById(teacher.house()).orElseThrow());
      }

      if(teacher.mainSubject() != null) {
        teacherToUpdate.setMainSubject(teacher.mainSubject());
      }
      if(teacher.employmentDate() != null) {
        teacherToUpdate.setEmploymentDate(teacher.employmentDate());
      }

      return Optional.of(toDTO(teacherRepository.save(teacherToUpdate)));
    } else {
      // TODO: handle error
      return Optional.empty();
    }
  }

  public Optional<TeacherResponseDTO> deleteById(int id) {
    Optional<TeacherResponseDTO> existingTeacher = teacherRepository.findById(id).map(this::toDTO);
    teacherRepository.deleteById(id);
    return existingTeacher;
  }

  private TeacherResponseDTO toDTO(Teacher teacherEntity) {
    TeacherResponseDTO dto = new TeacherResponseDTO(
        teacherEntity.getId(),
        teacherEntity.getFirstName(),
        teacherEntity.getMiddleName(),
        teacherEntity.getLastName(),
        teacherEntity.getHouse().getName(),
        teacherEntity.getMainSubject(),
        teacherEntity.getEmploymentDate()
    );

    return dto;
  }

  private Teacher fromDTO(TeacherRequestDTO teacherDTO) {
    Teacher entity = new Teacher(
        teacherDTO.firstName(),
        teacherDTO.middleName(),
        teacherDTO.lastName(),
        houseService.findById(teacherDTO.house()).orElseThrow(),
        teacherDTO.mainSubject(),
        teacherDTO.employmentDate()
    );

    return entity;
  }
}
