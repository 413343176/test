package cn.com.sky.hyperbola;

import java.lang.reflect.Field;

public class Test {
	
	public final String str = "test";
	
	public void change() throws Exception {

		for (Field f : getClass().getFields()) {
			if (f.getType() == String.class) {
//				System.out.println("改变前：" + f.get(this));
//				f.set(this, "sss");
//				System.out.println("改变后：" + f.get(this));
				System.out.println(f.getModifiers());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Test t = new Test();
		t.change();
	}
}
