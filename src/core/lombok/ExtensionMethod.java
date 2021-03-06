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
package lombok;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.*;

/**
 * Extension methods enable you to "add" methods to existing types without creating a new derived type, recompiling, or
 * otherwise modifying the original type. Extension methods are a special kind of static method, but they are called as
 * if they were instance methods on the extended type.
 * <p>
 * Before:
 * 
 * <pre>
 * &#064;ExtensionMethod({ java.util.Arrays.class })
 * class Example {
 * 	private void example() {
 * 		long[] values = new long[] { 2, 5, 7, 9 };
 * 		values.copyOf(3).sort();
 * 	}
 * }
 * </pre>
 * 
 * After:
 * 
 * <pre>
 * class Example {
 * 	private void example() {
 * 		long[] values = new long[] { 2, 5, 7, 9 };
 * 		java.util.Arrays.sort(java.util.Arrays.copyOf(values, 3));
 * 	}
 * }
 * </pre>
 */
@Target(TYPE)
@Retention(SOURCE)
public @interface ExtensionMethod {
	/** All types whose static methods will be exposed as extension methods. */
	Class<?>[] value();

	boolean suppressBaseMethods() default true;
}
