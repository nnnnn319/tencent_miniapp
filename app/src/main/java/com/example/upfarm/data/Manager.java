package com.example.upfarm.data;

import java.util.List;

public class Manager {

    /**
     * result : {"request":[{"request_time":"2020-07-12","user_id":2,"user_name":"a","id":1,"farm_name":"hhhhh"}],"farm":[{"store_id":1,"farm_img":"/image/summer1.jpg","farm_register_time":"2020-07-11","farm_x":"1110","farm_y":"111","farm_type":2,"farm_id":1,"farm_name":"Heee"}],"user":[{"user_birthday":"2001-03-10","user_password":"a","user_type":1,"user_id":2,"user_name":"a","user_address":"b","user_register_time":"2020-07-08","user_age":21,"user_phone":"1"}]}
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
        private List<RequestBean> request;
        private List<FarmBean> farm;
        private List<UserBean> user;

        public List<RequestBean> getRequest() {
            return request;
        }

        public void setRequest(List<RequestBean> request) {
            this.request = request;
        }

        public List<FarmBean> getFarm() {
            return farm;
        }

        public void setFarm(List<FarmBean> farm) {
            this.farm = farm;
        }

        public List<UserBean> getUser() {
            return user;
        }

        public void setUser(List<UserBean> user) {
            this.user = user;
        }

        public static class RequestBean {
            /**
             * request_time : 2020-07-12
             * user_id : 2
             * user_name : a
             * id : 1
             * farm_name : hhhhh
             */

            private String request_time;
            private int user_id;
            private String user_name;
            private int id;
            private String farm_name;
            private String farm_address;

            public String getFarm_address() {
                return farm_address;
            }

            public void setFarm_address(String farm_address) {
                this.farm_address = farm_address;
            }

            public String getRequest_time() {
                return request_time;
            }

            public void setRequest_time(String request_time) {
                this.request_time = request_time;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFarm_name() {
                return farm_name;
            }

            public void setFarm_name(String farm_name) {
                this.farm_name = farm_name;
            }
        }

        public static class FarmBean {
            /**
             * store_id : 1
             * farm_img : /image/summer1.jpg
             * farm_register_time : 2020-07-11
             * farm_x : 1110
             * farm_y : 111
             * farm_type : 2
             * farm_id : 1
             * farm_name : Heee
             */

            private int store_id;
            private String farm_img;
            private String farm_register_time;
            private String farm_x;
            private String farm_y;
            private int farm_type;
            private int farm_id;
            private String farm_name;

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getFarm_img() {
                return farm_img;
            }

            public void setFarm_img(String farm_img) {
                this.farm_img = farm_img;
            }

            public String getFarm_register_time() {
                return farm_register_time;
            }

            public void setFarm_register_time(String farm_register_time) {
                this.farm_register_time = farm_register_time;
            }

            public String getFarm_x() {
                return farm_x;
            }

            public void setFarm_x(String farm_x) {
                this.farm_x = farm_x;
            }

            public String getFarm_y() {
                return farm_y;
            }

            public void setFarm_y(String farm_y) {
                this.farm_y = farm_y;
            }

            public int getFarm_type() {
                return farm_type;
            }

            public void setFarm_type(int farm_type) {
                this.farm_type = farm_type;
            }

            public int getFarm_id() {
                return farm_id;
            }

            public void setFarm_id(int farm_id) {
                this.farm_id = farm_id;
            }

            public String getFarm_name() {
                return farm_name;
            }

            public void setFarm_name(String farm_name) {
                this.farm_name = farm_name;
            }
        }

        public static class UserBean {
            /**
             * user_birthday : 2001-03-10
             * user_password : a
             * user_type : 1
             * user_id : 2
             * user_name : a
             * user_address : b
             * user_register_time : 2020-07-08
             * user_age : 21
             * user_phone : 1
             */

            private String user_birthday;
            private String user_password;
            private int user_type;
            private int user_id;
            private String user_name;
            private String user_address;
            private String user_register_time;
            private int user_age;
            private String user_phone;

            public String getUser_birthday() {
                return user_birthday;
            }

            public void setUser_birthday(String user_birthday) {
                this.user_birthday = user_birthday;
            }

            public String getUser_password() {
                return user_password;
            }

            public void setUser_password(String user_password) {
                this.user_password = user_password;
            }

            public int getUser_type() {
                return user_type;
            }

            public void setUser_type(int user_type) {
                this.user_type = user_type;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_address() {
                return user_address;
            }

            public void setUser_address(String user_address) {
                this.user_address = user_address;
            }

            public String getUser_register_time() {
                return user_register_time;
            }

            public void setUser_register_time(String user_register_time) {
                this.user_register_time = user_register_time;
            }

            public int getUser_age() {
                return user_age;
            }

            public void setUser_age(int user_age) {
                this.user_age = user_age;
            }

            public String getUser_phone() {
                return user_phone;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;
            }
        }
    }
}
