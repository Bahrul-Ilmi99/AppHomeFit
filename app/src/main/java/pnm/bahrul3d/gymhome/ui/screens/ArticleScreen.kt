package pnm.bahrul3d.gymhome.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pnm.bahrul3d.gymhome.model.Article
import pnm.bahrul3d.gymhome.ui.ArticleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    onBack: () -> Unit
) {

    val articleViewModel: ArticleViewModel =
        viewModel()

    var title by remember {
        mutableStateOf("")
    }

    var content by remember {
        mutableStateOf("")
    }

    val articles by articleViewModel
        .articles
        .collectAsState()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Artikel Gym")
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)

        ) {

            OutlinedTextField(
                value = title,

                onValueChange = {
                    title = it
                },

                label = {
                    Text("Judul Artikel")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = content,

                onValueChange = {
                    content = it
                },

                label = {
                    Text("Isi Artikel")
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(

                onClick = {

                    if (
                        title.isNotEmpty() &&
                        content.isNotEmpty()
                    ) {

                        articleViewModel.addArticle(
                            title = title,
                            content = content,
                            author = "User GymHome"
                        )

                        title = ""
                        content = ""
                    }
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Publish Artikel")
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {

                items(articles) { article ->

                    ArticleCard(article)
                }
            }
        }
    }
}

@Composable
fun ArticleCard(
    article: Article
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )

    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.content
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "By ${article.author}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}