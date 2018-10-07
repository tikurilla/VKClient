public enum VKScope {
    notify("notify"),
    friends("friends"),
    photos("photos"),
    audio("audio"),
    video("video"),
    stories("stories"),
    pages("pages"),
    status("status"),
    notes("notes"),
    messages("messages"),
    wall("wall");
    // API вконтакте предоставляет значительно больше градаций прав доступа,
    // но в данном приложении указанного количества прав достаточно
    private final String scope;

    VKScope(String scope) {
        assert scope != null;
        this.scope = scope;
    }

    public String getScope() {
        return this.scope;
    }
}