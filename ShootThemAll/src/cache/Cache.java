package cache;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import model.User;

public class Cache {
	private static ConcurrentHashMap map;
	private static Cache cache;

	private Cache() {
		map = new ConcurrentHashMap();
	}
	
	public static Cache getCache() {
		if (cache == null) {
			cache = new Cache();
		}
		return cache;
	}
	
	public ConcurrentHashMap getCacheItems(){
		return map;
	}
}

