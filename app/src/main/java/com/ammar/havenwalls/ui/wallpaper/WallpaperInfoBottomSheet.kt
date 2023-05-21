package com.ammar.havenwalls.ui.wallpaper

import android.content.res.Configuration
import android.text.format.Formatter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ammar.havenwalls.extensions.capitalise
import com.ammar.havenwalls.model.Tag
import com.ammar.havenwalls.model.Wallpaper
import com.ammar.havenwalls.model.wallpaper1
import com.ammar.havenwalls.ui.theme.HavenWallsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperInfoBottomSheet(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    onDismissRequest: () -> Unit = {},
    onTagClick: (tag: Tag) -> Unit = {},
    onUploaderClick: () -> Unit = {},
    onSourceClick: () -> Unit = {},
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
    ) {
        WallpaperInfoBottomSheetContent(
            modifier = contentModifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                )
                .heightIn(min = 100.dp),
            wallpaper = wallpaper,
            onTagClick = onTagClick,
            onUploaderClick = onUploaderClick,
            onSourceClick = onSourceClick,
        )
    }
}

@Composable
fun WallpaperInfoBottomSheetContent(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    onTagClick: (tag: Tag) -> Unit = {},
    onUploaderClick: () -> Unit = {},
    onSourceClick: () -> Unit = {},
) {
    val context = LocalContext.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Text(
        //     text = stringResource(R.string.properties),
        //     style = MaterialTheme.typography.headlineSmall,
        // )

        if (!wallpaper.tags.isNullOrEmpty()) {
            TagsRow(
                modifier = Modifier.fillMaxWidth(),
                tags = wallpaper.tags,
                onTagClick = onTagClick,
            )
        }

        if (wallpaper.colors.isNotEmpty()) {
            ColorsRow(
                modifier = Modifier.fillMaxWidth(),
                colors = wallpaper.colors,
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (wallpaper.source.isNotBlank()) {
                PropertyRow(title = "Source") {
                    Box(
                        modifier = Modifier
                            .alignByBaseline()
                            .weight(3f)
                    ) {
                        Text(
                            modifier = Modifier.clickable(onClick = onSourceClick),
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                            text = wallpaper.source,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            if (wallpaper.uploader != null) {
                UploaderRow(
                    uploader = wallpaper.uploader,
                    onClick = onUploaderClick,
                )
            }
            PropertyRow(title = "Category") {
                Box(
                    modifier = Modifier
                        .alignByBaseline()
                        .weight(3f)
                ) {
                    Text(
                        text = wallpaper.category.capitalise(),
                    )
                }
            }
            PropertyRow(
                title = "Resolution",
                text = wallpaper.resolution.toString(),
            )
            PropertyRow(
                title = "Size",
                text = Formatter.formatShortFileSize(context, wallpaper.fileSize),
            )
            PropertyRow(
                title = "Views",
                text = wallpaper.views.toString()
            )
            PropertyRow(
                title = "Favorites",
                text = wallpaper.favorites.toString()
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewWallpaperInfoBottomSheetContent() {
    HavenWallsTheme {
        Surface {
            WallpaperInfoBottomSheetContent(
                modifier = Modifier.padding(16.dp),
                wallpaper = wallpaper1,
            )
        }
    }
}
