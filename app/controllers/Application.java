package controllers;

import play.*;
import play.data.Form;
import play.mvc.*;

import models.*;
import views.html.*;

public class Application extends Controller {
  
  // for login
  static Form<User> loginForm = form(User.class);

  
  public static Result index() {
    return ok(index.render(Post.all()));
  }
  
  public static Result loginPage() {
    return ok(login.render(loginForm));
  }
  
  public static Result login() {
      Form<User> filledLoginForm = loginForm.bindFromRequest();
      String t_username = filledLoginForm.field("username").value();
      String t_password = filledLoginForm.field("password").value();
      
      Long userid = User.auth(t_username, t_password);
      if ( userid == null ) {
          // auth failed
          filledLoginForm.reject("username", "Username donsn't exist");
      }
      
      if (filledLoginForm.hasErrors()) {
          flash("info", "form error");
          return badRequest( login.render(filledLoginForm) );
      } else {
          // login success
          session().clear();
          session("username", t_username);
          session("userid", userid.toString());
          flash("info", "login success");
          return redirect(routes.Application.index());
      }   
  }
  
  public static Result logout() {
      session().clear();
      flash("info", "logout success");
      return redirect(routes.Application.index());
  }
  
  public static Result manage() {
      // 1. session could only store string, 
      // 2. some db id might start from 0
      
      if (session("userid").equals("1") || session("userid").equals("0") ) {
          return ok( manage.render(User.all(), Post.all()) ); 
      } else {
          flash("info", "forbidden");
          return redirect(routes.Application.index());
      }
  }
}
