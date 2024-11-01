package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    private final User userTest = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
    private final UserDto userDtoTest = new UserDto(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");


    @Test
    void toDto_shouldMapUserToUserDto() {
        UserDto userDto = userMapper.toDto(userTest);

        assertNotNull(userDto);
        assertEquals(userTest.getId(), userDto.Id());
        assertEquals(userTest.getFirstName(), userDto.firstName());
        assertEquals(userTest.getLastName(), userDto.lastName());
        assertEquals(userTest.getBirthdate(), userDto.birthdate());
        assertEquals(userTest.getEmail(), userDto.email());
    }

    @Test
    void toSimpleDto_shouldMapUserToUserSimpleDto() {
        UserSimpleDto userSimpleDto = userMapper.toSimpleDto(userTest);

        assertNotNull(userSimpleDto);
        assertEquals(userTest.getId(), userSimpleDto.id());
        assertEquals(userTest.getFirstName(), userSimpleDto.firstName());
        assertEquals(userTest.getLastName(), userSimpleDto.lastName());
    }

    @Test
    void toEntity_shouldMapUserDtoToUser() {
        User user = userMapper.toEntity(userDtoTest);

        assertNotNull(user);
        assertEquals(userDtoTest.firstName(), user.getFirstName());
        assertEquals(userDtoTest.lastName(), user.getLastName());
        assertEquals(userDtoTest.birthdate(), user.getBirthdate());
        assertEquals(userDtoTest.email(), user.getEmail());
    }

    @Test
    void toFindByEmailDto_shouldMapUserToUserFindByEmailDto() {
        UserFindByEmailDto userFindByEmailDto = userMapper.toFindByEmailDto(userTest);

        assertNotNull(userFindByEmailDto);
        assertEquals(userTest.getId(), userFindByEmailDto.id());
        assertEquals(userTest.getEmail(), userFindByEmailDto.email());
    }
}