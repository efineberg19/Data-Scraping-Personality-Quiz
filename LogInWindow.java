import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * I was planning on creating this login window for a user to grant permissions.
 * But, I couldn't figure out how to get the user's access token, so I did not
 * end up using this. I got my personal access token from Facebook's developer
 * tools.
 * 
 * I got most of this code from an example on the Oracle website. I read it 
 * through and pretty much know how it works. I probably could have figured 
 * this out better, but I decided to put my research efforts into other things.
 *
 * @author Beth Fineberg
 * @version 1.0
 */

public class LogInWindow extends Application {
    public static void main(String[] args) 
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception 
    {
        WebView myWebView = new WebView();
        WebEngine engine = myWebView.getEngine();

        //redirect URL after successful login
        String domain = "https://www.facebook.com/connect/login_success.html";

        String appID = "2101481206748707";
        //something about cross-site forgery that I don't understand
        String idk = "123abc,ds=123456789";

        //shows login and will grant the specified permissions after scope
        String authURL = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appID + "&redirect_uri=" + domain + "&scope="
            + "user_birthday,user_events,user_photos,user_friends,user_hometown,user_likes,user_location,user_photos,"
            + "user_status,user_tagged_places,user_videos,ads_management,ads_read,email,"
            + "read_insights,";
        
        engine.load(authURL);
        
        //not 100% on what this stuff does, but it works
        VBox root = new VBox();

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);

        stage.show();
    }
}