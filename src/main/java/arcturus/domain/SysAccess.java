package arcturus.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Access
 */
@Entity
@Table(name = "sys_access")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_id")
    private Long accessId;

    @Column(name = "status")
    private String status;

    @Column(name = "access")
    private String access;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private SysModule sysModule;

    @ManyToMany(mappedBy = "accessIds")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<SysGroup> groupIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccessId() {
        return accessId;
    }

    public SysAccess accessId(Long accessId) {
        this.accessId = accessId;
        return this;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public String getStatus() {
        return status;
    }

    public SysAccess status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccess() {
        return access;
    }

    public SysAccess access(String access) {
        this.access = access;
        return this;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getDescription() {
        return description;
    }

    public SysAccess description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SysModule getSysModule() {
        return sysModule;
    }

    public SysAccess sysModule(SysModule sysModule) {
        this.sysModule = sysModule;
        return this;
    }

    public void setSysModule(SysModule sysModule) {
        this.sysModule = sysModule;
    }

    public Set<SysGroup> getGroupIds() {
        return groupIds;
    }

    public SysAccess groupIds(Set<SysGroup> sysGroups) {
        this.groupIds = sysGroups;
        return this;
    }

    public SysAccess addGroupId(SysGroup sysGroup) {
        this.groupIds.add(sysGroup);
        sysGroup.getAccessIds().add(this);
        return this;
    }

    public SysAccess removeGroupId(SysGroup sysGroup) {
        this.groupIds.remove(sysGroup);
        sysGroup.getAccessIds().remove(this);
        return this;
    }

    public void setGroupIds(Set<SysGroup> sysGroups) {
        this.groupIds = sysGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysAccess)) {
            return false;
        }
        return id != null && id.equals(((SysAccess) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysAccess{" +
            "id=" + getId() +
            ", accessId=" + getAccessId() +
            ", status='" + getStatus() + "'" +
            ", access='" + getAccess() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
