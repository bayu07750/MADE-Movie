package com.bayu.mademoviecompose.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bayu.mademoviecompose.presentation.UiState
import com.bayu.mademoviecompose.presentation.ui.theme.CharlestonGreen
import com.bayu.mademoviecompose.presentation.ui.theme.MADEMovieTheme
import com.bayu.mademoviecompose.presentation.util.JCallback
import com.bayu07750.mademovie.core.R

/**
 * scaffold for wrapper or base, screen that has topBar, list (or anything), loading, message empty or error
 */
@Composable
fun ScaffoldForCommonScreen(
    uiState: UiState<*>,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBarTitle: String = stringResource(id = com.bayu.mademoviecompose.R.string.app_name),
    topBar: @Composable () -> Unit = { AppTopBar(title = topBarTitle) },
    messageEmpty: String = stringResource(id = R.string.no_data),
    messageError: String = messageEmpty,
    content: @Composable (innerModifier: Modifier) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier
            .systemBarsPadding()
            .fillMaxSize(),
        topBar = topBar
    ) { innerPadding ->
        val innerModifier = Modifier.padding(innerPadding)
        content.invoke(innerModifier)
        when {
            uiState.isLoading -> {
                CenterBox(modifier = innerModifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }

            uiState.isError -> {
                CenterBox(modifier = innerModifier.fillMaxSize()) {
                    Text(text = messageError)
                }
            }

            uiState.data == null || uiState.data is List<*> && uiState.data.isEmpty() -> {
                CenterBox(modifier = innerModifier.fillMaxSize()) {
                    Text(text = messageEmpty)
                }
            }
        }
    }
}


@Composable
fun CenterBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        content = content
    )
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

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.SemiBold),
        modifier = modifier
    )
}

@Composable
fun CircleButton(
    onClick: JCallback,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            )
            .background(color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f), shape = CircleShape)
            .size(44.dp)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        content.invoke()
    }
}

@Preview
@Composable
fun PreviewCircleButton() {
    MADEMovieTheme {
        CircleButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Rounded.ArrowBackIos, contentDescription = null, tint = MaterialTheme.colors.onBackground)
        }
    }
}