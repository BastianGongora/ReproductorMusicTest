package com.example.reproductormusic



data class Song(
    val title: String,
    val audioResId: Int,
    val imageResId: Int
){}

class AppConstant {
    companion object{
        const val LOG_MAIN_ACTIVITY = "MainActivityReproductor"
        const val MEDIA_PLAYER_POSITION = "savePosicion"
        const val IS_PLAYING = "play"
        const val CURREN_INDEX_SONG = "Index"
        val songs = listOf(
            Song("pretty please Remix -Dua Lipa", R.raw.pp_remix,R.drawable.pretty_please),
            Song("Summertime Sadness Remix - Lana del Rey", R.raw.lr_ss,R.drawable.lr_ss),
            Song("in the end Remix - Linkin Park", R.raw.lp_in_the_end_remix,R.drawable.lp_in_the_emd_remix)
        )
    }
}