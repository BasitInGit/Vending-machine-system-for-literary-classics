/**
 * A method that creates an individual book
 * 
 * 
 */
public class Book{
    private String author;
    private String content;
    private String title;
    private int edition;
    /**
     * A method to get author name
     * @return returns author name
     */

    public String getAuthor(){
        return author;
    }
    /**
     * A method to retrieve the author of the book
     * @return the author string
     */
    public String getContent(){
        return content;
    }
    /**
     * A method to retrieve the content of the book
     * @return  the content string
     */
    public String getTitle(){
        return title;
    }
    /**
     * A method to retieve the title of the book
     * @return the title string
     */
    public int getEdition(){
        return edition;
    }
    /**
     * A constructor method to set the attribute of the book
     * @param t   a string containing the title of the book
     * @param a      a string containing the author of the book
     * @param c   a string containing the content of the book
     * @param e  an integer containing the edition of the book
     */
    public Book(String t, String a, String c, int e){ 
        author = a;
        title = t;
        content = c;
        edition = e;
    }
    /**
     * a method to retrieve the page number of the book
     * @return  the integer value of the book
     */
    public int getPages (){
        int pages = content.length();
        return pages;
    }
    /**
     * a method to display all the attributes of the string and their corresponding stored information 
     * @return a string containg the statement
     */
    public String toString(){
        String cons = "Title: "+title+"\n"+"Author: "+author+"\n"+"Edition: "+edition+"\n";
        return cons;
    }
}