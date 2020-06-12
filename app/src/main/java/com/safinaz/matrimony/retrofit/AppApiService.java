package com.safinaz.matrimony.retrofit;

import com.google.gson.JsonObject;

import java.util.Map;

import com.safinaz.matrimony.Model.VendorCategoryResponse;
import com.safinaz.matrimony.Model.VendorResponse;
import com.safinaz.matrimony.Utility.Utils;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Use for api call and get your response in your activity or fragment
 * Created by Nasirali on 02-02-2019.
 */

public interface AppApiService {

    @FormUrlEncoded
    @POST(Utils.get_token)
    Call<JsonObject> getToken(@FieldMap Map<String, String> params);


    /**
     * @param file1  Crop image
     * @param file2  Original image
     * @param params All params in multipart request body
     * @return JsonObject
     */
    @Multipart
    @POST(Utils.register_upload_profile_image)
    Call<JsonObject> uploadPhoto(@Part MultipartBody.Part file1, @Part MultipartBody.Part file2, @PartMap() Map<String, RequestBody> params);

    @Multipart
    @POST(Utils.upload_horoscope)
    Call<JsonObject> uploadHoroscopePhoto(@Part MultipartBody.Part file1, @PartMap() Map<String, RequestBody> params);

    @Multipart
    @POST(Utils.upload_id_proof_photo)
    Call<JsonObject> uploadIdProof(@Part MultipartBody.Part file1, @PartMap() Map<String, RequestBody> params);

    @Multipart
    @POST(Utils.upload_photo_new)
    Call<JsonObject> uploadMyPhotoWithCrop(@Part MultipartBody.Part file1, @Part MultipartBody.Part file2, @PartMap() Map<String, RequestBody> params);

    @Multipart
    @POST(Utils.upload_photo_new)
    Call<JsonObject> uploadMyPhotoWithoutCrop(@Part MultipartBody.Part file1, @PartMap() Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST(Utils.GET_PERSONALIZED_DATA)
    Call<JsonObject> getPersonalizedList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Utils.UPDATE_PERSONALIZED_STATUS_REQUEST)
    Call<JsonObject> sendUpdateMatchMakerStatusRequest(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Utils.GET_MEETING_DATA)
    Call<JsonObject> getMeetingList(@FieldMap Map<String, String> params);

    @GET(Utils.GET_VENDOR_CATEGORIES)
    Call<VendorCategoryResponse> getVendorCategories();

    @GET(Utils.GET_VENDORS)
    Call<VendorResponse> getVendorCategories(@Query("category_id") String id);

}
