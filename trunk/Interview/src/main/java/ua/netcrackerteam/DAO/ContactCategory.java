package ua.netcrackerteam.DAO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author
 */
@Entity
@Table(name="CONTACT_CATEGORY")
public class ContactCategory implements Serializable {
    private static final long serialVersionUID = -3235404536546181451L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "contact_category_seq_gen")
    @SequenceGenerator(name = "contact_category_seq_gen", sequenceName = "contact_category_seq")
    @Column(name= "ID_CONTACT_CATEGORY")
    private Long idContactCategory;

    @Column(name= "CATEGORY")
    private String category;

    @OneToMany(mappedBy= "contactCategory", fetch = FetchType.EAGER )
    private Set<Contact> contacts;      

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public ContactCategory() {
    }

    public Long getIdContactCategory() {
        return idContactCategory;
    }

    public void setIdContactCategory(Long idContactCategory) {
        this.idContactCategory = idContactCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
