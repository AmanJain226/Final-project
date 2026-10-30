# Final-project

This is a Java application that lets an organizer enter players, choose a format(single elimination/round-robin) and generate the full bracket automatically. As matches finish, the organizer enters game scores, and the app validates them against the best of # rules, and updates the brackets. All tournament data is saved to a local file so events can be reopened and continued later.

## The Problem

When my friends and I play ping pong, we usually track who is playing who with writing a bracket on the whiteboard or using a group chart, and its easy to lose track of the results, forget who isn't playing, and argue about the current standings. Existing bracket tools may not be able to handle other extraneous factors in a match, like best of 3 wins or best of 5, and tend to require accounts or paid subscriptions to get additional features.

## How it Works

[System Diagram](docs/System%20Diagram.drawio.pdf)

## Tech Stack

- **Language:** Java
- **Libraries:** Scanner, BufferedReader, BufferedWriter, JUnit, ArrayList, HashMap, Comparator
- **External tools:** VS Code

## Setup

**Requirements** Java 17 or newer

```bash
git clone https://github.com/AmanJain226/Final-project
cd Final-project

javac -d out src/*.java

java -cp out Main
```
The program starts a command-line menu. Follow the prompts to add players, choose a format, and enter scores. Tournaments are saved to a local file in the `data/` folder so you can reopen them later.

## Usage

Run the program with this command:

```bash
java -cp out Main
```

You'll see a menu:

```
Ping Pong Tournament Manager
1. Add player
2. List players
3. Start single-elimination tournament
4. Start round-robin tournament
5. Enter match score
6. View bracket/standings
7. Save and quit
Choose an option:
```

### Example: 4-player single elimination tournament

1. Choose `1` four times to add players(Alex, Sam, Jordan, Taylor)
2. Choose `3` and click best-of-3 when prompted. The app generates the bracket:

```

Round 1
Match 1: Alex vs. Sam
Match 2: Jordan vs. Taylor
```

3. Choose `5`, select match 1, enter the games won by each player(for example `2 1`). The app checks that the score is valid for a best of 3, and advances the winner to the final
4. Repeat for the remaining matches. When the score for the final is entered, the champion is displayed
5. Choose `7` to save. Your tournament is written to the `data/` folder, where it can be reopened the next time you run the program

## Example: Invalid score

```
Enter games won (Alex Sam): 3 3
Invalid score for a best-of-3. One player must win exactly 2 games out of 3.
```

The app asks again until the score is valid.

## Roadmap

[Eisenhower Matrix](docs/Eisenhower%20Matrix.pdf)

## Author

Aman Jain
[Project link](https://github.com/AmanJain226/Final-project)
