import play.*;
import play.libs.*;
import java.util.*;
import play.db.ebean.*;
import models.*;
import play.Configuration;

public class Global extends GlobalSettings {
    
    public void onStart( Application app ) {
        InitData.addRootUser();
    }
    
    private static class InitData {
        private static void addRootUser() {
            if (!User.exists("shenmideadminmeiyouchongming")) {
                User rootUser = new User("shenmideadminmeiyouchongming", "admin", "admin", "admin");
                User.create(rootUser);
            }
        }
    }
}
