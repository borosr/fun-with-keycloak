package hu.borosr.fun.persistence.sql.entity;

import hu.borosr.fun.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(unique = true)
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
