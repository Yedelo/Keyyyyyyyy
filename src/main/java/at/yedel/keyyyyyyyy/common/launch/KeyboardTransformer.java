package at.yedel.keyyyyyyyy.common.launch;



import java.util.ArrayList;

import org.objectweb.asm.Type;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import static at.yedel.keyyyyyyyy.common.KeyyyyyyyyConstants.keyyyyyyyy;



public class KeyboardTransformer implements Opcodes {
	private KeyboardTransformer() {}

	private static final KeyboardTransformer instance = new KeyboardTransformer();

	public static KeyboardTransformer getInstance() {
		return instance;
	}

	private final AnnotationNode keyyyyyyyyTransformedAnnotation = new AnnotationNode(Type.getDescriptor(KeyyyyyyyyTransformed.class));

	public void transform(ClassNode classNode) {
		keyyyyyyyy.info("Found Keyboard class, transforming...");
		if (classNode.visibleAnnotations == null) {
			classNode.visibleAnnotations = new ArrayList<>();
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
			methodNode.visibleAnnotations = new ArrayList<>();
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
