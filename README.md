## Running the Prefect Tests

To run the automated tests, follow these steps:

1. Ensure your development environment is set up with Java and Maven.
2. Navigate to the root directory of the project.
3. Run the following command to execute all tests:

```bash
./mvnw test
```


## What the tests do

### PrefectControllerTest

- **getAllPrefects**: Tests that the API returns all prefects correctly.


- **getPrefectById_ValidPrefect_ReturnsPrefect**: Ensures that the API returns the correct prefect when queried by ID.


- **getPrefectsByHouse_ValidHouse_ReturnsPrefectsInHouse**: Checks that the API returns all prefects from a specific house.


- **appointPrefect**: Verifies that the API correctly appoints a student as a prefect.


- **removePrefect**: Confirms that the API correctly removes the prefect status from a student.

### PrefectServiceTest

- **appointPrefect_ValidStudent_PrefectAppointed**: Tests that a valid student can be appointed as a prefect.


- **removePrefect_ValidPrefect_PrefectRemoved**: Ensures that a valid prefect can have their status removed.


- **getPrefectById_ValidPrefect_ReturnsPrefect**: Checks that the service can retrieve a prefect by ID.


- **getPrefectsByHouse_ValidHouse_ReturnsPrefectsInHouse**: Verifies that the service can retrieve all prefects from a specific house.


- **appointPrefect_StudentNotInFifthYear_ThrowsException**: Ensures the service throws an exception if a student not in the fifth year or higher is appointed as a prefect.


- **appointPrefect_TooManyPrefectsInHouse_ThrowsException**: Tests that the service throws an exception if appointing another prefect would exceed the maximum allowed in a house.


- **appointPrefect_GenderDiversityViolated_ThrowsException**: Confirms that the service throws an exception if appointing a prefect would violate gender diversity rules in the same house.
