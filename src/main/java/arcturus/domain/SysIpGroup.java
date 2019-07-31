package arcturus.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * IpGroup
 */
@Entity
@Table(name = "sys_ip_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysIpGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_group_id")
    private Long ipGroupId;

    @ManyToMany(mappedBy = "ipGroupIds")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<SysGroup> groupIds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("sysIpGroups")
    private SysIpGroupItem sysIpGroupItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIpGroupId() {
        return ipGroupId;
    }

    public SysIpGroup ipGroupId(Long ipGroupId) {
        this.ipGroupId = ipGroupId;
        return this;
    }

    public void setIpGroupId(Long ipGroupId) {
        this.ipGroupId = ipGroupId;
    }

    public Set<SysGroup> getGroupIds() {
        return groupIds;
    }

    public SysIpGroup groupIds(Set<SysGroup> sysGroups) {
        this.groupIds = sysGroups;
        return this;
    }

    public SysIpGroup addGroupId(SysGroup sysGroup) {
        this.groupIds.add(sysGroup);
        sysGroup.getIpGroupIds().add(this);
        return this;
    }

    public SysIpGroup removeGroupId(SysGroup sysGroup) {
        this.groupIds.remove(sysGroup);
        sysGroup.getIpGroupIds().remove(this);
        return this;
    }

    public void setGroupIds(Set<SysGroup> sysGroups) {
        this.groupIds = sysGroups;
    }

    public SysIpGroupItem getSysIpGroupItem() {
        return sysIpGroupItem;
    }

    public SysIpGroup sysIpGroupItem(SysIpGroupItem sysIpGroupItem) {
        this.sysIpGroupItem = sysIpGroupItem;
        return this;
    }

    public void setSysIpGroupItem(SysIpGroupItem sysIpGroupItem) {
        this.sysIpGroupItem = sysIpGroupItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysIpGroup)) {
            return false;
        }
        return id != null && id.equals(((SysIpGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysIpGroup{" +
            "id=" + getId() +
            ", ipGroupId=" + getIpGroupId() +
            "}";
    }
}
