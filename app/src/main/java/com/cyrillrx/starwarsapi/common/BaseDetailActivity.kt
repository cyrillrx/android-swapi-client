package com.cyrillrx.starwarsapi.common

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cyrillrx.logger.Logger
import com.cyrillrx.starwarsapi.IntentKey
import com.cyrillrx.starwarsapi.R
import com.cyrillrx.swapi.model.Entity
import com.cyrillrx.utils.deserialize
import com.cyrillrx.utils.prettyPrint
import kotlinx.android.synthetic.main.activity_detail.loader
import kotlinx.android.synthetic.main.activity_detail.tvContent
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

            stopLoading()
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            Logger.error(TAG, call?.request()?.url()?.toString() + " - ${t?.message}", t)
            stopLoading()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data: Entity? = intent.getStringExtra(IntentKey.ENTITY)?.deserialize()
        if (data == null) {
            Logger.error(TAG, "Missing intent param ${IntentKey.ENTITY}")
            return
        }

        entity = data
        entity.url?.let { url -> sendRequest(url) }
    }

    protected abstract fun getApiCall(url: String): Call<T>

    protected abstract fun bind(body: T)

    protected open fun startLoading() {
        loader.visibility = View.VISIBLE
    }

    protected open fun stopLoading() {
        loader.visibility = View.GONE
    }

    private fun sendRequest(url: String) {
        startLoading()
        getApiCall(url).enqueue(callback)
    }

    companion object {
        private val TAG = BaseDetailActivity::class.java.simpleName
    }
}