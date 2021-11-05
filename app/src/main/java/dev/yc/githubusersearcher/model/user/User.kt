package dev.yc.githubusersearcher.model.user

import com.google.gson.annotations.SerializedName

data class UserItems(
    @SerializedName("items")
    val items: List<User>
)

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("login") val name: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (avatarUrl != other.avatarUrl) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + avatarUrl.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
