package org.apache.kazon.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class InMemCache<K, V> { 
	private static final Log LOG = LogFactory.getLog(InMemCache.class);
	
	private LoadingCache<K, V> lruCache; 

	public void createCacheWithWriteExpiry(int maxSize, int expire) { 
		lruCache = makeCache(maxSize, 0, expire); 
	} 

	public void createCacheWithAccessExpiry(int maxSize, int expire) { 
		lruCache = makeCache(maxSize, expire, 0); 
	} 

	private class LoadingHandler extends CacheLoader<K, V> { 
		@Override public V load(K key) throws Exception {
			LOG.info("Loadkey: " + key);
			return null; 
		} 
	}

	private LoadingCache<K, V> makeCache(int maxSize, int expireAfterAccess, int expireAfterWrite) { 
		LOG.info("inside initCacheWithWriteExpiry"); 
		CacheBuilder<K, V> cacheBuilder = CacheBuilder.newBuilder(). 
							removalListener(new RemovalHandler()). 
							maximumSize(maxSize);
		if (expireAfterAccess !=0) {
			cacheBuilder = cacheBuilder.expireAfterAccess(expireAfterAccess, TimeUnit.MINUTES);
			
		} else if (expireAfterWrite != 0) {
			cacheBuilder = cacheBuilder.expireAfterWrite(expireAfterWrite, TimeUnit.MINUTES);
		}
		
		return cacheBuilder.build(new LoadingHandler());
	} 
	
	private class RemovalHandler implements RemovalListener<K, V> { 
		public void onRemoval(RemovalNotification<K, V> notification) { 
			RemovalCause cause = notification.getCause(); 
			//COLLECTED, REPLACED, EXPIRED, EXPLICIT, SIZE 
			LOG.info("Removekey: "+ notification.getKey() + " " + cause.toString()); 
		} 
	} 	


	public V getIfPresent(K key) { 
		return lruCache.getIfPresent(key); 
	} 

	public void put(K key, V value) { 
		lruCache.put(key, value); 
	} 

	public long size() { 
		return lruCache.size(); 
	} 

	public V get(K key) throws ExecutionException { 
		return lruCache.get(key); 
	} 
}

