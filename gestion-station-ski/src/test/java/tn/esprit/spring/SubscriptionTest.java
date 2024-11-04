package tn.esprit.spring.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionTest {

    private Subscription subscription;

    @BeforeEach
    void setUp() {
        subscription = new Subscription();
    }

    @Test
    void testGettersAndSetters() {
        Long numSub = 1L;
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Float price = 100.0f;
        TypeSubscription typeSub = TypeSubscription.MONTHLY;

        subscription.setNumSub(numSub);
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setPrice(price);
        subscription.setTypeSub(typeSub);

        assertThat(subscription.getNumSub()).isEqualTo(numSub);
        assertThat(subscription.getStartDate()).isEqualTo(startDate);
        assertThat(subscription.getEndDate()).isEqualTo(endDate);
        assertThat(subscription.getPrice()).isEqualTo(price);
        assertThat(subscription.getTypeSub()).isEqualTo(typeSub);
    }

    @Test
    void testDefaultConstructor() {
        Subscription newSubscription = new Subscription();
        assertThat(newSubscription.getNumSub()).isNull();
        assertThat(newSubscription.getStartDate()).isNull();
        assertThat(newSubscription.getEndDate()).isNull();
        assertThat(newSubscription.getPrice()).isNull();
        assertThat(newSubscription.getTypeSub()).isNull();
    }

    @Test
    void testParameterizedConstructor() {
        Long numSub = 2L;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);
        Float price = 150.0f;
        TypeSubscription typeSub = TypeSubscription.YEARLY;

        Subscription newSubscription = new Subscription(numSub, startDate, endDate, price, typeSub);

        assertThat(newSubscription.getNumSub()).isEqualTo(numSub);
        assertThat(newSubscription.getStartDate()).isEqualTo(startDate);
        assertThat(newSubscription.getEndDate()).isEqualTo(endDate);
        assertThat(newSubscription.getPrice()).isEqualTo(price);
        assertThat(newSubscription.getTypeSub()).isEqualTo(typeSub);
    }

    @Test
    void testDateConsistency() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);

        assertThat(subscription.getEndDate()).isAfterOrEqualTo(subscription.getStartDate());
    }
}
