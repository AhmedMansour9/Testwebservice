package gbstracking;

/**
 * Created by HP on 04/05/2018.
 */

public class SendAndGetposition {
    int postion;
    Boolean b;
   public SendAndGetposition(){}
    public SendAndGetposition(int postion,Boolean a) {
        this.postion = postion;
        this.b=a;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public Boolean getB() {
        return b;
    }

    public void setB(Boolean b) {
        this.b = b;
    }
}
