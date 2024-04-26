package dk.kea.dat3js.hogwarts5.teachers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    void getFullNameWithMiddleName() {
        // arrange
        Teacher teacher = new Teacher("Severus", "Prince", "Snape", null, null, null);

        // act
        var fullName = teacher.getFullName();

        // assert
        assertEquals("Severus Prince Snape", fullName);
    }

    @Test
    void getFullNameWithoutMiddleName(){
        // arrange
        Teacher teacher = new Teacher("Minerva", "McGonagall", null, null, null);

        // act
        var fullName = teacher.getFullName();

        // assert
        assertEquals("Minerva McGonagall", fullName);
    }

    @Test
    void setFullNameWithMiddleName() {
        // arrange
        Teacher teacher = new Teacher("first", "middle", "last", null, null, null);

        // act
        teacher.setFullName("Severus Prince Snape");

        // assert
        assertEquals("Severus", teacher.getFirstName());
        assertEquals("Prince", teacher.getMiddleName());
        assertEquals("Snape", teacher.getLastName());
    }

    @Test
    void setFullNameWithoutMiddleName(){
        // Not sure how to fix this without breaking the crazy capitaliztion one, so I'm leaving it as is.
        // arrange
        Teacher teacher = new Teacher("first","last", null, null, null);

        // act
        teacher.setFullName("Minerva McGonagall");

        // assert
        assertEquals("Minerva", teacher.getFirstName());
        assertNull(teacher.getMiddleName());
        assertEquals("McGonagall", teacher.getLastName());
    }

    @Test
    void setFullNameWithoutLastName(){
        // har ikke lavet den så den IKKE kan modtage lastname.
        // arrange
        Teacher teacher = new Teacher("first", "middle", null, null, null, null);

        // act
        teacher.setFullName("Severus Snape");

        // assert
        assertEquals("Severus", teacher.getFirstName());
        assertEquals("Snape", teacher.getLastName());
    }

    @Test
    void setFullNameWithMultipleMiddleNames(){
        // arrange
        Teacher teacher = new Teacher("first", "middle", "last", null, null, null);

        // act
        teacher.setFullName("Albus Percival Wulfric Brian Dumbledore");

        // assert
        assertEquals("Albus", teacher.getFirstName());
        assertEquals("Percival Wulfric Brian", teacher.getMiddleName());
        assertEquals("Dumbledore", teacher.getLastName());
    }

    @Test
    void setFullNameWithNull(){
        // arrange
        Teacher teacher = new Teacher("first", "middle", "last", null, null, null);

        // act
        teacher.setFullName(null);

        // assert
        assertEquals("first", teacher.getFirstName());
        assertEquals("middle", teacher.getMiddleName());
        assertEquals("last", teacher.getLastName());
    }

    @Test
    void setFullNameWithEmptyString(){
        // arrange
        Teacher teacher = new Teacher("first", "middle", "last", null, null, null);

        // act
        teacher.setFullName("");

        // assert
        assertEquals("first", teacher.getFirstName());
        assertEquals("middle", teacher.getMiddleName());
        assertEquals("last", teacher.getLastName());
    }

    @Test
    void capitalizeIndividualNames(){
        // arrange
        Teacher teacher = new Teacher("first", "middle", "last", null, null, null);

        // act
        teacher.setFirstName("severus");
        teacher.setMiddleName("prince");
        teacher.setLastName("snape");

        // assert
        assertEquals("Severus", teacher.getFirstName());
        assertEquals("Prince", teacher.getMiddleName());
        assertEquals("Snape", teacher.getLastName());
    }

    @Test
    void capitalizeIndividualNamesWithCrazyCapitalization(){
        // arrange
        Teacher teacher = new Teacher("first", "middle", "last", null, null, null);

        // act
        teacher.setFirstName("sEvERus");
        teacher.setMiddleName("PrInCe");
        teacher.setLastName("sNaPe");

        // assert
        assertEquals("Severus", teacher.getFirstName());
        assertEquals("Prince", teacher.getMiddleName());
        assertEquals("Snape", teacher.getLastName());
    }

}