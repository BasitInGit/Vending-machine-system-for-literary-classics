import java.util.ArrayList;
import java.util.List;
/**
 * A method for storing books and selling them to customers
 * 
 */
public class VendingMachine{
    private List<Book> shelf = new ArrayList<Book>();
    private double locationFactor;
    private int cassette;
    private int safe;
    private String password;
    /**
     * A constructor method for initialing the method and setting its attributes
     * @param lF   sets the locatiin factor of the vending machine
     * @param p     sets the password for accessing the safe and restocking the vending machine
     */
    public VendingMachine(double lF, String p){ 
        locationFactor = lF;
        password = p;
        cassette = 0;
        safe = 0;
    }
    /**
     * A method to get the current cassette value
     * @return an integer with the cassette value
     */
    public int getCassette(){
        return cassette;
    }
    /**
     * A method that checks if the right amount and type of coin has been inserted
     * @param coin  an integer cointaing inserted coin
     */
    public void insertCoin(int coin){
        int [] coins = {1,2,5,10,20,50,100,200};
        int i = 0;
        boolean found = false;
        while(i<coins.length && found == false){
            if (coin == coins[i]){
                cassette+=coin;
                found = true;
            }
            i++;
        }
        if (found == false){
            throw new IllegalArgumentException ();
        }
    }
    /**
     * a method that cancels sale and return coins if amount inserted doesn't match price
     * @return the inserted coins
     */
    public int cancel(){
        int ret = cassette;
        cassette=0;
        return ret;
    }
    /**
     * A method that restocks the vending machine upon confirmation of password
     * @param books  books to be placed in vending machine
     * @param psd    password typed in
     */
    public void restock(List<Book> books, String psd){
        if (password.equals(psd)){
            for (int i = 0; i <books.size();i++){
                shelf.add(books.get(i));
            }
        }
        else {
            throw new InvalidPasswordException("invalid Password");
        }
    }
    /**
     * A method to empty the safe
     * @param psd    A password typed in
     * @return the safe content
     */
    public int emptySafe(String psd){
        if (password.equals(psd)){
            int ret = safe; 
            safe = 0;
            return ret;
        }
        else{
            throw new InvalidPasswordException("invalid Password");
        }
    }
    /**
     * A method to retrieve the catalouge of books on the vending machine
     * @return   returns the catalogue
     */
    public List<String> getCatalogue(){
        List<String> catalogue = new ArrayList<String>();
        for (int i = 0; i<shelf.size();i++){
            catalogue.add(shelf.get(i).toString());
        }
        return catalogue;
    }
    /**
     * A method to retrieve the price of the book
     * @param index   pushes the location of book of interest
     * @return     the price value
     */
    public int getPrice(int index){
        if (index<shelf.size()){
            double price = shelf.get(index).getPages() * locationFactor;
            int precice = (int) Math.ceil(price);
            return precice;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }
    /**
     * A method to buy the book on vending machine
     * @param index pushes book of interest
     * @return    returns book of interest
     */
    public Book buyBook(int index){
        if (index>=shelf.size()){
            throw new IndexOutOfBoundsException();
        }
        int price = getPrice(index);

        if (price<=cassette){
            Book c = shelf.get(index);
            shelf.remove(index);
            cassette-=price;
            safe+=price;
            return c;
        }
        else {
            throw new CassetteException("Insert coins");
        }
    }
}