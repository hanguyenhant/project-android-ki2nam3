package nam3.baitaplon.letseat.Model;

public class Food {
    private String Name;
    private String Image;
    private String CategoryID;

    public Food()
    {

    }

    public Food(String name, String image, String categoryID) {
        Name = name;
        Image = image;
        CategoryID = categoryID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
}
