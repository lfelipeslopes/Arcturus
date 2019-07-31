package arcturus.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * GroupOperator
 */
@Entity
@Table(name = "sys_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    private String status;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private SysPerson sysPerson;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sys_user_group_id",
               joinColumns = @JoinColumn(name = "sys_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "group_id_id", referencedColumnName = "id"))
    private Set<SysGroup> groupIds = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sys_user_enterprise_id",
               joinColumns = @JoinColumn(name = "sys_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "enterprise_id_id", referencedColumnName = "id"))
    private Set<SysEnterprise> enterpriseIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public SysUser userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public SysUser status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public SysUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SysPerson getSysPerson() {
        return sysPerson;
    }

    public SysUser sysPerson(SysPerson sysPerson) {
        this.sysPerson = sysPerson;
        return this;
    }

    public void setSysPerson(SysPerson sysPerson) {
        this.sysPerson = sysPerson;
    }

    public Set<SysGroup> getGroupIds() {
        return groupIds;
    }

    public SysUser groupIds(Set<SysGroup> sysGroups) {
        this.groupIds = sysGroups;
        return this;
    }

    public SysUser addGroupId(SysGroup sysGroup) {
        this.groupIds.add(sysGroup);
        sysGroup.getUserIds().add(this);
        return this;
    }

    public SysUser removeGroupId(SysGroup sysGroup) {
        this.groupIds.remove(sysGroup);
        sysGroup.getUserIds().remove(this);
        return this;
    }

    public void setGroupIds(Set<SysGroup> sysGroups) {
        this.groupIds = sysGroups;
    }

    public Set<SysEnterprise> getEnterpriseIds() {
        return enterpriseIds;
    }

    public SysUser enterpriseIds(Set<SysEnterprise> sysEnterprises) {
        this.enterpriseIds = sysEnterprises;
        return this;
    }

    public SysUser addEnterpriseId(SysEnterprise sysEnterprise) {
        this.enterpriseIds.add(sysEnterprise);
        sysEnterprise.getUserIds().add(this);
        return this;
    }

    public SysUser removeEnterpriseId(SysEnterprise sysEnterprise) {
        this.enterpriseIds.remove(sysEnterprise);
        sysEnterprise.getUserIds().remove(this);
        return this;
    }

    public void setEnterpriseIds(Set<SysEnterprise> sysEnterprises) {
        this.enterpriseIds = sysEnterprises;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysUser)) {
            return false;
        }
        return id != null && id.equals(((SysUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysUser{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", status='" + getStatus() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
