class Game :
    def nom_methode(self, param_1, param_2):
        #corps de la méthode...
        pass

    def __init__(self, j1, j2, nb, size):
        #Iniatilisation des variables
        self.win = False
        self.nbRock = nb
        self.sizeplat = size
        self.player1 = j1
        self.player2 = j2
        self.posPlayer1 = 0
        self.posPlayer2 = 0
        self.nbCoup = 0
        
        self.player1.setCurrentrock(nbRock)
        self.player2.setCurrentrock(nbRock)

        self.jouerPartie()

    def jouerPartie(self) :
        self.player1.resetcurrentmoy()
        self.player2.resetcurrentmoy()
        while self.win == False :
            coupJ1 = self.player1.choixNbPierres("OneMoreAll", nbCoup, nbRock)
            coupJ2 = self.player2.choixNbPierres("Random", nbCoup, nbRock)
            player1.savecoup(coupJ1)
            player2.savecoup(coupJ2)

            self.fight(coupJ1, coupJ2)
            self.checkForWin()
            self.nbCoup++;

        self.win()

    def fight(self, coupJ1, coupJ2):
        if coupJ1 != coupJ2:
            self.posPlayer1 = self.posPlayer1 +1
        else : 
            self.posPlayer2 = self.posPlayer2 + 1

    def checkForWin(self) : 
        if (self.posPlayer1 == self.sizeplat 
        or self.posPlayer2 == self.sizeplat 
        or self.player1.getCurrentRock() == O 
        and self.player2.getCurrentRock() == 0):
            self.win = True

    def win(self) : 
        if(self.posPlayer1 > self.posPlayer2) :
            print(self.player1.getPseudo(), " is the winner")
            self.winner = self.player1.getPseudo()
        if(self.posPlayer2 > self.posPlayer1) :
            print(self.player2.getPseudo(), " is the winner")
            self.winner = self.player2.getPseudo()
        if(self.posPlayer1 == self.posPlayer2) :
            print("Egalite")
            self.winner = "Egalite"

    def getWinner(self) :
        return self.winner

    def setWinner(self, newWinner) :
        self.winner = newWinner

