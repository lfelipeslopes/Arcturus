package arcturus.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link arcturus.domain.SysLicense} entity.
 */
@ApiModel(description = "License")
public class SysLicenseDTO implements Serializable {

    private Long id;

    private Long licenseId;

    private String status;

    private String licenseKey;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private String mainKey;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Long licenseId) {
        this.licenseId = licenseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysLicenseDTO sysLicenseDTO = (SysLicenseDTO) o;
        if (sysLicenseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysLicenseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysLicenseDTO{" +
            "id=" + getId() +
            ", licenseId=" + getLicenseId() +
            ", status='" + getStatus() + "'" +
            ", licenseKey='" + getLicenseKey() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", mainKey='" + getMainKey() + "'" +
            "}";
    }
}
