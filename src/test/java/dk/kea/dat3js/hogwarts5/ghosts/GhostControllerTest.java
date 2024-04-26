package dk.kea.dat3js.hogwarts5.ghosts;

import dk.kea.dat3js.hogwarts5.house.House;
import dk.kea.dat3js.hogwarts5.house.HouseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GhostController.class)
class GhostControllerTest {

    @MockBean
    private HouseRepository houseRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllGhosts() throws Exception {
        mockMvc.perform((RequestBuilder) get("/ghosts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].name").value("Nearly Headless Nick"))
                .andExpect(jsonPath("$[1].name").value("The Bloody Baron"))
                .andExpect(jsonPath("$[2].name").value("The Fat Friar"))
                .andExpect(jsonPath("$[3].name").value("The Grey Lady"));
    }

    @Test
    void getGhostsByName() throws Exception {

        // Mocking the houseRepository
        when(houseRepository.findById("Gryffindor")).thenReturn(Optional.of(new House("Gryffindor", "Random", new String[]{"red", "gold"})));
        when(houseRepository.findById("Slytherin")).thenReturn(Optional.of(new House("Slytherin", "Random", new String[]{"green", "silver"})));
        when(houseRepository.findById("Hufflepuff")).thenReturn(Optional.of(new House("Hufflepuff", "Random", new String[]{"yellow", "black"})));
        when(houseRepository.findById("Ravenclaw")).thenReturn(Optional.of(new House("Ravenclaw", "Random", new String[]{"blue", "bronze"})));


        mockMvc.perform((RequestBuilder) get("/ghosts/Nearly Headless Nick"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nearly Headless Nick"))
                .andExpect(jsonPath("$.realName").value("Sir Nicholas de Mimsy-Porpington"))
                .andExpect(jsonPath("$.house.name").value("Gryffindor"));
    }

}