package control.tower.user.service.command.interceptors;

import control.tower.user.service.command.commands.CreateUserCommand;
import control.tower.user.service.core.data.UserLookupEntity;
import control.tower.user.service.core.data.UserLookupRepository;
import control.tower.user.service.core.models.UserRole;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class CreateUserCommandInterceptorTest {

    private CreateUserCommandInterceptor interceptor;
    private UserLookupRepository lookupRepository;

    @BeforeEach
    void setUp() {
        lookupRepository = mock(UserLookupRepository.class);
        interceptor = new CreateUserCommandInterceptor(lookupRepository);
    }

    @Test
    void testHandle_ValidCommand() {
        CreateUserCommand validCommand = CreateUserCommand.builder()
                .userId("userId")
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .phoneNumber("phoneNumber")
                .userRole(UserRole.CUSTOMER)
                .build();

        CommandMessage<CreateUserCommand> commandMessage = new GenericCommandMessage<>(validCommand);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        CommandMessage<?> processedCommand = result.apply(0, commandMessage);

        assertEquals(commandMessage, processedCommand);
    }

    @Test
    void testHandle_DuplicateProductId_ThrowsException() {
        String userId = "userId";
        CreateUserCommand duplicateCommand = CreateUserCommand.builder()
                .userId(userId)
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .phoneNumber("phoneNumber")
                .userRole(UserRole.CUSTOMER)
                .build();

        CommandMessage<CreateUserCommand> commandMessage = new GenericCommandMessage<>(duplicateCommand);

        UserLookupEntity existingEntity = new UserLookupEntity(userId);
        when(lookupRepository.findByUserId(userId)).thenReturn(existingEntity);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        assertThrows(IllegalStateException.class, () -> result.apply(0, commandMessage));

        verify(lookupRepository).findByUserId(userId);
    }
}
