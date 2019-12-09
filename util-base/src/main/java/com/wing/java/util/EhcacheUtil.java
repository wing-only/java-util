package com.wing.java.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * EhCache 缓存工具类
 * 使用String格式的value
 */
public class EhcacheUtil {

	private CacheManager cacheManager;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * 添加缓存
	 *
	 * @param key
	 * @param value
	 * @param timeToLiveSeconds 缓存生存时间（秒）
	 */
	public void set(String cacheName, String key, String value, int timeToLiveSeconds) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = new Element(
				key, value,
				0,// timeToIdleSeconds=0
				timeToLiveSeconds);
		cache.put(element);
	}

//	/**
//	 * 添加缓存
//	 *
//	 * @param key
//	 * @param value
//	 * @param timeToIdleSeconds 缓存过期时间（秒）
//	 */
//	public void set(String key, String value, int timeToIdleSeconds) {
//		Cache cache = cacheManager.getCache(cacheName);
//		Element element = new Element(
//				key, value,
//				timeToIdleSeconds,60 * 60 * 24);
//		cache.put(element);
//	}

	/**
	 * 添加缓存
	 * 使用默认生存时间
	 *
	 * @param key
	 * @param value
	 */
	public void set(String cacheName, String key, String value) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = new Element(
				key, value,
				0);
		cache.put(element);
	}

	/**
	 * 添加缓存
	 *
	 * @param key
	 * @param value
	 * @param timeToIdleSeconds 对象空闲时间，指对象在多长时间没有被访问就会失效。
	 *                          只对eternal为false的有效。传入0，表示一直可以访问。以秒为单位。
	 * @param timeToLiveSeconds 缓存生存时间（秒）
	 *                          只对eternal为false的有效
	 */
	public void set(String cacheName, String key, String value, int timeToIdleSeconds, int timeToLiveSeconds) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = new Element(
				key, value,
				timeToIdleSeconds,
				timeToLiveSeconds);
		cache.put(element);
	}

	/**
	 * 获取缓存
	 *
	 * @param key
	 * @return
	 */
	public String get(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = cache.get(key);
		if (element == null) {
			return null;
		}
		return (String) element.getObjectValue();
	}

}
