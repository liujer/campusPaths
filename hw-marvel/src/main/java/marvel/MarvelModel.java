package marvel;

import com.opencsv.bean.CsvBindByName;

public class MarvelModel {

    @CsvBindByName
    private String hero;
    @CsvBindByName
    private String book;

    public String getHero() {
        return hero;
    }

    public void setHero(String newHero) {
        this.hero = newHero;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String newBook) {
        this.book = newBook;
    }

}
