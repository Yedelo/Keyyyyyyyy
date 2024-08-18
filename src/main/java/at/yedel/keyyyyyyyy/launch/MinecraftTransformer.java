package at.yedel.keyyyyyyyy.launch;



import java.util.Objects;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;



public class MinecraftTransformer implements IClassTransformer, Opcodes {
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (!Objects.equals(transformedName, "net.minecraft.client.Minecraft")) return basicClass;
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		for (MethodNode methodNode: classNode.methods) {
			if (Objects.equals(methodNode.name, "dispatchKeypresses") || Objects.equals(methodNode.name, "func_152348_aa") || Objects.equals(methodNode.name, "Z")) {
				for (AbstractInsnNode insnNode: methodNode.instructions.toArray()) {
					if (insnNode instanceof MethodInsnNode) {
						if (Objects.equals(((MethodInsnNode) insnNode).name, "isRepeatEvent")) {
							InsnList insnList = new InsnList();
							insnList.add(new InsnNode(ICONST_1));
							insnList.add(new MethodInsnNode(INVOKESTATIC, "org/lwjgl/input/Keyboard", "enableRepeatEvents", "(Z)V", false));
							insnList.add(new InsnNode(ICONST_1));
							methodNode.instructions.insertBefore(insnNode, insnList);
							methodNode.instructions.remove(insnNode);
						}
					}
				}
			}
		}

		ClassWriter classWriter = new ClassWriter(0);
		classNode.accept(classWriter);
		return classWriter.toByteArray();
	}
}
