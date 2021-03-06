/*
 * Copyright © 2011-2012 Philipp Eichhorn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package lombok.javac.handlers;

import static lombok.core.util.ErrorMessages.*;
import static lombok.javac.handlers.JavacHandlerUtil.deleteAnnotationIfNeccessary;

import com.sun.tools.javac.tree.JCTree.JCAnnotation;

import lombok.*;
import lombok.core.AnnotationValues;
import lombok.core.AST.Kind;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.ast.JavacMethod;
import lombok.javac.handlers.ast.JavacType;

import org.mangosdk.spi.ProviderFor;

@ProviderFor(JavacAnnotationHandler.class)
public class HandleVisibleForTesting extends JavacAnnotationHandler<VisibleForTesting> {

	@Override
	public void handle(final AnnotationValues<VisibleForTesting> annotation, final JCAnnotation source, final JavacNode annotationNode) {
		deleteAnnotationIfNeccessary(annotationNode, VisibleForTesting.class);
		JavacNode mayBeMethod = annotationNode.up();
		if (mayBeMethod.getKind() == Kind.METHOD) {
			JavacMethod method = JavacMethod.methodOf(annotationNode, source);
			if (method.isAbstract()) {
				annotationNode.addError(canBeUsedOnConcreteMethodOnly(VisibleForTesting.class));
				return;
			}
			method.makePrivate();
			method.rebuild();
		} else if (mayBeMethod.getKind() == Kind.TYPE) {
			JavacType type = JavacType.typeOf(annotationNode, source);
			type.makePrivate();
			type.rebuild();
		} else {
			annotationNode.addError(canBeUsedOnClassAndMethodOnly(VisibleForTesting.class));
		}
	}
}
