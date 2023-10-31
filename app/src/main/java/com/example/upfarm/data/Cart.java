package com.example.upfarm.data;

import java.util.List;

public class Cart {

    /**
     * result : {"cart":[{"cart_price":20,"cart_total":20,"cart_number":1,"cart_user_id":1,"id":1,"cart_img":"/image/","cart_product_id":1}]}
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
        private List<CartBean> cart;

        public List<CartBean> getCart() {
            return cart;
        }

        public void setCart(List<CartBean> cart) {
            this.cart = cart;
        }

        public static class CartBean {
            /**
             * cart_price : 20
             * cart_total : 20
             * cart_number : 1
             * cart_user_id : 1
             * id : 1
             * cart_img : /image/
             * cart_product_id : 1
             */

            private int cart_price;
            private int cart_total;
            private int cart_number;
            private int cart_user_id;
            private int id;
            private String cart_img;
            private int cart_product_id;
            private String cart_product_name;

            public String getCart_product_name() {
                return cart_product_name;
            }

            public void setCart_product_name(String cart_product_name) {
                this.cart_product_name = cart_product_name;
            }

            public int getCart_price() {
                return cart_price;
            }

            public void setCart_price(int cart_price) {
                this.cart_price = cart_price;
            }

            public int getCart_total() {
                return cart_total;
            }

            public void setCart_total(int cart_total) {
                this.cart_total = cart_total;
            }

            public int getCart_number() {
                return cart_number;
            }

            public void setCart_number(int cart_number) {
                this.cart_number = cart_number;
            }

            public int getCart_user_id() {
                return cart_user_id;
            }

            public void setCart_user_id(int cart_user_id) {
                this.cart_user_id = cart_user_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCart_img() {
                return cart_img;
            }

            public void setCart_img(String cart_img) {
                this.cart_img = cart_img;
            }

            public int getCart_product_id() {
                return cart_product_id;
            }

            public void setCart_product_id(int cart_product_id) {
                this.cart_product_id = cart_product_id;
            }
        }
    }
}
