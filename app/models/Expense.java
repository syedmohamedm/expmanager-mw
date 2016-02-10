package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.jpa.JPA;

@NamedQueries({ @NamedQuery(name = "Expense.fetchAllExpense", query = "from Expense") })
@Entity
@Table(name = "expense")
public class Expense extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;

    @Column(name = "amount")
    public long amount;

    @Column(name = "date")
    public Date date;

    @Column(name = "note")
    public String note;

    @Transient
    public double amountInPercentage;

    public Expense() {

    }

    public Expense(String categoryName, long amountInPercentage) {
        this.amount = amountInPercentage;
        this.category = new Category(categoryName);
    }

    public static List<Expense> fetchExpenses() {
        return JPA.em().createNamedQuery("Expense.fetchAllExpense", Expense.class).getResultList();
    }

    public void save() {
        if (this.id == null)
            JPA.em().persist(this);
        else
            JPA.em().merge(this);
    }

    public static Expense fetchExpense(Long id) {
        return JPA.em().find(Expense.class, id);
    }

    public static void deleteExpense(Long id) {
        JPA.em().createNativeQuery("delete from expense where id = " + id).executeUpdate();
    }

    public static List<Expense> fetchExpensesForPieChart() {
        return JPA
                .em()
                .createQuery(
                        "select new Expense(c.name,(sum(e.amount)/(select sum(amount) from Expense) * 100)) from Expense e join e.category c group by c.name",
                        Expense.class).getResultList();
    }

    public static List<Object> fetchExpensesForBarChart() {
        return JPA
                .em()
                .createNativeQuery(
                        "select initcap(to_char(date,'month')), sum(amount) from expense where date_part('year',date) = date_part('year',now()) group by to_char(date,'month'), date_part('month',date) order by date_part('month',date)")
                        .getResultList();
    }

    @Override
    public String toString() {
        return "Expense [ amount=" + amount + ", date=" + date + ", note=" + note + "]";
    }

}
