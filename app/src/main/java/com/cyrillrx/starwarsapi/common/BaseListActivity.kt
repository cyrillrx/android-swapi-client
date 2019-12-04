package com.cyrillrx.starwarsapi.common

import com.cyrillrx.logger.Logger
import com.cyrillrx.swapi.model.ResultList
import com.cyrillrx.templates.BaseAdapter
import com.cyrillrx.templates.ListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Cyril Leroux
 *         Created on 06/04/2018.
 */
abstract class BaseListActivity<T : Any> : ListActivity() {

    override val adapter: BaseAdapter = BaseAdapter(ItemConverter())

    private val callback = object : Callback<ResultList<T>> {

        override fun onResponse(call: Call<ResultList<T>>?, response: Response<ResultList<T>>?) {

            if (response?.isSuccessful == true) {
                response.body()?.let { body ->
                    adapter.addAll(body.results)
                    // TODO Load next only if loader visible
                    body.next?.let { nextUrl -> loadNextPage(nextUrl) }
                }

            } else {
                Logger.error(TAG, call?.request()?.url()?.toString())
            }

            stopLoading()
        }

        override fun onFailure(call: Call<ResultList<T>>?, t: Throwable?) {
            Logger.error(TAG, call?.request()?.url()?.toString() + " - ${t?.message}", t)
            stopLoading()
        }
    }

    override fun sendRequest() {
        startLoading()
        adapter.add(title)
        getApiCall().enqueue(callback)
    }

    private fun loadNextPage(url: String) {
        getApiCall(url).enqueue(callback)
    }

    protected abstract fun getApiCall(): Call<ResultList<T>>

    protected abstract fun getApiCall(url: String): Call<ResultList<T>>

    companion object {
        private val TAG = BaseListActivity::class.java.simpleName
    }
}
