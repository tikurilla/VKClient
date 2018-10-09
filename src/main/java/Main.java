import static spark.Spark.*;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.objects.wall.WallPostFull;
import java.text.SimpleDateFormat;
import com.vk.api.sdk.exceptions.ApiAccessException;

import com.vk.api.sdk.objects.wall.responses.PostResponse;
import spark.ModelAndView;


import java.util.*;

public class Main {

    static final int MIN_POSTS_AMOUNT = 10;

    static final int MAX_POSTS_AMOUNT = 40;

    public static void main(String[] args) {
        VkApiClient vk = new VkApiClient(new HttpTransportClient());
        VKAPIController vkApiController = new VKAPIController(VKScope.wall, vk);
        // Logging process
        get("/login", (request, response) -> {
            response.redirect(vkApiController.getOAuthUrl());
            return null;
        });
        get("/callback", ((request, response) -> {
            UserAuthResponse authResponse = vk.oauth().userAuthorizationCodeFlow(vkApiController.getClientID(),
                                                                                 vkApiController.getProtectedKey(),
                                                                                 vkApiController.getRedirectUri(),
                                                                                 request.queryParams("code")).execute();
            vkApiController.setToken(authResponse.getAccessToken());
            vkApiController.setUserID(authResponse.getUserId());
            // redirecting to the wall page with minimal post amount
            response.redirect("/wall/" + Integer.toString(MIN_POSTS_AMOUNT)
                                       + "?token=" + vkApiController.getToken()
                                       + "&user=" + authResponse.getUserId());
            return null;
        }));

        get("/wall/:amount", (request, response) -> {
            int amount = 0;
            try {
                amount = Integer.parseInt(request.params(":amount"));
            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                amount = MIN_POSTS_AMOUNT;
            }

            // truncate amount with minimal and maximum values
            if (amount < MIN_POSTS_AMOUNT) {
                amount = MIN_POSTS_AMOUNT;
            }
            else if (amount > MAX_POSTS_AMOUNT) {
                amount = MAX_POSTS_AMOUNT;
            }
            // create actor only first time after login
            if (vkApiController.getActor()==null) {
                vkApiController.createActor(Integer.parseInt(request.queryParams("user")));
            }
            GetResponse getResponse = vk.wall().get(vkApiController.getActor())
                    .count(amount)    // posts amount
                    .execute();
            List<WallPostFull> wallList = getResponse.getItems();
            List<Post> postList = new ArrayList<>();
            List<String> ownerIds = new ArrayList<>();
            for(WallPostFull post: wallList) {
                ownerIds.add(Integer.toString(post.getFromId())); // getting wall posts IDs
            }
            List<UserXtrCounters> usersList = vk.users().get(vkApiController.getActor()).userIds(ownerIds).execute();
            Map<Integer, String> usersMap = new HashMap<>();
            for(UserXtrCounters user: usersList){
                usersMap.put(user.getId(), user.getFirstName() + " " + user.getLastName());
            }
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            for(WallPostFull post: wallList) {
                postList.add(new Post(post.getText(),
                                      format.format(new Date(post.getDate()*1000L)),
                                      usersMap.get(post.getFromId()),
                                      post.getFromId().toString())
                            );
            }
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("posts", postList);
            return new ModelAndView(attributes, "wall.ftl");
        }, new FreeMarkerTemplateEngine());

        get("/newmessage", (request, response) -> {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("errors", "Введите текст сообщения.");
            attributes.put("subject", "");
            return new ModelAndView(attributes, "newmessage.ftl");
        }, new FreeMarkerTemplateEngine());

        post("/newmessage", (request, response) -> {
            String message = request.queryParams("subject");
            HashMap<String, String> attributes = new HashMap<>();
            if (message.equals("")) {
                attributes.put("errors", "Сообщение должно содержать текст.");
                attributes.put("subject", message);
            }
            else {
                System.out.println("Post: " + message);
                PostResponse postResponse = new PostResponse();
                try {
                    System.out.println("Actor: " + vkApiController.getActor().toString());
                    postResponse = vk.wall().
                                post(vkApiController.getActor()).
                                ownerId(vkApiController.getUserID()).
                                message(message).
                                execute();

                }
                catch (ApiAccessException ex) {
                    ex.printStackTrace();
                    attributes.put("errors", "Не хватает прав или нет разрешения на публикацию сообщений на вашей стене.");
                    attributes.put("subject", message);
                    return new ModelAndView(attributes, "newmessage.ftl");
                }
                System.out.println("Post ID: " + postResponse.getPostId());
                response.redirect("/wall");
            }
            return new ModelAndView(attributes, "newmessage.ftl");
        }, new FreeMarkerTemplateEngine());
    }
}