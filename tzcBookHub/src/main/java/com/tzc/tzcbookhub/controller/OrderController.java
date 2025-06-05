package com.tzc.tzcbookhub.controller;

import com.tzc.tzcbookhub.mapper.GoodMapper;
import com.tzc.tzcbookhub.mapper.OrderMapper;
import com.tzc.tzcbookhub.mapper.UserMapper;
import com.tzc.tzcbookhub.model.Order;
import com.tzc.tzcbookhub.model.dto.BoughtSoldGoodResponse;
import com.tzc.tzcbookhub.model.dto.BuyGoodRequest;
import com.tzc.tzcbookhub.model.dto.OrderInfoResponse;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private UserMapper userMapper;

    @PostMapping("/Purchase")
    public String purchaseGood(@RequestBody BuyGoodRequest buyGoodRequest) {
        System.out.println("接收到的购买请求: " + buyGoodRequest);

        //获取用户和商品，判断用户的money是否大于商品的value
        int userId = buyGoodRequest.getUserId();
        int goodId = buyGoodRequest.getGoodId();
        Double userMoney = userMapper.getUserMoney(userId);
        System.out.println("用户ID: " + userId + ", 用户余额: " + userMoney);
        if (userMoney == null) {
            return "用户不存在或未登录";
        }
        Double sellerMoney = userMapper.getUserMoney(buyGoodRequest.getSellerID());
        System.out.println("卖家ID: " + buyGoodRequest.getSellerID() + ", 卖家余额: " + sellerMoney);
        if (sellerMoney == null) {
            return "卖家不存在";
        }
        Double goodValue = goodMapper.getGoodValue(goodId);
        System.out.println("商品ID: " + goodId + ", 商品价格: " + goodValue);
        if (goodValue == null) {
            return "商品不存在";
        }
        if (userMoney < goodValue) {
            return "余额不足，无法购买商品";
        } else {
            userMapper.updateUserMoney(userMoney - goodValue, userId);
            userMapper.updateUserMoney(sellerMoney + goodValue, buyGoodRequest.getSellerID());
            buyGoodRequest.setCreateTime(LocalDateTime.now());
            buyGoodRequest.setLastUpdateTime(LocalDateTime.now());
            int result = orderMapper.saveOrder(buyGoodRequest);
            if (result > 0) {
                return "下单成功";
            } else {
                return "下单失败";
            }
        }
    }

    @GetMapping("/getOrderInfoByConsumerId")
    public List<OrderInfoResponse> getAllOrders(@CookieValue("jwt1") int consumerId) {
        return orderMapper.getOrderInfoByConsumerId(consumerId);
    }

    @PostMapping("/updateOrderStatus")
    public void updateOrderStatus(@RequestParam("orderId") Integer orderId, @RequestParam("star") int star) {
        //检查订单是否存在
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        orderMapper.updateSellerStar(orderId, star);
        orderMapper.updateStatus(orderId, LocalDateTime.now(), "已完成");
    }

    @GetMapping("/boughtGoods")
    public List<BoughtSoldGoodResponse> getBoughtGoods(@CookieValue("jwt1") int consumerId) {
        return orderMapper.selectBoughtGoods(consumerId);
    }

    @GetMapping("/soldGoods")
    public List<BoughtSoldGoodResponse> getSoldGood(@CookieValue("jwt1") int sellerId){
        return orderMapper.selectSoldGoods(sellerId);
    }
}