import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.users.UserXtrCounters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VKAPIController {

    private final static String CONFIG_FILE = "config/db.config";

    private int clientID;
    private String protectedKey;
    private String host;
    private String port;
    private final VKScope scope;
    private final VkApiClient vk;
    private String token;
    private int userID;
    private UserActor actor;

    public VKAPIController(VKScope scope, VkApiClient vk) {
        this.scope = scope;
        this.vk = vk;
        readConfig();
    }

    public int getClientID() {
        return clientID;
    }

    public String getProtectedKey() {
        return protectedKey;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public UserActor getActor() {
        return actor;
    }

    public int getUserID() {
        return userID;
    }

    public void createActor(int userId) {
        actor = new UserActor(userId, token);
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getOAuthUrl() {
        return "https://oauth.vk.com/authorize?client_id="
                    + clientID
                    + "&display=page&redirect_uri="
                    + getRedirectUri()
                    + "&scope=" + scope.toString()
                    + "&response_type=code";
    }

    public String getRedirectUri() {
        return "http://" + host + ":" + port + "/callback";
    }

    private void readConfig() {
        Properties prop = new Properties();
        try {
            InputStream is = new FileInputStream(CONFIG_FILE);
            try {
                prop.load(is);
                clientID = Integer.parseInt(prop.getProperty("db.clientID"));
                protectedKey = prop.getProperty("db.protectedKey");
                host = prop.getProperty("db.host");
                port = prop.getProperty("db.port");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find configuration file " + CONFIG_FILE);
        }
    }

}