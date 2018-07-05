package com.mypriorities.data.network

import com.mypriorities.data.network.request.MyPriorityRequest
import com.mypriorities.data.network.response.DeleteMyPriorityResponse
import com.mypriorities.data.network.response.MyPriorityResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface MyPrioritiesApi {

    @Headers("Content-Type: application/json")
    @POST(MyPrioritiesNetworkAdapter.ADD_PRIORITY)
    fun addMyPriority(@Header(value = "Authorization") apiKey: String = MyPrioritiesNetworkAdapter.API_AUTH_KEY,
                      @Body myPriorityRequest: MyPriorityRequest): Observable<MyPriorityResponse>


    // [  { "id": 1, ......},{"id":2,.....}  ]
    @Headers("Content-Type: application/json")
    @GET(MyPrioritiesNetworkAdapter.GET_MY_PRIORITIES)
    fun getMyPriorities(@Header(value = "Authorization") apiKey: String = MyPrioritiesNetworkAdapter.API_AUTH_KEY):
            Observable<List<MyPriorityResponse>>

    @DELETE(MyPrioritiesNetworkAdapter.DELETE_MY_PRIORITIES)
    fun deleteMyPriority(@Header(value = "Authorization") apiKey: String = MyPrioritiesNetworkAdapter.API_AUTH_KEY,
                         @Path(value = "myPriorityId") myPriorityId: String): Observable<Response<Void>>


}