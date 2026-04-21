package org.ananchanon.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform