package models;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.*;
import play.data.format.*;

@Entity
@Table(name = "todo_post")
public class Post extends Model {
    
    @Id
    @GeneratedValue
    public Long postid;

    @Required
    @Column(columnDefinition = "TEXT", length=10000)
    public String content;
    
    
    @Formats.DateTime(pattern="MM/dd/yy")
    @Column(name = "created_at")
    public Date createdAt;
   
    @ManyToOne
    @Column(name = "userid")
    public Long userid;
    
    // end of field definition
    
    //

    @Override
    public void save() {
        createdAt();
        super.save();
    }
    
    @PrePersist
    private void createdAt() {
        this.createdAt = new Date();
    }

    public static String formatDate(Date d, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(d);
    }
    
    public String getCreatedAt() {
        return User.formatDate(this.createdAt, "MMMM dd yyyy HH:mm:ss");
    }
    
    public static Finder<Long, Post> find = new Finder<Long, Post>(Long.class, Post.class);
    
    public static List<Post> all() {
        return find.all();
    }
    
    public static void create(Post post) {
        post.save();
    }
    
    public static void delete(Long postid) {
        find.ref(postid).delete();
    }
    
    public static List<Post> findByUser(Long userid) {
        if (userid == null) {
            return null; 
        } else {
            return find.where().eq("userid", userid).findList();
        }
    }
}