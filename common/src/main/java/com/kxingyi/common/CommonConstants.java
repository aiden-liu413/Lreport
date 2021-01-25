package com.kxingyi.common;
/**
 * 通用常量类
 * @author admin
 *
 */
public class CommonConstants {

	/**
	 * 资产的扩展属性key值分类
	 * @author admin
	 *
	 */
	public interface RESOURCE_CONTENT_KEY {
		/**
		 * Hbase所使用的zookeeper的host
		 */
		public static final String hbase_zkQuorum = "zkQuorum";
		/**
		 * Hbase所使用zookeeper端口号
		 */
		public static final String hbase_zkClientPort = "zkClientPort";
		/**
		 * Hbase的znode信息
		 */
		public static final String hbase_znodeParent = "znodeParent";
		/**
		 * 认证方式
		 */
		public static final String MongoDB_authType = "authType";
		/**
		 * 认证机制
		 */
		public static final String MongoDB_authMechanism = "authMechanism";
		/**
		 * 认证数据库
		 */
		public static final String MongoDB_authDatabase = "authDatabase";
		/**
		 * 关系型数据库的数据库名称
		 */
		public static final String DBName = "dbName";
		/**
		 * Informix数据库服务名
		 */
		public static final String serviceName = "serviceName";

		public static final String DBVersion = "dbVersion";
	}
	
	/**
	 * 统一用分隔符
	 */
	public static final String SEPARATE = "#@";
}
