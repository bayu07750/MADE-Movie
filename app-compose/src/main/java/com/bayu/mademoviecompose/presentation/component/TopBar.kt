package com.bayu.mademoviecompose.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bayu.mademoviecompose.presentation.ui.theme.MADEMovieTheme
import com.bayu07750.mademovie.core.R

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
fun SearchBar(
    hint: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        modifier = modifier
            .clickable(
                role = Role.Button,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            ),
        shape = CircleShape,
        color = MaterialTheme.colors.onBackground.copy(alpha = .1f),
        contentColor = MaterialTheme.colors.onBackground
    ) {
        Row(
            modifier = Modifier
                .height(44.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(id = R.drawable.search), contentDescription = null)
            Text(text = hint, style = MaterialTheme.typography.caption)
        }
    }
}

@Preview
@Composable
fun PreviewSearchBar() {
    MADEMovieTheme {
        SearchBar(hint = "Search", onClick = {})
    }
}