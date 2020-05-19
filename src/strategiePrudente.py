#TODO TEEESTEEEER 

def Dico(nbRockJ1,nbRockJ2,trollPosition, size):
    """ Traite les cas triviaux"""
    #Si le troll est chez J1 
    if trollPosition == - ((size-1)/2) : 
        return 0

    #Si le troll est chez J2
    if trollPosition == (size-1)/2 :
        return 1

    #Si J1 et J2 ont 0 pierre et que le troll est en 0
    if (nbRockJ2 == 0) and (nbRockJ1 == 0):
        return 0

    #Si J1 a 0 pierres 
    if nbRockJ1 == 0:
        # Si J2 a assez de pierre pour repousser le troll  de l'autre cote
        if trollPosition - nbRockJ2 < 0:
            return -1
    
    #Si J2 a 0 pierres 
    if nbRockJ2 == 0:
        #Si J1 a assez de pierre pour repousser le troll de l'autre cote
        if trollPosition + nbRockJ1 > 0 :
            return 1

    else:
        return -2

# tab => [ [{0,1,-1},{0,1,-1},...] [{0,1,-1},{0,1,-1},...] ... ]
def evalGOpt(nbRockJ1, nbRockJ2, trollPosition, size) :
    tab = [[0 for i in range(nbRockJ2)] for i in range(nbRockJ1)]
    for i in range(1, nbRockJ1):
        for j in range(1, nbRockJ2):
            resDico = Dico(nbRockJ1, nbRockJ2, trollPosition, size)
            if resDico == -2:
                tab[i][j] = evalGOpt(nbRockJ1-i, nbRockJ2-j, trollPosition, size)
            else:
                tab[i][j] = resDico
    return tab

def analyzeGOpt(tab):
    """Regarde le nombre de pierres qu'il est le plus avantageux de lancer"""
    indiceMeilleur = 0
    meilleur = 0
    for indiceLigne in range(len(tab)):
        sommeCourante = tab[indiceLigne].count(0) + tab[indiceLigne].count(1)
        if sommeCourante > meilleur:
            meilleur = sommeCourante
            indiceMeilleur = indiceLigne

    return indiceMeilleur + 1




