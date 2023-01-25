package com.main.chat.data.storage.firebase.notification.api

import com.main.chat.data.entities.notification.PushNotification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {

    @Headers("Authorization: $AUTHORIZATION_KEY")
    @POST("fcm/send")
    suspend fun sendNotification(
        @Body pushNotification: PushNotification
    ) : Response<PushNotification>

    companion object {
        const val AUTHORIZATION_KEY = "key=AAAAkc3nsPA:APA91bFZbHNWo9qSDnaw4MFa4izLXAeFebRV6Ce7oFISQtSriKjFu3sxKO1JCwp9I96PsPWb_QtJ_BBnZc_Pi-rwLF0VbTPhj5jScg42RcqodlldObH_a3RZ6JOoypDjP1CJf7IbZ_4D"
        const val BASE_URL = "https://fcm.googleapis.com/"
    }
}