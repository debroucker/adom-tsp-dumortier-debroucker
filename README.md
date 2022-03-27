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
(nous ne sommes pas responsables des maux de tête occasionnés par la lecture de ce nombre).

En plus de cela, il est possible multiplier les critères. En effet, dans un premier temps, nous pouvons prendre 
en considération qu'un seul critère, comme le coût du trajet. Mais nous pouvons également en prendre plusieurs, 
comme le temps de trajet.

## Modélisation
Pour la résolution de ce problème, nous avons à notre disposition 6 fichiers, représentants chacun une instance
aléatoire contenant 100 villes. Ces instances contenaient les valeurs des coûts entre chaque pair de ville. 
Nous avons donc créé un parseur de ces fichiers, afin d'obtenir une matrice de coût par instance, mais également 
une méthode d'évaluation (c'est-à-dire les distances entre chaque ville) pour une liste de villes et une matrice 
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
le coût tourne autour de `200 000`. Ce qui est très loin des meilleures solutions connues, mais attendu. En effet, 
avec un nombre de solutions aussi grand que `4.6663107722e+155`, il est impossible de trouver LA meilleure solution 
de manière aléatoire.

## 2ème approche : Heuristique constructive
Une fois avoir obtenu de piètre résultats avec la 1ère approche, nous sommes parties sur l'heuristique constructive. 
Encore une fois, "Qu'est-ce que l'heuristique constructive ?", me demanderez-vous. Et bien, cette méthode consiste 
à calculer le coût entre la ville actuelle et toutes les autres, afin d'avoir la ville la plus proche, et on fait cette 
recherche pour chaque ville. Lors de nos recherches pour cette méthode, nous avons vu que la ville de départ était 
un choix important. En effet, en fonction de la ville de départ, nous n'avons pas les mêmes coûts. 

Ici, le coût tourne autour de `20 000`, donc 10 fois moins ! Nous nous rapprochons petit à petit des meilleures 
solutions connues, allons-nous y arriver ? (To be continued...)

#

Image montrant les résultats de la 1ère et 2ème approche :

![TP1](/img/cost_random_heuristic.png)

#

## 3ème approche : Algorithme de recherche locale (Two-Opt, mon meilleur ami)
Une fois n'est pas coutume, "Qu'est ce qu'un algorithme de recherche locale ?", m'interrogerez-vous. Et bien, un 
algorithme de recherche locale part d’une solution donnée. Son objectif : se déplacer, en partant de cette solution de 
manière itérative, afin d’arriver sur une solution voisine dont le coût est moindre que celui de départ. 
On parle alors d’un voisin améliorant. Cependant, cette méthode ne peut être utilisée seulement si l'on peut définir 
des voisins. C'est pour cette raison que nous allons le faire : les voisins d’une solution sont obtenus en changeant 
l'ordre des villes d'une solution.

Ici, nous avons 2 type de voisinages possibles afin de faire varier la position des villes : le `swap` (échanger 
la position de deux villes) et le `two-opt` (supprimer deux arrêtes et reconnecter les deux sous-tours obtenus).

Exemple d'un two-opt :

![two-opt](/img/two-opt.png)

#

Avec ces deux méthodes, nous pouvons obtenir des voisins différents et comparer leur coût. Nous avons fait en sorte 
que le calcul du potentiel gain des voisins, en fonction des méthodes de voisinage, ne recalcule pas toute
l'entièreté du chemin, mais seulement de calculer les coûts entre les villes permutées. Par exemple : si l'on a 
A-B-C-D-E comme chemin, et que l'on swap A et E, on se retrouve donc avec le chemin voisin E-B-C-D-A.
On va venir calculer D-A et E-B, car le reste du chemin, à savoir B-C-D est connue.

Nous avons également le choix entre deux types de stratégie de mouvement : `meilleur voisin améliorant`, ou `premier 
voisin améliorant`. La différence entre les deux ? Meilleur voisin améliorant, nous allons calculer tous les 
voisins possibles,  afin de les comparer pour trouver LE meilleur. Premier voisin améliorant, nous allons commencer 
calculer les voisins,  mais dès que l'on trouve un meilleur voisin, nous prenons celui-ci, peu importe si c'est le 
meilleur ou non. On parle d'optimum local.

Enfin, nous avons la possibilité de démarrer à partir d’une `solution aléatoire` (1ère approche), ou bien d’une 
solution obtenue à l’aide d’une `heuristique constructive` (2ème solution).

