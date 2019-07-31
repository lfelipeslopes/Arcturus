package arcturus.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link arcturus.domain.SysUser} entity.
 */
@ApiModel(description = "GroupOperator")
public class SysUserDTO implements Serializable {

    private Long id;

    private Long userId;

    private String status;

    private String name;


    private Long sysPersonId;

    private Set<SysGroupDTO> groupIds = new HashSet<>();

    private Set<SysEnterpriseDTO> enterpriseIds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSysPersonId() {
        return sysPersonId;
    }

    public void setSysPersonId(Long sysPersonId) {
        this.sysPersonId = sysPersonId;
    }

    public Set<SysGroupDTO> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Set<SysGroupDTO> sysGroups) {
        this.groupIds = sysGroups;
    }

    public Set<SysEnterpriseDTO> getEnterpriseIds() {
        return enterpriseIds;
    }

    public void setEnterpriseIds(Set<SysEnterpriseDTO> sysEnterprises) {
        this.enterpriseIds = sysEnterprises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysUserDTO sysUserDTO = (SysUserDTO) o;
        if (sysUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysUserDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", status='" + getStatus() + "'" +
            ", name='" + getName() + "'" +
            ", sysPerson=" + getSysPersonId() +
            "}";
    }
}
