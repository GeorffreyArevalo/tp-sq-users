package com.pragmafood.talentpool.users.domain.api.usecase;

import com.pragmafood.talentpool.users.domain.exception.DocumentAlreadyExistsException;
import com.pragmafood.talentpool.users.domain.exception.EmailAlreadyExistsException;
import com.pragmafood.talentpool.users.domain.exception.UserNotFoundException;
import com.pragmafood.talentpool.users.domain.exception.UserUnderAgeException;
import com.pragmafood.talentpool.users.domain.model.Role;
import com.pragmafood.talentpool.users.domain.model.User;
import com.pragmafood.talentpool.users.domain.spi.PasswordEncoderPort;
import com.pragmafood.talentpool.users.domain.spi.UserPersistencePort;
import com.pragmafood.talentpool.users.domain.usecase.UserUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setName("Juan");
        validUser.setLastName("Pérez");
        validUser.setDocumentId("123456789");
        validUser.setPhone("+573005698325");
        validUser.setBirthDate(LocalDate.of(2000, 1, 1));
        validUser.setEmail("juan@example.com");
        validUser.setPassword("password123");
    }

    @Test
    void createOwner_shouldCreateOwnerSuccessfully() {
        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPassword");
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        User result = userUseCase.createOwner(validUser);

        assertNotNull(result.getId());
        assertEquals(Role.OWNER, result.getRole());
        assertEquals("encodedPassword", result.getPassword());
        verify(userPersistencePort).saveUser(any(User.class));
    }

    @Test
    void createOwner_shouldThrowWhenUserIsUnderage() {
        validUser.setBirthDate(LocalDate.now().minusYears(17));

        assertThrows(UserUnderAgeException.class, () -> userUseCase.createOwner(validUser));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void createOwner_shouldThrowWhenEmailAlreadyExists() {
        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.of(validUser));

        assertThrows(EmailAlreadyExistsException.class, () -> userUseCase.createOwner(validUser));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void createOwner_shouldThrowWhenDocumentAlreadyExists() {
        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.of(validUser));

        assertThrows(DocumentAlreadyExistsException.class, () -> userUseCase.createOwner(validUser));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void createOwner_shouldEncodePassword() {
        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode("password123")).thenReturn("$2a$10$encodedHash");
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userUseCase.createOwner(validUser);

        assertEquals("$2a$10$encodedHash", result.getPassword());
        verify(passwordEncoderPort).encode("password123");
    }

    @Test
    void createOwner_shouldAlwaysSetRoleToPropietario() {
        validUser.setRole(Role.ADMIN);
        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode(anyString())).thenReturn("encoded");
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userUseCase.createOwner(validUser);

        assertEquals(Role.OWNER, result.getRole());
    }

    @Test
    void createOwner_shouldAcceptExactly18YearsOld() {
        validUser.setBirthDate(LocalDate.now().minusYears(18));
        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode(anyString())).thenReturn("encoded");
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userUseCase.createOwner(validUser);

        assertNotNull(result);
        assertEquals(Role.OWNER, result.getRole());
    }

    @Test
    void getUserById_shouldReturnUserWhenExists() {
        validUser.setId(1L);
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(validUser));

        User result = userUseCase.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userPersistencePort).findById(1L);
    }

    @Test
    void getUserById_shouldThrowWhenUserNotFound() {
        when(userPersistencePort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.getUserById(99L));
        verify(userPersistencePort).findById(99L);
    }

    @Test
    void createEmployee_shouldCreateEmployeeSuccessfully() {
        User employee = new User();
        employee.setName("Carlos");
        employee.setLastName("Lopez");
        employee.setDocumentId("987654321");
        employee.setPhone("+573001234567");
        employee.setEmail("carlos@example.com");
        employee.setPassword("secret123");

        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPassword");
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setId(2L);
            return saved;
        });

        User result = userUseCase.createEmployee(employee);

        assertNotNull(result.getId());
        assertEquals(Role.EMPLOYEE, result.getRole());
        assertEquals("encodedPassword", result.getPassword());
        verify(userPersistencePort).saveUser(any(User.class));
    }

    @Test
    void createEmployee_shouldThrowWhenEmailAlreadyExists() {
        User employee = new User();
        employee.setName("Carlos");
        employee.setLastName("Lopez");
        employee.setDocumentId("987654321");
        employee.setPhone("+573001234567");
        employee.setEmail("carlos@example.com");
        employee.setPassword("secret123");

        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.of(employee));

        assertThrows(EmailAlreadyExistsException.class, () -> userUseCase.createEmployee(employee));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void createEmployee_shouldThrowWhenDocumentAlreadyExists() {
        User employee = new User();
        employee.setName("Carlos");
        employee.setLastName("Lopez");
        employee.setDocumentId("987654321");
        employee.setPhone("+573001234567");
        employee.setEmail("carlos@example.com");
        employee.setPassword("secret123");

        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.of(employee));

        assertThrows(DocumentAlreadyExistsException.class, () -> userUseCase.createEmployee(employee));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void createEmployee_shouldEncodePassword() {
        User employee = new User();
        employee.setName("Carlos");
        employee.setLastName("Lopez");
        employee.setDocumentId("987654321");
        employee.setPhone("+573001234567");
        employee.setEmail("carlos@example.com");
        employee.setPassword("secret123");

        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode("secret123")).thenReturn("$2a$10$encodedHash");
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userUseCase.createEmployee(employee);

        assertEquals("$2a$10$encodedHash", result.getPassword());
        verify(passwordEncoderPort).encode("secret123");
    }

    @Test
    void createEmployee_shouldAlwaysSetRoleToEmployee() {
        User employee = new User();
        employee.setName("Carlos");
        employee.setLastName("Lopez");
        employee.setDocumentId("987654321");
        employee.setPhone("+573001234567");
        employee.setEmail("carlos@example.com");
        employee.setPassword("secret123");
        employee.setRole(Role.ADMIN);

        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode(anyString())).thenReturn("encoded");
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userUseCase.createEmployee(employee);

        assertEquals(Role.EMPLOYEE, result.getRole());
    }

    @Test
    void createClient_shouldCreateClientSuccessfully() {
        User client = new User();
        client.setName("Maria");
        client.setLastName("Garcia");
        client.setDocumentId("111222333");
        client.setPhone("+573009876543");
        client.setEmail("maria@example.com");
        client.setPassword("clientPass123");

        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPassword");
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setId(3L);
            return saved;
        });

        User result = userUseCase.createClient(client);

        assertNotNull(result.getId());
        assertEquals(Role.CLIENT, result.getRole());
        assertEquals("encodedPassword", result.getPassword());
        verify(userPersistencePort).saveUser(any(User.class));
    }

    @Test
    void createClient_shouldThrowWhenEmailAlreadyExists() {
        User client = new User();
        client.setName("Maria");
        client.setLastName("Garcia");
        client.setDocumentId("111222333");
        client.setPhone("+573009876543");
        client.setEmail("maria@example.com");
        client.setPassword("clientPass123");

        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.of(client));

        assertThrows(EmailAlreadyExistsException.class, () -> userUseCase.createClient(client));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void createClient_shouldThrowWhenDocumentAlreadyExists() {
        User client = new User();
        client.setName("Maria");
        client.setLastName("Garcia");
        client.setDocumentId("111222333");
        client.setPhone("+573009876543");
        client.setEmail("maria@example.com");
        client.setPassword("clientPass123");

        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.of(client));

        assertThrows(DocumentAlreadyExistsException.class, () -> userUseCase.createClient(client));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void createClient_shouldAlwaysSetRoleToClient() {
        User client = new User();
        client.setName("Maria");
        client.setLastName("Garcia");
        client.setDocumentId("111222333");
        client.setPhone("+573009876543");
        client.setEmail("maria@example.com");
        client.setPassword("clientPass123");
        client.setRole(Role.ADMIN);

        when(userPersistencePort.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userPersistencePort.findByDocumentId(anyString())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode(anyString())).thenReturn("encoded");
        when(userPersistencePort.saveUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userUseCase.createClient(client);

        assertEquals(Role.CLIENT, result.getRole());
    }
}
