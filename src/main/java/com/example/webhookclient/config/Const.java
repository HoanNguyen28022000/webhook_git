package com.example.webhookclient.config;

public class Const {
    public static enum MergeReqState {
        OPENED(1), CLOSED(2), MERGED(3), LOCKED(4);

        int value;
        MergeReqState(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}
