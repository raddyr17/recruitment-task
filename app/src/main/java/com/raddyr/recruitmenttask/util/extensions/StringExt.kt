package com.raddyr.recruitmenttask.util.extensions

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val URL_REGEX =
    "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)"

fun String.getUrlFromString(): String? {
    val pattern = Pattern.compile(URL_REGEX, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(this)
    return if (matcher.find()) {
        this.substring(matcher.start(0), matcher.end(0))
    } else null
}

fun String.removeUrlFromString(): String {
    return this.replace(URL_REGEX.toRegex(), "")
}

@SuppressLint("SimpleDateFormat")
fun String.formatDate(): String {
    var df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    return try {
        val date = df.parse(this)
        df = SimpleDateFormat("dd/MM/yyyy")
        if (date == null) this else df.format(date)
    }catch (e: ParseException) {
        this
    }
}