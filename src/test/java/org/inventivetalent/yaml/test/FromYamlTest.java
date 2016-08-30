package org.inventivetalent.yaml.test;

import org.bukkit.configuration.file.YamlConfiguration;
import org.inventivetalent.yaml.YamlClass;
import org.junit.Test;

import java.io.InputStreamReader;

import static  org.junit.Assert.*;

public class FromYamlTest {

	@Test
	public void simpleFromYamlTest() {
		YamlClass yamlClass = new YamlClass();

		YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(FromYamlTest.class.getResourceAsStream("/simpleFromYaml.yml")));

		TestClass testClass = yamlClass.fromYaml(config, TestClass.class);
		System.out.println(testClass);

		assertEquals("Hi!", testClass.a);
		assertEquals("Bye!", testClass.b);
		assertEquals(123, testClass.c);
		assertEquals(123.456, testClass.d,0);
		assertEquals(5432.1f, testClass.f, 0);
	}

	@Test
	public void nestedFromYamlTest() {
		YamlClass yamlClass = new YamlClass();

		YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(FromYamlTest.class.getResourceAsStream("/nestedFromYaml.yml")));

		NestedClass nestedClass = yamlClass.fromYaml(config, NestedClass.class);
		System.out.println(nestedClass);

		assertEquals("HI!", nestedClass.rootA);
		assertEquals(1337, nestedClass.rootB);

		assertEquals("Hi", nestedClass.childA.a);
		assertEquals("Bye", nestedClass.childA.b);

		assertEquals("Bye", nestedClass.childB.a);
		assertEquals("Hi!", nestedClass.childB.b);
	}

}
