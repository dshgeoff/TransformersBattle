package com.aequilibrium.transformersbattle.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.aequilibrium.transformersbattle.data.entity.Transformers;
import com.aequilibrium.transformersbattle.data.interfaces.ITransformersDataSource;
import com.aequilibrium.transformersbattle.general.data.remote.BaseDataSource;
import com.aequilibrium.transformersbattle.general.network.APIAction;
import com.aequilibrium.transformersbattle.general.network.APIManager;
import com.aequilibrium.transformersbattle.general.network.APIModule;
import com.aequilibrium.transformersbattle.general.network.ErrorResponse;
import com.aequilibrium.transformersbattle.general.network.StringCallbackHandler;
import com.aequilibrium.transformersbattle.general.network.TransformersResponse;
import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransformersDataSource extends BaseDataSource implements ITransformersDataSource {

    public TransformersDataSource(@NonNull Context context) {
        super(context);
    }

    @Override
    public void getAllSpark(@NonNull final RetrieveAllSparkCallback callback) {
        final APIModule apiModule = new APIModule(APIAction.AllSpark);
        APIManager.getNetworkUtil(getContext()).requestString(
                Request.Method.GET,
                null,
                apiModule,
                new StringCallbackHandler<JSONObject, ErrorResponse>(JSONObject.class, ErrorResponse.class) {
                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onRespond() {
                        callback.onRespond();
                    }

                    @Override
                    public void onSuccess(JSONObject o) {

                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onResponse(TransformersResponse response) {
                        if (!TextUtils.isEmpty(response.json)) {
                            APIModule.setAllSpark(response.json);
                            callback.onRetrieveAllSparkSuccess(response.json);
                        }
                    }
                }, APIAction.AllSpark.toString()
        );
    }

    @Override
    public void createTransformer(@NonNull String name, @NonNull String team, @NonNull String strength, @NonNull String intelligence, @NonNull String speed, @NonNull String endurance, @NonNull String rank, @NonNull String courage, @NonNull String firepower, @NonNull String skill, @NonNull final CreateTransformerCallback callback) {
        final APIModule apiModule = new APIModule(APIAction.CreateTransformer);
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("team", team);
        params.put("strength", strength);
        params.put("intelligence", intelligence);
        params.put("speed", speed);
        params.put("endurance", endurance);
        params.put("rank", rank);
        params.put("courage", courage);
        params.put("firepower", firepower);
        params.put("skill", skill);
        APIManager.getNetworkUtil(getContext()).requestString(
                Request.Method.POST,
                params,
                apiModule,
                new StringCallbackHandler<Transformers.Transformer, ErrorResponse>(Transformers.Transformer.class, ErrorResponse.class) {
                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onRespond() {
                        callback.onRespond();
                    }

                    @Override
                    public void onSuccess(Transformers.Transformer transformer) {
                        callback.onCreateTransformerSuccess(transformer);
                    }

                    @Override
                    public void onError() {

                    }

                }, APIAction.CreateTransformer.toString());
    }

    @Override
    public void getTransformers(@NonNull final GetTransformersCallback callback) {
        final APIModule apiModule = new APIModule(APIAction.GetTransformersList);
        APIManager.getNetworkUtil(getContext()).requestString(
                Request.Method.GET,
                null,
                apiModule,
                new StringCallbackHandler<Transformers, ErrorResponse>(Transformers.class, ErrorResponse.class) {
                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onRespond() {
                        callback.onRespond();
                    }

                    @Override
                    public void onSuccess(Transformers transformers) {
                        callback.onGetTransformersSuccess(transformers);
                    }

                    @Override
                    public void onError() {

                    }
                }, APIAction.GetTransformersList.toString());
    }

    @Override
    public void updateTransformer(@NonNull String id, @NonNull String name, @NonNull String team,
                                  @NonNull String strength, @NonNull String intelligence, @NonNull String speed,
                                  @NonNull String endurance, @NonNull String rank, @NonNull String courage,
                                  @NonNull String firepower, @NonNull String skill, @NonNull final UpdateTransformerCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("team", team);
        params.put("strength", strength);
        params.put("intelligence", intelligence);
        params.put("speed", speed);
        params.put("endurance", endurance);
        params.put("rank", rank);
        params.put("courage", courage);
        params.put("firepower", firepower);
        params.put("skill", skill);
        final APIModule apiModule = new APIModule(APIAction.UpdateTransformer);
        APIManager.getNetworkUtil(getContext()).requestString(
                Request.Method.PUT,
                params,
                apiModule,
                new StringCallbackHandler<Transformers.Transformer, ErrorResponse>(Transformers.Transformer.class, ErrorResponse.class) {
                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onRespond() {
                        callback.onRespond();
                    }

                    @Override
                    public void onSuccess(Transformers.Transformer transformer) {
                        callback.onUpdateTransformerSuccess(transformer);
                    }

                    @Override
                    public void onError() {

                    }
                }, APIAction.UpdateTransformer.toString());
    }

    @Override
    public void getSingleTransformer(@NonNull String id, @NonNull final GetSingleTransformerCallback callback) {
        final APIModule apiModule = new APIModule(APIAction.GetTransformer);
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        APIManager.getNetworkUtil(getContext()).requestString(
                Request.Method.GET,
                params,
                apiModule,
                new StringCallbackHandler<Transformers.Transformer, ErrorResponse>(Transformers.Transformer.class, ErrorResponse.class) {
                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onRespond() {
                        callback.onRespond();
                    }

                    @Override
                    public void onSuccess(Transformers.Transformer o) {

                    }

                    @Override
                    public void onError() {

                    }

                }, APIAction.GetTransformer.toString()
        );
    }

    @Override
    public void deleteTransformer(@NonNull String id, final DeleteTransformerCallback callback) {
        final APIModule apiModule = new APIModule(APIAction.DeleteTransformer);
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        APIManager.getNetworkUtil(getContext()).requestString(
                Request.Method.DELETE,
                params,
                apiModule,
                new StringCallbackHandler<Transformers, ErrorResponse>(Transformers.class, ErrorResponse.class) {
                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onRespond() {
                        if (callback != null) {
                            callback.onRespond();
                        }
                    }

                    @Override
                    public void onSuccess(Transformers transformers) {
                        if (callback != null) {
                            callback.onDeleteTransformerSuccess(transformers);
                        }
                    }

                    @Override
                    public void onError() {

                    }

                }, APIAction.DeleteTransformer.toString()
        );
    }
}
