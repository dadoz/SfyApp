package com.application.mxm.soundtracks.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Lyric extends RealmObject {

    @SerializedName("lyrics_id")
    @Expose
    private Integer lyricsId;
    @SerializedName("restricted")
    @Expose
    private Integer restricted;
    @SerializedName("instrumental")
    @Expose
    private Integer instrumental;
    @SerializedName("lyrics_body")
    @Expose
    private String lyricsBody;
    @SerializedName("lyrics_language")
    @Expose
    private String lyricsLanguage;
    @SerializedName("script_tracking_url")
    @Expose
    private String scriptTrackingUrl;
    @SerializedName("pixel_tracking_url")
    @Expose
    private String pixelTrackingUrl;
    @SerializedName("lyrics_copyright")
    @Expose
    private String lyricsCopyright;
    @SerializedName("backlink_url")
    @Expose
    private String backlinkUrl;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;
    private String trackId;

    public Integer getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(Integer lyricsId) {
        this.lyricsId = lyricsId;
    }

    public Integer getRestricted() {
        return restricted;
    }

    public void setRestricted(Integer restricted) {
        this.restricted = restricted;
    }

    public Integer getInstrumental() {
        return instrumental;
    }

    public void setInstrumental(Integer instrumental) {
        this.instrumental = instrumental;
    }

    public String getLyricsBody() {
        return lyricsBody;
    }

    public void setLyricsBody(String lyricsBody) {
        this.lyricsBody = lyricsBody;
    }

    public String getLyricsLanguage() {
        return lyricsLanguage;
    }

    public void setLyricsLanguage(String lyricsLanguage) {
        this.lyricsLanguage = lyricsLanguage;
    }

    public String getScriptTrackingUrl() {
        return scriptTrackingUrl;
    }

    public void setScriptTrackingUrl(String scriptTrackingUrl) {
        this.scriptTrackingUrl = scriptTrackingUrl;
    }

    public String getPixelTrackingUrl() {
        return pixelTrackingUrl;
    }

    public void setPixelTrackingUrl(String pixelTrackingUrl) {
        this.pixelTrackingUrl = pixelTrackingUrl;
    }

    public String getLyricsCopyright() {
        return lyricsCopyright;
    }

    public void setLyricsCopyright(String lyricsCopyright) {
        this.lyricsCopyright = lyricsCopyright;
    }

    public String getBacklinkUrl() {
        return backlinkUrl;
    }

    public void setBacklinkUrl(String backlinkUrl) {
        this.backlinkUrl = backlinkUrl;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
}