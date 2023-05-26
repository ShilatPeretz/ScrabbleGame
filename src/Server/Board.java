package Server;


import java.util.ArrayList;

public class Board {
    private enum bonus{TW,DW,TL,DL,RG,STAR}
    private bonus[][] bonuses={
            {bonus.TW, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.TW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.TW},
    {bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG},
    {bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG},
    {bonus.DL, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.DL},
    {bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.RG},
    {bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG},
    {bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG},
    {bonus.TW, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.STAR, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.TW},
    {bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG},
    {bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG},
    {bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.RG},
    {bonus.DL, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.DL},
    {bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG},
    {bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG},
    {bonus.TW, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.TW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.TW}
    };
    private static Board _instance = null;
    private Tile[][] game_board=new Tile[15][15];

    public static Board getBoard()
    {
        if (_instance==null)
            _instance=new Board();
        return _instance;
    }

    public Tile[][] getTiles()
    {
        return this.game_board.clone();
    }

    //check if our word is leaning against another tile
    //check all tiles in word and see if they are creating a true word on the board
    //if not this is breaking the games rules and return false
    public boolean LeaningTile(Word word) //only one needed
    {
        if (word.Get_vertical()) //up -> down
        {
            if(word.Get_row()-1>=0 && this.game_board[word.Get_row()-1][word.Get_col()]!=null)
            {
                return true;
            }
            int last_row=word.Get_row()+word.Get_Tiles().length;
            if(last_row<15 && this.game_board[last_row][word.Get_col()]!=null)
            {
                return true;
            }
            int rows = word.Get_row();
            for (int i = 0; i < word.Get_Tiles().length; i++) {
                if(word.Get_Tiles()[i]==null)
                {
                    rows++;
                    continue;
                }
                int r_col = word.Get_col();
                if(r_col+1<15 && this.game_board[rows][r_col + 1] != null)
                {
                    return true;
                }
                int l_col = word.Get_col();
                if(l_col-1>=0 && this.game_board[rows][l_col-1] != null)
                {
                    return true;
                }
                rows++;
            }
            return false;
        }
        else //left -> right
        {
            if(word.Get_col()-1>=0 && this.game_board[word.Get_row()][word.Get_col()-1]!=null)
            {
                return true;
            }
            int last_col=word.Get_col()+word.Get_Tiles().length;
            if(last_col<15 && this.game_board[word.Get_row()][last_col]!=null)
            {
                return true;
            }
            int cols = word.Get_col();
            for (int i = 0; i < word.Get_Tiles().length; i++) {
                if(word.Get_Tiles()[i]==null)
                {
                    cols++;
                    continue;
                }
                int u_row = word.Get_row();
                if(u_row-1>=0 && this.game_board[u_row - 1][cols] != null)
                {
                    return true;
                }
                int d_row = word.Get_row();
                if(d_row+1<=14 && this.game_board[d_row + 1][cols] != null)
                {
                    return true;
                }
                cols++;
            }
            return false;
        }
    }

    //the function being called when the star tile is empty
    //check if the first placement on the board truly is on the star tile
    public boolean firstPlacement(Word word)
    {
        //first placement can not contain null tiles
        for(int n=0; n<word.Get_Tiles().length;n++) {
            if (word.Get_Tiles()[n] == null)
            {
                return false;
            }
        }
        //show if there was a placement on the star tile
        if(word.Get_vertical()) //up -> down
        {
            if(word.Get_col() ==7 && ((word.Get_row()<7 && (word.Get_row()+word.Get_Tiles().length)>7) || (word.Get_row()==7) || ((word.Get_row()+word.Get_Tiles().length)==7)))
            {
                if(word.Get_row()>=0 && word.Get_row()+word.Get_Tiles().length<=15)
                    return true;
            }
        }
        else //left -> right
        {
            if(word.Get_row() ==7 && ((word.Get_col()<7 && (word.Get_col()+word.Get_Tiles().length)>7) || (word.Get_col()==7) || ((word.Get_col()+word.Get_Tiles().length)==7)))
                if(word.Get_col()>=0 && word.Get_col()+word.Get_Tiles().length<=15)
                    return true;
        }
        return false;
    }

