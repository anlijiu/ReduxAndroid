package com.example.reduxsample.models;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anlijiu on 17-9-6.
 */

@AutoValue
public abstract class GalleryItem {
    @SerializedName("id")
    public abstract String id();

    @SerializedName("title")
    public abstract String title();

    @SerializedName("description")
    public abstract String description();

    @SerializedName("views")
    public abstract int views();

    @SerializedName("link")
    public abstract String link();

    public static GalleryItem create(String id, String title, String description, int views, String link) {
        return builder()
                .id(id)
                .title(title)
                .description(description)
                .views(views)
                .link(link)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_GalleryItem.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder title(String title);

        public abstract Builder description(String description);

        public abstract Builder views(int views);

        public abstract Builder link(String link);

        public abstract GalleryItem build();
    }
}
