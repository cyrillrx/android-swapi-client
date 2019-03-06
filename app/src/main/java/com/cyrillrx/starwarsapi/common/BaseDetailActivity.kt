package com.cyrillrx.starwarsapi.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cyrillrx.logger.Logger
import com.cyrillrx.starwarsapi.IntentKey
import com.cyrillrx.starwarsapi.R
import com.cyrillrx.swapi.model.Entity
import com.cyrillrx.utils.deserialize
import com.cyrillrx.utils.prettyPrint
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Cyril Leroux
 *          Created on 02/03/2019.
 */
abstract class BaseDetailActivity<T : Any> : AppCompatActivity() {

    private lateinit var entity: Entity

    private val callback = object : Callback<T> {

        override fun onResponse(call: Call<T>?, response: Response<T>?) {

            if (response?.isSuccessful == true) {
                response.body()?.let {
                    tvContent.text = it.prettyPrint()
                    bind(it)
                }

            } else {
                Logger.error(TAG, call?.request()?.url()?.toString())
            }
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            Logger.error(TAG, call?.request()?.url()?.toString() + " - ${t?.message}", t)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        entity = intent.getStringExtra(IntentKey.ENTITY).deserialize()

        entity.url?.let { url -> sendRequest(url) }
    }

    private fun sendRequest(url: String) {
        getApiCall(url).enqueue(callback)
    }

    protected abstract fun getApiCall(url: String): Call<T>

    protected abstract fun bind(body: T)

    companion object {
        private val TAG = BaseDetailActivity::class.java.simpleName
    }
}