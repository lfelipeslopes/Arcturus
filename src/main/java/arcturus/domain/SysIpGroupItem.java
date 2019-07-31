package arcturus.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * IpGroupItem
 */
@Entity
@Table(name = "sys_ip_group_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysIpGroupItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_group_item_id")
    private Long ipGroupItemId;

    @Column(name = "initial_ip")
    private String initialIp;

    @Column(name = "final_ip")
    private String finalIp;

    @OneToMany(mappedBy = "sysIpGroupItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SysIpGroup> sysIpGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIpGroupItemId() {
        return ipGroupItemId;
    }

    public SysIpGroupItem ipGroupItemId(Long ipGroupItemId) {
        this.ipGroupItemId = ipGroupItemId;
        return this;
    }

    public void setIpGroupItemId(Long ipGroupItemId) {
        this.ipGroupItemId = ipGroupItemId;
    }

    public String getInitialIp() {
        return initialIp;
    }

    public SysIpGroupItem initialIp(String initialIp) {
        this.initialIp = initialIp;
        return this;
    }

    public void setInitialIp(String initialIp) {
        this.initialIp = initialIp;
    }

    public String getFinalIp() {
        return finalIp;
    }

    public SysIpGroupItem finalIp(String finalIp) {
        this.finalIp = finalIp;
        return this;
    }

    public void setFinalIp(String finalIp) {
        this.finalIp = finalIp;
    }

    public Set<SysIpGroup> getSysIpGroups() {
        return sysIpGroups;
    }

    public SysIpGroupItem sysIpGroups(Set<SysIpGroup> sysIpGroups) {
        this.sysIpGroups = sysIpGroups;
        return this;
    }

    public SysIpGroupItem addSysIpGroup(SysIpGroup sysIpGroup) {
        this.sysIpGroups.add(sysIpGroup);
        sysIpGroup.setSysIpGroupItem(this);
        return this;
    }

    public SysIpGroupItem removeSysIpGroup(SysIpGroup sysIpGroup) {
        this.sysIpGroups.remove(sysIpGroup);
        sysIpGroup.setSysIpGroupItem(null);
        return this;
    }

    public void setSysIpGroups(Set<SysIpGroup> sysIpGroups) {
        this.sysIpGroups = sysIpGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysIpGroupItem)) {
            return false;
        }
        return id != null && id.equals(((SysIpGroupItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysIpGroupItem{" +
            "id=" + getId() +
            ", ipGroupItemId=" + getIpGroupItemId() +
            ", initialIp='" + getInitialIp() + "'" +
            ", finalIp='" + getFinalIp() + "'" +
            "}";
    }
}
