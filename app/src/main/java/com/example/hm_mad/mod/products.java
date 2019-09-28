package com.example.hm_mad.mod;

public class products {
        private String name ,dic ,catagoyr , price, image, pid, date, time;
        public products(){

        }

        public products(String name, String dic, String catagoyr, String price, String image, String pid, String date, String time) {
            this.name = name;
            this.dic = dic;
            this.catagoyr = catagoyr;
            this.price = price;
            this.image = image;
            this.pid = pid;
            this.date = date;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDic() {
            return dic;
        }

        public void setDic(String dic) {
            this.dic = dic;
        }

        public String getCatagoyr() {
            return catagoyr;
        }

        public void setCatagoyr(String catagoyr) {
            this.catagoyr = catagoyr;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }


