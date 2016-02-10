package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({ @NamedQuery(name = "fetchAllCategories", query = "from Category"),
        @NamedQuery(name = "isCategoryExist", query = "select count(id) from Category where upper(name) = upper(:name) ") })
@Entity
@Table(name = "category")
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category extends BaseModel {

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    @Column(name = "name")
    public String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Expense> expenseList;

    public static List<Category> fetchCategories() {
        return JPA.em().createNamedQuery("fetchAllCategories").getResultList();
    }

    public static Category find(long id) {
        return JPA.em().find(Category.class, id);
    }

    public static boolean isCategoryExist(String name) {
        long count = (long) JPA.em().createNamedQuery("isCategoryExist").setParameter("name", name).getSingleResult();
        return count > 0 ? true : false;
    }

    public void save() {
        JPA.em().persist(this);
    }

}
