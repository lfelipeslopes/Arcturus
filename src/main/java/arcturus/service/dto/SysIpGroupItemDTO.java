package arcturus.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link arcturus.domain.SysIpGroupItem} entity.
 */
@ApiModel(description = "IpGroupItem")
public class SysIpGroupItemDTO implements Serializable {

    private Long id;

    private Long ipGroupItemId;

    private String initialIp;

    private String finalIp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIpGroupItemId() {
        return ipGroupItemId;
    }

    public void setIpGroupItemId(Long ipGroupItemId) {
        this.ipGroupItemId = ipGroupItemId;
    }

    public String getInitialIp() {
        return initialIp;
    }

    public void setInitialIp(String initialIp) {
        this.initialIp = initialIp;
    }

    public String getFinalIp() {
        return finalIp;
    }

    public void setFinalIp(String finalIp) {
        this.finalIp = finalIp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysIpGroupItemDTO sysIpGroupItemDTO = (SysIpGroupItemDTO) o;
        if (sysIpGroupItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysIpGroupItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysIpGroupItemDTO{" +
            "id=" + getId() +
            ", ipGroupItemId=" + getIpGroupItemId() +
            ", initialIp='" + getInitialIp() + "'" +
            ", finalIp='" + getFinalIp() + "'" +
            "}";
    }
}
