package Util;

import Matrix.Matrix;

public class GameTree {
    Leaf root;

    public GameTree () {
        this.root = new Leaf(new Matrix());
    }

    public void sortIn (Matrix[] gameList) {
        Leaf root = this.root;
        int i = 0;
        while (i < gameList.length && gameList[i] != null) {
            Matrix insert = gameList[i];

            for (int j = 0; j < root.children.length; j++) {
                if (root.children[j] == null) {
                    Leaf newLeaf = new Leaf(insert);
                    root.children[j] = newLeaf;
                    root = newLeaf;
                    break;
                }
                if (insert.equals(root.children[j].data)) {
                    root = root.children[j];
                    break;
                }
            }
            i++;
        }
    }

    public class Leaf{
        Matrix data;
        Leaf[] children;

        Leaf(Matrix data) {
            this.data = data;
            this.children = new Leaf[9];
            
        }
    }
}
