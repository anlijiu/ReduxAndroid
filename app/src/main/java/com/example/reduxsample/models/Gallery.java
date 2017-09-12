package com.example.reduxsample.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anlijiu on 17-9-6.
 */
@AutoValue
public abstract class Gallery {
    @SerializedName("data")
    public abstract List<GalleryItem> data();

    public static Gallery create(List<GalleryItem> data) {
        return builder()
                .data(data)
                .build();
    }

    @SerializedName("success")
    public boolean success;

    @SerializedName("status")
    public int status;

    public int page = -1;

    public static TypeAdapter<Gallery> typeAdapter(Gson gson) {
        return new AutoValue_Gallery.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_Gallery.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder data(List<GalleryItem> data);

        public abstract Gallery build();
    }
}
