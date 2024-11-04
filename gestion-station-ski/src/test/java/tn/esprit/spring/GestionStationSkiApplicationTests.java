package tn.esprit.spring.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SkierTest {

    private Skier skier;

    @BeforeEach
    public void setUp() {
        skier = new Skier();
    }

    @Test
    public void testSetAndGetNumSkier() {
        Long expectedNumSkier = 1L;
        skier.setNumSkier(expectedNumSkier);
        assertEquals(expectedNumSkier, skier.getNumSkier());
    }

    @Test
    public void testSetAndGetFirstName() {
        String expectedFirstName = "John";
        skier.setFirstName(expectedFirstName);
        assertEquals(expectedFirstName, skier.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        String expectedLastName = "Doe";
        skier.setLastName(expectedLastName);
        assertEquals(expectedLastName, skier.getLastName());
    }

    @Test
    public void testSetAndGetDateOfBirth() {
        LocalDate expectedDateOfBirth = LocalDate.of(1990, 1, 1);
        skier.setDateOfBirth(expectedDateOfBirth);
        assertEquals(expectedDateOfBirth, skier.getDateOfBirth());
    }

    @Test
    public void testSetAndGetCity() {
        String expectedCity = "Paris";
        skier.setCity(expectedCity);
        assertEquals(expectedCity, skier.getCity());
    }

    @Test
    public void testSetAndGetSubscription() {
        Subscription subscription = mock(Subscription.class);
        skier.setSubscription(subscription);
        assertEquals(subscription, skier.getSubscription());
    }

    @Test
    public void testSetAndGetPistes() {
        Set<Piste> expectedPistes = new HashSet<>();
        expectedPistes.add(mock(Piste.class));
        skier.setPistes(expectedPistes);
        assertEquals(expectedPistes, skier.getPistes());
    }

    @Test
    public void testSetAndGetRegistrations() {
        Set<Registration> expectedRegistrations = new HashSet<>();
        expectedRegistrations.add(mock(Registration.class));
        skier.setRegistrations(expectedRegistrations);
        assertEquals(expectedRegistrations, skier.getRegistrations());
    }
}
