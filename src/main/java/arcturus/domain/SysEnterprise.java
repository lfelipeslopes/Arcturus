package arcturus.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Enterprise
 */
@Entity
@Table(name = "sys_enterprise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enterprise_id")
    private Long enterpriseId;

    @Column(name = "status")
    private String status;

    @Column(name = "enterprise")
    private String enterprise;

    @Column(name = "alias")
    private String alias;

    @OneToOne
    @JoinColumn(unique = true)
    private SysLicense sysLicense;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sys_enterprise_group_id",
               joinColumns = @JoinColumn(name = "sys_enterprise_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "group_id_id", referencedColumnName = "id"))
    private Set<SysGroup> groupIds = new HashSet<>();

    @ManyToMany(mappedBy = "enterpriseIds")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<SysUser> userIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public SysEnterprise enterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
        return this;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getStatus() {
        return status;
    }

    public SysEnterprise status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public SysEnterprise enterprise(String enterprise) {
        this.enterprise = enterprise;
        return this;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getAlias() {
        return alias;
    }

    public SysEnterprise alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public SysLicense getSysLicense() {
        return sysLicense;
    }

    public SysEnterprise sysLicense(SysLicense sysLicense) {
        this.sysLicense = sysLicense;
        return this;
    }

    public void setSysLicense(SysLicense sysLicense) {
        this.sysLicense = sysLicense;
    }

    public Set<SysGroup> getGroupIds() {
        return groupIds;
    }

    public SysEnterprise groupIds(Set<SysGroup> sysGroups) {
        this.groupIds = sysGroups;
        return this;
    }

    public SysEnterprise addGroupId(SysGroup sysGroup) {
        this.groupIds.add(sysGroup);
        sysGroup.getEnterpriseIds().add(this);
        return this;
    }

    public SysEnterprise removeGroupId(SysGroup sysGroup) {
        this.groupIds.remove(sysGroup);
        sysGroup.getEnterpriseIds().remove(this);
        return this;
    }

    public void setGroupIds(Set<SysGroup> sysGroups) {
        this.groupIds = sysGroups;
    }

    public Set<SysUser> getUserIds() {
        return userIds;
    }

    public SysEnterprise userIds(Set<SysUser> sysUsers) {
        this.userIds = sysUsers;
        return this;
    }

    public SysEnterprise addUserId(SysUser sysUser) {
        this.userIds.add(sysUser);
        sysUser.getEnterpriseIds().add(this);
        return this;
    }

    public SysEnterprise removeUserId(SysUser sysUser) {
        this.userIds.remove(sysUser);
        sysUser.getEnterpriseIds().remove(this);
        return this;
    }

    public void setUserIds(Set<SysUser> sysUsers) {
        this.userIds = sysUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysEnterprise)) {
            return false;
        }
        return id != null && id.equals(((SysEnterprise) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysEnterprise{" +
            "id=" + getId() +
            ", enterpriseId=" + getEnterpriseId() +
            ", status='" + getStatus() + "'" +
            ", enterprise='" + getEnterprise() + "'" +
            ", alias='" + getAlias() + "'" +
            "}";
    }
}
