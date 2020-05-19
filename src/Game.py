class Game :

    def __init__(self, j1, j2, nb, size):
        #Iniatilisation des variables
        self.victory = False
        self.nbRock = nb
        self.sizeplat = size
        self.player1 = j1
        self.player2 = j2
        self.posPlayer1 = 0
        self.posPlayer2 = 0
        self.nbCoup = 0
        
        self.player1.setCurrentrock(self.nbRock)
        self.player2.setCurrentrock(self.nbRock)

        self.jouerPartie()

    def jouerPartie(self) :
        self.player1.resetcurrentmoy()
        self.player2.resetcurrentmoy()
        while self.victory == False :
            trollPosition = self.posPlayer1 - self.posPlayer2
            coupJ1 = self.player1.choixNbPierres("Prudente", self.nbCoup, self.nbRock, self.player2.currentRock, trollPosition, self.sizeplat)
            coupJ2 = self.player2.choixNbPierres("Random", self.nbCoup, self.nbRock)
            self.player1.savecoup(coupJ1)
            self.player2.savecoup(coupJ2)

            self.fight(coupJ1, coupJ2)
            self.checkForWin()
            self.nbCoup += 1

        self.win()

    def fight(self, coupJ1, coupJ2):
        """Fonction qui traite le resultat de la bataille"""
        if coupJ1 < coupJ2 :
            self.posPlayer1 = self.posPlayer1 + 1
            if (self.posPlayer2 > 0) :
                self.posPlayer2 = self.posPlayer2 - 1
        if coupJ2 > coupJ1 :
            self.posPlayer2 = self.posPlayer2 + 1
            if (self.posPlayer1 > 0) :
                self.posPlayer1 = self.posPlayer1 - 1

    def checkForWin(self) : 
        """Verifie si la partie est terminee"""
        if (self.posPlayer1 == (self.sizeplat-1)/2 or self.posPlayer2 == (self.sizeplat-1)/2 or self.player1.getCurrentRock() == 0 and self.player2.getCurrentRock() == 0):
            self.victory = True

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