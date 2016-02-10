package controllers;

import models.Category;
import models.Expense;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class ExpenseResource extends Controller {

    @Transactional(readOnly = true)
    public Result list() {
        return ok(Json.toJson(Expense.fetchExpenses()));
    }

    @Transactional
    public Result add() {
        Form<Expense> editForm = Form.form(Expense.class).bindFromRequest();
        Expense expense = editForm.get();
        expense.category = Category.find(expense.category.id);
        expense.save();
        return ok(Json.toJson("Success"));
    }

    @Transactional(readOnly = true)
    public Result find(Long id) {
        return ok(Json.toJson(Expense.fetchExpense(id)));
    }

    @Transactional()
    public Result delete(Long id) {
        Expense.deleteExpense(id);
        return ok();
    }

    @Transactional(readOnly = true)
    public Result listForPieChart() {
        return ok(Json.toJson(Expense.fetchExpensesForPieChart()));
    }

    @Transactional(readOnly = true)
    public Result listForBarChart() {
        return ok(Json.toJson(Expense.fetchExpensesForBarChart()));
    }

}
