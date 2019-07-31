package arcturus.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SysPerson.
 */
@Entity
@Table(name = "sys_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "person_description")
    private String personDescription;

    @Column(name = "person_contact")
    private String personContact;

    @ManyToOne
    @JsonIgnoreProperties("sysPeople")
    private SysPersonType sysPersonType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public SysPerson personId(Long personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonDescription() {
        return personDescription;
    }

    public SysPerson personDescription(String personDescription) {
        this.personDescription = personDescription;
        return this;
    }

    public void setPersonDescription(String personDescription) {
        this.personDescription = personDescription;
    }

    public String getPersonContact() {
        return personContact;
    }

    public SysPerson personContact(String personContact) {
        this.personContact = personContact;
        return this;
    }

    public void setPersonContact(String personContact) {
        this.personContact = personContact;
    }

    public SysPersonType getSysPersonType() {
        return sysPersonType;
    }

    public SysPerson sysPersonType(SysPersonType sysPersonType) {
        this.sysPersonType = sysPersonType;
        return this;
    }

    public void setSysPersonType(SysPersonType sysPersonType) {
        this.sysPersonType = sysPersonType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysPerson)) {
            return false;
        }
        return id != null && id.equals(((SysPerson) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SysPerson{" +
            "id=" + getId() +
            ", personId=" + getPersonId() +
            ", personDescription='" + getPersonDescription() + "'" +
            ", personContact='" + getPersonContact() + "'" +
            "}";
    }
}
