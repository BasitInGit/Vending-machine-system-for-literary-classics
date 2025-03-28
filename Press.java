import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
/**
 * A method to print books in batches and store them until they are needed in the vending machine
 */


public class Press{
    private Map <String, List<Book>> shelf = new HashMap <String, List<Book>>();
    private int shelfSize; 
    private String []fileList;
    private Map <String, Integer> edition = new HashMap <String,Integer>();
    /**
     * A constructor method that instantiates the object and sets its attributes
     * @param pathToBooKDir   pushes the file directory
     * @param size          pushes the max shelfsize for each book
     */
    public Press(String pathToBooKDir, int size) {
        shelfSize=size;
        List<Book> books = new ArrayList<Book>();
        try{
            File file = new File (pathToBooKDir);
            fileList = file.list();
            for (String inFile : fileList){
                shelf.put(inFile,books);
                edition.put(inFile, 0);
            }
        }
        catch (Exception e){}

    }
    /**
     * A method to retrieve the catalogue of books in print
     * @return  returns the string catalogue
     */
    public List<String> getCatalogue(){
        List<String> catalogue = new ArrayList<String>();
        try{
            for (String inFile : fileList){
                catalogue.add(inFile);
            }
        }
        catch (Exception e){}
        return catalogue;
    }
    /**
     * A method for request on books 
     * checks if the book is in print and provides the amount requested
     * prints new books for request if needed
     * restocks if it needs to
     * @param bookID  pushes name of book of interest
     * @param amount   pushes the amount of copies desired
     * @return    returns a list of books
     */

    public List<Book> request(String bookID, int amount) {
        int curEdit;                                                 //current edition amount
        List<Book> books = new ArrayList<Book>();
        if(shelf.containsKey(bookID)){                                  //checks if the book request is in print
            curEdit = edition.get(bookID);
            List<Book> bookList = shelf.get(bookID);
            if (curEdit==0){
                for (int i = 1; i <= amount; i++) {
                    try{
                        books.add(print(bookID, i));}
                    catch(IOException ioe){
                        return new ArrayList<>();
                    }
                }
                restock(bookID);
            }
            else if (amount <= curEdit){
                books.addAll(bookList.subList(0, amount));
                bookList.subList(0, amount).clear();
                edition.put(bookID, curEdit - amount);
                shelf.put(bookID,bookList);
            }
            else{
                books=bookList;
                for (int i = curEdit + 1; i <= amount; i++) {
                    try{
                        books.add(print(bookID, i));}
                    catch (Exception ioe){
                        return new ArrayList<>();
                    }
                }
                restock(bookID);
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return books;
    }
    /**
     * A method to restock the books on the shelf 
     * @param bookID  pushes the book to restock
     */
    private void restock(String bookID){
        List<Book>newCopies = new ArrayList<Book>();
        try{
            for (int i = 1; i<=shelfSize;i++){
                newCopies.add(print(bookID,i));}}
        catch (IOException ioe){
        }
        shelf.put(bookID,newCopies);
        edition.put(bookID, newCopies.size());
    }
    /**
     * A method to print new copies of a book
     * @param bookID  pushes the book to print
     * @param edition  pushes the edition of next copy
     * @return    returns a book printed
     * @throws IOException
     */
    protected Book print(String bookID, int edition) throws IOException{
        File file = new File(bookID);
        String lines = Files.readString(file.toPath());

        String t="";
        Pattern title = Pattern.compile("^Title:\\s(.*)");
        Matcher tMatcher = title.matcher(lines);
        if (tMatcher.find()){
            t =  tMatcher.group(1);
        }
        else{
            throw  new IOException();
        }

        String a = "";
        Pattern author = Pattern.compile("^Author:\\s(.*)");
        Matcher aMatcher = author.matcher(lines);
        if (aMatcher.find()){
            a =  aMatcher.group(1);
        }
        else{
            throw new IOException();
        }

        Pattern content = Pattern.compile("START\\sOF\\s.*");
        Matcher cMatcher = content.matcher(lines);
        int ind = cMatcher.end();
        String c = lines.substring(ind,lines.length());
        Book book = new Book(t, a, c, edition);
        return book;
    }

}

