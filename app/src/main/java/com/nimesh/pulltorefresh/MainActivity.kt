package com.nimesh.pulltorefresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nimesh.pulltorefresh.ui.theme.PullToRefreshTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PullToRefreshTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val items = remember {
                        (1..100).map { "Item $it" }
                    }
                    var isRefresing by remember { mutableStateOf(false) }
                    val scope = rememberCoroutineScope()

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        PullToRefreshLazyColumn(
                            items = items,
                            content = { itemTitle ->
                                Text(
                                    text = itemTitle,
                                    modifier = Modifier.padding(16.dp)
                                )
                            },
                            isRefreshing = isRefresing,
                            onRefresh = {
                                scope.launch {
                                    isRefresing = true
                                    delay(3000L)
                                    isRefresing = false
                                }
                            }
                        )
                        Button(
                            onClick = { isRefresing = true },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        ) {
                            Text(text = "Refresh")
                        }
                    }
                }
            }
        }
    }
}