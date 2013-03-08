package models;

import java.text.SimpleDateFormat;
import play.*;
import java.util.*;

import javax.persistence.*;

import play.data.format.Formats;
import play.data.validation.Constraints.*;
import play.db.ebean.*;

@Entity
@Table(name = "todo_user")
public class User extends Model {
    
    @Id
    @GeneratedValue
    public Long userid;
    
    @Required
    @MinLength(3)
    @Column(unique=true)
    public String username;
    
    @Required
    @MinLength(3)
    public String password;
    
    public int privilege;
    
    public Date since;
    
    public String firstname;
    
    public String lastname;
    
    public String twitterId;
    
    // JPA methods
    @Override
    public void save() {
        since();
        super.save();
    }
    
    @PrePersist
    private void since() {
        this.since = new Date();
    }
    
    // end of field definition
    
    // standard instance methods
    // constructor
    public User(String u, String p, String l, String f) {
        username = u;
        password = p;
        lastname = l;
        firstname = f;
    }
    
    public static String formatDate(Date d, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(d);
    }
    
    public String getSince() {
        return User.formatDate(this.since, "MMMM dd yyyy");
    }
    
    // class methods 
    
    public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
    
    public static boolean exists(String username) {
        return (find.where().eq("username", username).findRowCount() >= 1);
    }
    
    public static List<User> all() {
        return find.all();
    }
    
    
    public static void create(User user) {
        user.save();
    }
    
    public static void delete(Long userid) {
        find.ref(userid).delete();
    }
    
    public static Long auth(String username, String password) {
        List<Object> ids = find.where().eq("username", username).eq("password", password).findIds();
        if (ids.size() == 1) {
            return (Long)ids.get(0);
        } else {
            return null;
        }
    }
    
    
}