package dk.kea.dat3js.hogwarts5.students;

public record StudentRequestDTO(int id, String firstName, String middleName, String lastName, String name, String house, Integer schoolYear, boolean prefect, String gender) {
}
