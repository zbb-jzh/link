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
	
	//层数
	public static Integer floor = 2;
	
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
		
		//判断是否具体创业奖金条件
		List<Consumer> consumers = getChildrenRecursive(consumer.getParentId());
		if(consumers != null && consumers.size() == 2) {
			Salary salary1 = new Salary();
			salary1.setConsumerId(consumer.getParentId());
			salary1.setVentureCapital(setting.getVentureCapital());
			salary1.setManagementFee(salary1.getVentureCapital() * setting.getManagementFeeRatio()); //管理费
			salary1.setWithdrawalFee(salary1.getVentureCapital() * setting.getWithdrawalRatio());    //提现手续费
			salary1.setRealWage(salary1.getVentureCapital() - salary1.getManagementFee() - salary1.getWithdrawalFee()); //实发
			salary1.setBAdvertisingaward(0.0);
			salary1.setId(CommonUtil.getUUID());
			salary1.setCreateDate(ToolDateTime.getDate());
			salary1.save();
		}else {
			Consumer aboveConsumer = (Consumer)this.getById(consumer.getParentId()).getData();
			this.generatorFloor(aboveConsumer, 2);
			/*if(null != aboveConsumer && null != aboveConsumer.getParentId()) {
				Consumer tempConsumer = (Consumer)ConsumerService.service.getById(aboveConsumer.getParentId()).getData();
				//查询另一个区域
				Consumer otherConsumer = this.getOtherArea(tempConsumer.getId(), aboveConsumer.getArea());
				
				if(null != otherConsumer) {
					List<Consumer> otherConsumerCh = this.getChildrenRecursive(otherConsumer.getParentId());
					
					//如果另一个分支也有区域则形成层奖
					if(null != otherConsumerCh && otherConsumerCh.size() > 0) {
						Salary salary1 = new Salary();
						salary1.setConsumerId(tempConsumer.getId());
						salary1.setVentureCapital(setting.getVentureCapital());
						salary1.setManagementFee(salary1.getVentureCapital() * setting.getManagementFeeRatio()); //管理费
						salary1.setWithdrawalFee(salary1.getVentureCapital() * setting.getWithdrawalRatio());    //提现手续费
						salary1.setRealWage(salary1.getVentureCapital() - salary1.getManagementFee() - salary1.getWithdrawalFee()); //实发
						salary1.setBAdvertisingaward(0.0);
						salary1.setId(CommonUtil.getUUID());
						salary1.setCreateDate(ToolDateTime.getDate());
						salary1.save();
					}
				}
				
			}*/
		}
				
		
		//如果节点人和推荐人不一样，那么推荐人也得到相应的广告奖金
				/*if(!consumer.getParentId().equals(consumer.getReferrerId())) {
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
				}*/
		//9层奖金
		Consumer aboveConsumer = (Consumer)ConsumerService.service.getById(consumer.getParentId()).getData();
		String firstParentId = ((Consumer)ConsumerService.service.getById(consumer.getParentId()).getData()).getParentId();
		
		Consumer temp = null;

		for(int i=0; i<9; i++) {
			
			if(StrKit.notBlank(firstParentId)) {
				temp = (Consumer) ConsumerService.service.getById(firstParentId).getData();
			}
			
			if(null == temp) {
				break;
			}else {
				
				firstParentId = temp.getParentId();
				
				//如果节点人和推荐人不一样，那么推荐人也得到相应的广告奖金
				//if(!temp.getId().equals(consumer.getReferrerId())) {
					//奖金
					Salary tempSalary = new Salary();
					//节点人获取广告奖
					tempSalary.setConsumerId(temp.getId());
					if(aboveConsumer.getArea() == 1) {
						tempSalary.setAAdvertisingaward(setting.getAAdvertisingaward());
						tempSalary.setManagementFee(tempSalary.getAAdvertisingaward() * setting.getManagementFeeRatio()); //管理费
						tempSalary.setWithdrawalFee(tempSalary.getAAdvertisingaward() * setting.getWithdrawalRatio());    //提现手续费
						tempSalary.setRealWage(tempSalary.getAAdvertisingaward() - tempSalary.getManagementFee() - tempSalary.getWithdrawalFee()); //实发
						tempSalary.setBAdvertisingaward(0.0);
					}else {
						tempSalary.setBAdvertisingaward(setting.getBAdvertisingaward());
						tempSalary.setManagementFee(tempSalary.getBAdvertisingaward() * setting.getManagementFeeRatio());
						tempSalary.setWithdrawalFee(tempSalary.getBAdvertisingaward() * setting.getWithdrawalRatio());
						tempSalary.setRealWage(tempSalary.getBAdvertisingaward() - tempSalary.getManagementFee() - tempSalary.getWithdrawalFee());
						tempSalary.setAAdvertisingaward(0.0);
					}
					tempSalary.setLayerAward(setting.getLayerAward());
					
					SalaryService.service.add(tempSalary);
					aboveConsumer.setArea(temp.getArea());
					aboveConsumer.setId(temp.getId());
					temp = null;
				//}
			}
		}
		
		return new Result(Result.SUCCESS_STATUS, "添加成功");
	}
	private String consumerId = "";
	public void generatorFloor(Consumer aboveConsumer, Integer floor) {
		if(null != aboveConsumer && null != aboveConsumer.getParentId()) {
			
			Consumer tempConsumer = (Consumer)ConsumerService.service.getById(aboveConsumer.getParentId()).getData();
			consumerId = tempConsumer.getId();
			//查询另一个区域
			//Consumer otherConsumer = this.getOtherArea(tempConsumer.getId(), aboveConsumer.getArea());
			
			this.searchOtherCh(tempConsumer, aboveConsumer, floor);
			
			this.generatorFloor(tempConsumer, ++floor);
			
		}
	}
	
	/**
	 * 查询子节点
	 * @param aboveConsumer
	 * @param theConsumer
	 * @param floor
	 */
	public void searchOtherCh(Consumer aboveConsumer, Consumer theConsumer, Integer floor) {
		 
			//查询另一个区域
			Consumer otherConsumer = this.getOtherArea(aboveConsumer.getId(), theConsumer.getArea());
			if(null != otherConsumer) {
				List<Consumer> otherConsumerCh = this.getChildrenRecursive(otherConsumer.getId());
				
				//如果另一个分支也有区域则形成层奖
				if(this.searchCh(otherConsumerCh, floor)) {
					Salary salary1 = new Salary();
					//参数设置
					Setting setting = SettingService.service.getById();
					salary1.setConsumerId(consumerId);
					salary1.setVentureCapital(setting.getVentureCapital());
					salary1.setManagementFee(salary1.getVentureCapital() * setting.getManagementFeeRatio()); //管理费
					salary1.setWithdrawalFee(salary1.getVentureCapital() * setting.getWithdrawalRatio());    //提现手续费
					salary1.setRealWage(salary1.getVentureCapital() - salary1.getManagementFee() - salary1.getWithdrawalFee()); //实发
					salary1.setBAdvertisingaward(0.0);
					salary1.setId(CommonUtil.getUUID());
					salary1.setCreateDate(ToolDateTime.getDate());
					salary1.save();
				}
			}
		
	}
	
	/**
	 * 递归查询子节点
	 * @param consumerList
	 * @param floor
	 * @return
	 */
	public boolean searchCh(List<Consumer> consumerList, Integer floor) {
		if(floor == 2 && null != consumerList && consumerList.size() > 0) {
			return true;
		}else if(null == consumerList || consumerList.size() == 0){
			return false;
		}else{
			boolean flag = false;
			for(int j=floor; j>2; j--) {
				for(int i=0; i<consumerList.size(); i++) {
					List<Consumer> otherConsumerCh = this.getChildrenRecursive(consumerList.get(i).getId());
					if(this.searchCh(otherConsumerCh, --floor)) {
						flag = true;
						break;
					}else{
						++floor;
					}
				}
			}
			
			return flag;
		}
	}
	
	/**
	 * 查询另一个区域
	 * @param parentId
	 * @param area
	 * @return
	 */
	public Consumer getOtherArea(String parentId, Integer area) {
		Consumer model = Consumer.dao.findFirst("select * from consumer_consumer where status = ? and parentId = ? and area != ? ", Constant.UN_DELETE, parentId, area);
		return model;
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
	public Result treeByConsumer(User user)
	{
		Consumer consumer = Consumer.dao.findById(user.getConsumerId());
		
		consumer.setNodes(getChildrenRecursive(consumer.getId()));
		
		return new Result(Constant.SUCCESS, consumer);
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
            	if(tmp.getArea() == 1) {
            		tmp.setName("A区:" + tmp.getName());
            	}else {
            		tmp.setName("B区:" + tmp.getName());
            	}
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
