package dk.kea.dat3js.hogwarts5.students;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StudentControllerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void notNull(){
        assertNotNull(webClient);
        }

    @Test
    void createStudentWithFullName(){
        webClient
                .post().uri("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "name": "Harry James Potter",
                            "house": "Gryffindor",
                            "schoolYear": 1
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.firstName").isEqualTo("Harry")
                .jsonPath("$.middleName").isEqualTo("James")
                .jsonPath("$.lastName").isEqualTo("Potter")
                .jsonPath("$.fullName").isEqualTo("Harry James Potter")
                .jsonPath("$.house").isEqualTo("Gryffindor")
                .jsonPath("$.schoolYear").isEqualTo(1);
    }

    @Test
    void createStudentWithNameParts(){
        webClient
                .post().uri("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "firstName": "Harry",
                            "middleName": "James",
                            "lastName": "Potter",
                            "house": "Gryffindor",
                            "schoolYear": 1
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().json("""
                        {
                            "firstName": "Harry",
                            "middleName": "James",
                            "lastName": "Potter",
                            "fullName": "Harry James Potter",
                            "house": "Gryffindor",
                            "schoolYear": 1
                        }
                        """);
    }

    @Test
    void updatePrefectStatus_ShouldSucceedForEligibleStudent() {
        int studentId = 3; 
        boolean newPrefectStatus = true;

        webClient.patch().uri("/students/{id}/prefect", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(String.valueOf(newPrefectStatus))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.prefect").isEqualTo(newPrefectStatus)
                .jsonPath("$.id").isEqualTo(studentId);
    }
}
