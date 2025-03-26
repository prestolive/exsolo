package cn.exsolo.batis.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Map;

public class TinyCache<K, V> {

    private final int maxSize;
    private final Map<K, V> cacheMap;
    private final ConcurrentLinkedQueue<K> accessOrder;

    public TinyCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Cache size must be greater than zero.");
        }
        this.maxSize = maxSize;
        this.cacheMap = new ConcurrentHashMap<>();
        this.accessOrder = new ConcurrentLinkedQueue<>();
    }

    public V get(K key) {
        if (cacheMap.containsKey(key)) {
            // Move accessed key to the end of the queue to maintain access order
            accessOrder.remove(key);
            accessOrder.add(key);
            return cacheMap.get(key);
        }
        return null;
    }

    public void put(K key, V value) {
        if (cacheMap.containsKey(key)) {
            // Update existing key
            cacheMap.put(key, value);
            // Move updated key to the end of the queue
            accessOrder.remove(key);
            accessOrder.add(key);
        } else {
            // Check if the cache has reached its maximum size
            if (cacheMap.size() >= maxSize) {
                // Remove the least recently accessed item
                K lruKey = accessOrder.poll();
                if (lruKey != null) {
                    cacheMap.remove(lruKey);
                }
            }
            // Add new key
            cacheMap.put(key, value);
            accessOrder.add(key);
        }
    }

    public int size() {
        return cacheMap.size();
    }

    public void clear() {
        cacheMap.clear();
        accessOrder.clear();
    }

    public static void main(String[] args) {
        TinyCache<Integer, String> cache = new TinyCache<>(2);
        
        // Example usage
        cache.put(1, "One");
        cache.put(2, "Two");

        System.out.println("Get 1: " + cache.get(1)); // Output: One
        System.out.println("Get 2: " + cache.get(2)); // Output: Two
        
        cache.put(3, "Three");

        System.out.println("Get 1: " + cache.get(1)); // Output: null
        
        // Adding more items to trigger eviction when size exceeds 100
    }
}