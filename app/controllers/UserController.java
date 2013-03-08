package controllers;

import java.util.List;

import play.*;
import play.libs.*;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import models.*;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import twitter4j.TwitterException;

public class UserController extends Controller {
  
  static Form<User> userForm = form(User.class);
  static Long store_userid;
  
  public static Result user(Long userid) {
      
      if ( User.find.ref(userid) == null) {
          flash("info", "No such user found");
          return redirect(routes.UserController.users());
      }
      
      store_userid = Long.parseLong(session("userid"));
     
      String at = "82902347-laqCBg4D2kuy4YsSORUYUB07Q7qKWLp1K4SnTiiBU";
      String ats = "V59xqrwMv17s4ZNdPDSwyk14MciRBsrGOV3hlCVFQ";
      
      String ck = "HGF8tM2Eef8gsLEw9yQYQ";
      String cks = "1Zpvv9oRIZLtkmlHRIUEi3GDUEFnE7YxqNIRT9OmI";
      
      AccessToken accessToken = new AccessToken(at, ats);
      
      Twitter twitter = TwitterFactory.getSingleton();
      
      try { 
          twitter.setOAuthConsumer(ck, cks); 
      } catch (IllegalStateException e) {
          
      }
      
      twitter.setOAuthAccessToken(accessToken);
      
      List<twitter4j.Status> statuses;
      try {
          statuses = twitter.getUserTimeline(session("username"));
      } catch (TwitterException e) {
          statuses = null;
          Logger.info("user doensn't have twitter"); 
      }
      
      return ok( user.render(User.find.byId(store_userid), statuses ) );
      
//      return async(
//              WS.url(api_call).get().map(
//                      new Function<WS.Response, Result>() {
//                          public Result apply(WS.Response response) {
//                              return ok( user.render(User.find.byId(store_userid), response.asJson().toString() ) );
//                          }
//                      }
//              )
//      );
      
  }
  
  public static Result users() {
      return ok( users.render(User.all()) );
  }
  
  public static Result addUserPage() {
      return ok( add_user.render(userForm) );
  }
  
  
  public static Result addUser() {
      Form<User> filledForm = userForm.bindFromRequest();
      
      if ( User.exists(filledForm.field("username").value()) ){
          filledForm.reject("username", "Username exists");
      } 
      
      // filledForm populated with validation info
      if (filledForm.hasErrors()) {
          return badRequest( add_user.render(filledForm) );
      } else {
          // singUp success ! 
          // add session
          User a_user = filledForm.get();
          User.create(a_user);
          session("username", a_user.username );
          session("userid", a_user.userid.toString() );
          
          flash("info", "Sign up success!");
          // show index page
          return redirect(routes.Application.index());
      }
  }
  
  public static Result delUser(Long id) {
    Long userid = Long.parseLong(session("userid"));
    if ( (userid == 0 || userid == 1) || userid == id ) {
        User.delete(id);
        return redirect(routes.Application.manage());
    } else {
        flash("info", "forbidden");
        return redirect(routes.Application.index());
    }
  }
  
  public static Result editUser() {
    return TODO;
  }
  
}
