package floatingheads.snapclone.objects;

import android.graphics.Bitmap;

/**
 * Created by Mike on 2/26/18.
 */

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Bitmap avatar;

    public User() {
        id = -1;
        firstName = null;
        lastName = null;
        username = null;
        email = null;
        avatar = null;
    }

    public User(int id) {
        this();
        this.id = id;
    }

    public User(int id, String firstName, String lastName) {
        this(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(int id, String firstName, String lastName, String username) {
        this(id, firstName, lastName);
        this.username = username;
    }

    public User(int id, String firstName, String lastName, String username, String email) {
        this(id, firstName, lastName, username);
        this.email = email;
    }

    public User(int id, String firstName, String lastName, String username, String email, Bitmap avatar) {
        this(id, firstName, lastName, username, email);
        this.avatar = avatar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar() {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "User: " + getId() + ", " + getFirstName() + ", " + getLastName() + ", " + getUsername() + ", " + getEmail();
    }
}
