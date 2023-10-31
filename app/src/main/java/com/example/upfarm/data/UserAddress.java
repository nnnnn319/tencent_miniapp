package com.example.upfarm.data;

import java.util.List;

public class UserAddress {

    /**
     * result : {"address":[{"user_address_name":"何馨雨","user_address_phone":"13835649105","user_id":1,"id":1,"user_address_choose":"湖南省长沙市","user_address_specific":"铁道学院"},{"user_address_name":"何馨雨","user_address_phone":"13835649105","user_id":1,"id":2,"user_address_choose":"山西省晋城市","user_address_specific":"城区"}]}
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
        private List<AddressBean> address;

        public List<AddressBean> getAddress() {
            return address;
        }

        public void setAddress(List<AddressBean> address) {
            this.address = address;
        }

        public static class AddressBean {
            /**
             * user_address_name : 何馨雨
             * user_address_phone : 13835649105
             * user_id : 1
             * id : 1
             * user_address_choose : 湖南省长沙市
             * user_address_specific : 铁道学院
             */

            private String user_address_name;
            private String user_address_phone;
            private int user_id;
            private int id;
            private String user_address_choose;
            private String user_address_specific;

            public String getUser_address_name() {
                return user_address_name;
            }

            public void setUser_address_name(String user_address_name) {
                this.user_address_name = user_address_name;
            }

            public String getUser_address_phone() {
                return user_address_phone;
            }

            public void setUser_address_phone(String user_address_phone) {
                this.user_address_phone = user_address_phone;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUser_address_choose() {
                return user_address_choose;
            }

            public void setUser_address_choose(String user_address_choose) {
                this.user_address_choose = user_address_choose;
            }

            public String getUser_address_specific() {
                return user_address_specific;
            }

            public void setUser_address_specific(String user_address_specific) {
                this.user_address_specific = user_address_specific;
            }
        }
    }
}
