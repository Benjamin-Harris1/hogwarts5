package dk.kea.dat3js.hogwarts5.prefect;

import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;
import dk.kea.dat3js.hogwarts5.students.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PrefectServiceTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private PrefectService prefectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void appointPrefect_ValidStudent_PrefectAppointed() throws Exception {
        // Arrange
        int studentId = 1;
        StudentResponseDTO student = new StudentResponseDTO(studentId, "Harry", "James", "Potter", "Harry James Potter", "Gryffindor", 5, false, "Male");
        when(studentService.findById(studentId)).thenReturn(Optional.of(student));
        when(studentService.updatePrefectStatus(studentId, true)).thenReturn(Optional.of(student));

        // Act
        prefectService.appointPrefect(studentId);

        // Assert
        verify(studentService, times(1)).updatePrefectStatus(studentId, true);
    }

    @Test
    void removePrefect_ValidPrefect_PrefectRemoved() {
        // Arrange 
        int prefectId = 1;
        StudentResponseDTO prefect = new StudentResponseDTO(prefectId, "Harry", "James", "Potter", "Harry James Potter", "Gryffindor", 5, true, "Male");
        when(studentService.updatePrefectStatus(prefectId, false)).thenReturn(Optional.of(prefect));

        // Act
        prefectService.removePrefect(prefectId);

        // Assert
        verify(studentService, times(1)).updatePrefectStatus(prefectId, false);
    }

    @Test
    void getPrefectById_ValidPrefect_ReturnsPrefect() {
        // Arrange
        int prefectId = 1;
        StudentResponseDTO prefect = new StudentResponseDTO(prefectId, "Harry", "James", "Potter", "Harry James Potter", "Gryffindor", 5, true, "Male");
        when(studentService.findById(prefectId)).thenReturn(Optional.of(prefect));

        // Act
        Optional<StudentResponseDTO> result = prefectService.getPrefectById(prefectId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(prefect, result.get());
    }

    @Test
    void getPrefectsByHouse_ValidHouse_ReturnsPrefectsInHouse() {
        // Arrange
        String house = "Gryffindor";
        List<StudentResponseDTO> prefects = Arrays.asList(
            new StudentResponseDTO(1, "Harry", "James", "Potter", "Harry James Potter", house, 5, true, "Male"),
            new StudentResponseDTO(2, "Hermione", "Jean", "Granger", "Hermione Jean Granger", house, 5, true, "Female")
        );
        when(studentService.findAll()).thenReturn(prefects);

        // Act
        List<StudentResponseDTO> result = prefectService.getPrefectsByHouse(house);

        // Assert 
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.house().equals(house)));
    }

    @Test
    void appointPrefect_StudentNotInFifthYear_ThrowsException() {
        // Arrange
        int studentId = 1;
        StudentResponseDTO student = new StudentResponseDTO(studentId, "Harry", "James", "Potter", "Harry James Potter", "Gryffindor", 4, false, "Male");
        when(studentService.findById(studentId)).thenReturn(Optional.of(student));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            prefectService.appointPrefect(studentId);
        });

        assertEquals("Only students in 5th year or higher can be appointed as prefects.", exception.getMessage());
    }

    @Test
    void appointPrefect_TooManyPrefectsInHouse_ThrowsException() {
        // Arrange
        int studentId = 3;
        StudentResponseDTO student = new StudentResponseDTO(studentId, "Luna", "Lovegood", "Lovegood", "Luna Lovegood", "Ravenclaw", 5, false, "Female");
        List<StudentResponseDTO> existingPrefects = Arrays.asList(
            new StudentResponseDTO(1, "Cho", "Chang", "Chang", "Cho Chang", "Ravenclaw", 5, true, "Female"),
            new StudentResponseDTO(2, "Padma", "Patil", "Patil", "Padma Patil", "Ravenclaw", 5, true, "Female")
        );
        when(studentService.findById(studentId)).thenReturn(Optional.of(student));
        when(prefectService.getPrefectsByHouse("Ravenclaw")).thenReturn(existingPrefects);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            prefectService.appointPrefect(studentId);
        });

        assertEquals("There can only be two prefects per house.", exception.getMessage());
    }

    @Test
    void appointPrefect_GenderDiversityViolated_ThrowsException() {
        // Arrange
        int studentId = 3;
        StudentResponseDTO newStudent = new StudentResponseDTO(studentId, "Draco", null, "Malfoy", "Draco Malfoy", "Slytherin", 5, false, "Male");
        List<StudentResponseDTO> existingPrefects = Arrays.asList(
            new StudentResponseDTO(2, "Blaise", null, "Zabini", "Blaise Zabini", "Slytherin", 5, true, "Male")
        );
        when(studentService.findById(studentId)).thenReturn(Optional.of(newStudent));
        when(prefectService.getPrefectsByHouse("Slytherin")).thenReturn(existingPrefects);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            prefectService.appointPrefect(studentId);
        });

        assertEquals("Prefects in the same house must be of different genders", exception.getMessage());
    }
}