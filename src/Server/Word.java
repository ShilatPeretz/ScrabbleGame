package Server;

import java.util.Arrays;

public class Word {
    private Tile[] tiles;
    private int row, col;
    private boolean vertical;


    public Tile[] Get_Tiles() {return this.tiles;}
    public int Get_row() {return this.row;}
    public int Get_col() {return this.col;}
    public boolean Get_vertical() {return this.vertical;}

    public Word(Tile[] tiles, int row, int col, boolean vertical) {
        this.tiles = new Tile[tiles.length];
        this.tiles=tiles.clone();
        //System.arraycopy(tiles,0,this.tiles,0,tiles.length);
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return row == word.row && col == word.col && vertical == word.vertical && Arrays.equals(tiles, word.tiles);
    }

    public String getWord(){
        StringBuilder sb
                = new StringBuilder("");
        for(Tile tile: tiles){
            sb.append(tile.letter);
        }
        return sb.toString();
    }
}

