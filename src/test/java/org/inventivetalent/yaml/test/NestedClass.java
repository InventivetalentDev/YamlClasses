package org.inventivetalent.yaml.test;

import org.inventivetalent.yaml.YamlName;

public class NestedClass {

	@YamlName(value = "a",alt = "rootA") public String rootA;
	@YamlName(value = "b",alt = "rootB") public int    rootB;
	@YamlName(value = "c",alt = "rootC") public double rootC;

	public TestClass childA;
	public TestClass childB;

	@Override
	public String toString() {
		return "NestedClass{" +
				"rootA='" + rootA + '\'' +
				", rootB=" + rootB +
				", rootC=" + rootC +
				", childA=" + childA +
				", childB=" + childB +
				'}';
	}
}
