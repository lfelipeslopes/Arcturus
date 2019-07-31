package arcturus.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link arcturus.domain.SysGroup} entity.
 */
@ApiModel(description = "Group")
public class SysGroupDTO implements Serializable {

    private Long id;

    private Long groupId;

    private String status;

    private String group;


    private Set<SysIpGroupDTO> ipGroupIds = new HashSet<>();

    private Set<SysAccessDTO> accessIds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Set<SysIpGroupDTO> getIpGroupIds() {
        return ipGroupIds;
    }

    public void setIpGroupIds(Set<SysIpGroupDTO> sysIpGroups) {
        this.ipGroupIds = sysIpGroups;
    }

    public Set<SysAccessDTO> getAccessIds() {
        return accessIds;
    }

    public void setAccessIds(Set<SysAccessDTO> sysAccesses) {
        this.accessIds = sysAccesses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysGroupDTO sysGroupDTO = (SysGroupDTO) o;
        if (sysGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysGroupDTO{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", status='" + getStatus() + "'" +
            ", group='" + getGroup() + "'" +
            "}";
    }
}
