package tn.esprit.spring;

import tn.esprit.spring.entities.Skier;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class SkierTests {

    @Test
    void testSkierCreation() {
        // Given
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setDateOfBirth(dateOfBirth);
        skier.setCity("Paris");

        // When
        Long skierId = skier.getNumSkier();
        String firstName = skier.getFirstName();
        String lastName = skier.getLastName();
        LocalDate dob = skier.getDateOfBirth();
        String city = skier.getCity();

        // Then
        assertEquals(1L, skierId);
        assertEquals("John", firstName);
        assertEquals("Doe", lastName);
        assertEquals(dateOfBirth, dob);
        assertEquals("Paris", city);
    }

    @Test
    void testSkierRelationships() {
        // Create a skier and related entities for testing relationships
        Skier skier = new Skier();
        skier.setNumSkier(2L);
        
        // Assuming you have a Subscription class
        Subscription subscription = new Subscription();
        skier.setSubscription(subscription);
        
        // Further tests can be added for pistes and registrations
        assertNotNull(skier.getSubscription());
        
        // If you have methods to add/remove pistes, test those as well
    }
}
