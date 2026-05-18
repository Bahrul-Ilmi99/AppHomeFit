package pnm.bahrul3d.gymhome.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import pnm.bahrul3d.gymhome.ui.GymViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    exerciseId: Int,
    viewModel: GymViewModel,
    onBack: () -> Unit
) {
    val exercises by viewModel.allExercises.collectAsState()
    val exercise = exercises.find { it.id == exerciseId } ?: return

    val totalTime = exercise.durationSeconds ?: 30
    var timeLeft by remember { mutableIntStateOf(totalTime) }
    var isRunning by remember { mutableStateOf(false) }

    val progress by animateFloatAsState(
        targetValue = timeLeft.toFloat() / totalTime,
        label = "TimerProgress"
    )

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            isRunning = false
            viewModel.insertLog(exercise, totalTime)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        exercise.name,
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
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Exercise Image Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                AsyncImage(
                    model = exercise.imageUrl ?: "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=500&q=80",
                    contentDescription = exercise.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ayo Semangat!",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = exercise.description, 
                    style = MaterialTheme.typography.bodyLarge, 
                    color = Color.Gray,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Circular Timer Visual
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(240.dp),
                    strokeWidth = 12.dp,
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60),
                        fontSize = 64.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Detik Tersisa",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Gray
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Control Button (Menggunakan CircleShape agar benar-benar bulat)
            Button(
                onClick = { isRunning = !isRunning },
                modifier = Modifier
                    .padding(bottom = 48.dp)
                    .size(80.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRunning) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }

            if (timeLeft == 0) {
                Text(
                    "Selesai! Anda Hebat! 🎉",
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
