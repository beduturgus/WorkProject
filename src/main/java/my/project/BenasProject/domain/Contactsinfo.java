package my.project.BenasProject.domain;

import org.springframework.lang.Nullable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contactsinfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private String name;

    @NotNull
    private  String company;

    @NotNull
    private Integer phone;

    @Nullable
    private String empty;

    @Nullable
    private String filler;


    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @XmlElement
    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    @XmlElement
    @Nullable
    public String getEmpty() {
        return empty;
    }

    public void setEmpty(@Nullable String empty) {
        this.empty = empty;
    }

    @XmlElement
    @Nullable
    public String getFiller() {
        return filler;
    }

    public void setFiller(@Nullable String filler) {
        this.filler = filler;
    }

    public void setValueByFieldName(String fieldName, String newValue){
        switch (fieldName){
            case "name":
                setName(newValue);
                break;
            case "company":
                setCompany(newValue);
                break;
            case "phone":
                setPhone(5);
                break;
            case "empty":
                setEmpty(newValue);
                break;
            case "filler":
                setFiller(newValue);
                break;
        }
    }

}
