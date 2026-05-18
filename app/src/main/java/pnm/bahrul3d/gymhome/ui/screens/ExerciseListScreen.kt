package pnm.bahrul3d.gymhome.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pnm.bahrul3d.gymhome.ui.GymViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen(
    difficulty: String,
    viewModel: GymViewModel,
    onExerciseSelected: (Int) -> Unit,
    onBack: () -> Unit
) {
    val exercises by viewModel.allExercises.collectAsState()
    val filteredExercises = exercises.filter { it.difficulty == difficulty }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Latihan $difficulty",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                            contentDescription = "Back", 
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Daftar Gerakan",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Pilih gerakan untuk memulai sesi latihan",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(filteredExercises) { exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .clickable { onExerciseSelected(exercise.id) },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column {
                        Box {
                            AsyncImage(
                                model = exercise.imageUrl ?: "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=500&q=80",
                                contentDescription = exercise.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Surface(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.BottomEnd),
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Timer, 
                                        contentDescription = null, 
                                        modifier = Modifier.size(16.dp),
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${exercise.durationSeconds}s",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = exercise.name, 
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = exercise.description, 
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
