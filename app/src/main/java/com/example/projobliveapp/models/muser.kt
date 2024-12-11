package com.example.projobliveapp.models



data class MUser(
    val id: String?,
    val userId: String,
    val displayName: String,
    val firstName: String,
    val lastName: String,
    val location: String,
    val avatarUrl: String,
    val quote: String,
    val profession: String
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "first_name" to this.firstName,
            "last_name" to this.lastName,
            "location" to this.location,
            "quote" to this.quote,
            "profession" to this.profession,
            "avatar_url" to this.avatarUrl
        )
    }
}
