#!/usr/bin/python
# -*-coding:utf-8 -*
from random import randint

class Player():
    

    def __init__(self,name):
        self.moyAllCoupt = 0
        self.moyCurrentCoupt = 0;
        self.nbCurrentCoupt = 0
        self.nbAllCoupt = 0
        self.pseudo = name
        self.currentRock = 0



    def choixNbPierres(self,ia, nbturn, nbrock):
        choice = 0
        if self.currentRock != 0:
            if ia == "Random" :
              choice  = randint(0,self.currentRock) + 1
              print(choice, ", ", self.pseudo)
            # else:
            #     choix = 0
            if ia == "OneMore" :
                if nbturn == 0:
                    choice = int(nbrock / 4)
                    print("Je joue :", choice)
                else:
                    choice = self.moyCurrentCoupt + 1
                    print("Je joue: " + 1 )
            
            
            if ia == "OneMoreAll":
                if self.nbAllCoupt == 0:
                    choice = int(nbrock / 4)
                    print("Je joue: ", choice)
                else:
                    tempChoice = self.moyAllCoupt*self.nbAllCoupt + self.moyCurrentCoupt*self.nbCurrentCoupt
                    print(self.currentRock)
                    choice = int(tempChoice/self.nbAllCoupt + self.nbCurrentCoupt) +1
                    print("Je joue : ", choice)
            
            if self.currentRock - choice >= 0:
                self.currentRock = self.currentRock - choice
            else:
                choice = self.currentRock
                self.currentRock = 0
            return choice
        else:
            print("")
            return 0

    def resetcurrentmoy(self):
        self.moyCurrentCoupt = 0
        self.nbCurrentCoupt = 0

    def savecoup(self, choice):
        if self.nbCurrentCoupt!= 0:
            tempMoy = self.moyCurrentCoupt * self.nbCurrentCoupt + choice
            self.nbCurrentCoupt = self.nbCurrentCoupt + 1
            self.moyCurrentCoupt = tempMoy / self.nbCurrentCoupt
        else:
            self.moyCurrentCoupt = choice
            self.nbCurrentCoupt = self.nbCurrentCoupt + 1
            print(self.moyCurrentCoupt)
            print(self.nbCurrentCoupt)
        
        if self.nbAllCoupt !=0 :
            tempAll = self.moyAllCoupt * self.nbAllCoupt + choice
            self.nbAllCoupt = self.nbAllCoupt + 1
            self.moyAllCoupt = tempAll / self.nbAllCoupt
        else :
            self.moyAllCoupt = choice
            self.nbAllCoupt = self.nbAllCoupt + 1


    def getCurrentRock(self):
        return self.currentRock
    
    def setCurrentrock(self,currentRock):
        self.currentRock = currentRock
    
    def getPseudo(self):
        return self.pseudo


    def setPseudo(self,pseudo):
        self.pseudo = pseudo


