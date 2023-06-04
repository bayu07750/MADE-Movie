package com.bayu.mademoviecompose.presentation.home

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bayu.mademoviecompose.presentation.MainActivity
import com.bayu.mademoviecompose.presentation.UiState
import com.bayu.mademoviecompose.presentation.component.CenterBox
import com.bayu.mademoviecompose.presentation.component.SearchBar
import com.bayu.mademoviecompose.presentation.component.Title
import com.bayu07750.mademovie.core.R
import com.bayu07750.mademovie.core.data.cons.Cons
import com.bayu07750.mademovie.core.domain.model.Movie

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRetry: () -> Unit,
    onClickedMovie: (Movie) -> Unit,
    onClickedButtonSeeMore: (type: String) -> Unit,
    onSearch: () -> Unit,
    onChangeLanguage: (String) -> Unit,
    onChangeTrendingTimeWindow: (TrendingTimeWindow) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    if (uiState.successChangeLanguage) {
        LaunchedEffect(Unit) {
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }.also { context.startActivity(it) }
        }
    }

    Scaffold(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxSize(),
    ) { innerPadding ->
        val padModifier = Modifier.padding(horizontal = 16.dp)
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Language(
                icon = R.drawable.ic_language,
                onSelectedLanguage = onChangeLanguage,
                modifier = padModifier
            )
            Title(text = stringResource(id = R.string.welcome), modifier = padModifier)
            SearchBar(
                hint = stringResource(id = R.string.search_movie),
                onClick = onSearch,
                modifier = padModifier
                    .fillMaxWidth(),
            )
            listOf(
                stringResource(id = R.string.trending) to uiState.trendingState,
                stringResource(id = R.string.popular) to uiState.popularState,
                stringResource(id = R.string.now_playing) to uiState.nowPlayingState,
            ).forEach { (title, state) ->
                MovieSection(
                    uiState = state,
                    title = title,
                    onClickedButtonSeeMore = { onClickedButtonSeeMore.invoke(title) },
                    onClickedMovie = onClickedMovie,
                    onRetry = onRetry,
                    timeWindow = uiState.trendingTimeWindow,
                    onTimeWindowChanged = onChangeTrendingTimeWindow,
                    showDropDownTrendingTimeWindow = title == stringResource(id = R.string.trending),
                )
            }
        }
    }
}

@Composable
fun Language(
    @DrawableRes icon: Int,
    onSelectedLanguage: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val languages = remember {
        context.resources.getStringArray(R.array.list_available_language).toList()
    }
    var isDropdownOpen by remember { mutableStateOf(false) }

    Box {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = modifier
                .padding(top = 24.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                ) {
                    isDropdownOpen = true
                }
                .size(44.dp),
        )

        DropdownMenu(
            expanded = isDropdownOpen,
            onDismissRequest = { isDropdownOpen = false }
        ) {
            languages.forEach { language ->
                DropdownMenuItem(onClick = {
                    val selectedLang = when (language) {
                        context.getString(R.string.language_in) -> Cons.Language.ID
                        context.getString(R.string.language_ja) -> Cons.Language.JA
                        context.getString(R.string.language_es) -> Cons.Language.ES
                        context.getString(R.string.language_ar) -> Cons.Language.AR
                        else -> Cons.Language.EN
                    }
                    onSelectedLanguage.invoke(selectedLang)
                    isDropdownOpen = false
                }) {
                    Text(text = language)
                }
            }
        }
    }
}

@Composable
fun MovieSection(
    uiState: UiState<List<Movie>>,
    title: String,
    timeWindow: TrendingTimeWindow,
    onTimeWindowChanged: (TrendingTimeWindow) -> Unit,
    onClickedButtonSeeMore: () -> Unit,
    onClickedMovie: (Movie) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    showDropDownTrendingTimeWindow: Boolean = false,
) {
    val (isLoading, isError, message, data) = uiState

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MovieSectionHeader(
            title = title,
            timeWindow = timeWindow,
            onTimeWindowChanged = onTimeWindowChanged,
            onClickedButtonSeeMore = onClickedButtonSeeMore,
            showDropDownTrendingTimeWindow = showDropDownTrendingTimeWindow,
        )
        val stateModifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
        when {
            !data.isNullOrEmpty() -> {
                ListMovie(data = data, onClickedItem = onClickedMovie)
            }

            isLoading -> {
                CenterBox(modifier = stateModifier) {
                    CircularProgressIndicator()
                }
            }

            isError -> {
                CenterBox(modifier = stateModifier) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = message.orEmpty().ifEmpty { stringResource(id = R.string.message_error) },
                            style = MaterialTheme.typography.caption,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Button(onClick = onRetry, shape = CircleShape) {
                            Text(text = stringResource(id = R.string.button_retry))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieSectionHeader(
    title: String,
    timeWindow: TrendingTimeWindow,
    onTimeWindowChanged: (TrendingTimeWindow) -> Unit,
    onClickedButtonSeeMore: () -> Unit,
    modifier: Modifier = Modifier,
    showDropDownTrendingTimeWindow: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium
            )
            if (showDropDownTrendingTimeWindow) {
                TrendingTimeWindowDropDown(
                    timeWindow = timeWindow,
                    onTimeWindowChanged = onTimeWindowChanged
                )
            }
        }
        TextButton(onClick = onClickedButtonSeeMore) {
            Text(
                text = stringResource(id = R.string.see_more),
                style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.onBackground.copy(.8f))
            )
        }
    }
}

@Composable
fun TrendingTimeWindowDropDown(
    timeWindow: TrendingTimeWindow,
    onTimeWindowChanged: (TrendingTimeWindow) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Box(modifier = modifier) {
        Surface(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.DropdownList,
                ) {
                    expanded = true
                },
            shape = CircleShape,
            color = MaterialTheme.colors.onBackground.copy(alpha = .1F),
            contentColor = MaterialTheme.colors.onBackground,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            ) {
                val text = when (timeWindow) {
                    TrendingTimeWindow.WEEK -> stringResource(id = R.string.this_week)
                    TrendingTimeWindow.DAY -> stringResource(id = R.string.today)
                }
                Text(text = text, modifier = Modifier, style = MaterialTheme.typography.caption)
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listOf(stringResource(id = R.string.this_week), stringResource(id = R.string.today)).forEach {
                DropdownMenuItem(onClick = {
                    val selectedTimeWindow = when (it) {
                        context.getString(R.string.this_week) -> TrendingTimeWindow.WEEK
                        else -> TrendingTimeWindow.DAY
                    }
                    onTimeWindowChanged.invoke(selectedTimeWindow)
                    expanded = false
                }) {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
fun ListMovie(
    data: List<Movie>,
    onClickedItem: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(data) { movie ->
            MovieItem(image = movie.poster, onClick = { onClickedItem.invoke(movie) })
        }
    }
}

@Composable
fun MovieItem(
    image: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .crossfade(400)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clickable(
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .width(170.dp)
            .height(280.dp)
            .clip(RoundedCornerShape(12.dp)),
        placeholder = painterResource(id = R.drawable.placeholder),
        error = painterResource(id = R.drawable.placeholder),
        fallback = painterResource(id = R.drawable.placeholder),
    )
}