package org.b3log.solo.bolo.prop;

public class FaviconCache {
    private final String mediaFileType;
    private final byte[] data;

    public FaviconCache(String mediaFileType, byte[] data) {
        this.mediaFileType = mediaFileType;
        this.data = data;
    }

    public String getMediaFileType() {
        return mediaFileType;
    }

    public byte[] getData() {
        return data;
    }
}
