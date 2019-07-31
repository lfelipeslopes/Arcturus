package arcturus.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link arcturus.domain.SysAccess} entity.
 */
@ApiModel(description = "Access")
public class SysAccessDTO implements Serializable {

    private Long id;

    private Long accessId;

    private String status;

    private String access;

    private String description;


    private Long sysModuleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSysModuleId() {
        return sysModuleId;
    }

    public void setSysModuleId(Long sysModuleId) {
        this.sysModuleId = sysModuleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysAccessDTO sysAccessDTO = (SysAccessDTO) o;
        if (sysAccessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysAccessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysAccessDTO{" +
            "id=" + getId() +
            ", accessId=" + getAccessId() +
            ", status='" + getStatus() + "'" +
            ", access='" + getAccess() + "'" +
            ", description='" + getDescription() + "'" +
            ", sysModule=" + getSysModuleId() +
            "}";
    }
}
