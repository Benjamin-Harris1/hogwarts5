package dk.kea.dat3js.hogwarts5.students;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void getFullNameWithMiddleName() {
        // arrange
        Student student = new Student("Harry", "James", "Potter", null, 5);

        // act
        var fullName = student.getFullName();

        // assert
        assertEquals("Harry James Potter", fullName);
    }

    @Test
    void getFullNameWithoutMiddleName(){
        // arrange
        Student student = new Student("Cho", "Chang", null, 5);

        // act
        var fullName = student.getFullName();

        // assert
        assertEquals("Cho Chang", fullName);
    }

    @Test
    void setFullNameWithMiddleName() {
        // arrange
        Student student = new Student("first", "middle", "last", null, 5);

        // act
        student.setFullName("Hermione Jean Granger");

        // assert
        assertEquals("Hermione", student.getFirstName());
        assertEquals("Jean", student.getMiddleName());
        assertEquals("Granger", student.getLastName());
    }

    @Test
    void setFullNameWithoutMiddleName(){
        // arrange
        Student student = new Student("first","last", null, 5);

        // act
        student.setFullName("Ron Weasley");

        // assert
        assertEquals("Ron", student.getFirstName());
        assertNull(student.getMiddleName());
        assertEquals("Weasley", student.getLastName());
    }

    @Test
    void setFullNameWithoutLastName(){
        // arrange
        Student student = new Student("first", "middle", null, null, 5);

        // act
        student.setFullName("Luna Lovegood");

        // assert
        assertEquals("Luna", student.getFirstName());
        assertEquals("Lovegood", student.getLastName());
    }

    @Test
    void setFullNameWithMultipleMiddleNames(){
        // arrange
        Student student = new Student("first", "middle", "last", null, 5);

        // act
        student.setFullName("Hermione Jean Jean Granger");

        // assert
        assertEquals("Hermione", student.getFirstName());
        assertEquals("Jean Jean", student.getMiddleName());
        assertEquals("Granger", student.getLastName());
    }

    @Test
    void setFullNameWithNull(){
        // arrange
        Student student = new Student("first", "middle", "last", null, 5);

        // act
        student.setFullName(null);

        // assert
        assertEquals("first", student.getFirstName());
        assertEquals("middle", student.getMiddleName());
        assertEquals("last", student.getLastName());
    }

    @Test
    void setFullNameWithEmptyString(){
        // arrange
        Student student = new Student("first", "middle", "last", null, 5);

        // act
        student.setFullName("");

        // assert
        assertEquals("first", student.getFirstName());
        assertEquals("middle", student.getMiddleName());
        assertEquals("last", student.getLastName());
    }

    @Test
    void capitalizeIndividualNames(){
        // arrange
        Student student = new Student("first", "middle", "last", null, 5);

        // act
        student.setFirstName("harry");
        student.setMiddleName("james");
        student.setLastName("potter");

        // assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James", student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }

    @Test
    void capitalizeIndividualNamesWithCrazyCapitalization(){
        // arrange
        Student student = new Student("first", "middle", "last", null, 5);

        // act
        student.setFirstName("hArRy");
        student.setMiddleName("jAmEs");
        student.setLastName("pOtTeR");

        // assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James", student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }
}