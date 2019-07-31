package arcturus.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * Module
 */
@Entity
@Table(name = "sys_module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_id")
    private Long moduleId;

    @Column(name = "module_code")
    private String moduleCode;

    @Column(name = "module_description")
    private String moduleDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public SysModule moduleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public SysModule moduleCode(String moduleCode) {
        this.moduleCode = moduleCode;
        return this;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public SysModule moduleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
        return this;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysModule)) {
            return false;
        }
        return id != null && id.equals(((SysModule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysModule{" +
            "id=" + getId() +
            ", moduleId=" + getModuleId() +
            ", moduleCode='" + getModuleCode() + "'" +
            ", moduleDescription='" + getModuleDescription() + "'" +
            "}";
    }
}
