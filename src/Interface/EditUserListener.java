package Interface;

/**
 * Created by Alander on 2017/12/14.
 */
public interface EditUserListener {
    void resetPassword();
    void confirmEdit(String id, String name, String phone);
}
