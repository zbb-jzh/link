package com.future.link.consumer.service;

import java.util.ArrayList;
import java.util.List;

import com.future.link.base.model.Setting;
import com.future.link.base.service.SettingService;
import com.future.link.common.Result;
import com.future.link.consumer.model.Consumer;
import com.future.link.consumer.model.Salary;
import com.future.link.user.model.User;
import com.future.link.utils.CommonUtil;
import com.future.link.utils.Constant;
import com.future.link.utils.MD5Util;
import com.future.link.utils.ToolDateTime;
import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

public class ConsumerService {
	
	public static final ConsumerService service = Enhancer.enhance(ConsumerService.class);
	
	/**
	 * 新增
	 * @param user
	 * @return
	 */
	@Before(Tx.class)
	public Result add(Consumer consumer, User curUser)
	{
		
		Consumer model = Consumer.dao.findFirst("select * from consumer_consumer where status = ? and parentId = ? and area = ? ", Constant.UN_DELETE, consumer.getParentId(), consumer.getArea());
		
		if(model != null) {
			return new Result(Constant.AREA_IS_ONE, "每个区域只能设置一人");
		}
		consumer.setId(CommonUtil.getUUID());
		consumer.setStatus(Consumer.UN_DELETE);
		consumer.setOrigin(Consumer.ORIGIN_OFFLINE);
		consumer.setCreateTime(ToolDateTime.getDateByTime());
		consumer.save();
		
		User user = new User();
		user.setId(CommonUtil.getUUID());
		user.setConsumerId(consumer.getId());
		user.setName(consumer.getUserName());
		user.setPassword(MD5Util.generatePassword(consumer.getUserPwd()));
		user.setTwoPassword(MD5Util.generatePassword(consumer.getTwoPassword()));
		user.setPhone(consumer.getPhone());
		user.setType(2);
		user.setCreateTime(ToolDateTime.getDateByTime());
		user.save();
		
		//参数设置
		Setting setting = SettingService.service.getById();
		//奖金
		Salary salary = new Salary();
		//节点人获取广告奖
		salary.setConsumerId(consumer.getParentId());
		if(consumer.getArea() == 1) {
			salary.setAAdvertisingaward(setting.getAAdvertisingaward());
			salary.setManagementFee(salary.getAAdvertisingaward() * setting.getManagementFeeRatio()); //管理费
			salary.setWithdrawalFee(salary.getAAdvertisingaward() * setting.getWithdrawalRatio());    //提现手续费
			salary.setRealWage(salary.getAAdvertisingaward() - salary.getManagementFee() - salary.getWithdrawalFee()); //实发
			salary.setBAdvertisingaward(0.0);
		}else {
			salary.setBAdvertisingaward(setting.getBAdvertisingaward());
			salary.setManagementFee(salary.getBAdvertisingaward() * setting.getManagementFeeRatio());
			salary.setWithdrawalFee(salary.getBAdvertisingaward() * setting.getWithdrawalRatio());
			salary.setRealWage(salary.getBAdvertisingaward() - salary.getManagementFee() - salary.getWithdrawalFee());
			salary.setAAdvertisingaward(0.0);
		}
		salary.setLayerAward(setting.getLayerAward());
		
		SalaryService.service.add(salary);
		
		//如果节点人和推荐人不一样，那么推荐人也得到相应的广告奖金
		if(!consumer.getParentId().equals(consumer.getReferrerId())) {
			//奖金
			Salary referrersalary = new Salary();
			//节点人获取广告奖
			referrersalary.setConsumerId(consumer.getReferrerId());
			if(consumer.getArea() == 1) {
				referrersalary.setAAdvertisingaward(setting.getAAdvertisingaward());
				referrersalary.setManagementFee(referrersalary.getAAdvertisingaward() * setting.getManagementFeeRatio()); //管理费
				referrersalary.setWithdrawalFee(referrersalary.getAAdvertisingaward() * setting.getWithdrawalRatio());    //提现手续费
				referrersalary.setRealWage(referrersalary.getAAdvertisingaward() - referrersalary.getManagementFee() - referrersalary.getWithdrawalFee()); //实发
				referrersalary.setBAdvertisingaward(0.0);
			}else {
				referrersalary.setBAdvertisingaward(setting.getBAdvertisingaward());
				referrersalary.setManagementFee(referrersalary.getBAdvertisingaward() * setting.getManagementFeeRatio());
				referrersalary.setWithdrawalFee(referrersalary.getBAdvertisingaward() * setting.getWithdrawalRatio());
				referrersalary.setRealWage(referrersalary.getBAdvertisingaward() - referrersalary.getManagementFee() - referrersalary.getWithdrawalFee());
				referrersalary.setAAdvertisingaward(0.0);
			}
			referrersalary.setLayerAward(setting.getLayerAward());
			
			SalaryService.service.add(referrersalary);
		}
		
		return new Result(Result.SUCCESS_STATUS, "添加成功");
	}
	
	/**
	 * 根据ID获取数据
	 * @param id
	 * @return
	 */
	public Result getById(String id)
	{
		Consumer consumer = Consumer.dao.findById(id);
		consumer.setPrizeCoin(SalaryService.service.searchSalary(id) - WithdrawService.service.searchWithdraw(id));
		return new Result(Result.SUCCESS_STATUS, consumer);
	}
	
	/**
	 * 根据ID获取数据
	 * @param id
	 * @return
	 */
	public Result getByUserJJB(String id)
	{
		Consumer consumer = Consumer.dao.findById(id);
		consumer.setPrizeCoin(SalaryService.service.searchSalary(id));
		consumer.setWithdraws( WithdrawService.service.searchWithdraw(id));
		return new Result(Result.SUCCESS_STATUS, consumer);
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
		Consumer model = Consumer.dao.findFirst("select * from consumer_consumer where status = ? and parentId = ? and area = ? ", Constant.UN_DELETE, consumer.getParentId(), consumer.getArea());
		
		if(model != null) {
			return new Result(Constant.AREA_IS_ONE, "每个区域只能设置一人");
		}
		consumer.update();
		return new Result(Result.SUCCESS_STATUS, "修改成功");
	}
	
	
	/**
	 * 更新
	 * @param user
	 * @return
	 */
	public Result doUpdatePersonInfo(Consumer consumer)
	{
		consumer.update();
		return new Result(Result.SUCCESS_STATUS, "修改成功");
	}
	
	/**
	 * 获取客户推荐的客户
	 * @param consumerId
	 * @return
	 */
	public Result searchReferrers(String consumerId) {
		
		List<Consumer> list = Consumer.dao.find("select * from consumer_consumer where status = ? and referrerId = ? order by createTime desc", Constant.UN_DELETE, consumerId);
		return new Result(Result.SUCCESS_STATUS, list);
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
	 * 客户只能看到自己一下会员，获取，树形结构
	 * @return
	 */
	public Consumer treeByConsumer(User user)
	{
		Consumer consumer = Consumer.dao.findById(user.getConsumerId());
		
		consumer.setNodes(getChildrenRecursive(consumer.getId()));
		return consumer;
		
		//return new Result(Constant.SUCCESS, consumer);
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
