package pnm.bahrul3d.gymhome.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
    onLogout: () -> Unit,
    onOpenArticle: () -> Unit
) {

    val difficulties = listOf(
        "Beginner",
        "Intermediate",
        "Advanced"
    )

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "GymHome",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },

                actions = {

                    TextButton(
                        onClick = onOpenArticle
                    ) {

                        Text(
                            text = "Artikel",
                            color = Color.White
                        )
                    }

                    IconButton(
                        onClick = onViewHistory
                    ) {

                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History",
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = {

                            authViewModel.logout()
                            onLogout()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)

        ) {

            // HEADER
            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 32.dp,
                            bottomEnd = 32.dp
                        )
                    )
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                            )
                        )
                    )
                    .padding(
                        horizontal = 24.dp,
                        vertical = 32.dp
                    )

            ) {

                Column {

                    Text(
                        text = "Halo, Selamat Datang!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Siap untuk latihan hari ini?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // BODY
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {

                Text(
                    text = "Pilih Level Kamu",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                difficulties.forEach { difficulty ->

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                onDifficultySelected(difficulty)
                            },

                        shape = RoundedCornerShape(16.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),

                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )

                    ) {

                        Row(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),

                            verticalAlignment = Alignment.CenterVertically,

                            horizontalArrangement =
                                Arrangement.SpaceBetween

                        ) {

                            Column {

                                Text(
                                    text = difficulty,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(

                                    text = when (difficulty) {

                                        "Beginner" ->
                                            "Latihan dasar & ringan"

                                        "Intermediate" ->
                                            "Mulai tantangan baru"

                                        else ->
                                            "Latihan intensitas tinggi"
                                    },

                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }

                            Text(
                                text = "➔",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}