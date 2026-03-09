package hu.bme.aut.android.recept.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.recept.service.TimerService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current

    var customMinutes by remember { mutableStateOf("") }

    fun startTimer(minutes: Int) {
        val intent = Intent(context, TimerService::class.java).apply {
            putExtra("DURATION", minutes * 60 * 1000L)
        }
        context.startForegroundService(intent)
    }

    fun stopTimer() {
        val intent = Intent(context, TimerService::class.java).apply { action = "STOP" }
        context.startService(intent)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Timer") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Select Duration", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { startTimer(1) }) { Text("1 Min") }
                Button(onClick = { startTimer(5) }) { Text("5 Min") }
                Button(onClick = { startTimer(10) }) { Text("10 Min") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Or Custom Time:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = customMinutes,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            customMinutes = it
                        }
                    },
                    label = { Text("Minutes") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(120.dp)
                )

                Button(
                    onClick = {
                        val minutes = customMinutes.toIntOrNull()
                        if (minutes != null && minutes > 0) {
                            startTimer(minutes)
                            customMinutes = ""
                        }
                    },
                    enabled = customMinutes.isNotEmpty()
                ) {
                    Text("Start")
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = { stopTimer() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Stop Timer")
            }
        }
    }
}