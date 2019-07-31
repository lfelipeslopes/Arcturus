package arcturus.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link arcturus.domain.SysEnterprise} entity.
 */
@ApiModel(description = "Enterprise")
public class SysEnterpriseDTO implements Serializable {

    private Long id;

    private Long enterpriseId;

    private String status;

    private String enterprise;

    private String alias;


    private Long sysLicenseId;

    private Set<SysGroupDTO> groupIds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getSysLicenseId() {
        return sysLicenseId;
    }

    public void setSysLicenseId(Long sysLicenseId) {
        this.sysLicenseId = sysLicenseId;
    }

    public Set<SysGroupDTO> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Set<SysGroupDTO> sysGroups) {
        this.groupIds = sysGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysEnterpriseDTO sysEnterpriseDTO = (SysEnterpriseDTO) o;
        if (sysEnterpriseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysEnterpriseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysEnterpriseDTO{" +
            "id=" + getId() +
            ", enterpriseId=" + getEnterpriseId() +
            ", status='" + getStatus() + "'" +
            ", enterprise='" + getEnterprise() + "'" +
            ", alias='" + getAlias() + "'" +
            ", sysLicense=" + getSysLicenseId() +
            "}";
    }
}
