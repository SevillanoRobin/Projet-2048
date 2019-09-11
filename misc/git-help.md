# Notes sur Git (aide)
## Sommaire
 * [Mise à jour du projet](#mise-à-jour-du-projet)
 * [Copie du projet](#copie-du-projet)
 * [Gestion des branches](#branches)
 * [**Liens intéréssants**](#liens-intéréssants)
___
## Mise à jour du projet
```
git pull
```
**OU**
```
git fetch
git checkout HEAD
```

## Copie du projet
Si le projet existe déjà en local, il suffit de le mettre à jour.

Sinon, il faut "cloner" le projet (un nouveau dossier est créé) :
```Git
git clone https://github.com/SevillanoRobin/Projet-2048-Behm-Richez-Sevillano.git
```

Si un répertoire pour le projet existe déjà, il est possible de l'utiliser :
```Git
(git init)
git remote add origin https://github.com/SevillanoRobin/Projet-2048-Behm-Richez-Sevillano.git
git fetch
git branch -u origin/master
```

## Branches
### Création
```
git checkout -b [nom de la branche]
```
**OU**
```
git branch [nom de la branche]
git checkout [branche]
```

### Changement de branche
```
git checkout [branche]
```

### Changement de nom
```
git branch -mv [nouveau nom]
```

### *Push* initial
```
git push -u origin [nom de la branche]
```

### Suppression de branche
```
git branch -d [branche]
```

___
## Liens intéréssants
 * [Guide intéractif](https://learngitbranching.js.org/?demo)