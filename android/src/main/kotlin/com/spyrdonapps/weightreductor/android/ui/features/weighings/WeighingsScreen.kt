package com.spyrdonapps.weightreductor.android.ui.features.weighings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spyrdonapps.common.model.Weighing
import com.spyrdonapps.common.util.extensions.getStringWithFormat
import com.spyrdonapps.weightreductor.android.ui.features.main.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun WeighingsScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = getViewModel()
) {
    val weighings by viewModel.weighingsLiveData.observeAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        item {
            Text(text = "Weighings:")
        }
        items(weighings.orEmpty()) { item: Weighing ->
            Row(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(8.dp)
                    .background(shape = RoundedCornerShape(50), color = Color.Cyan)
            ) {
                Card(
                    border = BorderStroke(2.dp, Color.Black),
                    modifier = Modifier.padding(4.dp),
                ) {
                    Text(
                        text = item.weight.toString(),
                        modifier = Modifier.padding(4.dp),
                    )
                }
                Text(
                    text = item.date.getStringWithFormat(),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        item {
            Button(onClick = { viewModel.postWeighing() }) {
                Text(text = "Post weighing")
            }
            Button(onClick = { viewModel.reloadWeighings() }) {
                Text(text = "Reload weighings")
            }
        }
    }
}