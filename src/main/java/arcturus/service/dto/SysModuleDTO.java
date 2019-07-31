package arcturus.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link arcturus.domain.SysModule} entity.
 */
@ApiModel(description = "Module")
public class SysModuleDTO implements Serializable {

    private Long id;

    private Long moduleId;

    private String moduleCode;

    private String moduleDescription;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysModuleDTO sysModuleDTO = (SysModuleDTO) o;
        if (sysModuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysModuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysModuleDTO{" +
            "id=" + getId() +
            ", moduleId=" + getModuleId() +
            ", moduleCode='" + getModuleCode() + "'" +
            ", moduleDescription='" + getModuleDescription() + "'" +
            "}";
    }
}
