package tavebalak.OTTify.common.constant;

import lombok.Getter;

@Getter
public enum CacheType {

    PROGRAM_TRENDING("programTrending", 60 * 60, 100);

    CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }

    private String cacheName;
    private int expiredAfterWrite;
    private int maximumSize;
}