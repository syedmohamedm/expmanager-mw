package controllers;

import models.Category;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class CategoryResource extends Controller {

    @Transactional(readOnly = true)
    public Result list() {
        return ok(Json.toJson(Category.fetchCategories()));
    }

    @Transactional
    public Result add(String name) {
        boolean isCategoryExist = Category.isCategoryExist(name);
        if (!isCategoryExist) {
            Category category = new Category(name);
            category.save();
        } else {
            return internalServerError("Category already exist. Try another.");
        }
        return ok();
    }

}
