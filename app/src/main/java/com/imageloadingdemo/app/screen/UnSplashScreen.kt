package com.imageloadingdemo.app.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.imageloadingdemo.app.R
import com.imageloadingdemo.app.utils.downloadImage
import com.imageloadingdemo.app.viewmodel.ImageViewModel
import kotlinx.coroutines.launch

@Composable
fun UnSplashScreen(
    modifier: Modifier = Modifier,
    viewModel: ImageViewModel = hiltViewModel()
) {
    val response = viewModel.unSplashResponse.collectAsLazyPagingItems()
    val imageState = remember { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val imageBytes = downloadImage(response.itemSnapshotList.items)
            if (imageBytes != null) {
                imageState.value = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            }
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize()
    ) {

        items(response.itemCount) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(response[it]?.urls?.thumb ?: "-")
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .padding(5.dp)
                    .aspectRatio(1f)
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "",

                )
        }

        response.apply {
            when {
                loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                    item {
                        Text(text = "Error")
                    }
                }

                loadState.refresh is LoadState.NotLoading -> {
                }
            }
        }
    }

}