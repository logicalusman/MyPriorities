package com.mypriorities.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(@field:SerializedName("session_token") var sessionToken: String? = null,
                         @field:SerializedName("description") var name: String? = null)



