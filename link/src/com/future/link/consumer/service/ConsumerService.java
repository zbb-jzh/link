package com.future.link.consumer.service;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Enhancer;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.future.link.common.Result;
import com.future.link.consumer.model.Consumer;
import com.future.link.goods.model.Category;
import com.future.link.user.model.User;
import com.future.link.utils.CommonUtil;
import com.future.link.utils.Constant;
import com.future.link.utils.ToolDateTime;

public class ConsumerService {
	
	public static final ConsumerService service = Enhancer.enhance(ConsumerService.class);
	
	/**
	 * 新增
	 * @param user
	 * @return
	 */
	public Result add(Consumer consumer)
	{
		consumer.setId(CommonUtil.getUUID());
		consumer.setStatus(Consumer.UN_DELETE);
		consumer.setOrigin(Consumer.ORIGIN_OFFLINE);
		consumer.setCreateTime(ToolDateTime.getDateByTime());
		consumer.save();
		return new Result(Result.SUCCESS_STATUS, "添加成功");
	}
	
	/**
	 * 根据ID获取数据
	 * @param id
	 * @return
	 */
	public Result getById(String id)
	{
		return new Result(Result.SUCCESS_STATUS, Consumer.dao.findById(id));
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public Result deleteById(String id)
	{
		Db.update("update consumer_consumer set status = ? where id = ?", Consumer.DELETEED, id);
		return new Result(Result.SUCCESS_STATUS, "删除成功");
	}
	
	/**
	 * 更新
	 * @param user
	 * @return
	 */
	public Result update(Consumer consumer)
	{
		consumer.update();
		return new Result(Result.SUCCESS_STATUS, "修改成功");
	}
	
	/**
	 * 获取，树形结构
	 * @return
	 */
	public Result tree()
	{
		List<Consumer> list = getParent();
		for(Consumer model : list)
		{
			model.setNodes(getChildrenRecursive(model.getId()));
		}
		return new Result(Constant.SUCCESS, list);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Consumer> getParent()
	{
		List<Consumer> list = Consumer.dao.find("select * from consumer_consumer where status = ? and parentId is ? order by createTime desc", Constant.UN_DELETE, Constant.ISNULL);
		return list;
	}
	
	/**
	 * 根据父类id获取子类（递归调用）
	 * @param pid
	 * @return
	 */
	public List<Consumer> getChildrenRecursive(String pid)
	{
		List<Consumer> list = Consumer.dao.find("select * from consumer_consumer where status = ? and parentId = ? order by createTime desc", Constant.UN_DELETE, pid);
		if(list!= null && list.size()>0) {
            for(Consumer tmp: list) {
                tmp.setNodes(this.getChildrenRecursive(tmp.getId()));
            }
        }
		return list;
	}
	
	/**
     * 分页查询
     * @return
     */
    public Page<Consumer> page(int pageNumber, int pageSize, String name, String type, long startTime, long endTime){

        StringBuffer sql = new StringBuffer(" from consumer_consumer where status = ? ");
        List<Object> params = new ArrayList<>();
        params.add(Consumer.UN_DELETE);
        
        if(startTime > 0){
        	sql.append(" and createTime >= ?");
            params.add(startTime);
        }
        if(endTime > 0){
        	sql.append(" and createTime <= ?");
            params.add(endTime);
        }
        
        if(StrKit.notBlank(name)){
            sql.append(" and name like ?");
            params.add("%" + name + "%");
        }
        if(StrKit.notBlank(type)){
            sql.append(" and type = ?");
            params.add(Integer.parseInt(type));
        }
        sql.append(" order by createTime desc");
        Page<Consumer> page = Consumer.dao.paginate(pageNumber, pageSize, "select * ", sql.toString(), params.toArray());
        return page;
    }
}
