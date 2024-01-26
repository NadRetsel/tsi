public class Main{
    public static void main(String[] args){
		String str = "Hello Dan!";
        System.out.println(str);


		System.out.println("---------------");
		byte a = 14;
		char b = 'Y';
		String c = "Hello!";
		float d = 2.5f;
		short e = 307;
		byte f = -1;
		int g = 438000000;
		boolean h = false;

		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		System.out.println(f);
		System.out.println(g);
		System.out.println(h);


		System.out.println("---------------");
		String thanks = "Thank you,";
		String name = "miguel!";
		String order = "Your order number is #";
		int previousOrder = 715;
		
		System.out.println(thanks + " " + name.toUpperCase() + "\n" + order.concat(Integer.toString(++previousOrder)));


		System.out.println("---------------");
		int x = 50;
		int y = 10;
		System.out.println( (x > y ? "Greater" : "Lesser") );

		x = 50;
		y = 50;
		System.out.println( (x == y ? "Equal" : "Unequal") );

		x = 50;
		y = 49;
		System.out.println( (x == y ? "Equal" : "Unequal") );


		System.out.println("---------------");
		int i_def = -10;
		int i = i_def;
		while(i < 6){
			System.out.println(i++);
		}

		i = i_def;
		do {
			if(i < 6) System.out.println(i++);
		}
		while(i < 6);


		System.out.println("---------------");
		String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		for(i = 2; i < 31; i+=3){
			System.out.println("Day " + (i+1) + ": " + days[i%7]);
		}


		System.out.println("---------------");


    }
}