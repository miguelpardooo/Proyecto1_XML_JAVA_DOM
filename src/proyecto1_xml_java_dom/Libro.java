package proyecto1_xml_java_dom;

public class Libro {

    private String id, author, title, genre, price, publish_date, description;

    public Libro(String id, String author, String title, String genre, String price, String publish_date, String description) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.publish_date = publish_date;
        this.description = description;
    }

    public Libro() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\t<book id=\"" + this.id + "\">"
                + "\n\t\t<author>" + this.author + "</author>"
                + "\n\t\t<title>" + this.title + "</title>"
                + "\n\t\t<genre>" + this.genre + "</genre>"
                + "\n\t\t<price>" + this.price + "</price>"
                + "\n\t\t<publish_date>" + this.publish_date + "</publish_date>"
                + "\n\t\t<description>" + this.description + "</description>"
                + "\t\n</book>";
    }
}
