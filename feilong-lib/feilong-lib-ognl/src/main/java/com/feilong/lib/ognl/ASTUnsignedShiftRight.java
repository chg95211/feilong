//--------------------------------------------------------------------------
//	Copyright (c) 1998-2004, Drew Davidson and Luke Blanshard
//  All rights reserved.
//
//	Redistribution and use in source and binary forms, with or without
//  modification, are permitted provided that the following conditions are
//  met:
//
//	Redistributions of source code must retain the above copyright notice,
//  this list of conditions and the following disclaimer.
//	Redistributions in binary form must reproduce the above copyright
//  notice, this list of conditions and the following disclaimer in the
//  documentation and/or other materials provided with the distribution.
//	Neither the name of the Drew Davidson nor the names of its contributors
//  may be used to endorse or promote products derived from this software
//  without specific prior written permission.
//
//	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
//  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
//  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
//  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
//  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
//  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
//  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
//  OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
//  AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
//  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
//  THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
//  DAMAGE.
//--------------------------------------------------------------------------
package com.feilong.lib.ognl;

/**
 * @author Luke Blanshard (blanshlu@netscape.net)
 * @author Drew Davidson (drew@ognl.org)
 */
class ASTUnsignedShiftRight extends NumericExpression{

    /**
     * 
     */
    private static final long serialVersionUID = -8111586890804900475L;

    public ASTUnsignedShiftRight(int id){
        super(id);
    }

    public ASTUnsignedShiftRight(OgnlParser p, int id){
        super(p, id);
    }

    @Override
    protected Object getValueBody(OgnlContext context,Object source) throws OgnlException{
        Object v1 = _children[0].getValue(context, source);
        Object v2 = _children[1].getValue(context, source);
        return OgnlOps.unsignedShiftRight(v1, v2);
    }

    @Override
    public String getExpressionOperator(int index){
        return ">>>";
    }

    @Override
    public String toGetSourceString(OgnlContext context,Object target){
        String result = "";

        try{

            String child1 = OgnlRuntime.getChildSource(context, target, _children[0]);
            child1 = coerceToNumeric(child1, context, _children[0]);

            String child2 = OgnlRuntime.getChildSource(context, target, _children[1]);
            child2 = coerceToNumeric(child2, context, _children[1]);

            Object v1 = _children[0].getValue(context, target);
            int type = OgnlOps.getNumericType(v1);

            if (type <= NumericTypes.INT){
                child1 = "(int)" + child1;
                child2 = "(int)" + child2;
            }

            result = child1 + " >>> " + child2;

            context.setCurrentType(Integer.TYPE);
            context.setCurrentObject(getValueBody(context, target));

        }catch (Throwable t){
            throw OgnlOps.castToRuntime(t);
        }

        return result;
    }
}
