# Connect 4 AI

A Java-based Connect 4 game featuring a powerful AI opponent that leverages advanced techniques like alpha-beta pruning, transposition tables, and bitboard representation. Players can challenge the AI or play head-to-head with another human.

## 🚀 Overview 

Connect 4 is a classic two-player strategy game played on a 7-column by 6-row grid. Players take turns dropping colored discs into a column, and the disc occupies the lowest available slot. The objective is to be the first to connect four of one’s own discs in a row — vertically, horizontally, or diagonally. The game is solved, with the first player forcing a win if played perfectly.
Unlike Tic Tac Toe, Connect 4 is significantly more complex, with 4,531,985,219,092 (about 4.5 trillion) possible game states and deeper strategic considerations. The complete game tree at the start of the game has a depth of 42 (first depth 0). This project introduces an optimized AI capable of competing at a high level using modern search enhancements and efficient board encoding.

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

## :robot: AI Implementation

Connect 4's increased complexity demands more than a brute-force search. To enable intelligent decision-making at reasonable speeds, the AI employs several optimization techniques:

### Minimax with Alpha-Beta Pruning
Alpha-beta pruning significantly reduces the number of nodes evaluated in the game tree by pruning branches that cannot possibly influence the final decision. This allows deeper searches within the same time constraints.

### Transposition Tables
Transposition tables store the results of previously evaluated board states. Since the same game state can often be reached through different move sequences, this avoids redundant computations and accelerates decision-making.

### Bitboard Representation
Instead of using a 2D array, the game board is represented using bitboards — compact 64-bit integers where each bit represents a cell. This allows:
- Fast board evaluation using bitwise operations
- Quick detection of winning conditions
- Memory-efficient board state storage

### Opening Book
Using AI to generate a list of moves and there best response, I was able to generate a book of about 4-plies to help the game solve the opening sequences more faster.

### Move Ordering
The center columns are statistically proven to form more winning streaks. By exploring them first, the algorithm was able to prune more branches, leading to more efficiency in computation. 

### Evaluation Function
When the maximum search depth is reached or the game is not yet in a terminal state, a heuristic evaluation function scores the board. Factors considered include:
- Potential lines of 4
- Central column advantage
- Blocking opponent threats

The AI dynamically adjusts its evaluation depending on the game phase, enabling smart offensive and defensive play.

## 📌Future Improvements
- Incorporate a more advanced evaluation function to be able to distinguish between equal moves at the start of the game
- Add support for parallelized search using multithreading
- Implement iterative deepening with time control
- Test the game against multiple AI
- Rewrite the project in C++ to be more efficient and pass the 18-ply ceiling

## References and Resources
- [Main tutorial I followed](http://blog.gamesolver.org/solving-connect-four/01-introduction/)
- [Connect 4 Wikipedia page](https://en.wikipedia.org/wiki/Connect_Four)
- [Connect 4 Solver](https://connect4.gamesolver.org/)
- **Alpha-Beta Pruning**
  - [Sebastian Lague video](https://www.youtube.com/watch?v=l-hh51ncgDI)
  - [JavaTpoint article](https://www.javatpoint.com/ai-alpha-beta-pruning)
  - My OneNote Note:
- **Negamax Algorithm**
  - [Negamax Wikipedia page](https://en.wikipedia.org/wiki/Negamax)
  - My OneNote Note:
- **Bitboards**
  - [Bitboards and Connect 4](https://github.com/denkspuren/BitboardC4/blob/master/BitboardDesign.md)
  - My OneNote Note:
- **Transposition Table and Zobrist Hashing**
  - My OneNote Note: 
