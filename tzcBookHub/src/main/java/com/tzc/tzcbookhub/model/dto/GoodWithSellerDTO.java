package com.tzc.tzcbookhub.model.dto;
import com.tzc.tzcbookhub.model.Good;
import com.tzc.tzcbookhub.model.User;
import lombok.Data;

@Data
public class GoodWithSellerDTO {
    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    private Good good;
    private User seller;
}