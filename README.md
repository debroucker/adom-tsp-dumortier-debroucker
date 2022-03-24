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

![](/img/cost_random_heuristic.png)