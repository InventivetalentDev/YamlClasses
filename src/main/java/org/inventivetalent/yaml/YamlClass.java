package org.inventivetalent.yaml;

import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class YamlClass {

	public YamlClass() {
	}

	public <T> ConfigurationSection toYaml(T object, ConfigurationSection section) {
		return toYaml(object, object.getClass(), section);
	}

	public <T> ConfigurationSection toYaml(T object, Class<? extends T> type, ConfigurationSection section) {
		for (Field field : type.getFields()) {
			field.setAccessible(true);
			try {
				String name = field.getName();
				Object value = field.get(object);

				YamlName nameAnnotation = field.getAnnotation(YamlName.class);
				if (nameAnnotation != null) {
					name = nameAnnotation.value();
				}

				if (field.getType().isPrimitive() || String.class.isAssignableFrom(field.getType())) {
					section.set(name, value);
				} else {
					ConfigurationSection newSection = section.createSection(name);
					newSection = toYaml(value, newSection);
					section.set(name, newSection);
				}
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
		}
		return section;
	}

	public <T> T fromYaml(ConfigurationSection section, Class<? extends T> type) {
		try {
			T newInstance = type.newInstance();

			for (Field field : type.getFields()) {
				if (Modifier.isFinal(field.getModifiers())) { continue; }
				field.setAccessible(true);
				List<String> names = new LinkedList<>();

				YamlName nameAnnotation = field.getAnnotation(YamlName.class);
				if (nameAnnotation != null) {
					names.add(nameAnnotation.value());
					names.addAll(Arrays.asList(nameAnnotation.alt()));
				} else {
					names.add(field.getName());
				}

				System.out.println(names);

				for (String name : names) {
					if (section.contains(name)) {
						if (field.getType().isPrimitive()) {
							if (Byte.TYPE.isAssignableFrom(field.getType())) {
								field.setByte(newInstance, (byte) section.getInt(name));
							} else if (Short.TYPE.isAssignableFrom(field.getType())) {
								field.setShort(newInstance, (short) section.getInt(name));
							} else if (Integer.TYPE.isAssignableFrom(field.getType())) {
								field.setInt(newInstance, section.getInt(name));
							} else if (Long.TYPE.isAssignableFrom(field.getType())) {
								field.setLong(newInstance, section.getLong(name));
							} else if (Float.TYPE.isAssignableFrom(field.getType())) {
								field.setFloat(newInstance, (float) section.getDouble(name));
							} else if (Double.TYPE.isAssignableFrom(field.getType())) {
								field.setDouble(newInstance, section.getDouble(name));
							} else if (Character.TYPE.isAssignableFrom(field.getType())) {
								field.setChar(newInstance, section.getString(name).charAt(0));
							} else if (Boolean.TYPE.isAssignableFrom(field.getType())) {
								field.setBoolean(newInstance, section.getBoolean(name));
							}
						} else if (String.class.isAssignableFrom(field.getType())) {
							field.set(newInstance, section.getString(name));
						} else {
							Object fromYaml = fromYaml(section.getConfigurationSection(name), field.getType());
							field.set(newInstance, fromYaml);
						}
						break;
					}
				}

			}

			return newInstance;
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

}
