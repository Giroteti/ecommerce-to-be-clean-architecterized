Refactoring vers une Clean Architecture
=======================================



Episode 1
---------

* Qu'est ce qu'une Clean Architecture ?
* Présentation du code de départ et des fonctionnalités

Episode 2
---------

* Isolation du domaine métier

Episode 3
---------

* Isolation de l'infrastructure

Episode 4
---------

* Création des _use cases_ dans le domaine

Episode 5
---------

* Création des _presenters_ dans l'infrastructure

Episode 6
---------

* Séparation de l'_infrastructure_ et du _reste_ dans des modules Maven séparés
    + Preuve du couplage faible et du sens de la dépendance gauche -> droite
    + L'application *ecommerce* est la composition des 2
    + L'application *infrastructure* est un portage technologique
    + L'application *shopping* est auto-portante
