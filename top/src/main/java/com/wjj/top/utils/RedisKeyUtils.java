package com.wjj.top.utils;

public class RedisKeyUtils {

    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String  BIZ_EVENT = "EVENT";

    public static String getSPLITQueue() {
        return SPLIT;
    }

    public static String getLikeQueue() {
        return BIZ_LIKE;
    }

    public static String getDislikeQueue() {
        return BIZ_DISLIKE;
    }

    public static String getEventQueueKey() {
        return BIZ_EVENT;
    }
}
