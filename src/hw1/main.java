package hw1;

public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Order o1 = makeOrder("Daejeon");
		Order o2 = makeOrder(null);
		FOption<Order> f1 = FOption.of(o1);
		FOption<Order> f2 = FOption.of(o2);
		System.out.println(f1
				.map(Order::getMember)
				.map(Member::getAddress)
				.map(Address::getCity)
				.getOrElse("Seoul")
				);
		System.out.println(f2
				.map(Order::getMember)
				.map(Member::getAddress)
				.map(Address::getCity)
				.getOrElse("Seoul")
				);
	}
	private static Order makeOrder(String city) {
		// TODO Auto-generated method stub
		return new Order(new Member(new Address(city)));
	}
}

class Order{
	private Member member;
	public Order(Member m) {
		this.member = m;
	}
	public Member getMember() {
		return this.member;
	}
}

class Member{
	private Address address;
	public Member(Address a) {
		this.address = a;
	}
	public Address getAddress() {
		return this.address;
	}
}

class Address{
	private String city;
	public Address(String c) {
		this.city = c;
	}
	public String getCity() {
		return this.city;
	}
}

