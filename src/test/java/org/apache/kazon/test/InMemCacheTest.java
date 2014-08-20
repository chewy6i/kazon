package org.apache.kazon.test;

import org.apache.kazon.cache.InMemCache;

public class InMemCacheTest {

	public static void main(String[] args) {
		InMemCache <String, Object> inMemCache;
		inMemCache = new InMemCache<String, Object>();
		inMemCache.createCacheWithWriteExpiry(100, 1);

	}
	

}