Nous nous retrouvons donc avec 8 possibilités pour chacune de nos instances. Et pour chacun de ces dernières, 
nous avons comparé tous ces algos entre eux en coût, mais également en temps de calcul (en ms), sur 100 executions 
par instance. 

#

En ce qui concerne le `choix de l'initialisation` :

Nous voyons que l'Heuristique constructive est meilleure en coût, mais également en temps, ce qui est attendue. 
En effet, comme nous l'avons dit plus haut, l'Heuristique constructive est une bien meilleure solution qu'une solution 
aléatoire. Il est donc normal que l'Heuristique constructive demande moins de temps pour obtenir un résultat, mais aussi 
que le coût soit moins élevé. Et ceci, que ça soit en meilleur ou premier voisin améliorant, mais aussi 
concernant le swap ou le two-opt.

_Quelques données :_
 - Pour l'instance B, en Premier voisin améliorant - Swap, on a `16 208` en coût pour l'Heuristique, contre `39 169` 
pour la solution random. Et pour le temps, on a respectivement `0,290`ms contre `1,270`ms.
 - Pour l'instance C, en Meilleur voisin améliorant - Two-Opt, on a `14 181` en coût pour l'Heuristique, contre `18 733` 
pour la solution random. Et pour le temps, on a respectivement `0,460`ms contre `3,100`ms.

#

Pour ce qui est du `type de voisinage` :

Ici, nous voyons que le Two-Opt est meilleur en coût, mais également en temps, par rapport au Swap.

_Quelques données :_
- Pour l'instance A, en Random - Premier voisin améliorant, on a `39 015` en coût pour le swap, contre `19 812`
  pour le two-opt. Et pour le temps, on a respectivement `1,350`ms contre `0,640`ms.
- Pour l'instance D, en Random - Meilleur voisin améliorant, on a `40 424` en coût pour 
le swap, contre `18 134` pour le two-opt. Pour le temps, on a respectivement `3,790`ms contre `2,820`ms.

#

Enfin, pour la `stratégie de mouvement` :
Concernant cette stratégie, nous avons des résultats mitigés. Le premier voisin améliorant met moins de temps de calcul, 
tandis que le meilleur voisin améliorant nous permet d'avoir de meilleurs coûts. Ces résultats étaient tout de même attendus. 
En effet, il est normal que le premier voisin améliorant mette moins de temps de calcul, car dès qu'il trouve un 
meilleur voisin améliorant, il s'arrête. Alors que le meilleur voisin améliorant doit calculer tous ses voisins.
De même, il est normal que ce dernier donne des coûts plus bas, car il choisit LE meilleur voisin, donc à chaque fois, 
celui ayant le coût le plus faible.

_Quelques données :_
- Pour l'instance E, en Heuristique - Swap, on a `16 797` pour le premier voisin, contre `16 168` pour le meilleur 
voisin. Pour le temps, on obtient respectivement `0,220`ms contre `0,320`ms.
- Pour l'instance F, en Random - Two-Opt, on a `18 539` pour le premier voisin, contre `18 054` pour le meilleur 
voisin. Et pour le temps, on a respectivement `0,480`ms contre `2,770`ms.

#

_Conclusion :_

Nous avons de bien meilleur résultat si l'on initialise avec une solution Heuristique constructive qu'avec une Random. 
Nous avons également vu que le Two-Opt est plus optimal que le Swap.
Cependant, pour ce qui est du choix Premier/Meilleur voisin améliorant, nous dirons que cela 
dépends du critère que nous voulons mettre en avant. Si le coût est plus important, il vaut mieux favoriser le Premier 
voisin améliorant. En revanche, si nous préférons mettre l'accent sur le temps de calcul, il faut partir 
sur le Meilleur voisin améliorant.

#

Pour plus de détails, voici les résultats de chaque instance, que nous avons fait dans cet ordre :
- Heuristique - Premier voisin améliorant - Swap
- Heuristique - Premier voisin améliorant - Two-Opt
- Heuristique - Meilleur voisin améliorant - Swap
- Heuristique - Meilleur voisin améliorant - Two-Opt
- Random - Premier voisin améliorant - Swap
- Random - Premier voisin améliorant - Two-Opt
- Random - Meilleur voisin améliorant - Swap
- Random - Meilleur voisin améliorant - Two-Opt

![TP2 - Instance A](/img/A.png)

![TP2 - Instance B](/img/B.png)

![TP2 - Instance C](/img/C.png)

![TP2 - Instance D](/img/D.png)

![TP2 - Instance E](/img/E.png)

![TP2 - Instance F](/img/F.png)