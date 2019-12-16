/*
 * Copyright (c) 17/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */
package model;

import javafx.event.EventHandler;
import javafx.util.Callback;
import model.events.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Grille de jeu
 *
 * @author Robin
 */
public class Grid extends AbstractModelEventEmitter implements ModelEventEmitter, Serializable {

    private final Tile[] grid;
    private int bestValue;
    private boolean fusion = true;

    /** File d'indices qui ont été créés avant l'initialisation du flux d'événements. */
    private ArrayList<Integer> eventFlowQueue = new ArrayList<>();

    /**
     * Constructeur
     */
    Grid() {
        this.grid = new Tile[Parameters.SIZE];
        this.bestValue = this.initialNewTile();
    }

    /**
     * Construit une grille à partir d'un tableau de tuile
     *
     * @param _g
     */
    Grid(Tile[] _g) {
        this.grid = _g;
        this.parseBestValue();
    }

    /**
     * Renvoie vrai si le joueur a perdu
     *
     * @return
     */
    boolean lose() {
        Tile[] tampon = copy();

        boolean control0 = left();
        override(tampon);

        boolean control1 = right();
        override(tampon);

        boolean control2 = up();
        override(tampon);

        boolean control3 = down();
        override(tampon);

        return !(control0 || control1 || control2 || control3);
    }

