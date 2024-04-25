package dk.kea.dat3js.hogwarts5.config;

import dk.kea.dat3js.hogwarts5.house.HouseRepository;
import dk.kea.dat3js.hogwarts5.students.Student;
import dk.kea.dat3js.hogwarts5.students.StudentRepository;
import dk.kea.dat3js.hogwarts5.teachers.Teacher;
import dk.kea.dat3js.hogwarts5.teachers.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import dk.kea.dat3js.hogwarts5.house.House;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InitData implements CommandLineRunner {

  private final HouseRepository houseRepository;
  private final StudentRepository studentRepository;
  private final TeacherRepository teacherRepository;

  public InitData(HouseRepository houseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
    this.houseRepository = houseRepository;
    this.studentRepository = studentRepository;
    this.teacherRepository = teacherRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    createHouses();
    createStudents();
    createTeachers();
  }

  private void createStudents() {
    // To avoid creating and re-creating the same students, we first get all those that already exist
    Set<Student> existingStudents = new HashSet<>();
    existingStudents.addAll(studentRepository.findAll());

    Student harry = new Student("Harry", "James", "Potter", gryffindor, 5);
    Student hermione = new Student("Hermione", "Jean", "Granger", gryffindor, 5);
    Student ron = new Student("Ron", "Bilius", "Weasley", gryffindor, 5);
    Student neville = new Student("Neville", "Frank", "Longbottom", gryffindor, 5);
    Student ginny = new Student("Ginny", "Molly", "Weasley", gryffindor, 5);
    Student fred = new Student("Fred", "Gideon", "Weasley", gryffindor, 5);
    Student george = new Student("George", "Fabian", "Weasley", gryffindor, 5);
    Student percy = new Student("Percy", "Ignatius", "Weasley", gryffindor, 5);

    Student draco = new Student("Draco", "", "Malfoy", slytherin, 5);
    Student cedric = new Student("Cedric", "", "Diggory", hufflepuff, 6);
    Student luna = new Student("Luna", "", "Lovegood", ravenclaw, 4);

    existingStudents.addAll(List.of(harry, hermione, ron, neville, ginny, fred, george, percy, draco, cedric, luna));
    studentRepository.saveAll(existingStudents);
  }

  private void createTeachers() {
    // To avoid creating and re-creating the same teachers, we first get all those that already exist
    Set<Teacher> existingTeachers = new HashSet<>();
    existingTeachers.addAll(teacherRepository.findAll());

    Teacher severus = new Teacher("Severus", "Prince", "Snape", slytherin, "Potions", LocalDate.of(1981, 11, 1));
    Teacher minerva = new Teacher("Minerva", "", "McGonagall", gryffindor, "Transfiguration", LocalDate.of(1956, 12, 1));
    Teacher filius = new Teacher("Filius", "", "Flitwick", ravenclaw, "Charms", LocalDate.of(1975, 9, 1));
    Teacher pomona = new Teacher("Pomona", "", "Sprout", hufflepuff, "Herbology", LocalDate.of(1975, 9, 1));
    Teacher sybill = new Teacher("Sybill", "Cassandra", "Trelawney", ravenclaw, "Divination", LocalDate.of(1979, 9, 1));
    Teacher alastor = new Teacher("Alastor", "Mad-Eye", "Moody", gryffindor, "Defence Against the Dark Arts", LocalDate.of(1994, 9, 1));

    existingTeachers.addAll(List.of(severus, minerva, filius, pomona, sybill, alastor));
    teacherRepository.saveAll(existingTeachers);
  }

  private House gryffindor;
  private House slytherin;
  private House hufflepuff;
  private House ravenclaw;


  private void createHouses() {
      gryffindor = new House("Gryffindor", "Godric Gryffindor", new String[] {"red", "gold"});
      slytherin = new House("Slytherin", "Salazar Slytherin", new String[] {"green", "silver"});
      hufflepuff = new House("Hufflepuff", "Helga Hufflepuff", new String[] {"yellow", "black"});
      ravenclaw = new House("Ravenclaw", "Rowena Ravenclaw", new String[] {"blue", "bronze"});

     houseRepository.save(gryffindor);
      houseRepository.save(slytherin);
      houseRepository.save(hufflepuff);
      houseRepository.save(ravenclaw);


  }
}
