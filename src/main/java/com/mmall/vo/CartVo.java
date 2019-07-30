package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

// 整个购物车的Vo
public class CartVo {

    List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice; // 购物车中所有商品的总价
    private Boolean allChecked; // 是否所有商品都勾选
    private String imageHost; // 在购物车中显示的商品图片

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
