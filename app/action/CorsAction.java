package action;

import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Response;
import play.mvc.Result;

public class CorsAction extends Action.Simple {

    public Promise<Result> call(Context context) throws Throwable {
        Response response = context.response();
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Handle preflight requests
        if (context.request().method().equals("OPTIONS")) {
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
            response.setHeader("Access-Control-Allow-Headers",
                    "Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Auth-Token");
        }
        return delegate.call(context);
    }

}