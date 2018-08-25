package com.future.link.goods.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.future.link.base.service.ImageManagerService;
import com.future.link.common.Result;
import com.future.link.goods.model.Goods;
import com.future.link.goods.model.GoodsCategoryRelation;
import com.future.link.goods.model.GoodsSeach;
import com.future.link.utils.AssociationIDUtils;
import com.future.link.utils.CommonUtil;
import com.future.link.utils.Constant;
import com.future.link.utils.ToolDateTime;
import com.future.link.utils.ToolString;
import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

public class GoodsService{
	
	public static final GoodsService service = Enhancer.enhance(GoodsService.class);
	
	/**
	 * 新增
	 * @param goods
	 */
	@Before(Tx.class)
	public Result add(Goods goods)
	{
		goods.setId(CommonUtil.getUUID());
        //设置上架状态，默认为上架
        goods.setSellStatus(Constant.SELLSTATUS_ON);
        goods.setStatus(Constant.UN_DELETE);

        //设置创建时间和最新更新时间
        goods.setCreateTime(ToolDateTime.getDateByTime());
        goods.setUpdateTime(ToolDateTime.getDateByTime());
        
        goods.save();
        
        //批量插入产品与店内分类的关系
        List<GoodsCategoryRelation> shopCategoryList = goods.get("categoryList"); //goods.getShopCategoryList();
        if (shopCategoryList != null && shopCategoryList.size() > 0) {
            for (GoodsCategoryRelation value : shopCategoryList) {
                value.setId(CommonUtil.getUUID());
                value.setGoodsId(goods.getId());
            }
            Db.batchSave(shopCategoryList, shopCategoryList.size());
        }
        return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@Before(Tx.class)
	public Result update(Goods goods)
	{
		goods.setUpdateTime(ToolDateTime.getDateByTime());
		
		//批量删除产品与店内分类
        StringBuffer qm = new StringBuffer("delete from goods_goods_category_relation where goodsId = ?");
        Db.update(qm.toString(), goods.getId());
        
        //批量插入产品与店内分类的关系
        List<GoodsCategoryRelation> shopCategoryList = goods.get("categoryList"); // bean.getShopCategoryList();
        if (shopCategoryList != null && shopCategoryList.size() > 0) {
            for (GoodsCategoryRelation value : shopCategoryList) {
                value.setId(CommonUtil.getUUID());
                value.setGoodsId(goods.getId());
            }
            Db.batchSave(shopCategoryList, shopCategoryList.size());
        }
        goods.update();
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result getById(String id)
	{
		Goods goods = Goods.dao.findById(id);
		
		//获取跟分类之间的关联
//        StringBuffer qm = new StringBuffer("select * from goods_goods_category_relation where goodsId = ?");
//        List<GoodsCategoryRelation> shopCategoryList = GoodsCategoryRelation.dao.find(qm.toString(), goods.getId());
//        
//        goods.setCategoryList(shopCategoryList);
		
        this.completeShowUrl(goods);
        return new Result(Constant.SUCCESS, goods);
	}
	
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	@Before(Tx.class)
	public Result deleteById(String id)
	{
		Db.update("update goods_goods set status = ? where id = ?", Constant.DELETEED, id);
		
		//删除分类关系
		Db.update("delete from goods_goods_category_relation where goodsId = ?", id);
		
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 商品上下架
	 * @param id
	 * @return
	 */
	public Result changeSellStatus(String id, String sellStatus)
	{
		Db.update("update goods_goods set sellStatus = ? where id = ?", Integer.parseInt(sellStatus), id);
		return new Result(Constant.SUCCESS);
	}
	
	/**
     * 分页查询商品
     * @return
     */
    public Page<Goods> page(GoodsSeach search){

        String selectField = " id, categoryId, shopId, name, sellStatus, shopPrice,discountedPrice, quantity, imageUrl, status, description,unit, tag, createTime";

        Map<String, Object> sql = this.getSearchSql(search, selectField, null, null);

        List<Object> params = (List<Object>) sql.get("params");

        Page page = null;
        if(params != null && params.size() > 0){
            page = Goods.dao.paginate(search.getPageNumber(), search.getPageSize(), "select " + selectField, sql.get("sql").toString(), params.toArray());
        }else{
            page = Goods.dao.paginate(search.getPageNumber(), search.getPageSize(), "select " + selectField, sql.get("sql").toString());
        }
        List<Goods> list = page.getList();
        for(Goods goods : list)
        {
        	this.completeShowUrl(goods);
        }
        return page;
    }

    /**
     * 查询list
     * @param search
     * @param ids
     * @return
     */
    public List<Goods> list(GoodsSeach search, String ids) {
        String selectField = "select * ";
        Map<String, Object> sql = this.getSearchSql(search, selectField, ids, null);

        List<Object> params = (List<Object>) sql.get("params");
        List<Goods> list = null;
        if(params != null && params.size() > 0){
            list = Goods.dao.find(selectField + sql.get("sql").toString(), params.toArray());
        }else{
            list = Goods.dao.find(selectField + sql.get("sql").toString());
        }
        //this.completeShop(list, search.getGroupTypeId());
        for(Goods goods : list)
        {
        	this.completeShowUrl(goods);
        }
        return list;
    }

    /**
     * 该方法是所有商品查询的最底层函数
     * @param search
     * @param selectField 查询的字段
     * @param ids 限定的商品id列表
     * @param shopIds 限定的店铺id列表
     * @return
     */
    private Map<String, Object> getSearchSql(GoodsSeach search, String selectField, String ids, String shopIds) {

        StringBuffer sb = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
        sb.append(" from goods_goods where status = ? ");
        params.add(Constant.UN_DELETE);
        
        /**
         * 分类
         */
//        if (search.getCategoryId() != null) {
//            long nextCategoryId = AssociationIDUtils.getNextId(search.getCategoryId(), 3);
//            sb.append(" and categoryId>= ? and categoryId< ?");
//            params.add(search.getCategoryId());
//            params.add(nextCategoryId);
//        }

        /**
         * 上下架状态
         */
        if (search.getSellStatus() != null) {
            sb.append(" and sellStatus = ?");
            params.add(search.getSellStatus());
        }

        /**
         * 删除状态
         */
//        if (search.getStatus() != null) {
//            sb.append(" and status = ?");
//            params.add(search.getStatus());
//        }

        /**
         * 产品类型（实物，虚拟产品等）
         */
//        if(search.getGoodsType() != null && search.getGoodsType() == 4){
//            sb.append(" and goodsType !=3 and @goodsType !=4");
//        }else if (search.getGoodsType() != null) {
//            sb.append(" and goodsType = ?");
//            params.add(search.getGoodsType());
//        } else{
//            sb.append(" and goodsType != 3");
//        }

        /**
         * 关键字搜索，现在仅搜素商品标题
         */
        if (StrKit.notBlank(search.getTitleKeyword())) {
            sb.append(" and name like ?");
            params.add("%" + search.getTitleKeyword() + "%");
        }

        /**
         * 每日特惠
         */
        if(search.getIsDeals() != null ){
        	if(search.getIsDeals() == 1){
        		sb.append(" and discountedPrice > ?");
        		params.add(Constant.ISZERO);
        	}else{
        		sb.append(" and discountedPrice is ?");
                params.add(Constant.ISNULL);
        	}
        }

        /**
         * 搜索店内分类
         */
        if (StrKit.notBlank(search.getShopCategoryId())) {
            List<GoodsCategoryRelation> list = CategoryService.service.searchGoodsByCategoryId(search.getShopCategoryId());
            String idString = ToolString.idToSqlIn(list, "goodsId", true);
            if(StrKit.notBlank(idString)) {
                sb.append(" and id in (").append(idString).append(")");
            }else {
                sb.append(" and id in (").append("''").append(")");
            }
        }

        /**
         * 用户关注
         */
//        if(StrKit.notBlank(search.getUserId())){
//            List<UserCollections> list = UserCollectionsService.service.search(search.getUserId());
//            String idString = ToolString.idToSqlIn(list, "goodsId", true);
//            if(StrKit.notBlank(idString)) {
//                sb.append(" and id in (").append(idString).append(")");
//            }else {
//                sb.append(" and id in (").append("''").append(")");
//            }
//        }

        /**
         * 指定id的查询
         */
        if(StrKit.notBlank(ids)) {
            sb.append(" and id in(").append(ids).append(") ");
        }
        sb.append(" order by createTime desc ");
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("sql", sb.toString());
        obj.put("params", params);
        return obj;
    }
    
    // 图片绝对地址写入Goods对象中
    private void completeShowUrl(Goods goods) {
    	
    	// 获取相对地址
        String relUrl = goods.getImageUrl();
        if (StrKit.isBlank(relUrl)) {
            return;
        }
        // 绝对地址
        String absUrl = ImageManagerService.service.processAbsUrl(relUrl);
        goods.setShowUrl(absUrl);
    }
}
