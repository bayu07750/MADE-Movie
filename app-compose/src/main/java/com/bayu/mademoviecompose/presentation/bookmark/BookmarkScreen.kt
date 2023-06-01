package com.bayu.mademoviecompose.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bayu.mademoviecompose.presentation.UiState
import com.bayu.mademoviecompose.presentation.component.ScaffoldForCommonScreen
import com.bayu07750.mademovie.core.R
import com.bayu07750.mademovie.core.domain.model.Movie

@Composable
fun BookmarkScreen(
    uiState: UiState<List<Movie>>,
    onItemMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (_, _, _, data) = uiState

    ScaffoldForCommonScreen(
        uiState = uiState,
        modifier = modifier,
        topBarTitle = stringResource(id = R.string.bookmark),
        messageEmpty = stringResource(id = R.string.no_bookmarked_movies_yet)
    ) { innerMod ->
        // TODO:
    }
}