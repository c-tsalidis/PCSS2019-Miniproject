public class Card {
    public String type;
    public int name;
    public String colour;

    public Card(){

    }
    public Card(int name, String colour){
        this.name=name;
        this.colour=colour;
    }
    public Card(String type, String colour){
        this.type=type;
        this.colour=colour;
    }

    public void setName(int name) {
        this.name = name;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }

    public int getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
