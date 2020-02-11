package my.project.BenasProject.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ContactsInfo implements Serializable {

    @NotNull
    @XmlElement
    public String name;

    @NotNull
    @XmlElement
    public String company;

    @NotNull
    @XmlElement
    public Integer phone;

    @Nullable
    @XmlElement
    public String empty;

    @Nullable
    @XmlElement
    public String filler;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(@Nullable String empty) {
        this.empty = empty;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(@Nullable String filler) {
        this.filler = filler;
    }
}
