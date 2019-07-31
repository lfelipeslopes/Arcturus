package arcturus.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * License
 */
@Entity
@Table(name = "sys_license")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysLicense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_id")
    private Long licenseId;

    @Column(name = "status")
    private String status;

    @Column(name = "license_key")
    private String licenseKey;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "main_key")
    private String mainKey;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLicenseId() {
        return licenseId;
    }

    public SysLicense licenseId(Long licenseId) {
        this.licenseId = licenseId;
        return this;
    }

    public void setLicenseId(Long licenseId) {
        this.licenseId = licenseId;
    }

    public String getStatus() {
        return status;
    }

    public SysLicense status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public SysLicense licenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
        return this;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public SysLicense startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public SysLicense endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getMainKey() {
        return mainKey;
    }

    public SysLicense mainKey(String mainKey) {
        this.mainKey = mainKey;
        return this;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysLicense)) {
            return false;
        }
        return id != null && id.equals(((SysLicense) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysLicense{" +
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
