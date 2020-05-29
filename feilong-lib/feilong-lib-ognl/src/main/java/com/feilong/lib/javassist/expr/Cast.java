/*
 * Javassist, a Java-bytecode translator toolkit.
 * Copyright (C) 1999- Shigeru Chiba. All Rights Reserved.
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License.  Alternatively, the contents of this file may be used under
 * the terms of the GNU Lesser General Public License Version 2.1 or later,
 * or the Apache License Version 2.0.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 */

package com.feilong.lib.javassist.expr;

import com.feilong.lib.javassist.CannotCompileException;
import com.feilong.lib.javassist.ClassPool;
import com.feilong.lib.javassist.CtBehavior;
import com.feilong.lib.javassist.CtClass;
import com.feilong.lib.javassist.NotFoundException;
import com.feilong.lib.javassist.bytecode.BadBytecode;
import com.feilong.lib.javassist.bytecode.Bytecode;
import com.feilong.lib.javassist.bytecode.CodeAttribute;
import com.feilong.lib.javassist.bytecode.CodeIterator;
import com.feilong.lib.javassist.bytecode.ConstPool;
import com.feilong.lib.javassist.bytecode.MethodInfo;
import com.feilong.lib.javassist.bytecode.Opcode;
import com.feilong.lib.javassist.compiler.CompileError;
import com.feilong.lib.javassist.compiler.Javac;
import com.feilong.lib.javassist.compiler.JvstCodeGen;
import com.feilong.lib.javassist.compiler.JvstTypeChecker;
import com.feilong.lib.javassist.compiler.ProceedHandler;
import com.feilong.lib.javassist.compiler.ast.ASTList;

/**
 * Explicit type cast.
 */
public class Cast extends Expr {
    /**
     * Undocumented constructor.  Do not use; internal-use only.
     */
    protected Cast(int pos, CodeIterator i, CtClass declaring, MethodInfo m) {
        super(pos, i, declaring, m);
    }

    /**
     * Returns the method or constructor containing the type cast
     * expression represented by this object.
     */
    @Override
    public CtBehavior where() { return super.where(); }

    /**
     * Returns the line number of the source line containing the
     * type-cast expression.
     *
     * @return -1       if this information is not available.
     */
    @Override
    public int getLineNumber() {
        return super.getLineNumber();
    }

    /**
     * Returns the source file containing the type-cast expression.
     *
     * @return null     if this information is not available.
     */
    @Override
    public String getFileName() {
        return super.getFileName();
    }

    /**
     * Returns the <code>CtClass</code> object representing
     * the type specified by the cast.
     */
    public CtClass getType() throws NotFoundException {
        ConstPool cp = getConstPool();
        int pos = currentPos;
        int index = iterator.u16bitAt(pos + 1);
        String name = cp.getClassInfo(index);
        return thisClass.getClassPool().getCtClass(name);
    }

    /**
     * Returns the list of exceptions that the expression may throw.
     * This list includes both the exceptions that the try-catch statements
     * including the expression can catch and the exceptions that
     * the throws declaration allows the method to throw.
     */
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }

    /**
     * Replaces the explicit cast operator with the bytecode derived from
     * the given source text.
     *
     * <p>$0 is available but the value is <code>null</code>.
     *
     * @param statement         a Java statement except try-catch.
     */
    @Override
    public void replace(String statement) throws CannotCompileException {
        thisClass.getClassFile();   // to call checkModify().
        @SuppressWarnings("unused")
        ConstPool constPool = getConstPool();
        int pos = currentPos;
        int index = iterator.u16bitAt(pos + 1);

        Javac jc = new Javac(thisClass);
        ClassPool cp = thisClass.getClassPool();
        CodeAttribute ca = iterator.get();

        try {
            CtClass[] params
                = new CtClass[] { cp.get(javaLangObject) };
            CtClass retType = getType();

            int paramVar = ca.getMaxLocals();
            jc.recordParams(javaLangObject, params, true, paramVar,
                            withinStatic());
            int retVar = jc.recordReturnType(retType, true);
            jc.recordProceed(new ProceedForCast(index, retType));

            /* Is $_ included in the source code?
             */
            checkResultValue(retType, statement);

            Bytecode bytecode = jc.getBytecode();
            storeStack(params, true, paramVar, bytecode);
            jc.recordLocalVariables(ca, pos);

            bytecode.addConstZero(retType);
            bytecode.addStore(retVar, retType);     // initialize $_

            jc.compileStmnt(statement);
            bytecode.addLoad(retVar, retType);

            replace0(pos, bytecode, 3);
        }
        catch (CompileError e) { throw new CannotCompileException(e); }
        catch (NotFoundException e) { throw new CannotCompileException(e); }
        catch (BadBytecode e) {
            throw new CannotCompileException("broken method");
        }
    }

    /* <type> $proceed(Object obj)
     */
    static class ProceedForCast implements ProceedHandler {
        int index;
        CtClass retType;

        ProceedForCast(int i, CtClass t) {
            index = i;
            retType = t;
        }

        @Override
        public void doit(JvstCodeGen gen, Bytecode bytecode, ASTList args)
            throws CompileError
        {
            if (gen.getMethodArgsLength(args) != 1)
                throw new CompileError(Javac.proceedName
                        + "() cannot take more than one parameter "
                        + "for cast");

            gen.atMethodArgs(args, new int[1], new int[1], new String[1]);
            bytecode.addOpcode(Opcode.CHECKCAST);
            bytecode.addIndex(index);
            gen.setType(retType);
        }

        @Override
        public void setReturnType(JvstTypeChecker c, ASTList args)
            throws CompileError
        {
            c.atMethodArgs(args, new int[1], new int[1], new String[1]);
            c.setType(retType);
        }
    }
}