    public boolean boardLegal(Word word) {
        boolean flag=false;
        for(int n=0; n<word.Get_Tiles().length;n++) {
            if (word.Get_Tiles()[n] != null)
            {
                flag=true;
                break;
            }
        }
        if(!flag)
        {
            return false;
        }
        if(this.game_board[7][7]==null) //first placement
        {
            return this.firstPlacement(word);
        }
        if (word.Get_vertical())//up -> down
        {
            int last_tile_row = word.Get_row() + word.Get_Tiles().length;
            int col = word.Get_col();
            if (col > 14 || col < 0)
                return false;
            int tiles_index=0;
            int matching_tiles=0; //do I have any matching tile?
            for (int r = word.Get_row(); r < last_tile_row; r++) {
                if (r > 14 || r < 0)
                    return false;
                if (word.Get_Tiles()[tiles_index]==null && this.game_board[r][col]==null)
                {
                    return false;
                }
               if(word.Get_Tiles()[tiles_index]==null && this.game_board[r][col]!=null)
                    matching_tiles++;
                else if(this.game_board[r][col]!=null && word.Get_Tiles()[tiles_index]!=null )
                    return false;
                tiles_index++;
            }
            if(matching_tiles==0)//if there aren't any matching tiles check if the word is leaning against another tile //and creating a true word
            {
                return this.LeaningTile(word);
            }
            else
            {
                return true;
            }
        }
        else//left -> right
        {
            int last_tile_col = word.Get_col() + word.Get_Tiles().length;
            int row = word.Get_row();
            if (row > 14 || row < 0)
                return false;
            int tiles_index=0;
            int matching_tiles=0;
            for (int c = word.Get_col(); c < last_tile_col; c++) {
                if (c > 14 || c < 0)
                    return false;
                if (word.Get_Tiles()[tiles_index]==null && this.game_board[row][c]==null)
                {
                    return false;
                }
                if(word.Get_Tiles()[tiles_index]==null && this.game_board[row][c]!=null)
                    matching_tiles++;
                else if(this.game_board[row][c]!=null && word.Get_Tiles()[tiles_index]!=null && word.Get_Tiles()[tiles_index].letter!=this.game_board[row][c].letter)
                    return false;
                tiles_index++;
            }
            if(matching_tiles==0)
            {
                return this.LeaningTile(word);
            }
            else
            {
                return true;
            }
        }

    }

    public boolean dictionaryLegal (Word word)
    {
        Dictionary dictionary = new Dictionary();
        return dictionary.query(word.getWord());
    }

    public ArrayList<Word> check_row_colum(Word word) {
        ArrayList<Word> words = new ArrayList<Word>();
        if (word.Get_vertical()) //up -> down
        {
            //check the colum for a legal word
            int u_row = word.Get_row();
            for (; u_row > 0; u_row--) {
                if (this.game_board[u_row - 1][word.Get_col()] == null)
                    break;
            }
            int d_row = word.Get_row() + word.Get_Tiles().length-1;
            for (; d_row < 14; d_row++) {
                if (this.game_board[d_row + 1][word.Get_col()] == null)
                    break;
            }
            int len = d_row - u_row + 1;
            if (len != word.Get_Tiles().length) {
                Tile[] new_letters = new Tile[len];
                int num_u_rows = u_row;
                for (int j = 0; j < len; j++) {
                    if (u_row == word.Get_row()) {
                        for (int y = 0; y < word.Get_Tiles().length; y++) {
                            if (word.Get_Tiles()[y] == null) {
                                new_letters[j] = this.game_board[u_row][word.Get_col()];
                                j++;
                            } else {
                                new_letters[j]=word.Get_Tiles()[y];
                                j++;
                            }
                            u_row++;
                        }
                    }
                    else{
                        new_letters[j] = this.game_board[u_row][word.Get_col()];
                        u_row++;
                    }
                }
                Word new_word1 = new Word(new_letters, num_u_rows, word.Get_col(), true);
                words.add(new_word1);
            }
            return words;
        }
        else//left -> right
        {
            //check the row for a legal word
            int r_col = word.Get_col()+word.Get_Tiles().length-1;
            for (; r_col <14 ; r_col++) {
                if (this.game_board[word.Get_row()][r_col + 1] == null)
                    break;
            }
            int l_col = word.Get_col();
            for (; l_col > 0; l_col--) {
                if (this.game_board[word.Get_row()][l_col - 1] == null)
                    break;
            }
            int len = r_col - l_col +1;
            if(len!=word.Get_Tiles().length)
            {
                Tile[] new_letters = new Tile[len];
                int num_l_col=l_col;
                for (int j = 0; j < len; j++) {
                    if(l_col==word.Get_col())
                    {
                        for(int y=0; y<word.Get_Tiles().length; y++)
                        {
                            if(word.Get_Tiles()[y]==null)
                            {
                                new_letters[j]=this.game_board[word.Get_row()][l_col];
                                j++;
                            }
                            else {
                                new_letters[j]=word.Get_Tiles()[y];
                                j++;
                            }
                            l_col++;
                        }
                    }
                    else{
                        new_letters[j] = this.game_board[word.Get_row()][l_col];
                        l_col++;
                    }
                }
                Word new_word = new Word(new_letters, word.Get_row(), num_l_col, false);
                words.add(new_word);
            }
            return words;
        }
    }

