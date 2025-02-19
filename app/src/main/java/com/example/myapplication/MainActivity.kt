package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeGame()
        }
    }
}

@Composable
fun TicTacToeGame() {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2E), Color(0xFF3A3D4A))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Title
            Text(
                "Tic Tac Toe",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Game Board
            for (row in 0 until 3) {
                Row {
                    for (col in 0 until 3) {
                        val index = row * 3 + col
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .shadow(8.dp, RoundedCornerShape(16.dp))
                                .background(Color.White, RoundedCornerShape(16.dp))
                                .clickable {
                                    if (board[index].isEmpty() && winner == null) {
                                        board = board.toMutableList().apply { set(index, currentPlayer) }
                                        winner = checkWinner(board)
                                        if (winner == null) {
                                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                                        }
                                    }
                                }
                                .animateContentSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                board[index],
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (board[index] == "X") Color(0xFF762945) else Color(0xFF1E88E5)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Winner or Tie Message
            winner?.let {
                Text(
                    text = "Game Over: $it",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Restart Button
            Button(
                onClick = {
                    board = List(9) { "" }
                    winner = null
                    currentPlayer = "X"
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF762945)),
                shape = RoundedCornerShape(50)
            ) {
                Text("Restart", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

// Function to check the winner
fun checkWinner(board: List<String>): String? {
    val winPatterns = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
        listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Columns
        listOf(0, 4, 8), listOf(2, 4, 6) // Diagonals
    )
    for (pattern in winPatterns) {
        val (a, b, c) = pattern
        if (board[a].isNotEmpty() && board[a] == board[b] && board[a] == board[c]) {
            return "${board[a]} Wins!"
        }
    }
    return if (board.all { it.isNotEmpty() }) "It's a Tie!" else null
}

@Preview(showBackground = true)
@Composable
fun PreviewTicTacToe() {
    TicTacToeGame()
}
