package at.yedel.keyyyyyyyy.common.launch;



import java.util.ArrayList;

import at.yedel.keyyyyyyyy.common.KeyyyyyyyyLogger;
import org.objectweb.asm.Type;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;



public class KeyboardTransformer implements Opcodes {
	private KeyboardTransformer() {}

	private static final KeyboardTransformer instance = new KeyboardTransformer();

	public static KeyboardTransformer getInstance() {
		return instance;
	}

	private final AnnotationNode keyyyyyyyyTransformedAnnotation = new AnnotationNode(Type.getDescriptor(KeyyyyyyyyTransformed.class));

	public void transform(ClassNode classNode) {
		KeyyyyyyyyLogger.log("Found Keyboard class, transforming...");
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

	/**
	 * Transforms the given methodNode to always set the first local variable to 1 or true.
	 * <p>
	 * Example: {@code StaticClass.setBoolean(bool)} -> {@code StaticClass.setBoolean(true)}
	 * @param methodNode a method node to transform.
	 *                   This method node should represent a static method where the first parameter is a number or boolean.
	 */
	private void transformEnableRepeatEvents(MethodNode methodNode) {
		addKeyyyyyyyyTransformedAnnotation(methodNode);
		insertInsns(
			methodNode,
			new InsnNode(ICONST_1), new VarInsnNode(ISTORE, 0)
		);
	}

	/**
	 * Transforms the given methodNode to always return true.
	 * <p>
	 * Example: {@code boolean bool = AnyClass.getBoolean()} -> {@code boolean bool = true}
	 * @param methodNode a method node to transform.
	 *                   This method node should represent a method that returns a number or boolean.
	 */
	private void transformAreRepeatEventsEnabled(MethodNode methodNode) {
		addKeyyyyyyyyTransformedAnnotation(methodNode);
		insertInsns(
			methodNode,
			new InsnNode(ICONST_1), new InsnNode(IRETURN)
		);
	}

	/**
	 * Transforms the given methodNode to always return false.
	 * <p>
	 * Example: boolean bool = {@code AnyClass.getBoolean()} -> {@code boolean bool = false}
	 * @param methodNode a method node to transform.
	 *                   This method node should represent a method that returns a number or boolean.
	 */
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
		KeyyyyyyyyLogger.log("- Found \"" + methodName + "\" method, transforming...");
	}
}
