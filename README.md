# Connect 4 AI

A Java-based Tic-Tac-Toe game with AI opponents of varying difficulty levels, including Random, Beatable, and Unbeatable AI using the Minimax algorithm. Players can compete against another human or different AI modes.

## 🚀 Overview 

Tic Tac Toe is a solved game, with a draw being forced if both players play optimally. If a player plays optimally, he can win or draw based on the other player's moves. Two players play the game on a 3x3 grid where each player is assigned 'X' or 'O'. The game takes turns with each player placing their mark on the grid. The player wins if he places his mark in a completely horizontal, vertical, or diagonal way. The game is a draw if the grid is full with no player winning. The game contains 138 terminal states and 255,168 possible valid states. The complete game tree at the start of the game has a depth of 9 (first depth 0).

## 🏆 Features 

-  **Play Against AI or a Human Opponent**  
-  **Multiple AI Difficulty Levels**:  
  - **Random AI**  (Picks moves randomly)  
  - **Beatable AI**  (Makes some mistakes)  
  - **Unbeatable AI**  (Uses the Minimax algorithm)  
-  **Console-based UI**  
-  **Modular and Extensible Codebase**  

## 🎮 How to Play 

1. **Run the game** and enter the name of the player
2. **Choose the Game mode** select **1** to play against AI or **2** to play against another Human player.
3. If playing against AI, choose difficulty:  
   - **1** - Random AI 
   - **2** - Beatable AI  
   - **3** - Unbeatable AI (Minimax)
4. If playing against another Human, enter his name
5. **Enter your move** by selecting a row and column (1-3).
6. The **game ends** when a player wins or the board is full (draw).
7. **To replay the game** enter **1** or enter any other key **to exit**
8. **Enjoy playing!** 🎉

## 📂 Project Structure
- **src/**
  - **GameLogic/**
    - `Game.java` → Game management
    - `MoveScore.java` → Represents move evaluation in Minimax
  - **Grid/**
    - `Grid.java` → Represents the Tic-Tac-Toe board
  - **Players/**
    - `Player.java` → Base abstract class for all players
    - `HumanPlayer.java` → Handles human player input
    - `AIPlayer.java` → AI player with Minimax & difficulty levels
  - **Enums/**
    - `AIMode.java` → Enum for AI difficulty modes (RANDOM, BEATABLE, UNBEATABLE)
  - `Main.java` → Main game loop and entry point of the game

## :robot: AI Implementation

Due to the simplicity of Tic Tac TOe and its relatively easy computation, we can brute-force all the possible game states until they reach the terminal states easily without further optimizations. Although this works for Tic Tac Toe, more complex games (Connect 4, Chess, Go) will need many more optimizations to beat humans. This is meant as an introductory project to the world of zero-sum games and their AI.

This is a simple implementation of a brute-force minimax algorithm that has no extra optimizations like:
- Alpha-beta pruning
- Evaluation function
- etc...

The **Unbeatable AI** uses the **Minimax Algorithm** to find the best move by:
1. Finding all possible moves
2. Alternating between MIN and MAX opponents
3. Assigning each terminal state a value
   - +1 for a WIN
   - -1 for a LOSS
   - 0 for a TIE
4. Backtracking to choose the move with the highest score and better depth to play the game optimally

## 📌Future Improvements
- Add alpha-beta pruning for optimization

## References and Resources
- [Connect 4 Wikipedia page](https://en.wikipedia.org/wiki/Connect_Four)
- [Connect 4 Solver](https://connect4.gamesolver.org/)
- **Alpha-Beta Pruning**
  - [Sebastian Lague video](https://www.youtube.com/watch?v=l-hh51ncgDI)
  - [JavaTpoint article](https://www.javatpoint.com/ai-alpha-beta-pruning)
  - My OneNote Note:
- **Negamax Algorithm**
  - [Negamax Wikipedia page](https://en.wikipedia.org/wiki/Negamax) 
