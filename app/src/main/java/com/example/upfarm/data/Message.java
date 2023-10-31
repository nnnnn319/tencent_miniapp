package com.example.upfarm.data;

import java.util.List;

public class Message {

    /**
     * result : {"message":[{"fromuserid":0,"message_status":0,"message_text":"已同意申请，请重新登录","message_time":"2020-07-15","touserid":0,"type_id":0,"message_id":1}]}
     * msg : success
     */

    private ResultBean result;
    private String msg;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResultBean {
        private List<MessageBean> message;

        public List<MessageBean> getMessage() {
            return message;
        }

        public void setMessage(List<MessageBean> message) {
            this.message = message;
        }

        public static class MessageBean {
            /**
             * fromuserid : 0
             * message_status : 0
             * message_text : 已同意申请，请重新登录
             * message_time : 2020-07-15
             * touserid : 0
             * type_id : 0
             * message_id : 1
             */

            private int fromuserid;
            private int message_status;
            private String message_text;
            private String message_time;
            private int touserid;
            private int type_id;
            private int message_id;

            public int getFromuserid() {
                return fromuserid;
            }

            public void setFromuserid(int fromuserid) {
                this.fromuserid = fromuserid;
            }

            public int getMessage_status() {
                return message_status;
            }

            public void setMessage_status(int message_status) {
                this.message_status = message_status;
            }

            public String getMessage_text() {
                return message_text;
            }

            public void setMessage_text(String message_text) {
                this.message_text = message_text;
            }

            public String getMessage_time() {
                return message_time;
            }

            public void setMessage_time(String message_time) {
                this.message_time = message_time;
            }

            public int getTouserid() {
                return touserid;
            }

            public void setTouserid(int touserid) {
                this.touserid = touserid;
            }

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public int getMessage_id() {
                return message_id;
            }

            public void setMessage_id(int message_id) {
                this.message_id = message_id;
            }
        }
    }
}
