package com.example.retrofitandgson.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.retrofitandgson.R
import com.example.retrofitandgson.model.Pockemon
import com.example.retrofitandgson.viewmodel.PockUiState

@Composable
fun HomeScreen(
    pokUiState: PockUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (pokUiState) {
        is PockUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PockUiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),  // Two columns
                contentPadding = contentPadding,
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp), // Spacing between columns
                verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between rows
            ) {
                items(pokUiState.pokemons) { pockemon ->
                    PockPhotoCard(pockemon = pockemon)
                }
            }
        }
        is PockUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */


@Composable
fun PhotosGridScreen(
    pockemons: List<Pockemon?>?,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        if (pockemons != null) {
            items(items = pockemons.map { it }) { pockemon ->
                if (pockemon != null) {
                    PockPhotoCard(
                        pockemon,
                        modifier = modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                    )
                }
            }
        }
    }
}
@Composable
fun PockPhotoCard(pockemon: Pockemon, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Display the Pokémon's image
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(pockemon.sprites?.regular)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.pockphoto),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // Adjust as needed
            )

            // Display the Pokémon's name
            Text(
                text = pockemon.name?.en ?: "Unknown Pokémon",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
