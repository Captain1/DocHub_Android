package me.android.dochub.models;

public class DocItem {
    private int id, user_id;
    private String title, topic, description, license,filepicker_url, created_at;
    private String email = "frankwdy11@gmail.com";
    private String image = filepicker_url + "convert?";
    private String profilePic = "https://secure.gravatar.com/avatar/";

    public DocItem() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLicense() {return license;}
    public void setLicense(String license) {
        this.license = license;
    }

    public String getProfilePic() {return profilePic;}
    public void setProfilePic(String profilePic) {this.profilePic = profilePic;}

    public String getImage() {return image;}
    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFilepicker_url() { return filepicker_url; }
    public void setFilepicker_url(String filepicker_url) { this.filepicker_url = filepicker_url; }

    public String getEmail() {return email;}
    public void setEmail(String email) {
        this.email = email;
    }

}
