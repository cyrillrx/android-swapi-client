package com.cyrillrx.starwarsapi.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cyrillrx.starwarsapi.IntentKey
import com.cyrillrx.starwarsapi.R
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * @author Cyril Leroux
 *          Created on 02/03/2019.
 */
abstract class BaseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvContent.text = intent.getStringExtra(IntentKey.ENTITY)
    }
}