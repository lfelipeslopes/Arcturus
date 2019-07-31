package arcturus.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link arcturus.domain.SysPersonType} entity.
 */
@ApiModel(description = "Tipo de pessoa - Física/Jurídica")
public class SysPersonTypeDTO implements Serializable {

    private Long id;

    private Long personTypeId;

    private String personTypeCode;

    private String personTypeDescription;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonTypeId() {
        return personTypeId;
    }

    public void setPersonTypeId(Long personTypeId) {
        this.personTypeId = personTypeId;
    }

    public String getPersonTypeCode() {
        return personTypeCode;
    }

    public void setPersonTypeCode(String personTypeCode) {
        this.personTypeCode = personTypeCode;
    }

    public String getPersonTypeDescription() {
        return personTypeDescription;
    }

    public void setPersonTypeDescription(String personTypeDescription) {
        this.personTypeDescription = personTypeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysPersonTypeDTO sysPersonTypeDTO = (SysPersonTypeDTO) o;
        if (sysPersonTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysPersonTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysPersonTypeDTO{" +
            "id=" + getId() +
            ", personTypeId=" + getPersonTypeId() +
            ", personTypeCode='" + getPersonTypeCode() + "'" +
            ", personTypeDescription='" + getPersonTypeDescription() + "'" +
            "}";
    }
}
