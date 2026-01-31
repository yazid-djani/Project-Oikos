# Project-Oikos

Arborescence :

Project-Oikos/
├── .gitignore             
├── README.md              
├── LICENSE                
│
├── oikos-engine/          # [MODULE MOTEUR] : Les outils génériques (réutilisables pour un autre jeu)
│   └── src/com/oikos/engine/
│       ├── core/
│       │   ├── GameLoop.java       # Le Cœur : La boucle infinie (while true) qui gère les 60 FPS.
│       │   ├── GameObject.java     # Le Parent : Classe de base (X, Y, Vie). Tout le monde hérite de lui.
│       │   └── Scene.java          # Les États : Pour passer du "Menu Principal" au "Jeu".
│       │
│       ├── graphics/
│       │   ├── Sprite.java         # L'Image : Objet qui contient une image chargée en mémoire.
│       │   ├── AssetManager.java   # L'Entrepôt : Charge les images une seule fois au début pour optimiser.
│       │   └── Camera.java         # L'Œil : Calcule le décalage (offset) pour suivre le joueur (scrolling).
│       │
│       ├── physics/
│       │   ├── CollisionSystem.java # L'Arbitre : Vérifie si deux rectangles se touchent.
│       │   └── Hitbox.java          # Le Corps : Définit la zone solide d'un objet (rectangle invisible).
│       │
│       ├── math/
│       │   └── Vector2.java        # La Position : Gère la position (x, y).
│       │
│       ├── ai/                     # 
│       │   └── Pathfinding.java    # Le Cerveau : Algo A* pour que les loups trouvent leur chemin.
│       │
│       └── input/
│           └── InputHandler.java   # Les Commandes : Écoute le clavier (Z,Q,S,D) et la souris.
│
└── oikos-game/            # [MODULE JEU] : Ton contenu spécifique (L'histoire, les persos)
├── resources/         # Tes fichiers bruts
│   ├── characters/    # Images des PNJ/Animaux (wolf.png, villager.png...)
│   └── environment/   # Images des décors (tree.png, grass.png...)
│
└── src/com/oikos/game/
├── Main.java              # Le Démarreur : Contient "public static void main". Lance la fenêtre.
│
├── entities/              # Tes Créatures (Héritent de GameObject)
│   ├── Wolf.java          # Logique du loup (chasser, manger).
│   ├── Sheep.java         # Logique du mouton (fuir, brouter).
│   ├── Villager.java      # Logique des humains génériques.
│   └── Lumberjack.java    # Spécifique : Coupe du bois (ex-Bucheron).
│
├── items/                 # Tes Objets
│   └── Item.java          # (ex-Objet.java)
│
└── scenarios/
└── VillageLevel.java  # La Carte : Initialise la map, place les arbres et les premiers loups.