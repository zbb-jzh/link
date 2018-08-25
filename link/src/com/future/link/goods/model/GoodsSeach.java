package com.future.link.goods.model;

/**
 * Created by zhengbingbing on 2016/3/16.
 */
public class GoodsSeach {

    /**
     * 商品名称关键字
     */
    private String titleKeyword;

    /**
     * 商品所属分类
     */
    private Long categoryId;

    /**
     * 所属店铺
     */
    private String shopId;

    /**
     * 商品源id，多个id的组合
     */
    private String sourceIds;

    /**
     * 上下架状态
     */
    private Integer sellStatus;

    /**
     * 删除状态
     */
    private Integer status;

    /**
     * 店内分类
     */
    private String shopCategoryId;

    /**
     * 冗余存储GoodsCategory里面的字段，为了方便检索，该分类的商品类型： 1=实体商品，2=服务，3=虚拟商品
     * @hibernate.property
     */
    private Integer goodsType;

    /**
     * 商户类型id
     */
    private String groupTypeId;

    /**
     * 每日特惠
     */
    private Integer isDeals;

    /**
     * 用户id用于查询用户关注
     */
    private String userId;

    /**
     * 收藏
     */
    private Integer isCollect;

    /**
     * 第几页
     */
    private int pageNumber;

    /**
     * 每页个数
     */
    private int pageSize;

    /**
     * 区分是后台请求
     */
    private Integer isBack;


    public String getTitleKeyword() {
        return titleKeyword;
    }

    public void setTitleKeyword(String titleKeyword) {
        this.titleKeyword = titleKeyword;
    }

    public String getGroupTypeId() {
        return groupTypeId;
    }

    public void setGroupTypeId(String groupTypeId) {
        this.groupTypeId = groupTypeId;
    }

    public String getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(String shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(Integer sellStatus) {
        this.sellStatus = sellStatus;
    }

    public String getSourceIds() {
        return sourceIds;
    }

    public void setSourceIds(String sourceIds) {
        this.sourceIds = sourceIds;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getIsDeals() {
        return isDeals;
    }

    public void setIsDeals(Integer isDeals) {
        this.isDeals = isDeals;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIsBack() {
        return isBack;
    }

    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
    }
}
