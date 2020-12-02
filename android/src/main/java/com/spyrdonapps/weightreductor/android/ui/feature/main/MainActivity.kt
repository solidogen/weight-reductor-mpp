package com.spyrdonapps.weightreductor.android.ui.feature.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.spyrdonapps.common.repository.SampleClientRepository
import com.spyrdonapps.common.repository.getLogger
import org.koin.android.ext.android.getKoin

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        setContentView(textView.apply {
            text = "elo"
        })

        lifecycleScope.launchWhenCreated {
            getKoin().get<SampleClientRepository>().fetchHome().let {
                textView.text = it.toString()

                getLogger().d("loaded", "MainActivity")
            }
        }
    }
}