    //after securing that the word is legal.
    //find all the new words that has been created on the board
    public ArrayList<Word> getWords (Word word)
    {
        ArrayList<Word> words = new ArrayList<Word>();
        if (word.Get_vertical()) //up -> down
        {
            Tile[] original_word=new Tile[word.Get_Tiles().length];
            int rows = word.Get_row();
            for (int i = 0; i < word.Get_Tiles().length; i++) {
                if(word.Get_Tiles()[i]==null)
                {
                    original_word[i]=this.game_board[rows][word.Get_col()];
                    rows++;
                    continue;
                }
                else {
                    original_word[i]=word.Get_Tiles()[i];
                }
                int r_col = word.Get_col();
                for (; r_col <14 ; r_col++) {
                    if (this.game_board[rows][r_col + 1] == null)
                        break;
                }
                int l_col = word.Get_col();
                for (; l_col > 0; l_col--) {
                    if (this.game_board[rows][l_col - 1] == null)
                        break;
                }
                int len = r_col - l_col +1;

                if(len!=1)
                {
                    Tile[] new_letters = new Tile[len];
                    int num_l_col=l_col;
                    for (int j = 0; j < len; j++) {
                        if(l_col==word.Get_col())
                        {
                            new_letters[j]=word.Get_Tiles()[i];
                        }
                        else {
                            new_letters[j] = this.game_board[rows][l_col];
                        }
                        l_col++;
                    }
                    Word new_word = new Word(new_letters, rows, num_l_col, false);
                    words.add(new_word);
                }
                rows++;
            }
            //check the colum
            ArrayList<Word> words1 = this.check_row_colum(word);
            if(words1.size()!=0)
            {
                words.add(words1.get(0));
            }
            words.add(new Word(original_word,word.Get_row(),word.Get_col(),word.Get_vertical()));
            return words;
        }
        else //left -> right
        {
            Tile[] original_word=new Tile[word.Get_Tiles().length];
            int cols = word.Get_col();
            for (int i = 0; i < word.Get_Tiles().length; i++) {
                if(word.Get_Tiles()[i]==null)
                {
                    original_word[i]=this.game_board[word.Get_row()][cols];
                    cols++;
                    continue;
                }
                else {
                    original_word[i]=word.Get_Tiles()[i];
                }
                int u_row = word.Get_row();
                for (; u_row >0; u_row--) {
                    if (this.game_board[u_row - 1][cols] == null)
                        break;
                }
                int d_row = word.Get_row();
                for (; d_row <14; d_row++) {
                    if (this.game_board[d_row + 1][cols] == null)
                        break;
                }
                int len = d_row - u_row + 1;
                if(len!=1)
                {
                    Tile[] new_letters = new Tile[len];
                    int num_u_rows = u_row;
                    for (int j = 0; j < len; j++) {
                        if(u_row==word.Get_row())
                        {
                            new_letters[j]=word.Get_Tiles()[i];
                        }
                        else {
                            new_letters[j] = this.game_board[u_row][cols];
                        }
                        u_row++;
                    }
                    Word new_word = new Word(new_letters, num_u_rows, cols, true);
                    words.add(new_word);
                }
                cols++;
            }
            //check the row
            ArrayList<Word> words1 = this.check_row_colum(word);
            if(words1.size()!=0)
            {
                words.add(words1.get(0));
            }
            words.add(new Word(original_word,word.Get_row(),word.Get_col(),word.Get_vertical()));
            return words;
        }
    }
    public int getScore (Word word)
    {
        if(word.Get_vertical()) //up -> down
        {
            int multiply_word = 1;
            int total_score = 0;
            int letter_score=0;
            int row=word.Get_row(), col=word.Get_col();
            for (int i = 0; i < word.Get_Tiles().length; i++)
            {
                letter_score=word.Get_Tiles()[i].score;
                switch (this.bonuses[row][col]) {
                    case STAR:
                        multiply_word *= 2;
                        total_score += letter_score;
                        this.bonuses[7][7]=bonus.RG;
                        break;
                    case TW:
                        multiply_word *= 3;
                        total_score += letter_score;
                        break;
                    case DW:
                        multiply_word *= 2;
                        total_score += letter_score;
                        break;
                    case TL:
                        total_score += letter_score * 3;
                        break;
                    case DL:
                        total_score += letter_score * 2;
                        break;
                    case RG:
                        total_score += letter_score;
                        break;
                }
                row++;

            }
            return total_score*multiply_word;
        }
        else //left -> right
        {
            int multiply_word = 1;
            int total_score = 0;
            int letter_score=0;
            int row=word.Get_row(), col=word.Get_col();
            for (int i = 0; i < word.Get_Tiles().length; i++)
            {
                letter_score=word.Get_Tiles()[i].score;
                switch (this.bonuses[row][col]) {
                    case STAR:
                        multiply_word *= 2;
                        total_score += letter_score;
                        this.bonuses[7][7]=bonus.RG;
                        break;
                    case TW:
                        multiply_word *= 3;
                        total_score += letter_score;
                        break;
                    case DW:
                        multiply_word *= 2;
                        total_score += letter_score;
                        break;
                    case TL:
                        total_score += letter_score * 3;
                        break;
                    case DL:
                        total_score += letter_score * 2;
                        break;
                    case RG:
                        total_score += letter_score;
                        break;
                }
                col++;

            }
            return total_score*multiply_word;
        }
    }
    public void placeWord(Word word)
    {
        if(word.Get_vertical()) //up -> down
        {
            int row=word.Get_row();
            for(int i=0; i<word.Get_Tiles().length; i++)
            {
                if(word.Get_Tiles()[i]==null) {
                    row++;
                    continue;
                }
                this.game_board[row][word.Get_col()]=word.Get_Tiles()[i];
                row++;
            }
        }
        else //left -> right
        {
            int col=word.Get_col();
            for(int i=0; i<word.Get_Tiles().length; i++)
            {
                if(word.Get_Tiles()[i]==null)
                {
                    col++;
                    continue;
                }
                this.game_board[word.Get_row()][col]=word.Get_Tiles()[i];
                col++;
            }
        }
    }
    public int tryPlaceWord (Word word)
    {
        if(this.boardLegal(word)) //place the new word without placing matching tiles
        {
            ArrayList<Word> words=this.getWords(word);
            for(int i=0;i<words.size();i++)
            {
                if(!this.dictionaryLegal(words.get(i)))
                {
                    return 0;
                }
            }
            this.placeWord(word);
            int sum=0;
            for(int i=0;i<words.size();i++)
            {
                sum+=this.getScore(words.get(i));
            }
            return sum;
        }
        else
            return 0;
    }

}
