package com.bayu.mademoviecompose.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bayu.mademoviecompose.presentation.UiState
import com.bayu.mademoviecompose.presentation.component.CenterBox
import com.bayu.mademoviecompose.presentation.component.CircleButton
import com.bayu.mademoviecompose.presentation.component.GradientCharlestonGreen
import com.bayu07750.mademovie.core.R
import com.bayu07750.mademovie.core.domain.model.Cast
import com.bayu07750.mademovie.core.domain.model.Genre
import com.bayu07750.mademovie.core.domain.model.MovieDetail

@Composable
fun DetailScreen(
    uiState: UiState<MovieDetail>,
    onBack: () -> Unit,
    onBookmarked: (MovieDetail) -> Unit,
    onGenreClicked: (Genre) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (isLoading, isError, message, data) = uiState
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        val innerModifier = Modifier.padding(innerPadding)
        when {
            isLoading -> {
                CenterBox(modifier = innerModifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }

            isError -> {
                CenterBox(modifier = innerModifier.fillMaxSize()) {
                    Text(text = message.orEmpty().ifEmpty { stringResource(id = R.string.message_error) })
                }
            }

            data != null -> {
                DetailContent(
                    data = data,
                    onBack = onBack,
                    onBookmarked = onBookmarked,
                    onGenreClicked = onGenreClicked,
                    modifier = innerModifier
                )
            }

            else -> {
                CenterBox(modifier = innerModifier.fillMaxSize()) {
                    Text(text = stringResource(id = R.string.no_data))
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    data: MovieDetail,
    onBack: () -> Unit,
    onBookmarked: (MovieDetail) -> Unit,
    onGenreClicked: (Genre) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val extraPaddingHorizontal = Modifier.padding(horizontal = 16.dp)
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.poster)
                    .crossfade(true)
                    .crossfade(400)
                    .build(),
                contentDescription = data.name,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                fallback = painterResource(id = R.drawable.placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(9f / 16f),
                contentScale = ContentScale.Crop,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = extraPaddingHorizontal
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(top = 8.dp),
            ) {
                CircleButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = null)
                }
                CircleButton(onClick = { onBookmarked.invoke(data) }) {
                    val icon = painterResource(
                        id = if (data.bookmarked) R.drawable.bookmark_select else R.drawable.bookmark_unselect,
                    )
                    Icon(painter = icon, contentDescription = null)
                }
            }
            GradientCharlestonGreen(modifier = Modifier.matchParentSize())
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.SemiBold),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = extraPaddingHorizontal,
                )
                ListGenreText(data = data.genres, onGenreClicked = onGenreClicked)
            }
        }
        ListCast(data = data.casts, onCastClicked = { })
        Text(
            text = stringResource(id = R.string.synopsis),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
            modifier = extraPaddingHorizontal,
        )
        Description(text = data.synopsis, modifier = extraPaddingHorizontal)
    }
}

@Composable
fun Description(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier)
}

@Composable
fun ListGenreText(
    data: List<Genre>,
    onGenreClicked: (Genre) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(data) { genre ->
            GenreTextItem(
                name = genre.name,
                onClick = { onGenreClicked.invoke(genre) }
            )
        }
    }
}

@Composable
fun GenreTextItem(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        text = name,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            )
            .background(
                color = MaterialTheme.colors.onBackground.copy(alpha = .1f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(8.dp)
            .then(modifier),
    )
}

@Composable
fun ListCast(
    data: List<Cast>,
    onCastClicked: (Cast) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(data) { cast ->
            CastItem(
                cast = cast,
                onClick = { onCastClicked.invoke(cast) }
            )
        }
    }
}

@Composable
fun CastItem(
    cast: Cast,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cast.poster)
                .crossfade(true)
                .crossfade(400)
                .build(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            fallback = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape),
        )
        Column {
            Text(text = cast.character, fontWeight = FontWeight.Medium)
            Text(text = cast.name, fontSize = 12.sp)
        }
    }
}