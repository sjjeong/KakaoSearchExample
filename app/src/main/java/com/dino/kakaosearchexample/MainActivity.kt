package com.dino.kakaosearchexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dino.kakaosearchexample.ui.extension.toSimpleDateFormat
import com.dino.kakaosearchexample.ui.remote.NetworkManager
import com.dino.kakaosearchexample.ui.remote.model.KakaoSearchBlogResponse
import com.dino.kakaosearchexample.ui.theme.KakaoSearchExampleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KakaoSearchExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val kakaoSearchApi = remember { NetworkManager.kakaoSearchApi }
    var items by remember {
        mutableStateOf(listOf<KakaoSearchBlogResponse>())
    }

    fun search(query: String) {
        coroutineScope.launch {
            val response = kakaoSearchApi.searchBlog(query)
            items = response.documents

            keyboardController?.hide()
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var query by remember { mutableStateOf("") }
            TextField(
                modifier = Modifier.weight(1f),
                value = query,
                onValueChange = { query = it },
                placeholder = { Text(text = "검색어를 입력하세요") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions {
                    search(query)
                }
            )
            Button(onClick = {
                search(query)
            }) {
                Text(text = "검색")
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items = items, key = { it.url }) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    border = BorderStroke(Dp.Hairline, Color.LightGray)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = AnnotatedString.fromHtml(item.blogName),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            modifier = Modifier.basicMarquee(),
                            text = AnnotatedString.fromHtml(item.title),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = AnnotatedString.fromHtml(item.contents),
                                fontSize = 14.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                            )
                            if (item.thumbnail.isNotEmpty()) {
                                AsyncImage(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .size(60.dp),
                                    model = item.thumbnail,
                                    contentDescription = item.title
                                )
                            }
                        }
                        Text(
                            text = item.dateTime.toSimpleDateFormat(),
                            fontSize = 8.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KakaoSearchExampleTheme {
        MainScreen()
    }
}