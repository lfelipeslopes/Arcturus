package arcturus.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Group
 */
@Entity
@Table(name = "sys_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "status")
    private String status;

    @Column(name = "jhi_group")
    private String group;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sys_group_ip_group_id",
               joinColumns = @JoinColumn(name = "sys_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ip_group_id_id", referencedColumnName = "id"))
    private Set<SysIpGroup> ipGroupIds = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sys_group_access_id",
               joinColumns = @JoinColumn(name = "sys_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "access_id_id", referencedColumnName = "id"))
    private Set<SysAccess> accessIds = new HashSet<>();

    @ManyToMany(mappedBy = "groupIds")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<SysEnterprise> enterpriseIds = new HashSet<>();

    @ManyToMany(mappedBy = "groupIds")
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

    public Long getGroupId() {
        return groupId;
    }

    public SysGroup groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public SysGroup status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroup() {
        return group;
    }

    public SysGroup group(String group) {
        this.group = group;
        return this;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Set<SysIpGroup> getIpGroupIds() {
        return ipGroupIds;
    }

    public SysGroup ipGroupIds(Set<SysIpGroup> sysIpGroups) {
        this.ipGroupIds = sysIpGroups;
        return this;
    }

    public SysGroup addIpGroupId(SysIpGroup sysIpGroup) {
        this.ipGroupIds.add(sysIpGroup);
        sysIpGroup.getGroupIds().add(this);
        return this;
    }

    public SysGroup removeIpGroupId(SysIpGroup sysIpGroup) {
        this.ipGroupIds.remove(sysIpGroup);
        sysIpGroup.getGroupIds().remove(this);
        return this;
    }

    public void setIpGroupIds(Set<SysIpGroup> sysIpGroups) {
        this.ipGroupIds = sysIpGroups;
    }

    public Set<SysAccess> getAccessIds() {
        return accessIds;
    }

    public SysGroup accessIds(Set<SysAccess> sysAccesses) {
        this.accessIds = sysAccesses;
        return this;
    }

    public SysGroup addAccessId(SysAccess sysAccess) {
        this.accessIds.add(sysAccess);
        sysAccess.getGroupIds().add(this);
        return this;
    }

    public SysGroup removeAccessId(SysAccess sysAccess) {
        this.accessIds.remove(sysAccess);
        sysAccess.getGroupIds().remove(this);
        return this;
    }

    public void setAccessIds(Set<SysAccess> sysAccesses) {
        this.accessIds = sysAccesses;
    }

    public Set<SysEnterprise> getEnterpriseIds() {
        return enterpriseIds;
    }

    public SysGroup enterpriseIds(Set<SysEnterprise> sysEnterprises) {
        this.enterpriseIds = sysEnterprises;
        return this;
    }

    public SysGroup addEnterpriseId(SysEnterprise sysEnterprise) {
        this.enterpriseIds.add(sysEnterprise);
        sysEnterprise.getGroupIds().add(this);
        return this;
    }

    public SysGroup removeEnterpriseId(SysEnterprise sysEnterprise) {
        this.enterpriseIds.remove(sysEnterprise);
        sysEnterprise.getGroupIds().remove(this);
        return this;
    }

    public void setEnterpriseIds(Set<SysEnterprise> sysEnterprises) {
        this.enterpriseIds = sysEnterprises;
    }

    public Set<SysUser> getUserIds() {
        return userIds;
    }

    public SysGroup userIds(Set<SysUser> sysUsers) {
        this.userIds = sysUsers;
        return this;
    }

    public SysGroup addUserId(SysUser sysUser) {
        this.userIds.add(sysUser);
        sysUser.getGroupIds().add(this);
        return this;
    }

    public SysGroup removeUserId(SysUser sysUser) {
        this.userIds.remove(sysUser);
        sysUser.getGroupIds().remove(this);
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
        if (!(o instanceof SysGroup)) {
            return false;
        }
        return id != null && id.equals(((SysGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysGroup{" +
            "id=" + getId() +
            ", groupId=" + getGroupId() +
            ", status='" + getStatus() + "'" +
            ", group='" + getGroup() + "'" +
            "}";
    }
}
