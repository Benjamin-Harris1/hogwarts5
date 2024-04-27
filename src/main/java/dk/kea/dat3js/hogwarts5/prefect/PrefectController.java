package dk.kea.dat3js.hogwarts5.prefect;

import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prefects")
public class PrefectController {

    private final PrefectService prefectService;


    public PrefectController(PrefectService prefectService) {
        this.prefectService = prefectService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllPrefects() {
        List<StudentResponseDTO> prefects = prefectService.getAllPrefects();
        return ResponseEntity.ok(prefects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getPrefectById(@PathVariable Integer studentId) {
    return ResponseEntity.of(prefectService.getPrefectById(studentId));
            
}

    @GetMapping("/house/{house}")
    public ResponseEntity<List<StudentResponseDTO>> getPrefectsByHouse(@PathVariable String house) {
        List<StudentResponseDTO> prefects = prefectService.getPrefectsByHouse(house);
        return ResponseEntity.ok(prefects);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> appointPrefect(@PathVariable Integer studentId) {
        prefectService.appointPrefect(studentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePrefect(@PathVariable Integer studentId) {
        prefectService.removePrefect(studentId);
        return ResponseEntity.ok().build();
    }
}
