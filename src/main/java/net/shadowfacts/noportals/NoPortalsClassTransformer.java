package net.shadowfacts.noportals;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author shadowfacts
 */
public class NoPortalsClassTransformer implements IClassTransformer {

	private static final List<String> classes = Arrays.asList("net.minecraft.entity.boss.EntityDragon");

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		boolean isObfuscated = !name.equals(transformedName);
		int index = classes.indexOf(transformedName);
		return index != -1 ? transform(index, bytes, isObfuscated) : bytes;
	}

	private static byte[] transform(int index, byte[] bytes, boolean isObfuscated) {
		System.out.println("[NoPortals] Transforming " + classes.get(index));
		try {

			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(bytes);
			classReader.accept(classNode, 0);

			switch (index) {
				case 0:
					transformEntityDragon(classNode, isObfuscated);
					break;
			}

			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(classWriter);
			return classWriter.toByteArray();

		} catch (Exception e) {
			System.err.println("There was a problem transforming " + classes.get(index));
			e.printStackTrace();
		}

		return bytes;
	}

	private static void transformEntityDragon(ClassNode entityDragonClass, boolean isObfuscated) {
		final String ON_DEATH_UPDATE = isObfuscated ? "aF" : "onDeathUpdate";
		final String ON_DEATH_UPDATE_DESC = "()V";
		final String CREATE_ENDER_PORTAL = isObfuscated ? "b" : "createEnderPortal";
		final String CREATE_ENDER_PORTAL_DESC = "(II)V";

		entityDragonClass.methods.stream()
				.filter(method -> method.name.equals(ON_DEATH_UPDATE) && method.desc.equals(ON_DEATH_UPDATE_DESC))
				.forEach(method -> {

					AbstractInsnNode targetNode = null;

					/*
					Removing:

					ALOAD 0
					ALOAD 0
					GETFIELD net/minecraft/entity/boss/EntityDragon.posX : D
					INVOKESTATIC net/minecraft/util/MathHelper.floor_double (D)I
					ALOAD 0
					GETFIELD net/minecraft/entity/boss/EntityDragon.posZ : D
					INVOKESTATIC net/minecraft/util/MathHelper.floor_double (D)I
					INVOKESPECIAL net/minecraft/entity/boss/EntityDragon.createEnderPortal (II)V
					 */

					for (AbstractInsnNode instruction : method.instructions.toArray()) {
						if (instruction.getOpcode() == INVOKESPECIAL) {
							MethodInsnNode methodInsnNode = (MethodInsnNode)instruction;
							if (methodInsnNode.name.equals(CREATE_ENDER_PORTAL) && methodInsnNode.desc.equals(CREATE_ENDER_PORTAL_DESC)) {
								targetNode = instruction;
								break;
							}
						}
					}

					if (targetNode != null) {

						for (int i = 0; i < 8; i++) {
							targetNode = targetNode.getPrevious();
							method.instructions.remove(targetNode.getNext());
						}

						/*
						Add call to spawn dragon egg

						ALOAD 0
						INVOKESTATIC net/shadowfacts/noportals/Utils.spawnEgg (Lnet/minecraft/entity/Entity;)V
						 */
						final String SPAWN_EGG_DESC = isObfuscated ? "(Lsa;)V" : "(Lnet/minecraft/entity/Entity;)V";

						InsnList toInsert = new InsnList();
						toInsert.add(new VarInsnNode(ALOAD, 0));
						toInsert.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(Utils.class), "spawnEgg", SPAWN_EGG_DESC, false));

						method.instructions.insertBefore(targetNode.getNext(), toInsert);

					} else {
						System.err.println("Target node (INVOKESPECIAL createEnderPortal (II)V) could not be found in EntityDragon.onDeathUpdate");
					}

				});

	}

}
