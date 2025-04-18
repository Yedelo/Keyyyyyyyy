package at.yedel.keyyyyyyyy.launch;



import java.util.ArrayList;
import java.util.Objects;

import org.objectweb.asm.Type;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import static at.yedel.keyyyyyyyy.launch.KeyyyyyyyyLoadingPlugin.keyyyyyyyy;



public class KeyboardTransformer implements IClassTransformer, Opcodes {
	private final AnnotationNode keyyyyyyyyTransformedAnnotation = new AnnotationNode(Type.getDescriptor(KeyyyyyyyyTransformed.class));

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (!Objects.equals(name, "org.lwjgl.input.Keyboard")) return basicClass;
		keyyyyyyyy.info("Found Keyboard class, transforming...");
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		if (classNode.visibleAnnotations == null) {
			classNode.visibleAnnotations = new ArrayList<AnnotationNode>();
		}
		classNode.visibleAnnotations.add(keyyyyyyyyTransformedAnnotation);
		for (MethodNode methodNode: classNode.methods) {
			switch (methodNode.name) {
				case "enableRepeatEvents":
					logTransformation(methodNode.name);
					transformEnableRepeatEvents(methodNode);
					break;
				case "areRepeatEventsEnabled":
					logTransformation(methodNode.name);
					transformAreRepeatEventsEnabled(methodNode);
					break;
				case "isRepeatEvent":
					logTransformation(methodNode.name);
					transformIsRepeatEvent(methodNode);
					break;
			}
		}

		ClassWriter classWriter = new ClassWriter(0);
		classNode.accept(classWriter);
		return classWriter.toByteArray();
	}

	private void transformEnableRepeatEvents(MethodNode methodNode) {
		addKeyyyyyyyyTransformedAnnotation(methodNode);
		insertInsns(
			methodNode,
			new InsnNode(ICONST_1), new VarInsnNode(ISTORE, 0)
		);
	}

	private void transformAreRepeatEventsEnabled(MethodNode methodNode) {
		addKeyyyyyyyyTransformedAnnotation(methodNode);
		insertInsns(
			methodNode,
			new InsnNode(ICONST_1), new InsnNode(IRETURN)
		);
	}

	private void transformIsRepeatEvent(MethodNode methodNode) {
		addKeyyyyyyyyTransformedAnnotation(methodNode);
		insertInsns(
			methodNode,
			new InsnNode(ICONST_0), new InsnNode(IRETURN)
		);
	}

	private void addKeyyyyyyyyTransformedAnnotation(MethodNode methodNode) {
		if (methodNode.visibleAnnotations == null) {
			methodNode.visibleAnnotations = new ArrayList<AnnotationNode>();
		}
		methodNode.visibleAnnotations.add(keyyyyyyyyTransformedAnnotation);
	}

	private void insertInsns(MethodNode methodNode, AbstractInsnNode... insns) {
		InsnList insnList = new InsnList();
		for (AbstractInsnNode insnNode: insns) {
			insnList.add(insnNode);
		}
		methodNode.instructions.insert(insnList);
	}

	private void logTransformation(String methodName) {
		keyyyyyyyy.info("- Found \"{}\" method, transforming...", methodName);
	}
}
