package arcturus.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link arcturus.domain.SysPerson} entity.
 */
public class SysPersonDTO implements Serializable {

    private Long id;

    private Long personId;

    private String personDescription;

    private String personContact;


    private Long sysPersonTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonDescription() {
        return personDescription;
    }

    public void setPersonDescription(String personDescription) {
        this.personDescription = personDescription;
    }

    public String getPersonContact() {
        return personContact;
    }

    public void setPersonContact(String personContact) {
        this.personContact = personContact;
    }

    public Long getSysPersonTypeId() {
        return sysPersonTypeId;
    }

    public void setSysPersonTypeId(Long sysPersonTypeId) {
        this.sysPersonTypeId = sysPersonTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysPersonDTO sysPersonDTO = (SysPersonDTO) o;
        if (sysPersonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysPersonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysPersonDTO{" +
            "id=" + getId() +
            ", personId=" + getPersonId() +
            ", personDescription='" + getPersonDescription() + "'" +
            ", personContact='" + getPersonContact() + "'" +
            ", sysPersonType=" + getSysPersonTypeId() +
            "}";
    }
}
