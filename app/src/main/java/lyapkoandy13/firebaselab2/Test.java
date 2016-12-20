package lyapkoandy13.firebaselab2;

/**
 * Created by lyapkoandy13 on 16.12.16.
 */

public class Test {
    private String question;
    private String right_var;
    private String var1;
    private String var2;
    private String var3;
    private String image_url;

    public Test(){

    }

    public Test(String question, String right_var, String var1, String var2, String var3, String image_url) {
        this.question = question;
        this.right_var = right_var;
        this.var1 = var1;
        this.var2 = var2;
        this.var3 = var3;
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRight_var() {
        return right_var;
    }

    public void setRight_var(String right_var) {
        this.right_var = right_var;
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

    public String getVar3() {
        return var3;
    }

    public void setVar3(String var3) {
        this.var3 = var3;
    }
}
