package dk.kea.dat3js.hogwarts5.prefect;

import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;
import dk.kea.dat3js.hogwarts5.students.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(studentService.setPrefectStatus(studentId, true)).thenReturn(Optional.of(student));

        // Act
        prefectService.appointPrefect(studentId);

        // Assert
        verify(studentService, times(1)).setPrefectStatus(studentId, true);
    }

    @Test
    void removePrefect_ValidPrefect_PrefectRemoved() throws Exception {
        // Arrange 
        int prefectId = 1;
        StudentResponseDTO prefect = new StudentResponseDTO(prefectId, "Harry", "James", "Potter", "Harry James Potter", "Gryffindor", 5, true, "Male");
        when(studentService.setPrefectStatus(prefectId, false)).thenReturn(Optional.of(prefect));

        // Act
        prefectService.removePrefect(prefectId);

        // Assert
        verify(studentService, times(1)).setPrefectStatus(prefectId, false);
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
    void appointPrefect_StudentNotInFifthYear_ThrowsException() throws Exception {
        // Arrange
        int studentId = 1;
        StudentResponseDTO student = new StudentResponseDTO(studentId, "Harry", "James", "Potter", "Harry James Potter", "Gryffindor", 4, false, "Male");
        when(studentService.findById(studentId)).thenReturn(Optional.of(student));

        // Mock the StudentService to throw an exception when setPrefectStatus is called
        doThrow(new Exception("Only students in 5th year or higher can be appointed as prefects."))
            .when(studentService).setPrefectStatus(studentId, true);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            prefectService.appointPrefect(studentId);
        });

        assertEquals("Only students in 5th year or higher can be appointed as prefects.", exception.getMessage());
    }

    @Test
    void appointPrefect_TooManyPrefectsInHouse_ThrowsException() throws Exception {
        // Arrange
        int studentId = 3;
        StudentResponseDTO student = new StudentResponseDTO(studentId, "Luna", "Lovegood", "Lovegood", "Luna Lovegood", "Ravenclaw", 5, false, "Female");
        when(studentService.findById(studentId)).thenReturn(Optional.of(student));

        // Assuming setPrefectStatus is supposed to throw an exception under certain conditions
        doThrow(new Exception("There can only be two prefects per house."))
            .when(studentService).setPrefectStatus(studentId, true);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            prefectService.appointPrefect(studentId);
        });

        assertEquals("There can only be two prefects per house.", exception.getMessage());
    }

    @Test
    void appointPrefect_GenderDiversityViolated_ThrowsException() throws Exception {
        // Arrange
        int studentId = 3;
        StudentResponseDTO newStudent = new StudentResponseDTO(studentId, "Draco", null, "Malfoy", "Draco Malfoy", "Slytherin", 5, false, "Male");
        when(studentService.findById(studentId)).thenReturn(Optional.of(newStudent));

        // Mock the StudentService to throw an exception when setPrefectStatus is called
        doThrow(new Exception("Prefects in the same house must be of different genders"))
            .when(studentService).setPrefectStatus(studentId, true);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            prefectService.appointPrefect(studentId);
        });

        assertEquals("Prefects in the same house must be of different genders", exception.getMessage());
    }
}