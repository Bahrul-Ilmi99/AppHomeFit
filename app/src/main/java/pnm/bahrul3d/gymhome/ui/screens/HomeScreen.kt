package pnm.bahrul3d.gymhome.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pnm.bahrul3d.gymhome.ui.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    onDifficultySelected: (String) -> Unit,
    onViewHistory: () -> Unit,
    onLogout: () -> Unit
) {
    val difficulties = listOf("Beginner", "Intermediate", "Advanced")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "GymHome", 
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    ) 
                },
                actions = {
                    IconButton(onClick = onViewHistory) {
                        Icon(Icons.Default.History, contentDescription = "History", tint = Color.White)
                    }
                    IconButton(onClick = {
                        authViewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.White)
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
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                            )
                        )
                    )
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Column {
                    Text(
                        "Halo, Selamat Datang!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                    Text(
                        "Siap untuk latihan hari ini?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Body Section
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    "Pilih Level Kamu",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                difficulties.forEach { difficulty ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onDifficultySelected(difficulty) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = difficulty,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = when(difficulty) {
                                        "Beginner" -> "Latihan dasar & ringan"
                                        "Intermediate" -> "Mulai tantangan baru"
                                        else -> "Latihan intensitas tinggi"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                            // Placeholder for icon/arrow
                            Text("➔", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}
