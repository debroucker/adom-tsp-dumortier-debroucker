# adom-tsp-dumortier-debroucker

Projet réalisé en `Java 17`, pour travailler sur le `TSP`.

Par _Raphaël Dumortier_ et _Tommy Debroucker_.

## Contexte
Dans le cadre de ce cours, nous avons vu le TSP. "Qu'est-ce que le TSP ?", me direz-vous. 
Et bien, le TSP, en informatique, c'est le problème du voyageur de commerce, ou problème du 
commis voyageur. C'est un problème d'optimisation qui consiste à déterminer, étant donné une 
liste de villes et les distances entre toutes les paires de villes, le plus court circuit 
qui passe par chaque ville une et une seule fois.
(Merci qui? Et non, merci wiki).
TSP voulant dire Traveling-Salesman Problem.

![TSP](/img/tsp.png)

Vous l'aurez compris, le but est de trouver le chemin le plus optimal pour visiter chaque ville.
Dis comme ça, ça peut paraître assez simple. Cependant, le nombre de solutions est énorme !
En effet, nous avons `(n-1)!/2` solutions possibles, pour n villes. Donc lister l'ensemble des 
solutions, pour obtenir le meilleur, serait beaucoup trop long et fastidieux.
Dans les TPs que nous avons étudiés, nous avons 100 villes, et donc 99!/2, ce qui donne environ `4.6663107722e+155`.
(nous ne sommes pas responsables des maux de têtes occasionnés par la lecture de ce nombre)

En plus de cela, il est possible multiplier les critères. En effet, dans un premier temps, nous pouvons prendre 
en considération qu'un seul critère, comme le coût du trajet. Mais nous pouvons également en prendre plusieurs, 
comme le temps de trajet.

## Modélisation
Pour la résolution de ce problème, nous avons à notre disposition 6 fichiers, représentants chacun une instance
aléatoire contenant 100 villes. Ces instances contenaient les valeurs des coûts entre chaque pair de villes. 
Nous avons donc créé un parseur de ces fichiers, afin d'obtenir une matrice de coût par instance, mais également 
une méthode d'évaluation (c'est-à-dire les distances entre chaque ville) pour une liste de ville et une matrice 
données.

L'objectif étant d'avoir les coûts les plus bas, nous avons à notre disposition les meilleurs coûts connus pour 
les instances données : (spoiler : nous n'avons pas réussis à trouver les mêmes coûts)
 - Instance A : 10659
 - Instance B : 9234
 - Instance C : 9529
 - Instance D : 9108
 - Instance E : 8899
 - Instance F : 8989
 
## 1ère approche : Random (pas une bonne idée)
La 1ère méthode que nous avons, et la plus simple à faire, était de générer une liste de villes, dans un ordre 
aléatoire et d'en évaluer le coût, pour chacune de nos instances et donc de matrices. En règle générale, 
le coût tourne autour de `200 000`. Ce qui est très loin des meilleures solutions connues, mais attendue. En effet, 
avec un nombre de solutions aussi grand que `4.6663107722e+155`, il est impossible de trouver LA meilleure solution 
de manière aléatoire.

## 2ème approche : Heuristique
Une fois avoir obtenue de pietre resultats avec la 1ère approche, nous sommes parties sur l'heuristique constructive. 
Encore une fois, "Qu'est-ce que l'heuristique constructive ?", me demanderez-vous. Et bien, cette méthode consiste 
à calculer le coût entre la ville actuelle et toutes les autres, afin d'avoir la ville la plus proche, et on fait cette 
recherche pour chaque ville. Lors de nos recherches pour cette méthode, nous avons vu que la ville de départ était 
un choix important. En effet, en fonction de la ville de départ, nous n'avons pas les mêmes coûts. 
Ici, le coût tourne autour de `20 000`, donc 10 fois moins ! Nous nous rapprochons petit à petit des meilleures 
solutions connues, allons-nous y arriver ?

Image montrant les résultats de la 1ère et 2ème approche
![TP1](/img/cost_random_heuristic.png)



Suite à cela, nous avons vu l'approche des voisins améliorants, notamment le swap et le two-opt.
Mais également deux méthodes de mouvement, meilleur voisin améliorant, ou premier voisin améliorant.
On a donc développé ces algos avec toutes les variantes et comparé.
On a des résultats pour premier voisin en swap et two-opt, mais aussi pour le meilleur voisin en 
swap et two-opt, pour deux manières initialiser une liste de ville : soit random, soit heuristic.

On a comparé tous ces algos entre eux en cout mais également en temps de calcul (en ms), sur une 
moyenne de 100 executions par instance.

On a conclu que le two-opt était plus local que le swap, et on obtient de meilleurs couts et de 
de meilleurs temps pour une liste initialiser de manière heuristic. 
Enfin, en prenant le premier voisin améliorant, on obtient des couts plus élevés qu'avec 
le meilleur voisin améliorant, mais de meilleurs résultats en temps de calcul.

![TP2 - Instance A](/img/A.png)

![TP2 - Instance B](/img/B.png)

![TP2 - Instance C](/img/C.png)

![TP2 - Instance D](/img/D.png)

![TP2 - Instance E](/img/E.png)

![TP2 - Instance F](/img/F.png)