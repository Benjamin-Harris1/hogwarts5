package dk.kea.dat3js.hogwarts5.prefect;

import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;
import dk.kea.dat3js.hogwarts5.students.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrefectController.class)
class PrefectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrefectService prefectService;

    @MockBean
    private StudentService studentService;

    @Test
    void getAllPrefects() throws Exception {
        // Arrange
        StudentResponseDTO prefect1 = new StudentResponseDTO(1, "Harry", "James", "Potter", "Harry James Potter", "Gryffindor", 7, true, "Male");
        StudentResponseDTO prefect2 = new StudentResponseDTO(2, "Ron", null, "Weasley", "Ron Weasley", "Gryffindor", 7, false, "Male");
        //List<StudentResponseDTO> prefects = Arrays.asList(prefect1, prefect2);
        when(prefectService.getAllPrefects()).thenReturn(Arrays.asList(prefect1, prefect2));

        // Act and assert
        mockMvc.perform(get("/prefects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id").value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].firstName").value(containsInAnyOrder("Harry", "Ron")));

    }

    @Test
void getPrefectById_ValidPrefect_ReturnsPrefect() throws Exception {
    // Arrange
    int prefectId = 1;
    StudentResponseDTO prefect = new StudentResponseDTO(prefectId, "Harry", "James", "Potter", "Harry James Potter", "Gryffindor", 5, true, "Male");
    when(prefectService.getPrefectById(prefectId)).thenReturn(Optional.of(prefect));

    // Act & Assert
    mockMvc.perform(get("/prefects/{id}", prefectId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(prefectId))
            .andExpect(jsonPath("$.firstName").value("Harry"))
            .andExpect(jsonPath("$.prefect").value(true));
}

@Test
void getPrefectsByHouse_ValidHouse_ReturnsPrefectsInHouse() throws Exception {
    // Arrange
    String house = "Gryffindor";
    List<StudentResponseDTO> prefects = Arrays.asList(
        new StudentResponseDTO(1, "Harry", "James", "Potter", "Harry James Potter", house, 5, true, "Male"),
        new StudentResponseDTO(2, "Hermione", "Jean", "Granger", "Hermione Jean Granger", house, 5, true, "Female")
    );
    when(prefectService.getPrefectsByHouse(house)).thenReturn(prefects);

    // Act & Assert
    mockMvc.perform(get("/prefects/house/{house}", house))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[*].house").value(containsInAnyOrder(house, house)));
}

    @Test
    void appointPrefect() {
    }

    @Test
    void removePrefect() {
    }
}