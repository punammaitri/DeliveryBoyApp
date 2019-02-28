package com.aiprous.deliveryboy.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.adapter.OrderAdapter;
import com.aiprous.deliveryboy.application.DeliveryBoyApp;
import com.aiprous.deliveryboy.model.AllOrderModel;
import com.aiprous.deliveryboy.utils.APIConstant;
import com.aiprous.deliveryboy.utils.BaseActivity;
import com.aiprous.deliveryboy.utils.CustomProgressDialog;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aiprous.deliveryboy.utils.APIConstant.GETALLORDERDETAIL;
import static com.aiprous.deliveryboy.utils.BaseActivity.isNetworkAvailable;


public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.rc_medicine_list)
    RecyclerView rc_medicine_list;

    @BindView(R.id.txtTitle)
    TextView txtTitle;

    ArrayList<AllOrderModel.Data> mlistModelsArray = new ArrayList<>();
    private Context mContext = this;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        txtTitle.setText("Orders");

        //Change status bar color
        BaseActivity baseActivity = new BaseActivity();
        baseActivity.changeStatusBarColor(this);

        //add static data into array list
      /*  mlistModelsArray.add(new ListModel(R.drawable.pending, "Order #12121212", "Pending"));
        mlistModelsArray.add(new ListModel(R.drawable.processing, "Order #12121212", "Processing"));
        mlistModelsArray.add(new ListModel(R.drawable.checked, "Order #12121212", "Completed"));
        mlistModelsArray.add(new ListModel(R.drawable.pending, "Order #12121212", "Pending"));
        mlistModelsArray.add(new ListModel(R.drawable.processing, "Order #12121212", "Processing"));
        mlistModelsArray.add(new ListModel(R.drawable.checked, "Order #12121212", "Completed"));
        mlistModelsArray.add(new ListModel(R.drawable.pending, "Order #12121212", "Pending"));
        mlistModelsArray.add(new ListModel(R.drawable.processing, "Order #12121212", "Processing"));
        mlistModelsArray.add(new ListModel(R.drawable.checked, "Order #12121212", "Completed"));*/


    }

    @OnClick(R.id.rlayout_back_button)
    public void BackPressList() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("db_id", DeliveryBoyApp.onGetId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!isNetworkAvailable(this)) {
            CustomProgressDialog.getInstance().showDialog(mContext, mContext.getResources().getString(R.string.check_your_network), APIConstant.ERROR_TYPE);
        } else {
            CustomProgressDialog.getInstance().showDialog(mContext, "", APIConstant.PROGRESS_TYPE);
            getAllOrder(jsonObject);
        }
    }

    private void getAllOrder(final JSONObject jsonObject) {
        AndroidNetworking.post(GETALLORDERDETAIL)
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            JsonObject getAllResponse = (JsonObject) new JsonParser().parse(response.toString());
                            String status = getAllResponse.get("status").getAsString();

                            if (status.equals("success")) {
                                JsonArray getAllProductList = getAllResponse.get("data").getAsJsonArray();

                                mlistModelsArray.clear();
                                for (int i = 0; i < getAllProductList.size(); i++) {
                                    JsonObject mAllData = getAllProductList.get(i).getAsJsonObject();
                                    String increment_id = "Order #" + mAllData.get("increment_id").getAsString();
                                    String mStatus = mAllData.get("status").getAsString();
                                    String created_at = mAllData.get("created_at").getAsString();

                                    //To access address
                                    JsonObject mShippingAddress = mAllData.get("shipping_address").getAsJsonObject();
                                    String mAddress = mShippingAddress.get("street").getAsString();
                                    String mCity = mShippingAddress.get("city").getAsString();
                                    String fullAddress = mAddress + "," + mCity;

                                    //add to constructor
                                    AllOrderModel.Data allOrderModel = new AllOrderModel.Data(increment_id, mStatus, created_at, fullAddress);
                                    allOrderModel.setEntity_id(increment_id);
                                    allOrderModel.setStatus(mStatus);
                                    allOrderModel.setCreated_at(created_at);
                                    allOrderModel.setAddress(fullAddress);
                                    mlistModelsArray.add(allOrderModel);
                                }

                                layoutManager = new LinearLayoutManager(mContext);
                                rc_medicine_list.setLayoutManager(new LinearLayoutManager(OrderActivity.this, LinearLayoutManager.VERTICAL, false));
                                rc_medicine_list.setHasFixedSize(true);
                                rc_medicine_list.setAdapter(new OrderAdapter(mContext, mlistModelsArray));
                            }
                            CustomProgressDialog.getInstance().dismissDialog();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            CustomProgressDialog.getInstance().dismissDialog();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        CustomProgressDialog.getInstance().dismissDialog();
                        //Toast.makeText(CartActivity.this, "Something wrong at server site", Toast.LENGTH_SHORT).show();
                        Log.e("Error", "onError errorCode : " + error.getErrorCode());
                        Log.e("Error", "onError errorBody : " + error.getErrorBody());
                        Log.e("Error", "onError errorDetail : " + error.getErrorDetail());
                    }
                });
    }
}
