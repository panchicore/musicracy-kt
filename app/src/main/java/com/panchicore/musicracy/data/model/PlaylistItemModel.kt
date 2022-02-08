package com.panchicore.musicracy.data.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseRelation
import com.parse.ParseUser

@ParseClassName("PlaylistItem")
class PlaylistItemModel: ParseObject() {

    var playlist: PlaylistModel?
        get() = getParseObject("playlist") as PlaylistModel
        set(value) {
            put("playlist", value!!)
        }

    var name : String?
        get() = getString("name")
        set(value) {
            put("name", value!!)
        }

    var sourceId : String?
        get() = getString("sourceId")
        set(value) {
            put("sourceId", value!!)
        }

    var user : ParseUser?
        get() = getParseUser("user")
        set(value) {
            put("user", value!!)
        }

    var thumbnailUrl : String?
        get() = getString("thumbnailUrl")
        set(value) {
            put("thumbnailUrl", value!!)
        }

    var status : String?
        get() = getString("status")
        set(value) {
            put("status", value!!)
        }
}
