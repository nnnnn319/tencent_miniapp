package com.example.upfarm.data;

import java.util.List;

public class Order {
    /**
     * result : {"product":[{"address":"湖南","product_order_id":1,"product_order_type":0,"product_items_num":2,"user_id":1,"product_id":"1：2","product_order_description":"/image/summer1","product_total":45.5,"product_order_date":"2020-07-13"}],"farm":[{"farm_order_type":0,"user_id":1,"farm_order_date":"2020-07-13","farm_order_number":1,"farm_order_results":1,"farm_order_description":"/image/summer2","farm_order_image":"/image/summer1","farm_order_plant":1,"farm_order_plant_time":"2020-07-19","farm_order_id":1,"farm_id":1}]}
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
        private List<ProductBean> product;
        private List<FarmBean> farm;

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public List<FarmBean> getFarm() {
            return farm;
        }

        public void setFarm(List<FarmBean> farm) {
            this.farm = farm;
        }

        public static class ProductBean {
            /**
             * address : 湖南
             * product_order_id : 1
             * product_order_type : 0
             * product_items_num : 2
             * user_id : 1
             * product_id : 1：2
             * product_order_description : /image/summer1
             * product_total : 45.5
             * product_order_date : 2020-07-13
             */

            private String address;
            private int product_order_id;
            private int product_order_type;
            private int product_items_num;
            private int user_id;
            private String product_id;
            private String product_order_description;
            private double product_total;
            private String product_order_date;
            private String product_address;
            private String product_phone;
            private String product_username;

            public String getProduct_address() {
                return product_address;
            }

            public void setProduct_address(String product_address) {
                this.product_address = product_address;
            }

            public String getProduct_phone() {
                return product_phone;
            }

            public void setProduct_phone(String product_phone) {
                this.product_phone = product_phone;
            }

            public String getProduct_username() {
                return product_username;
            }

            public void setProduct_username(String product_username) {
                this.product_username = product_username;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getProduct_order_id() {
                return product_order_id;
            }

            public void setProduct_order_id(int product_order_id) {
                this.product_order_id = product_order_id;
            }

            public int getProduct_order_type() {
                return product_order_type;
            }

            public void setProduct_order_type(int product_order_type) {
                this.product_order_type = product_order_type;
            }

            public int getProduct_items_num() {
                return product_items_num;
            }

            public void setProduct_items_num(int product_items_num) {
                this.product_items_num = product_items_num;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getProduct_order_description() {
                return product_order_description;
            }

            public void setProduct_order_description(String product_order_description) {
                this.product_order_description = product_order_description;
            }

            public double getProduct_total() {
                return product_total;
            }

            public void setProduct_total(double product_total) {
                this.product_total = product_total;
            }

            public String getProduct_order_date() {
                return product_order_date;
            }

            public void setProduct_order_date(String product_order_date) {
                this.product_order_date = product_order_date;
            }
        }

        public static class FarmBean {
            /**
             * farm_order_type : 0
             * user_id : 1
             * farm_order_date : 2020-07-13
             * farm_order_number : 1
             * farm_order_results : 1
             * farm_order_description : /image/summer2
             * farm_order_image : /image/summer1
             * farm_order_plant : 1
             * farm_order_plant_time : 2020-07-19
             * farm_order_id : 1
             * farm_id : 1
             */

            private int farm_order_type;
            private int user_id;
            private String farm_order_date;
            private int farm_order_number;
            private int farm_order_results;
            private String farm_order_description;
            private String farm_order_image;
            private int farm_order_plant;
            private String farm_order_plant_time;
            private int farm_order_id;
            private int farm_id;
            private int product_id;
            private int same_order;
            private String farm_address;
            private String farm_name;
            private String farm_phone;

            public String getFarm_address() {
                return farm_address;
            }

            public void setFarm_address(String farm_address) {
                this.farm_address = farm_address;
            }

            public String getFarm_name() {
                return farm_name;
            }

            public void setFarm_name(String farm_name) {
                this.farm_name = farm_name;
            }

            public String getFarm_phone() {
                return farm_phone;
            }

            public void setFarm_phone(String farm_phone) {
                this.farm_phone = farm_phone;
            }

            public int getSame_order() {
                return same_order;
            }

            public void setSame_order(int same_order) {
                this.same_order = same_order;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getFarm_order_type() {
                return farm_order_type;
            }

            public void setFarm_order_type(int farm_order_type) {
                this.farm_order_type = farm_order_type;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getFarm_order_date() {
                return farm_order_date;
            }

            public void setFarm_order_date(String farm_order_date) {
                this.farm_order_date = farm_order_date;
            }

            public int getFarm_order_number() {
                return farm_order_number;
            }

            public void setFarm_order_number(int farm_order_number) {
                this.farm_order_number = farm_order_number;
            }

            public int getFarm_order_results() {
                return farm_order_results;
            }

            public void setFarm_order_results(int farm_order_results) {
                this.farm_order_results = farm_order_results;
            }

            public String getFarm_order_description() {
                return farm_order_description;
            }

            public void setFarm_order_description(String farm_order_description) {
                this.farm_order_description = farm_order_description;
            }

            public String getFarm_order_image() {
                return farm_order_image;
            }

            public void setFarm_order_image(String farm_order_image) {
                this.farm_order_image = farm_order_image;
            }

            public int getFarm_order_plant() {
                return farm_order_plant;
            }

            public void setFarm_order_plant(int farm_order_plant) {
                this.farm_order_plant = farm_order_plant;
            }

            public String getFarm_order_plant_time() {
                return farm_order_plant_time;
            }

            public void setFarm_order_plant_time(String farm_order_plant_time) {
                this.farm_order_plant_time = farm_order_plant_time;
            }

            public int getFarm_order_id() {
                return farm_order_id;
            }

            public void setFarm_order_id(int farm_order_id) {
                this.farm_order_id = farm_order_id;
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
