public class Post {

    public Post(String postText, String date, String authorName, String userId) {
        this.postText = postText;
        this.date = date;
        this.authorName = authorName;
        this.userId = userId;
    }

    private String postText;

    private String date;

    private String authorName;

    private String userId;

    public String getPostText() {
        return postText;
    }

    public String getDate() {
        return date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getUserId() { return userId; }


}
