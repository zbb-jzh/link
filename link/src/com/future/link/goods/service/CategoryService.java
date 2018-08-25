package com.future.link.goods.service;

import java.util.ArrayList;
import java.util.List;

import com.future.link.common.Result;
import com.future.link.goods.model.Category;
import com.future.link.goods.model.GoodsCategoryRelation;
import com.future.link.utils.AssociationIDUtils;
import com.future.link.utils.Constant;
import com.future.link.utils.ToolDateTime;
import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;

public class CategoryService {
	
	public static final CategoryService service = Enhancer.enhance(CategoryService.class);
	
	/**
	 * 新增
	 * @param category
	 * @return
	 */
	public Result add(Category category)
	{
		//category.setId(CommonUtil.getUUID());
		category.setCreateTime(ToolDateTime.getDateByTime());
		category.setStatus(Constant.UN_DELETE);
		
		/**
         * 超找当前最大的id
         */
        int idLevel = 1;
        StringBuffer qm = new StringBuffer("select max(id) as id from goods_category");
        //查询参数
        List<Object> params = new ArrayList<Object>();

        if(category.getParentId()!=  null) {
            long[] scope= AssociationIDUtils.getChildrenScope(category.getParentId(), 3);
            idLevel= (int) scope[2] +1;
            qm.append(" where id>= ? and id< ?");
            params.add(scope[0]);
            params.add(scope[1]);
        }
        Category max = null;
        if(params.size() > 0){
            max = Category.dao.findFirst(qm.toString(), params.toArray());
        }else{
            max = Category.dao.findFirst(qm.toString());
        }

        /**
         *生成分类id
         */
        long id = 0;
        if(max!= null && max.getId()!= null) {
            id= AssociationIDUtils.getNextId4Level(max.getId(), idLevel, 3);
        } else {
            id = AssociationIDUtils.getNextId4Level(category.getParentId(), idLevel, 3);
        }
        category.setId(id);
        
		category.save();
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 修改
	 * @param category
	 * @return
	 */
	public Result update(Category category)
	{
		category.update();
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@Before(Tx.class)
	public Result delete(long  id)
	{
		//父类下有子类
		List<Category> list = this.getChildren(id);
		if(list != null && list.size() > 0)
		{
			return  Result.flomErrorData(Constant.HAS_CHILDREN);
		}
		//分类下有商品
		
		List<GoodsCategoryRelation> relationList = this.searchGoodsByCategoryId(id+"");
		if(relationList != null && relationList.size() > 0){
			return  Result.flomErrorData(Constant.HAS_Goods);
		}
		return new Result(Constant.SUCCESS, Category.dao.deleteById(id));
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Result getById(String id)
	{
		return new Result(Constant.SUCCESS, Category.dao.findById(id));
	}
	
	/**
     * 根据商品id查询，商品关联的分类
     * @param goodsId
     * @return
     */
    public List<Category>  searchCategoryByGoodsId(String goodsId){

        List<Category> list = Category.dao.find("select * from goods_category where id in (select categoryId from goods_goods_category_relation where goodsId = ?) and status = ?", goodsId, Constant.UN_DELETE);
        return list;
    }
    
	/**
	 * 获取所以分类，树形结构
	 * @return
	 */
	public Result tree()
	{
		List<Category> list = getParent();
		for(Category model : list)
		{
			model.setNodes(getChildrenRecursive(model.getId()));
		}
		return new Result(Constant.SUCCESS, list);
	}
	
	/**
	 * 获取一级分类
	 * @return
	 */
	public List<Category> getParent()
	{
		List<Category> list = Category.dao.find("select * from goods_category where status = ? and parentId is ? order by createTime desc", Constant.UN_DELETE, Constant.ISNULL);
		return list;
	}
	
	/**
	 * 根据父类id获取子类（递归调用）
	 * @param pid
	 * @return
	 */
	public List<Category> getChildrenRecursive(long pid)
	{
		List<Category> list = Category.dao.find("select * from goods_category where status = ? and parentId = ? order by createTime desc", Constant.UN_DELETE, pid);
		if(list!= null && list.size()>0) {
            for(Category tmp: list) {
                tmp.setNodes(this.getChildrenRecursive(tmp.getId()));
            }
        }
		return list;
	}
	
	/**
	 * 根据父类id查询所有子类
	 * @param pid
	 * @return
	 */
	public List<Category> getChildren(long pid)
	{
		return Category.dao.find("select * from goods_category where status = ? and parentId = ? order by createTime desc", Constant.UN_DELETE, pid);

	}
	
	/**
	 * 获取商品关联的分类
	 * @param id
	 * @return
	 */
	public List<GoodsCategoryRelation> searchGoodsByCategoryId(String id)
	{
		return GoodsCategoryRelation.dao.find("select * from goods_goods_category_relation where  categoryId = ?", id);
	}
	
	/**
     * 获取二级分类
     */
    public List<Category> searchSec(){

        return Category.dao.find("select * from goods_category where parentId is not null and status = ?", Constant.UN_DELETE);
    }
}
