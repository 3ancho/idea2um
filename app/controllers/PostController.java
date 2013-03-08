package controllers;

import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import models.*;

public class PostController extends Controller {
  
  static Form<Post> postForm = form(Post.class);
  
  public static Result posts() {
      String loggedin = session("userid");
      if ( loggedin == null) {
          return ok( posts.render(Post.all(), null));
      } else {
          Long userid = Long.parseLong( session("userid") );
          return ok( posts.render(Post.findByUser(userid), postForm) );
      }
  }  
  
  public static Result post(Long id) {
      return TODO;
  }
  
  
  public static Result addPost() {
      Form<Post> filledForm = postForm.bindFromRequest();
      if (filledForm.hasErrors()) {
          return badRequest( posts.render(Post.all(), filledForm) );
      } else {
          Post a_post = filledForm.get();
          a_post.userid = Long.parseLong(session("userid"));
          Post.create(a_post);
          return redirect(routes.PostController.posts());
      }   
  }
  
  // TODO ajax
  public static Result delPost(Long id) {
    Post.delete(id);
    return redirect(routes.PostController.posts());
  }
  
  // TODO ajax
  public static Result editPost() {
    return TODO;
  }
}
