/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

/**
 *
 * @author user
 */
public class UserInfo {
    public int userId;
    private String name;
    private String password;
    
    public UserInfo(int userid,String name, String password){
        this.userId = userId;
        this.name = name;
        this.password = password;
    }   

    public UserInfo(){
        
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
