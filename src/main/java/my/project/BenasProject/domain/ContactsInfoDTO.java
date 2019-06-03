package my.project.BenasProject.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.Annotation;


@Getter
@Setter
public class ContactsInfoDTO {

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