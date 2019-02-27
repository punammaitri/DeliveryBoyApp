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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
                            JSONObject getAllObject = new JSONObject(getAllResponse.toString()); //first, get the jsonObject
                            JSONArray getAllProductList = getAllObject.getJSONArray("data");//get the array with the key "response"

                            if (getAllProductList != null) {
                                mlistModelsArray.clear();
                                for (int i = 0; i < getAllProductList.length(); i++) {

                                    String increment_id = "Order #" + getAllProductList.getJSONObject(i).get("increment_id").toString();
                                    String status = getAllProductList.getJSONObject(i).get("status").toString();
                                    String created_at = getAllProductList.getJSONObject(i).get("created_at").toString();

                                    //To access address
                                    JSONObject shipping_address = getAllObject.getJSONObject("shipping_address");
                                    String mAddress = shipping_address.getString("street");
                                    String mCity = shipping_address.getString("city");
                                    String fullAddress = mAddress + "," + mCity;

                                    AllOrderModel.Data allOrderModel = new AllOrderModel.Data(increment_id, status, created_at, fullAddress);
                                    allOrderModel.setEntity_id(increment_id);
                                    allOrderModel.setStatus(status);
                                    allOrderModel.setCreated_at(created_at);
                                    allOrderModel.setAddress(fullAddress);

                                    mlistModelsArray.add(allOrderModel);
                                }
                            }
                            layoutManager = new LinearLayoutManager(mContext);
                            rc_medicine_list.setLayoutManager(new LinearLayoutManager(OrderActivity.this, LinearLayoutManager.VERTICAL, false));
                            rc_medicine_list.setHasFixedSize(true);
                            rc_medicine_list.setAdapter(new OrderAdapter(mContext, mlistModelsArray));

                            CustomProgressDialog.getInstance().dismissDialog();

                        } catch (JSONException e) {
                            CustomProgressDialog.getInstance().dismissDialog();
                            e.printStackTrace();
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
