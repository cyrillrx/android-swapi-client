package com.cyrillrx.starwarsapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cyrillrx.logger.Logger
import com.cyrillrx.starwarsapi.adapter.BaseAdapter
import com.cyrillrx.swapi.model.ResultList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Cyril Leroux
 *         Created on 06/04/2018.
 */
abstract class ListActivity<T : Any> : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private val adapter = BaseAdapter()

    private val callback = object : Callback<ResultList<T>> {

        override fun onResponse(call: Call<ResultList<T>>?, response: Response<ResultList<T>>?) {
            val body = response?.body() ?: return

            adapter.addAll(body.results)

            // TODO Load next only if loader visible
            body.next?.let { loadNextPage(it, this) }
        }

        override fun onFailure(call: Call<ResultList<T>>?, t: Throwable?) {
            Logger.error(TAG, "", t)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recyclerView = findViewById(R.id.recycler)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.add(title)

        sendRequest(callback)
    }

    protected abstract fun sendRequest(callback: Callback<ResultList<T>>)

    protected abstract fun loadNextPage(url: String, callback: Callback<ResultList<T>>)

    companion object {
        private val TAG = ListActivity::class.java.simpleName
    }
}
