package com.panchicore.musicracy.data.network

import android.util.Log
import com.panchicore.musicracy.core.exception.PlaylistNotFoundException
import com.panchicore.musicracy.data.model.PlaylistItemModel
import com.panchicore.musicracy.data.model.PlaylistModel
import com.panchicore.musicracy.data.model.youtube.VideoItem
import com.parse.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.reflect.KClass

class ParseService @Inject constructor() {

    private val CURRENT_PLAYLIST_TAG = "currentPlaylist"

    suspend fun getPlaylistItems(playlist: PlaylistModel): Result<ArrayList<PlaylistItemModel>> {
        Log.i("getPlaylistItems ID=", playlist.objectId.toString())
        return withContext(Dispatchers.IO){
            try{
                val query = ParseQuery.getQuery(PlaylistItemModel::class.java)
                query.whereEqualTo("playlist", playlist)
                query.include("user")
                val results = query.find() as ArrayList
                Result.success(results)
            }catch (e: Exception){
                Result.failure(e)
            }

        }
    }

    suspend fun getYoutubeVideoInfoByUrl(videoUrl: String): Result<VideoItem> {
        return withContext(Dispatchers.IO){
            try {
                val parameters: HashMap<String, String> = HashMap()
                parameters["url"] = videoUrl
                val response = ParseCloud.callFunction<HashMap<String, Any>>("youtubeGetVideoInfoByUrl", parameters)
                val videoItem = mapToObject(response["item"] as Map<String, Any>, VideoItem::class)
                Result.success(videoItem)
            }catch (e: Exception){
                Result.failure(e)
            }
        }
    }

    suspend fun getYoutubeRelatedVideosByVideoId(videoId: String): Result<List<VideoItem>>{
        return withContext(Dispatchers.IO){
            try {
                val parameters: HashMap<String, String> = HashMap()
                parameters["videoId"] = videoId
                val response = ParseCloud.callFunction<HashMap<String, Any>>("youtubeGetRelatedVideosByVideoId", parameters)
                val relatedVideos = mutableListOf<VideoItem>()
                for(i in response["items"] as List<Any>){
                    relatedVideos.add(mapToObject(i as Map<String, Any>, VideoItem::class))
                }
                Result.success(relatedVideos)
            }catch (e: Exception){
                Result.failure(e)
            }
        }
    }

    suspend fun connectToPlaylist(name: String): Result<PlaylistModel> {
        return withContext(Dispatchers.IO){
            try {
                val query = ParseQuery.getQuery(PlaylistModel::class.java)
                query.whereEqualTo("name", name)
                query.setLimit(1)
                val results = query.find()
                if(results.size > 0){
                    val playlist: PlaylistModel = results[0]
                    playlist.pin(CURRENT_PLAYLIST_TAG)
                    Log.i("connectToPlaylist", "'$name' found, connecting...")
                    Result.success(playlist)
                }else{
                    Result.failure(PlaylistNotFoundException("'$name' was not found"))
                }

            }catch (e: Exception){
                Log.e("connectToPlaylist", e.toString())
                Result.failure(e)
            }
        }
    }

    suspend fun submitVideoItem(playlist: PlaylistModel, item: VideoItem): Result<PlaylistItemModel> {
        return withContext(Dispatchers.IO){
            try{
                val submission = PlaylistItemModel()
                submission.name = item.title
                submission.sourceId = item.id
                submission.user = ParseUser.getCurrentUser()
                submission.thumbnailUrl = item.thumbnailUrl
                submission.playlist = playlist
                submission.save()
                Result.success(submission)
            }catch (e: Exception){
                Result.failure(e)
            }
        }
    }

    suspend fun getCurrentPlaylist(): Result<PlaylistModel?>{
        return withContext(Dispatchers.IO){
            try {
                val query = ParseQuery.getQuery(PlaylistModel::class.java)
                query.fromPin(CURRENT_PLAYLIST_TAG)
                val results = query.find()
                if(results.size > 0){
                    val playlist: PlaylistModel = results[0]
                    Log.i("getCurrentPlaylist", "'${playlist.get("name")}' is the current playlist...")
                    Result.success(playlist)
                }else{
                    Log.i("getCurrentPlaylist", "No current playlist!")
                    Result.success(null)
                }
            }catch (e: Exception){
                Log.e("getCurrentPlaylist", e.toString())
                Result.failure(e)
            }
        }
    }

    fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>) : T {
        //Get default constructor
        val constructor = clazz.constructors.first()

        //Map constructor parameters to map values
        val args = constructor
            .parameters
            .map { it to map.get(it.name) }
            .toMap()

        //return object from constructor call
        return constructor.callBy(args)
    }

}