    /**
     * Remplace la grille
     *
     * @param _tampon
     */
    private void override(Tile[] _tampon) {
        for (int index = 0; index < Parameters.SIZE; index++) {
            try {
                if (_tampon[index] != null) {
                    grid[index] = (Tile) _tampon[index].clone();
                } else {
                    grid[index] = null;
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Crée une copie de la grille
     *
     * @return
     */
    Tile[] copy() {
        Tile[] tampon = new Tile[Parameters.SIZE];
        for (int index = 0; index < Parameters.SIZE; index++) {
            try {
                if (grid[index] != null) {
                    tampon[index] = (Tile) grid[index].clone();
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return tampon;
    }

    /**
     * Meilleur valeur
     */
    private void parseBestValue() {
        Thread thread = new Thread(() -> {
            for (Tile t : Grid.this.grid) {
                if (t != null && t.getValue() > Grid.this.bestValue) {
                    Grid.this.bestValue = t.getValue();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException _e) {
            _e.printStackTrace();
        }
    }


    /**
     * Gère les mouvement de la grille
     *
     * @param _d
     *         direction du mouvement
     *
     * @return
     */
    boolean move(int _d) {
        switch (_d) {
            case Parameters.LEFT:
                return left();
            case Parameters.RIGHT:
                return right();
            case Parameters.UP:
                return up();
            case Parameters.DOWN:
                return down();
            default:
                System.out.println("Erreur de déplacement");
                return true;
        }
    }


    /**
     * Déplacement des tuiles de la grille vers la gauche
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers la gauche
     */
    boolean left() {
        // première ligne
        fusion = false;
        boolean move0 = moveTile(0, 1);
        boolean move1 = moveTile(1, 2);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move2 = moveTile(0, 1);
        fusion = false; // On donne a nouveau la possibilité de faire une fusion pour prochaine ligne

        // seconde ligne
        boolean move3 = moveTile(3, 4);
        boolean move4 = moveTile(4, 5);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move5 = moveTile(3, 4);
        fusion = false;

        // troisième ligne
        boolean move6 = moveTile(6, 7);
        boolean move7 = moveTile(7, 8);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move8 = moveTile(6, 7);
        fusion = false;

        return move0 || move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8;
    }

    /**
     * Déplacement des tuiles de la grille vers la droite
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers la droite
     */
    boolean right() {
        // première ligne
        fusion = false;
        boolean move0 = moveTile(2, 1);
        boolean move1 = moveTile(1, 0);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move2 = moveTile(2, 1);
        fusion = false;

        // seconde ligne
        boolean move3 = moveTile(5, 4);
        boolean move4 = moveTile(4, 3);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move5 = moveTile(5, 4);
        fusion = false;

        // troisième ligne
        boolean move6 = moveTile(8, 7);
        boolean move7 = moveTile(7, 6);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move8 = moveTile(8, 7);
        fusion = false;

        return move0 || move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8;
    }

    /**
     * Déplacement des tuiles de la grille vers le haut
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers le haut
     */
    boolean up() {
        fusion = false;
        // première ligne
        boolean move0 = moveTile(0, 3);
        boolean move1 = moveTile(3, 6);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move2 = moveTile(0, 3);
        fusion = false;

        // seconde ligne
        boolean move3 = moveTile(1, 4);
        boolean move4 = moveTile(4, 7);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move5 = moveTile(1, 4);
        fusion = false;

        // troisième ligne
        boolean move6 = moveTile(2, 5);
        boolean move7 = moveTile(5, 8);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move8 = moveTile(2, 5);
        fusion = false;

        return move0 || move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8;
    }

    /**
     * Déplacement des tuiles de la grille vers le bas
     *
     * @return Retourne vrai s'il est possible d'effectuer un mouvement vers le bas
     */
    boolean down() {
        // première ligne
        fusion = false;
        boolean move0 = moveTile(6, 3);
        boolean move1 = moveTile(3, 0);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move2 = moveTile(6, 3);
        fusion = false;

        // seconde ligne
        boolean move3 = moveTile(7, 4);
        boolean move4 = moveTile(4, 1);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move5 = moveTile(7, 4);

        fusion = false;

        // troisième ligne
        boolean move6 = moveTile(8, 5);
        boolean move7 = moveTile(5, 2);
        // si il y a déjà eu une fusion sur la ligne on n'en permet pas une nouvelle
        boolean move8 = moveTile(8, 5);

        fusion = false;

        return move0 || move1 || move2 || move3 || move4 || move5 || move6 || move7 || move8;
    }

    /**
     * @return
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < Parameters.SIDE; i++) {
            if (i == 0) {
                s.append("|--------------|");
            } else {
                s.append("  |--------------|");
            }
        }

        for (int x = 0; x < Parameters.SIDE; x++) {
            s.append("\n|");
            for (int index = 0; index < Parameters.SIDE; index++) {
                for (int y = 0; y < Parameters.SIDE; y++) {
                    if (this.grid[x * Parameters.SIDE + y] == null) {
                        s.append("    ");
                    } else if (this.grid[x * Parameters.SIDE + y].getValue() < 9) {
                        s.append(" ").append(this.grid[x * Parameters.SIDE + y].getValue()).append("  ");
                    } else if (this.grid[x * Parameters.SIDE + y].getValue() < 99) {
                        s.append(" ").append(this.grid[x * Parameters.SIDE + y].getValue()).append(" ");
                    } else if (this.grid[x * Parameters.SIDE + y].getValue() < 999) {
                        s.append(this.grid[x * Parameters.SIDE + y].getValue());
                    }
                    s.append("|");
                }
                if (index + 1 < Parameters.SIDE) {
                    s.append("  |");
                }
            }
        }
        s.append("\n");
        for (int i = 0; i < Parameters.SIDE; i++) {
            if (i == 0) {
                s.append("|--------------|");
            } else {
                s.append("  |--------------|");
            }
        }
        return s.toString();
    }

    /**
     * Effectue un mouvement entre deux tuiles
     *
     * @param _a
     *         première coordonnée
     * @param _b
     *         seconde coordonnée
     *
     * @return boolean
     */
    private boolean moveTile(int _a, int _b) {

        // Les case sont vide
        if (grid[_a] == null && grid[_b] == null) {
            return false;
        } // Mouvement de _b vers _a
        else if (grid[_a] == null && grid[_b] != null) {
            grid[_a] = grid[_b];
            grid[_b] = null;

            grid[_a].setX(_a);

            // Flux d'événements [!b]
            if (!this.getListeners().isEmpty()) {
                this.fireMoveEvent(_b, _a);
            }

            return true;
        } // Fusion de _a et _b
        else if (grid[_b] != null && grid[_a].getValue() == grid[_b].getValue() && !fusion) {
            int newValue = grid[_a].getValue() * 2;
            grid[_a].setValue(newValue);
            grid[_b] = null;

            // Flux d'événements [!b]
            if (!this.getListeners().isEmpty()) {
                this.fireFusionEvent(_b, _a);
            }

            if (this.bestValue < newValue) {
                this.bestValue = newValue;
            }

            fusion = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ajoute une nouvelle tuile à la grille.
     * <p>
     * Cette méthode n'est pas utilisée pour obtenir la valeur de la tuile créée, et ne reverra donc pas de valeur.
     * <p>
     * Cette méthode envoie un événement au {@link EventHandler listener} s'il existe.
     *
     * @see #newTile(Callback)
     * @see #initialNewTile()
     * @see #fireNewTileEvent(int)
     */
    void newTile() {
        this.newTile((_arrayPos) -> {
            if (!this.getListeners().isEmpty()) {
                this.fireNewTileEvent(_arrayPos);
            }
            return null;
        });
    }

    /**
     * Ajoute une nouvelle tuile à la grille.
     * <p>
     * La valeur de retour est utilisée par le constructeur {@link #Grid()} afin d'obtenir la meilleure valeur
     * au début de partie de façon plus optimale que de devoir parcourir le tableau des tuiles.
     * <p>
     * Cette méthode stocke l'indice créé afin de l'envoyer au {@link EventHandler listener} une fois créé.
     *
     * @return Si une tuile est créée, retourne sa valeur.
     * Sinon, retourne 0.
     */
    private int initialNewTile() {
        return newTile((_ind) -> {
            this.eventFlowQueue.add(_ind);

            // Utilisé pour le Callback<Integer,Void>, censé retourner un void (ou null).
            return null;
        });
    }

    /**
     * Ajoute une nouvelle tuile à la grille
     *
     * @return
     */
    private int newTile(Callback<Integer, Void> _additionalTasks) {
        ArrayList<Integer> emptyTiles = furnishEmptyTiles();

        int nbOfEmptyTiles = emptyTiles.size();
        if (nbOfEmptyTiles > 0) {

            int[] creationResults = createNewTile(emptyTiles, nbOfEmptyTiles);
            _additionalTasks.call(creationResults[0]);
            return creationResults[1];
        }

        return 0;
    }

    /**
     * @return
     */
    private ArrayList<Integer> furnishEmptyTiles() {
        ArrayList<Integer> emptyTiles = new ArrayList<>();

        for (int index = 0; index < Parameters.SIZE - 1; index++) {
            if (grid[index] == null) {
                emptyTiles.add(index);
            }
        }

        return emptyTiles;
    }

    /**
     * Crée une nouvelle tuile à partir des tuiles vides.
     *
     * @param _emptyTiles
     *         Tableau contenant les indices des tuiles vides de la grille ; fourni par {@link #furnishEmptyTiles()}.
     * @param _nbOfEmptyTiles
     *         Nombre de tuiles vides, calculé à partir de {@code emptyTiles} par {@link #newTile(Callback)}.
     *
     * @return Retourne un tableau d'entier, tel que : <br>
     *      - pos 0: Indice de la tuile créée dans l'attribut {@link #grid}. <br>
     *      - pos 1: Valeur de la tuile créée (retour de {@link #newTile(Callback)}.
     *
     * @see #newTile(Callback)
     */
    private int[] createNewTile(ArrayList<Integer> _emptyTiles, int _nbOfEmptyTiles) {
        int pos = new Random().nextInt(_nbOfEmptyTiles);
        Integer ind = _emptyTiles.get(pos);

        Tile tile = new Tile(pos);
        this.grid[ind] = tile;

        return new int[] { ind, tile.getValue() };
    }

    /// --- ACCESSEURS & MODIFICATEURS --- ///

    /**
     * renvoie l'objet
     *
     * @return
     */
    Tile[] getGrid() {
        return this.grid;
    }

    int getBestValue() {
        return this.bestValue;
    }

    /**
     * Accesseur des éléments de l'attribut {@code grid} (tableau de {@link Tile tuile}).
     *
     * @param _ind
     *         Indice de la tuile dans le tableau.
     *
     * @return la tuile trouvée.
     */
    public Tile getTile(int _ind) {
        return this.grid[_ind];
    }

    // --- Gestion du flux des événements --- //

    /**
     * Lie un {@link EventHandler} à l'objet courant.
     * <p>
     * Vide également la liste d'indices non-envoyés.
     *
     * @param _listener
     *         Listener à lier.
     */
    @Override
    public void addListener(EventHandler<ModelEvent> _listener) {
        super.addListener(_listener);
        this.emptyEventQueue();
    }

    /**
     * Vide la file d'indices de nouvelles tuiles et transmet les événements correspondant.
     * <p>
     * La file se remplit quand le listener n'est pas disponible, mais que des éléments devraient être
     * envoyés.
     */
    private void emptyEventQueue() {
        if (this.eventFlowQueue != null) {
            for (int i : this.eventFlowQueue) {
                this.fireNewTileEvent(i);
            }
            this.eventFlowQueue.clear();
        }
    }

    /**
     * Envoie un événement "Nouvelle tuile" aux listeners.
     *
     * @param _ind
     *         Indice de la nouvelle tuile.
     */
    private void fireNewTileEvent(int _ind) {
        this.fireEvent(ModelNewTileEvent.createInstance(ModelEventSubtype.NEW_TILE_EVENT, _ind));
    }

    /**
     * Envoie un événement "tuile déplacée" aux listeners.
     *
     * @param _initInd
     *         Indice initiale de la tuile.
     * @param _finalInd
     *         Indice finale de la tuile.
     */
    private void fireMoveEvent(int _initInd, int _finalInd) {
        ModelMovedTileEvent event = ModelMovedTileEvent.createInstance(
                ModelEventSubtype.MOVED_TILE_EVENT, _initInd, _finalInd);
        this.fireEvent(event);
    }

    /**
     * Envoie un événement "tuiles fusionnées" aux listeners.
     *
     * @param _ind1
     *         1ère indice (indice initiale / de la tuile n°1).
     * @param _ind2
     *         2ème indice (indice finale / de la tuile n°2).
     */
    private void fireFusionEvent(int _ind1, int _ind2) {
        this.fireEvent(ModelFusionEvent.createInstance(ModelEventSubtype.FUSED_TILES_EVENT, _ind1, _ind2));
    }
}
