public class User {

	public Integer id;
	public String name;
	public String address;

	public User(Integer id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}

	@Override
	public String toString() {
		return "User(" + id + "," + name + "," + address + ")";
	}

}