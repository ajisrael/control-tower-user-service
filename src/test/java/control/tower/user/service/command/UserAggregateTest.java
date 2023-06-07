package control.tower.user.service.command;

import control.tower.user.service.command.commands.CreateUserCommand;
import control.tower.user.service.core.events.UserCreatedEvent;
import control.tower.user.service.core.models.UserRole;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAggregateTest {

    private final String USER_ID = "userId";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String EMAIL = "email";
    private final String PHONE_NUMBER = "phoneNumber";
    private final UserRole USER_ROLE = UserRole.CUSTOMER;

    private FixtureConfiguration<UserAggregate> fixture;

    @BeforeEach
    void setup() {
        fixture = new AggregateTestFixture<>(UserAggregate.class);
    }

    @Test
    void shouldCreateUserAggregate() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(USER_ID)
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .email(EMAIL)
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(USER_ROLE)
                                .build())
                .expectEvents(
                        UserCreatedEvent.builder()
                                .userId(USER_ID)
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .email(EMAIL)
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(USER_ROLE)
                                .build())
                .expectState(
                        userAggregate -> {
                            assertEquals(USER_ID, userAggregate.getUserId());
                            assertEquals(FIRST_NAME, userAggregate.getFirstName());
                            assertEquals(LAST_NAME, userAggregate.getLastName());
                            assertEquals(EMAIL, userAggregate.getEmail());
                            assertEquals(PHONE_NUMBER, userAggregate.getPhoneNumber());
                            assertEquals(USER_ROLE, userAggregate.getUserRole());
                        }
                );
    }

    @Test
    void shouldNotCreateUserAggregateWhenUserIdIsNull() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(null)
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .email(EMAIL)
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(USER_ROLE)
                                .build())
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateUserAggregateWhenUserIdIsEmpty() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId("")
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .email(EMAIL)
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(USER_ROLE)
                                .build())
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateUserAggregateWhenFirstNameIsNull() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(USER_ID)
                                .firstName(null)
                                .lastName(LAST_NAME)
                                .email(EMAIL)
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(USER_ROLE)
                                .build())
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateUserAggregateWhenFirstNameIsEmpty() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(USER_ID)
                                .firstName("")
                                .lastName(LAST_NAME)
                                .email(EMAIL)
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(USER_ROLE)
                                .build())
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateUserAggregateWhenLastNameIsNull() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(USER_ID)
                                .firstName(FIRST_NAME)
                                .lastName(null)
                                .email(EMAIL)
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(USER_ROLE)
                                .build())
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateUserAggregateWhenLastNameIsEmpty() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(USER_ID)
                                .firstName(FIRST_NAME)
                                .lastName("")
                                .email(EMAIL)
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(USER_ROLE)
                                .build())
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateUserAggregateWhenEmailIsNull() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(USER_ID)
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .email(null)
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(USER_ROLE)
                                .build())
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateUserAggregateWhenEmailIsEmpty() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(USER_ID)
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .email("")
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(USER_ROLE)
                                .build())
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateUserAggregateWhenPhoneNumberIsNull() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(USER_ID)
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .email(EMAIL)
                                .phoneNumber(null)
                                .userRole(USER_ROLE)
                                .build())
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateUserAggregateWhenPhoneNumberIsEmpty() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(USER_ID)
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .email(EMAIL)
                                .phoneNumber("")
                                .userRole(USER_ROLE)
                                .build())
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateUserAggregateWhenUserRoleIsNull() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateUserCommand.builder()
                                .userId(USER_ID)
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .email(EMAIL)
                                .phoneNumber(PHONE_NUMBER)
                                .userRole(null)
                                .build())
                .expectException(IllegalArgumentException.class);
    }
}
