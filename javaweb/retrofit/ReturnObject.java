package retrofit;

/**
 * Exception object
 * <p/>
 * Created by billin on 16-9-21.
 */
public class ReturnObject<D> {

    private int status;

    private String message;

    private D data;

    public ReturnObject(int status, String message, D data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
