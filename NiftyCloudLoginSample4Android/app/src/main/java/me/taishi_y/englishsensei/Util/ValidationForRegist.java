package me.taishi_y.englishsensei.Util;

/**
 * Created by yamasakitaishi on 2016/02/21.
 */
public class ValidationForRegist {
    /**
     * Created by yamasakitaishi on 2016/02/21.
     *
     * SignUp関係
     */

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public static boolean passwordConfirm(String password,String confirmPassword) {
        //TODO: Replace this with your own logic
        return password.equals(confirmPassword);
    }
}
