SCRABBLE BOT!!

-- Overview --
A bot programmed to play a full game of scrabble, using the tiles on your rack and the board to suggest optimal plays
Uses the American scrabble disctionary (Not sure which one exactly, credit goes to https://gist.github.com/deostroll for creating the dictionary used in this code, however you can use any dictionary you'd like

-- Code Summary -- 
Main files
Board.java - Board class, holds functions regarding the state of the gameboard, most importantly whether or not a move is vaild
ComputeWords.java - Uses the tiles in the rack to determine the full list of words that could possibly be played
Gamer.java - The main function run by the bot
Move.java - Small code file made to store information on a specific move
Points.java - Calculates the points of a given move, placed in a seperate file to save space
Reader.java - Creates .ser file used in Gamer file
Test files
Gamertest.java - Tests other functions/classes of the code
useless.py - Small calculations, has no bearing in the actual program
wordfinder.java - Test code for ComputeWords

-- What was used --
Solely coded in java
.io and .util packages used, .lang used in one instance (Points)

-- Sample Game (Coming Soon) --

-- How to run --

Download all files that aren't test files, and the dictionary.txt file
If you don't have the .ser file with all the words, run "Reader.java" 
- If you want to run the code on a different scrabble dictionary, such as Collins or an updated dictionary, use that other dictionary in .txt format
Once you have the .ser file, run Gamer.java to start
- Corridinates are 1-indexed "x y" with y descending (Ex: "1 1" is the top left triple space, "8 8" is the center, "1 15" is the bottom left triple")
- USE ALL CAPS FOR TYPING IN RACK AND WORDS
- Type "Vertical" or "Horizontal" when saying the orientation of your move

-- Future Updates --
- Fix minor bugs regarding vaildity system
- Account for overlapped words and blanks (not counting blanks as points)

-- Created by --
Gaurav Gupta
UT Austin Computer Science C.O. 2029
Email: g.gupta31415@gmail.com

No lisense, anyone is free to use as they wish
