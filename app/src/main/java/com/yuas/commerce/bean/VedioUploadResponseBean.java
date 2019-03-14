package com.yuas.commerce.bean;


/**
 * Created by liqing on 2018/12/28.
 */

public class VedioUploadResponseBean extends BaseBean {

    /**
     * thumbnailUrl : null
     * originalUrl : /dcim/video/broadcast/video-c392c6d43a414d76b1df33fcfa4521e2.mp4
     * videoPicuture : /dcim/video/broadcast/video-c392c6d43a414d76b1df33fcfa4521e2.jpg
     */

    private Object thumbnailUrl;
    private String originalUrl;
    private String videoPicuture;

    public Object getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(Object thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getVideoPicuture() {
        return videoPicuture;
    }

    public void setVideoPicuture(String videoPicuture) {
        this.videoPicuture = videoPicuture;
    }
}
