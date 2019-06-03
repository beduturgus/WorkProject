package my.project.BenasProject.domain;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private String name;

    @NotNull
    private String company;

    @NotNull
    private String phone;

    @Nullable
    private String empty;

    @Nullable
    private String filler;
}
