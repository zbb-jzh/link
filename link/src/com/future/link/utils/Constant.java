package com.future.link.utils;

public class Constant {
	
	/**
	 * 未删除
	 */
	public static final int UN_DELETE = 1;
	
	/**
	 * 删除
	 */
	public static final int DELETEED = 0;
	
	/**
	 * 上架在售
	 */
	public static final int SELLSTATUS_ON = 1;

	/**
	 * 商品下架
	 */
	public static final int SELLSTATUS_OFF = 0;
	
	/**
	 * 用户信息放在session里
	 */
	public static final String SESSION_USER = "user";
	
	/**
	 * 成功
	 */
	public static final int SUCCESS = 100;
	
	/**
	 * 为null
	 */
	public static final Object ISNULL = null;
	
	/**
	 * 为0
	 */
	public static final int ISZERO = 0;
	
	/**
	 * 是否为叶子
	 */
	public static final int IS_LEAF = 1;
	
	/**
	 * 是否为叶子
	 */
	public static final int IS_NOT_LEAF = 0;
	
	/**
	 * id为空
	 */
	public static final int ID_IS_EMPTY = -100;
	
	/**
	 * 对象为空
	 */
	public static final int OBJECT_IS_NULL = -101;
	
	/**
	 * 父类下有子类
	 */
	public static final int HAS_CHILDREN = -102;
	
	/**
	 * 文件为空
	 */
	public static final int FILE_IS_NULL = -103;
	
	/**
	 * 路径为空
	 */
	public static final int PATH_IS_NULL = -104;
	
	/**
	 * 状态传输为空
	 */
	public static final int STATUS_IS_NULL = -105;
	
	/**
	 * 分类下有商品
	 */
	public static final int HAS_Goods = -106;
	
	/**
	 * 已存在
	 */
	public static final int HAS_EXIT = -107;
	
	/**
	 * 不存在
	 */
	public static final int HAS_NOT_EXIT = -108;
	
	/**
	 * 密码错误
	 */
	public static final int PWD_IS_ERROR = -109;
	
	/**
	 * 登录失效
	 */
	public static final int LOGIN_INVALID = -110;
	
	/**
	 * 密码为空
	 */
	public static final int PWD_IS_EMPTY = -111;
	
	/**
	 * 原始密码错误
	 */
	public static final int OLDPWD_IS_ERROR = -112;
	
	/**
	 * 每个区域只能一个人
	 */
	public static final int AREA_IS_ONE = -113;
	
	
	/**
	 * 图片访问路径
	 */
	public static final String SYSTEM_IMAGE_URL = "system.image.url";
	
	/**
	 * 图片微信访问路径
	 */
	public static final String SYSTEM_IMAGE_WXPREFIX ="system.image.wxprefix";
	
	/**
	 * 图片上传路径
	 */
	public static final String SYSTEM_IMAGE_UPLOADURLPREFIX = "system.image.uploadurlprefix";
	
	/**
	 * 显示前缀
	 */
	public static final String SYSTEM_FILE_SHOWURLPREFIX = "system.file.showurlprefix";
	
	/**
	 * 本地处理参数
	 */
	public static final String SYSTEM_IMAGE_LOCALPROCESSOR = "system.image.localprocessor";

}
