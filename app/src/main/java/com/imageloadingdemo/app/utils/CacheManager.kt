import android.graphics.Bitmap
import android.util.LruCache

object CacheManager {
    private val cacheSize = 4 * 1024 * 1024 // 4MB cache size (adjust as needed)
    private val memoryCache = LruCache<String, Bitmap>(cacheSize)

    fun saveImageToCache(key: String, bitmap: Bitmap) {
        memoryCache.put(key, bitmap)
    }

    fun getImageFromCache(key: String): Bitmap? {
        return memoryCache.get(key)
    }
}