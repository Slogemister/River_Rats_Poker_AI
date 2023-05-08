# River Rats Project

## File Contents

- Introduction 
- Requirements
- Installation
- Usage


## Introduction

This project was intended to incorporate a computer agent into a limit
hold'em poker environment (ESTHER). The current agents created to use for training
and for study are a rules-based agent and an agent using neural networks and epoch-training
to play games with the other pre-defined or user created agents.


## Requirements

Access to the github repository and a text editor capable of executing Java code.


## Installation

- Go to https://github.com/Slogemister/River_Rats_Poker_AI
- Open github repository and clone the project to your text editor
- For unit testing purposes, configure the tests around junit.jupiter
- Go to the file named ESTHER.java and execute it to launch the poker framework of ESTHER


## Usage

1. Can play the game manually by selecting AgentHumanCommandLine as one of the agents 
in ESTHER.java
2. Can create new agents by implementing the Player.java abstract class (read the TableData and EstherTools files for more information about data and helpful methods surrounding the poker game).
3. Can switch the mode types of what game to play. Head into ESTHER.java and select 1 of 3 available game modes (1 = a select amount of hands are played based on amount of players, 2 = 1 game of truly random card data, 3 = agent tournament)