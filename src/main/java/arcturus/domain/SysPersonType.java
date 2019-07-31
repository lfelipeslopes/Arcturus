package arcturus.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * Tipo de pessoa - Física/Jurídica
 */
@Entity
@Table(name = "sys_person_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysPersonType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_type_id")
    private Long personTypeId;

    @Column(name = "person_type_code")
    private String personTypeCode;

    @Column(name = "person_type_description")
    private String personTypeDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonTypeId() {
        return personTypeId;
    }

    public SysPersonType personTypeId(Long personTypeId) {
        this.personTypeId = personTypeId;
        return this;
    }

    public void setPersonTypeId(Long personTypeId) {
        this.personTypeId = personTypeId;
    }

    public String getPersonTypeCode() {
        return personTypeCode;
    }

    public SysPersonType personTypeCode(String personTypeCode) {
        this.personTypeCode = personTypeCode;
        return this;
    }

    public void setPersonTypeCode(String personTypeCode) {
        this.personTypeCode = personTypeCode;
    }

    public String getPersonTypeDescription() {
        return personTypeDescription;
    }

    public SysPersonType personTypeDescription(String personTypeDescription) {
        this.personTypeDescription = personTypeDescription;
        return this;
    }

    public void setPersonTypeDescription(String personTypeDescription) {
        this.personTypeDescription = personTypeDescription;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysPersonType)) {
            return false;
        }
        return id != null && id.equals(((SysPersonType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysPersonType{" +
            "id=" + getId() +
            ", personTypeId=" + getPersonTypeId() +
            ", personTypeCode='" + getPersonTypeCode() + "'" +
            ", personTypeDescription='" + getPersonTypeDescription() + "'" +
            "}";
    }
}
