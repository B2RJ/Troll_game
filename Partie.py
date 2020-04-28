
#!/usr/bin/python
# -*-coding:utf-8 -*
from CoupInvalide import CoupInvalide
# Classe représentant la partie de jeux
# Attributs :
# nbCases => le nombre de cases du terrain
# stockPierres => nombre de pierres initial
# pierresGauche => nombre de pierre du joueur Gauche
# pierresDroit => nombre de pierre du joueur Droit
# coupsPrecedents => Liste des coups précédent pour les stratétgies
# gagnants => représente le joueur gagnants : 0 partie nulle | 1 joueur Gauche | 2 Joueur Droit
class Partie():
    def __init__(self, nbCases, stockPierres):
        if nbCases % 2 == 0:
            raise Exception("Le nombre de case doit être impair !")
        self.nbCases = nbCases
        self.stockPierres = stockPierres
        self.gagnant = 0
        self.troll = nbCases // 2
        self.coupsPrecedent = []
        self.pierresGauche = stockPierres
        self.pierresDroit = stockPierres


    def Resume(self):
        taille = len(str(self.stockPierres))
        gauche, droite = self.coupsPrecedent[len(self.coupsPrecedent)-1]
        return " {0} {1} {2} ".formant(str(gauche).rjust(taille)," " *self.nombreCases, str(droite).rjust(taille))
    def Tour(self, choixGauche, choixDroit):
        self.coupsPrecedent.append((choixGauche, choixDroit))

        invalideGauche = False
        invalideDroit = False

        if choixGauche is None:
            invalideGauche = True
            messageInvalideGauche = "Le joueur de gauche n'a rien choisi"
        elif not isinstance(choixGauche,int):
            invalideGauche = True
            messageInvalideGauche = "Le joueur de gauche n'a pas entrer de nombre entier"
        elif choixGauche > self.pierresGauche:
            invalideGauche = True
            messageInvalideGauche = " Le joueur de gauche ne peux pas lancer autant de pierres ! "
        elif choixGauche < 1:
            invalideGauche = True
            messageInvalideGauche = " Le joueur de gauche a entrer un nombre nulle ou négatif"

        if choixDroit is None:
            invalideDroit = True
            messageInvalideDroit = "Le joueur de droite n'a rien choisi"
        elif not isinstance(choixDroit,int):
            invalideDroit = True
            messageInvalideDroit = "Le joueur de droite n'a pas entrer de nombre entier"
        elif choixDroit > self.pierresGauche:
            invalideDroit = True
            messageInvalideDroit = " Le joueur de droite ne peux pas lancer autant de pierres ! "
        elif choixDroit < 1:
            invalideDroit = True
            messageInvalideDroit = "Le joueur de droite a entrer un nombre nulle ou négatif"

        if invalideGauche and invalideDroit:
            raise CoupInvalide(messageInvalideGauche + "\n" + messageInvalideDroit)
        elif invalideGauche:
            raise CoupInvalide(messageInvalideGauche)
        elif invalideDroit:
            raise CoupInvalide(messageInvalideDroit)
        else:
            self.pierresGauche -= choixGauche
            self.pierresDroit -= choixDroit
            if choixGauche > choixDroit:
                self.troll += 1
            if choixDroit > choixGauche:
                self.troll -= 1
            #Si l'un des joueurs n'a plus de pierres

            if (self.troll != 0 and self.troll != self.nbCases - 1) and ( self.pierresGauche == 0 or self.pierreDroit == 0):
                self.__ViderPierres()
            partieFinie = self.__PartieFinie()
            return (partieFinie, self.gagnant)

    def __ViderPierres(self):
        if self.pierresGauche > 0:
            nouvellePosition = min(self.pierresGauche, self.nbCases - self.troll - 1)
            self.troll += nouvellePosition
            self.pierresGauche -= nouvellePosition
        if self.pierresDroit > 0:
            nouvellePosition = min(self.pierresDroit, self.nbCases -self.troll - 1)
            self.troll += nouvellePosition
            self.pierresDroit -= nouvellePosition

    def __PartieFinie(self):
        if self.troll == 0:
            self.gagnant = 2
            return True
        elif self.troll == self.nbCases - 1:
            self.gagnant = 1
            return True
        elif self.pierresGauche <= 0 or self.pierresDroit <= 0:
            if self.troll > self.nbCases // 2:
                self.gagnant = 1
                return True
            if self.troll < self.nbCases // 2:
                self.gagnant = 2
                return True
            else:
                self.gagnant = 0
                return True
        else:
            return False
