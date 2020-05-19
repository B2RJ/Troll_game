#!/usr/bin/python
# -*-coding:utf-8 -*
from Player import Player
from Game import Game



#Debut CODE 19/05

def main():
    list = []
    nbVictoire1 = 0
    nbVictoire2 = 0
    raw = 0

    nbPartie = 10;
    Pierre = Player("Pierre")
    Richard = Player("Richard")

    for i in range(0, nbPartie) :
        jeu = Game(Pierre, Richard, 31, 11)
        list.append(jeu.getWinner())
        print(jeu.getWinner())

    for j in range(0, nbPartie) :
        if list[j] == Pierre.pseudo :
            nbVictoire1 = nbVictoire1 + 1
        if list[j] == Richard.pseudo : 
            nbVictoire1 = nbVictoire1 + 1
        if list[j] == "Egalite" :
            raw = raw + 1

    print("Pierre a gagne ", nbVictoire1, " fois.")
    print("Richard a gagne ", nbVictoire2, " fois")
    print("Il y a eu ", raw, "egalités")        

if __name__ == "__main__":
    main()

