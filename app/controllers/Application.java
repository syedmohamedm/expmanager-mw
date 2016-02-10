package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import action.CorsAction;

@With(CorsAction.class)
public class Application extends Controller {

    public Result index(String name) {
        return ok("Hello" + name);
    }

    public Result checkPreFlight() {
        return ok();
    }
}
