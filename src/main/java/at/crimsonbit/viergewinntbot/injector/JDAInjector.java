package at.crimsonbit.viergewinntbot.injector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.JDA;

public class JDAInjector {

	public static List<Class> targetClasses;

	public static void registerTarget(Class<?> c) {
		if (targetClasses == null) {
			targetClasses = new ArrayList<>();
		}
		targetClasses.add(c);
	}

	private static void inject(JDA jda, Class<?> c) {
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			if (f.isAnnotationPresent(at.crimsonbit.viergewinntbot.annotation.InjectJDA.class)) {
				f.setAccessible(true);
				try {
					f.set(null, jda);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void InjectJDA(JDA jda) {

		targetClasses.forEach(c -> inject(jda, c));

	}

}
