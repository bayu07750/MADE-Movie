package com.bayu.mademoviecompose.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bayu.mademoviecompose.presentation.UiState
import com.bayu.mademoviecompose.presentation.component.ScaffoldForCommonScreen
import com.bayu.mademoviecompose.presentation.home.MovieItem
import com.bayu07750.mademovie.core.R
import com.bayu07750.mademovie.core.domain.model.Movie

@Composable
fun BookmarkScreen(
    uiState: UiState<List<Movie>>,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (_, _, _, data) = uiState

    ScaffoldForCommonScreen(
        uiState = uiState,
        modifier = modifier,
        topBarTitle = stringResource(id = R.string.bookmark),
        messageEmpty = stringResource(id = R.string.no_bookmarked_movies_yet)
    ) { innerModifier ->
        if (!data.isNullOrEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                contentPadding = PaddingValues(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = innerModifier,
            ) {
                items(data) { movie ->
                    MovieItem(image = movie.poster, onClick = { onMovieClicked.invoke(movie) })
                }
            }
        }
    }
}