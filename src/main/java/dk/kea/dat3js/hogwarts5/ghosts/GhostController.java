package dk.kea.dat3js.hogwarts5.ghosts;

import dk.kea.dat3js.hogwarts5.house.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ghosts")
public class GhostController {

    private final HouseService houseService;

    private List<Ghost> ghosts = new ArrayList<>();

    public GhostController(HouseService houseService){
        this.houseService = houseService;
    }

    private void initGhosts(){
        if(ghosts.isEmpty()) {
            ghosts.add(new Ghost(1, "Nearly Headless Nick", "Sir Nicholas de Mimsy-Porpington", houseService.findById("Gryffindor").get()));
            ghosts.add(new Ghost(2, "The Bloody Baron", "Baron", houseService.findById("Slytherin").get()));
            ghosts.add(new Ghost(3, "The Fat Friar", "Friar", houseService.findById("Hufflepuff").get()));
            ghosts.add(new Ghost(4, "The Grey Lady", "Helena Ravenclaw", houseService.findById("Ravenclaw").get()));
        }
    }

    @GetMapping
    public List<Ghost> getAllGhosts(){
        initGhosts();
        return ghosts;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Ghost> getGhostByName(@PathVariable String name){
        initGhosts();
        return ResponseEntity.of(ghosts.stream().filter(ghost -> ghost.getName().contains(name)).findFirst());
    }
}
