
package com.example.upfarm.market;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarketProduct {
    int id;
    String name;
    int type;
    int option;
    String image;

    int product_id;
    String product_name;
    int store_id;
    String product_description;
    int product_number;
    int product_type;
    String product_register_time;
    private String product_img;

    private String msg;
    /**
     * result : {"channel":[{"image":"/image/imageIMG_20200629_071611.jpg","name":"食物","id":1,"type":1,"option":3},{"image":"/image/summer1.jpg","name":"水果","id":2,"type":2,"option":2},{"image":"/image/summer2.jpg","name":"生鲜","id":3,"type":3,"option":1},{"image":"/image/summer3.jpg","name":"土地","id":4,"type":4,"option":5}],"hot":[{"store_id":1,"product_type":1,"product_id":1,"product_description":"绿色健康很环保","product_name":"tomato","product_img":"/image/imageIMG_20200629_071611.jpg","product_number":10,"product_register_time":1594051200000},{"store_id":2,"product_type":1,"product_id":2,"product_description":"绿色健康很环保","product_name":"potato","product_img":"/image/summer1.jpg","product_number":1,"product_register_time":1594310400000}]}
     */

    private ResultBean result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public int getProduct_number() {
        return product_number;
    }

    public void setProduct_number(int product_number) {
        this.product_number = product_number;
    }

    public int getProduct_type() {
        return product_type;
    }

    public void setProduct_type(int product_type) {
        this.product_type = product_type;
    }

    public String getProduct_register_time() {
        return product_register_time;
    }

    public void setProduct_register_time(String product_register_time) {
        this.product_register_time = product_register_time;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }


    public static class ResultBean {
        private List<MarketProduct> channel;
        private List<HotBean> hot;

        public List<MarketProduct> getChannel() {
            return channel;
        }

        public void setChannel(List<MarketProduct> channel) {
            this.channel = channel;
        }

        public List<HotBean> getHot() {
            return hot;
        }

        public void setHot(List<HotBean> hot) {
            this.hot = hot;
        }

        public static class HotBean {
            /**
             * store_id : 1
             * product_type : 1
             * product_id : 1
             * product_description : 绿色健康很环保
             * product_name : tomato
             * product_img : /image/imageIMG_20200629_071611.jpg
             * product_number : 10
             * product_register_time : 1594051200000
             */

            @SerializedName("store_id")
            private int store_idX;
            @SerializedName("product_type")
            private int product_typeX;
            @SerializedName("product_id")
            private int product_idX;
            @SerializedName("product_description")
            private String product_descriptionX;
            @SerializedName("product_name")
            private String product_nameX;
            @SerializedName("product_img")
            private String product_imgX;
            @SerializedName("product_number")
            private int product_numberX;
            @SerializedName("product_register_time")
            private long product_register_timeX;

            public int getStore_idX() {
                return store_idX;
            }

            public void setStore_idX(int store_idX) {
                this.store_idX = store_idX;
            }

            public int getProduct_typeX() {
                return product_typeX;
            }

            public void setProduct_typeX(int product_typeX) {
                this.product_typeX = product_typeX;
            }

            public int getProduct_idX() {
                return product_idX;
            }

            public void setProduct_idX(int product_idX) {
                this.product_idX = product_idX;
            }

            public String getProduct_descriptionX() {
                return product_descriptionX;
            }

            public void setProduct_descriptionX(String product_descriptionX) {
                this.product_descriptionX = product_descriptionX;
            }

            public String getProduct_nameX() {
                return product_nameX;
            }

            public void setProduct_nameX(String product_nameX) {
                this.product_nameX = product_nameX;
            }

            public String getProduct_imgX() {
                return product_imgX;
            }

            public void setProduct_imgX(String product_imgX) {
                this.product_imgX = product_imgX;
            }

            public int getProduct_numberX() {
                return product_numberX;
            }

            public void setProduct_numberX(int product_numberX) {
                this.product_numberX = product_numberX;
            }

            public long getProduct_register_timeX() {
                return product_register_timeX;
            }

            public void setProduct_register_timeX(long product_register_timeX) {
                this.product_register_timeX = product_register_timeX;
            }
        }
    }
}
