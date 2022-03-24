# adom-tsp-dumortier-debroucker

## TSP
En informatique, le problème du voyageur de commerce, ou problème du commis voyageur, 
est un problème d'optimisation qui consiste à déterminer, étant donné une liste de villes 
et les distances entre toutes les paires de villes, le plus court circuit qui passe 
par chaque ville une et une seule fois.
(Merci qui? et non, merci wiki)

## Analyse

Au départ, on est parti sur une solution random, c'est-à-dire, on prend la liste des villes, 
on la mélange, et on regarde le coup pour chaque parcours.
En règle générale, le cout pour chaque instance (de A jusqu'à F) tourne autour de 200.000.

Le souci étant le cout bien trop grand. On est parti donc sur une première solution, qui consiste 
à prendre une ville, calculer chaque cout entre cette dernière et toutes les autres villes, et on
prend le plus petit cout, et ainsi de suite. Ici, on arrive à de bien meilleurs résultats, qui 
tournent autour de 20.000.


![TP1](/img/cost_random_heuristic.png)

Suite à cela, nous avons vu l'approche des voisins améliorants, notamment le swap et le two-opt.
Mais également deux méthodes de mouvement, meilleur voisin améliorant, ou premier voisin améliorant.
On a donc développé ces algos avec toutes les variantes et comparé.
On a des résultats pour premier voisin en swap et two-opt, mais aussi pour le meilleur voisin en 
swap et two-opt, pour deux manières initialiser une liste de ville : soit random, soit heuristic.

On a comparé tous ces algos entre eux en cout mais également en temps de calcul, sur une 
moyenne de 100 executions par instance.

On a conclu que le two-opt était plus local que le swap, et on obtient de meilleurs couts 
pour une liste initialiser de manière heuristic. Enfin, en prenant le premier voisin améliorant, 
on obtient des couts plus élevés qu'avec le meilleur voisin améliorant, mais de meilleurs résultats 
en temps de calcul.

![TP2 - Instance A](/img/A)

![TP2 - Instance B](/img/B)

![TP2 - Instance C](/img/C)

![TP2 - Instance D](/img/D)

![TP2 - Instance E](/img/E)

![TP2 - Instance F](/img/F)