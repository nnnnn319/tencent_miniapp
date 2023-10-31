package com.example.upfarm.data;

import java.util.List;

public class Comment {

    /**
     * result : {"comment":[{"comment_time":"2020-13-13","comment_value":"从多个角度描述宝贝，可以帮助更多的人","user_id":1,"product_id":1,"comment_id":1,"comment_imag":"/imageIMG_20200629_071611.jpg","farm_id":1}]}
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
        private List<CommentBean> comment;

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class CommentBean {
            /**
             * comment_time : 2020-13-13
             * comment_value : 从多个角度描述宝贝，可以帮助更多的人
             * user_id : 1
             * product_id : 1
             * comment_id : 1
             * comment_imag : /imageIMG_20200629_071611.jpg
             * farm_id : 1
             */

            private String comment_time;
            private String comment_value;
            private int user_id;
            private int product_id;
            private int comment_id;
            private String comment_imag;
            private int farm_id;
            private String farm_name;

            public String getFarm_name() {
                return farm_name;
            }

            public void setFarm_name(String farm_name) {
                this.farm_name = farm_name;
            }

            public String getComment_time() {
                return comment_time;
            }

            public void setComment_time(String comment_time) {
                this.comment_time = comment_time;
            }

            public String getComment_value() {
                return comment_value;
            }

            public void setComment_value(String comment_value) {
                this.comment_value = comment_value;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public String getComment_imag() {
                return comment_imag;
            }

            public void setComment_imag(String comment_imag) {
                this.comment_imag = comment_imag;
            }

            public int getFarm_id() {
                return farm_id;
            }

            public void setFarm_id(int farm_id) {
                this.farm_id = farm_id;
            }
        }
    }
}
