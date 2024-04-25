package dk.kea.dat3js.hogwarts5.teachers;

import java.time.LocalDate;

public record TeacherResponseDTO(int id, String firstName, String middleName, String lastName, String house, String mainSubject, LocalDate employmentDate) {
}
