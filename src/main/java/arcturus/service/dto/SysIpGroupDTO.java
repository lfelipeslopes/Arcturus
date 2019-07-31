package arcturus.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link arcturus.domain.SysIpGroup} entity.
 */
@ApiModel(description = "IpGroup")
public class SysIpGroupDTO implements Serializable {

    private Long id;

    private Long ipGroupId;


    private Long sysIpGroupItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIpGroupId() {
        return ipGroupId;
    }

    public void setIpGroupId(Long ipGroupId) {
        this.ipGroupId = ipGroupId;
    }

    public Long getSysIpGroupItemId() {
        return sysIpGroupItemId;
    }

    public void setSysIpGroupItemId(Long sysIpGroupItemId) {
        this.sysIpGroupItemId = sysIpGroupItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysIpGroupDTO sysIpGroupDTO = (SysIpGroupDTO) o;
        if (sysIpGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysIpGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysIpGroupDTO{" +
            "id=" + getId() +
            ", ipGroupId=" + getIpGroupId() +
            ", sysIpGroupItem=" + getSysIpGroupItemId() +
            "}";
    }
}
