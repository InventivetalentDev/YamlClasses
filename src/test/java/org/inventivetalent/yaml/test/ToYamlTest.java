package org.inventivetalent.yaml.test;

import org.bukkit.configuration.file.YamlConfiguration;
import org.inventivetalent.yaml.YamlClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ToYamlTest {

	@Test
	public void simpleToYamlTest() throws IOException {
		YamlClass yamlClass = new YamlClass();
		YamlConfiguration config = new YamlConfiguration();

		TestClass testClass = new TestClass();
		testClass.a = "Hi";
		testClass.b = "Bye";
		testClass.c = 123;
		testClass.d = 123.456;
		testClass.f = 5432.1f;

		yamlClass.toYaml(testClass, config);

		assertEquals("Hi", config.getString("a"));
		assertEquals("Bye", config.getString("b"));
		assertEquals(123, config.getInt("c"));
		assertEquals(123.456, config.getDouble("d"), 0);
		assertEquals(5432.1f, config.getDouble("f"), 0);

		File file = new File("/simpleToYamlOut.yml");
		if (!file.exists()) {
			file.createNewFile();
		}
		config.save(file);
	}

	@Test
	public void nestedToYamlTest() throws IOException {
		YamlClass yamlClass = new YamlClass();
		YamlConfiguration config = new YamlConfiguration();

		NestedClass nestedClass = new NestedClass() {
			{
				rootA = "HI!";
				rootB = 1337;
				rootC = 73.31;
				childA = new TestClass() {
					{
						a = "Hi";
						b = "Bye";
						c = 123;
						d = 123.456;
						f = 5432.1f;
					}
				};
				childB = new TestClass() {
					{
						a = "Bye";
						b = "Hi!";
						c = 123;
						d = 123.456;
						f = 5432.1f;
					}
				};
			}
		};

		yamlClass.toYaml(nestedClass, config);

		assertEquals("HI!", config.getString("a"));

		assertEquals("Hi", config.getString("childA.a"));
		assertEquals("Bye", config.getString("childA.b"));

		assertEquals("Bye", config.getString("childB.a"));
		assertEquals("Hi!", config.getString("childB.b"));

		File file = new File("/nestedToYamlOut.yml");
		if (!file.exists()) {
			file.createNewFile();
		}
		config.save(file);
	}

}
