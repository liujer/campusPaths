package marvel;

import com.opencsv.bean.CsvBindByName;

public class MarvelModel {
    @CsvBindByName
    private String character;

    @CsvBindByName
    private String book;

    public String getCharacter() {
        return this.character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getBook() {
        return this.book;
    }

    public void setBook(String book) {
        this.book = book;
    }
}
