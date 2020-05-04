package com.example.feddbackapplication;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class MySingelton {

    private static MySingelton mInstance;
    private RequestQueue requestQueue;
    private Context ctx;

    public MySingelton(Context ctx){
        this.ctx = ctx;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            Cache cache = new DiskBasedCache(ctx.getCacheDir(), 1024*1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return  requestQueue;
    }

    public static synchronized MySingelton getmInstance(Context context){
        if(mInstance == null){
            mInstance = new MySingelton(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }

}
