package com.papayacoders.allinonedownloader.AllDownload;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable {
    @SerializedName("taken_at")
    private long takenAt;
    @SerializedName("pk")
    private double pk;
    @SerializedName("id")
    private String id;
    @SerializedName("device_timestamp")
    private long deviceTimestamp;
    @SerializedName("media_type")
    private int mediaType;
    @SerializedName("code")
    private String code;
    @SerializedName("client_cache_key")
    private String clientCacheKey;
    @SerializedName("filter_type")
    private long filterType;
    @SerializedName("is_unified_video")
    private boolean isUnifiedVideo;
    @SerializedName("next_max_id")
    private double nextMaxid;
    @SerializedName("inline_composer_display_condition")
    private String inlineComposerDisplayCondition;
    @SerializedName("title")
    private String title;
    @SerializedName("product_type")
    private String productType;
    @SerializedName("nearly_complete_copyright_match")
    private boolean nearlyCompleteCopyrightMatch;
    @SerializedName("thumbnails")
    private Thumbnails thumbnails;
    @SerializedName("igtv_exists_in_viewer_series")
    private boolean igtvExistsInViewerSeries;
    @SerializedName("is_post_live")
    private boolean isPostLive;
    @SerializedName("image_versions2")
    private ImageVersions2 imageVersions2;
    @SerializedName("video_codec")
    private String videoCodec;
    @SerializedName("number_of_qualities")
    private long numberOfQualities;
    @SerializedName("video_versions")
    private List<VideoVersion> videoVersions;
    @SerializedName("has_audio")
    private boolean hasAudio;
    @SerializedName("video_duration")
    private double videoDuration;
    @SerializedName("carousel_media_count")
    private long carouselMediaCount;
    @SerializedName("carousel_media")
    private List<CarouselMedia> carouselMedia;


    public Item() {
    }

    @SerializedName("carousel_media")
    public List<CarouselMedia> getCarouselMedia() {
        return carouselMedia;
    }

    @SerializedName("carousel_media")
    public void setCarouselMedia(List<CarouselMedia> value) {
        this.carouselMedia = value;
    }

    @SerializedName("carousel_media_count")
    public long getCarouselMediaCount() {
        return carouselMediaCount;
    }

    @SerializedName("carousel_media_count")
    public void setCarouselMediaCount(long value) {
        this.carouselMediaCount = value;
    }

    @SerializedName("taken_at")
    public long getTakenAt() {
        return takenAt;
    }

    @SerializedName("taken_at")
    public void setTakenAt(long value) {
        this.takenAt = value;
    }

    @SerializedName("pk")
    public double getPk() {
        return pk;
    }

    @SerializedName("pk")
    public void setPk(double value) {
        this.pk = value;
    }

    @SerializedName("id")
    public String getid() {
        return id;
    }

    @SerializedName("id")
    public void setid(String value) {
        this.id = value;
    }

    @SerializedName("device_timestamp")
    public long getDeviceTimestamp() {
        return deviceTimestamp;
    }

    @SerializedName("device_timestamp")
    public void setDeviceTimestamp(long value) {
        this.deviceTimestamp = value;
    }

    @SerializedName("media_type")
    public int getMediaType() {
        return mediaType;
    }

    @SerializedName("media_type")
    public void setMediaType(int value) {
        this.mediaType = value;
    }

    @SerializedName("code")
    public String getCode() {
        return code;
    }

    @SerializedName("code")
    public void setCode(String value) {
        this.code = value;
    }

    @SerializedName("client_cache_key")
    public String getClientCacheKey() {
        return clientCacheKey;
    }

    @SerializedName("client_cache_key")
    public void setClientCacheKey(String value) {
        this.clientCacheKey = value;
    }

    @SerializedName("filter_type")
    public long getFilterType() {
        return filterType;
    }

    @SerializedName("filter_type")
    public void setFilterType(long value) {
        this.filterType = value;
    }

    @SerializedName("is_unified_video")
    public boolean getIsUnifiedVideo() {
        return isUnifiedVideo;
    }

    @SerializedName("is_unified_video")
    public void setIsUnifiedVideo(boolean value) {
        this.isUnifiedVideo = value;
    }

    @SerializedName("next_max_id")
    public double getNextMaxid() {
        return nextMaxid;
    }

    @SerializedName("next_max_id")
    public void setNextMaxid(double value) {
        this.nextMaxid = value;
    }

    @SerializedName("inline_composer_display_condition")
    public String getInlineComposerDisplayCondition() {
        return inlineComposerDisplayCondition;
    }

    @SerializedName("inline_composer_display_condition")
    public void setInlineComposerDisplayCondition(String value) {
        this.inlineComposerDisplayCondition = value;
    }

    @SerializedName("title")
    public String getTitle() {
        return title;
    }

    @SerializedName("title")
    public void setTitle(String value) {
        this.title = value;
    }

    @SerializedName("product_type")
    public String getProductType() {
        return productType;
    }

    @SerializedName("product_type")
    public void setProductType(String value) {
        this.productType = value;
    }

    @SerializedName("nearly_complete_copyright_match")
    public boolean getNearlyCompleteCopyrightMatch() {
        return nearlyCompleteCopyrightMatch;
    }

    @SerializedName("nearly_complete_copyright_match")
    public void setNearlyCompleteCopyrightMatch(boolean value) {
        this.nearlyCompleteCopyrightMatch = value;
    }

    @SerializedName("thumbnails")
    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    @SerializedName("thumbnails")
    public void setThumbnails(Thumbnails value) {
        this.thumbnails = value;
    }

    @SerializedName("igtv_exists_in_viewer_series")
    public boolean getIgtvExistsInViewerSeries() {
        return igtvExistsInViewerSeries;
    }

    @SerializedName("igtv_exists_in_viewer_series")
    public void setIgtvExistsInViewerSeries(boolean value) {
        this.igtvExistsInViewerSeries = value;
    }

    @SerializedName("is_post_live")
    public boolean getIsPostLive() {
        return isPostLive;
    }

    @SerializedName("is_post_live")
    public void setIsPostLive(boolean value) {
        this.isPostLive = value;
    }

    @SerializedName("image_versions2")
    public ImageVersions2 getImageVersions2() {
        return imageVersions2;
    }

    @SerializedName("image_versions2")
    public void setImageVersions2(ImageVersions2 value) {
        this.imageVersions2 = value;
    }

    @SerializedName("video_codec")
    public String getVideoCodec() {
        return videoCodec;
    }

    @SerializedName("video_codec")
    public void setVideoCodec(String value) {
        this.videoCodec = value;
    }

    @SerializedName("number_of_qualities")
    public long getNumberOfQualities() {
        return numberOfQualities;
    }

    @SerializedName("number_of_qualities")
    public void setNumberOfQualities(long value) {
        this.numberOfQualities = value;
    }

    @SerializedName("video_versions")
    public List<VideoVersion> getVideoVersions() {
        return videoVersions;
    }

    @SerializedName("video_versions")
    public void setVideoVersions(List<VideoVersion> value) {
        this.videoVersions = value;
    }

    @SerializedName("has_audio")
    public boolean getHasAudio() {
        return hasAudio;
    }

    @SerializedName("has_audio")
    public void setHasAudio(boolean value) {
        this.hasAudio = value;
    }

    @SerializedName("video_duration")
    public double getVideoDuration() {
        return videoDuration;
    }

    @SerializedName("video_duration")
    public void setVideoDuration(double value) {
        this.videoDuration = value;
    }
}
