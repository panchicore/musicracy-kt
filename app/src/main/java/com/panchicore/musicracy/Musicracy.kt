package com.panchicore.musicracy

import android.app.Application
import com.panchicore.musicracy.data.model.PlaylistItemModel
import com.panchicore.musicracy.data.model.PlaylistModel
import dagger.hilt.android.HiltAndroidApp
import com.parse.Parse
import com.parse.ParseObject


@HiltAndroidApp
class Musicracy : Application() {
    override fun onCreate() {
        super.onCreate()
        ParseObject.registerSubclass(PlaylistModel::class.java)
        ParseObject.registerSubclass(PlaylistItemModel::class.java)
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .enableLocalDataStore()
                .build()
        )
    }
}