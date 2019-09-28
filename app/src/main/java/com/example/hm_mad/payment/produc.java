package com.example.hm_mad.payment;

public class produc {
    private String nameonCard;
    private String cardNo;
    private String expDate;
    private String secCode;
    private String price;

    public produc() {
    }

    public produc(String nameonCard, String cardNo, String expDate, String secCode, String price) {
        this.nameonCard = nameonCard;
        this.cardNo = cardNo;
        this.expDate = expDate;
        this.secCode = secCode;
        this.price = price;
    }

    public String getNameonCard() {
        return nameonCard;
    }

    public void setNameonCard(String nameonCard) {
        this.nameonCard = nameonCard;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
