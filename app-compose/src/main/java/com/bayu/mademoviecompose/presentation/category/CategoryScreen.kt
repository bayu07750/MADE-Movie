package com.bayu.mademoviecompose.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bayu.mademoviecompose.presentation.UiState
import com.bayu.mademoviecompose.presentation.ui.theme.CharlestonGreen
import com.bayu07750.mademovie.core.R
import com.bayu07750.mademovie.core.domain.model.Genre

@Composable
fun CategoryScreen(
    uiState: UiState<List<Genre>>,
    onClickedItemGenre: (Genre) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (isLoading, isError, message, data) = uiState

    Scaffold(
        modifier = modifier
            .systemBarsPadding()
            .fillMaxSize(),
        topBar = {
            AppTopBar(title = stringResource(id = R.string.genre))
        }
    ) { innerPadding ->
        val innerModifier = Modifier.padding(innerPadding)
        when {
            !data.isNullOrEmpty() -> {
                ListMovieGenre(
                    data = data,
                    onClickedItem = onClickedItemGenre,
                    modifier = innerModifier
                )
            }

            else -> {
                Box(modifier = innerModifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    when {
                        isLoading -> {
                            CircularProgressIndicator()
                        }

                        data.isNullOrEmpty() -> {
                            val text = if (isError) message else stringResource(id = R.string.no_data)
                            Text(text = text.orEmpty())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppTopBar(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.h6)
    }
}

@Composable
fun ListMovieGenre(
    data: List<Genre>,
    onClickedItem: (Genre) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        modifier = modifier.fillMaxSize(),
        state = lazyGridState,
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(data, key = { it.id }) { genre ->
            GenreItem(
                title = genre.name,
                image = genre.poster,
                onClick = { onClickedItem.invoke(genre) },
                modifier = Modifier
            )
        }
    }
}

@Composable
fun GenreItem(
    title: String,
    image: String,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = true,
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
            ),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .crossfade(400)
                    .build(),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                fallback = painterResource(id = R.drawable.placeholder),
            )
            GradientCharlestonGreen(modifier = Modifier.matchParentSize())
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun GradientCharlestonGreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.onBackground.copy(alpha = 0f),
                        CharlestonGreen
                    )
                )
            )
    )
}
