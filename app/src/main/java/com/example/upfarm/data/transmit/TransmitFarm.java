package com.example.upfarm.data.transmit;

public class TransmitFarm {

    /**
     * farmInformation : {"farm_id":1,"farm_img":"/image/summer1.jpg","farm_name":"Heee","farm_register_time":"2020-07-11","farm_type":1,"farm_x":"1110","farm_y":"111","store_id":1}
     */

    private FarmInformationBean farmInformation;

    public FarmInformationBean getFarmInformation() {
        return farmInformation;
    }

    public void setFarmInformation(FarmInformationBean farmInformation) {
        this.farmInformation = farmInformation;
    }

    public static class FarmInformationBean {
        /**
         * farm_id : 1
         * farm_img : /image/summer1.jpg
         * farm_name : Heee
         * farm_register_time : 2020-07-11
         * farm_type : 1
         * farm_x : 1110
         * farm_y : 111
         * store_id : 1
         */

        private int farm_id;
        private String farm_img;
        private String farm_name;
        private String farm_register_time;
        private int farm_type;
        private String farm_x;
        private String farm_y;
        private int store_id;

        public int getFarm_id() {
            return farm_id;
        }

        public void setFarm_id(int farm_id) {
            this.farm_id = farm_id;
        }

        public String getFarm_img() {
            return farm_img;
        }

        public void setFarm_img(String farm_img) {
            this.farm_img = farm_img;
        }

        public String getFarm_name() {
            return farm_name;
        }

        public void setFarm_name(String farm_name) {
            this.farm_name = farm_name;
        }

        public String getFarm_register_time() {
            return farm_register_time;
        }

        public void setFarm_register_time(String farm_register_time) {
            this.farm_register_time = farm_register_time;
        }

        public int getFarm_type() {
            return farm_type;
        }

        public void setFarm_type(int farm_type) {
            this.farm_type = farm_type;
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

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }
    }
}
