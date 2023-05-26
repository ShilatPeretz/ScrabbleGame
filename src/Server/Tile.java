package Server;

import java.lang.Math;
import java.util.Objects;

public class Tile {
    public final char letter;
    public final int score;

    private Tile(char letter, int score)
    {
        this.letter=letter;
        this.score=score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    public static class Bag{
        private int[] Tiles_amount = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        private final int[] original_amount = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        private Tile[] Tiles_letters=new Tile[26];
        private static Bag _instance = null;

        private Bag() {
            int[] scores={1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
            for (int i=65; i<91; i++)
            {
                int index=i-65;
                this.Tiles_letters[index]=new Tile((char)i, scores[index]);
            }
        }

        public static Bag getBag()
        {
            if (_instance==null)
                _instance=new Bag();
            return _instance;
        }

        public Tile getRand()
        {
            int rand = (int)(Math.random() * 25);
            if (this.Tiles_amount[rand]!=0)
            {
                return Bag.getBag().getTile(this.Tiles_letters[rand].letter);
            }
            else
            {
                for (int i = (rand + 1)%26; i != rand; i=(i+1)%26)
                {
                    if(this.Tiles_amount[i]!=0)
                        return Bag.getBag().getTile((char)(i+65));
                }
                return null;
            }

        }
        public Tile getTile(char letter)
        {
            int index=(int)letter-65;
            if ((int)letter>=65 && (int)letter<=90)
            {
                if(this.Tiles_amount[index]!=0) {
                    this.Tiles_amount[index]--;
                    return this.Tiles_letters[index];
                }
                else
                    return null;
            }
            else
                return null;
        }
        public void put(Tile tile)
        {
            //בהינתן אריח יש להחזירו לשק ולעדכן את הכמות
            int index=tile.letter -65;
            if((int)tile.letter>=65 && (int)tile.letter<=90 && this.Tiles_amount[index]<this.original_amount[index])
                Bag.getBag().Tiles_amount[index]++;
        }
        public int size()
        {
            int amount=0;
            for(int i=0; i<26; i++)
            {
                amount+=this.Tiles_amount[i];
            }
            return amount;
        }
        public int[] getQuantities()
        {
            int[] amount_copy= this.Tiles_amount.clone();
            return amount_copy;
        }
    }
}
