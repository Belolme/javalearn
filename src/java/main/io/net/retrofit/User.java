package java.main.io.net.retrofit;

/**
 * pojo class for user
 * <p/>
 * Created by billin on 16-9-20.
 */
public class User {

    private int id;

    private String name;

    private int sex;

    private String email;

    private String phone;

    private long birthday;

    private String address;

    private String password;

    private String remark;

    /*
    "sex":1, - 1~boy; 2~girl
    "email":"123456aaaa@gmial.com", - 少于60个字符且符合email的规则
    "phone":"1234567-8", - 少于15个字符且符合电话号码的规则
    "birthday":123, - 必填, 内容为时间戳
    "address":"address", - 必填, 长度小于等于120个字符
    "password":"password", - 必填, 长度大于等于6个字符且小于15个字符, 成功后会返回一个加密的字符串
    "remark":"" - 备注名, 可选
    */

    public User(int id, String name, int sex, String email, String phone, long birthday, String address, String password, String remark) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.password = password;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
