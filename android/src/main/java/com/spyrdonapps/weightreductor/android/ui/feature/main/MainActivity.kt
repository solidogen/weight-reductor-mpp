package com.spyrdonapps.weightreductor.android.ui.feature.main

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.spyrdonapps.common.model.ShoppingListItem
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

// todo remove. this resolves correctly
private val shoppingList = mutableListOf(
    ShoppingListItem(0, "Cucumbers 🥒", 1),
    ShoppingListItem(1, "Tomatoes 🍅", 2),
    ShoppingListItem(2, "Orange Juice 🍊", 3)
)