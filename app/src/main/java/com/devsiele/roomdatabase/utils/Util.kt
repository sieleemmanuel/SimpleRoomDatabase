package com.devsiele.roomdatabase.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.devsiele.roomdatabase.model.Note

class Util {
    fun formatNotes(notes: List<Note>): Spanned {
        val sb = StringBuilder()
        sb.apply {
            notes.forEach {
                append("${it.noteId}<br>")
                append("${it.noteTitle}<br>")
                append("${it.noteCategory}<br>")
                append("${it.noteText}<br><br>")
            }
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
        } else {
            HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }
}