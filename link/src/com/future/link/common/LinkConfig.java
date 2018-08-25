package com.future.link.common;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.future.link.base.BaseRouter;
import com.future.link.consumer.ConsumerRouter;
import com.future.link.goods.GoodsRouter;
import com.future.link.user.UserRouter;

public class LinkConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		
		//数据库配置
		PropKit.use("DataBase.properties");
		me.setDevMode(true);
	}

	@Override
	public void configRoute(Routes me) {
		me.add(new UserRouter());
		me.add(new ConsumerRouter());
		me.add(new GoodsRouter());
		me.add(new BaseRouter());
	}
	
	/**
	 * 获取数据了链接池
	 */
	public C3p0Plugin getC3p0Plugin(String type){
		
		/**
		 * 获取MySQL链接池
		 */
		if(type.equals("mysql")){
			return new C3p0Plugin(PropKit.get("jdbc.mysqlUrl").trim(), PropKit.get("jdbc.mysqlUser").trim(), PropKit.get("jdbc.mysqlPassword").trim());
		}else if(type.equals("sqlserver")){
			/**
			 * 获取sql server链接池
			 */
			return new C3p0Plugin(PropKit.get("jdbc.sqlserverUrl").trim(), PropKit.get("jdbc.sqlserverUser").trim(), PropKit.get("jdbc.sqlserverPassword").trim(), PropKit.get("jdbc.sqlserverdriverClassName"));
		}else{
			/**
			 * 获取oracle链接池
			 */
			return new C3p0Plugin(PropKit.get("jdbc.oracleUrl").trim(), PropKit.get("jdbc.oracleUser").trim(), PropKit.get("jdbc.oraclePassword").trim(), PropKit.get("jdbc.oracledriverClassName"));
		}
	}
	
	/**
	 * 多数据源支持
	 */
	@Override
	public void configPlugin(Plugins me) {
		
		String dbType = PropKit.get("jdbc.dbType");
		C3p0Plugin cp = getC3p0Plugin(dbType);
		
		//Oracle驱动
        //cp. setDriverClass("oracle.jdbc.driver.OracleDriver"); 
		me.add(cp);
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp);
		arp.setShowSql(true);
		
		if(dbType.equals("sqlserver")){
			arp.setDialect(new AnsiSqlDialect());
			arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		}else if(dbType.equals("oracle")){
			 //Oracle方言  
	        arp.setDialect(new OracleDialect());  
	        // 大小写敏感
	        arp.setContainerFactory(new CaseInsensitiveContainerFactory());  
		}
		
		com.future.link.user.model._MappingKit.mapping(arp);
		com.future.link.consumer.model._MappingKit.mapping(arp);
		com.future.link.goods.model._MappingKit.mapping(arp);
		com.future.link.base.model._MappingKit.mapping(arp);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}
}
