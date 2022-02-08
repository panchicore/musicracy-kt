package com.panchicore.musicracy.data.model

import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("Playlist")
class PlaylistModel: ParseObject() {
    var name: String?
        get() = getString("name")
        set(value) {
            put("name", value!!)
        }
}