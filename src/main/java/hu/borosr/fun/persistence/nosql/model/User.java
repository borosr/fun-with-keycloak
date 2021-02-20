package hu.borosr.fun.persistence.nosql.model;

import hu.borosr.fun.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String username;
    private String fullName;

    public static User fromDto(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .fullName(userDTO.getFullName())
                .build();
    }

    public UserDTO toDto() {
        return UserDTO.builder()
                .id(id)
                .username(username)
                .fullName(fullName)
                .build();
    }
}
